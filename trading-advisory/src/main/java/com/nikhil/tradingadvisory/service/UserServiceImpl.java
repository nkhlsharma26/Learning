package com.nikhil.tradingadvisory.service;

import com.nikhil.tradingadvisory.emailService.EmailSender;
import com.nikhil.tradingadvisory.exception.AppException;
import com.nikhil.tradingadvisory.model.*;
import com.nikhil.tradingadvisory.model.payload.ApiResponse;
import com.nikhil.tradingadvisory.model.payload.JwtAuthenticationResponse;
import com.nikhil.tradingadvisory.model.payload.LoginRequest;
import com.nikhil.tradingadvisory.model.payload.SignUpRequest;
import com.nikhil.tradingadvisory.repository.RoleRepository;
import com.nikhil.tradingadvisory.samco.repository.SamcoUserRepository;
import com.nikhil.tradingadvisory.repository.UserRepository;
import com.nikhil.tradingadvisory.samco.Abstraction.SamcoAuthService;
import com.nikhil.tradingadvisory.security.JwtTokenProvider;
import com.nikhil.tradingadvisory.service.Abstraction.UserService;
import com.nikhil.tradingadvisory.util.EncryptorDecryptor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SamcoUserRepository samcoUserRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SamcoAuthService samcoAuthService;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private EncryptorDecryptor encryptorDecryptor;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Value("${serverUrl}")
    private String serverUrl;

    public ResponseEntity UserSignup(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user account
        User user = new User(signUpRequest.getFullName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), new Date(), false);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        //Create confirmation token
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // send confirmation token
        String link = serverUrl+"/confirm/" + token;
        emailSender.sendRegistrationEmail(
                signUpRequest.getEmail(),
                emailSender.buildConfirmationEmail(signUpRequest.getFullName(), link));


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    public ResponseEntity UserSignin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(), "").get().getIsActive()) {
            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        } else {
            return ResponseEntity.ok("User is not active.");
        }
    }

    @Transactional
    public String confirmToken(String token, Map<String, String> samcoUserDetails) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }


        SamcoUserDetails details = new SamcoUserDetails(samcoUserDetails.get("userId"), samcoUserDetails.get("password"), samcoUserDetails.get("yob"));

        String sessionToken = samcoAuthService.authorizeUser(details);
        if(sessionToken != null){
            Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
            details.setId(id);
            /*try {
                PublicKey publicKey = encryptorDecryptor.getPublic("src/main/resources/KeyPair/publicKey");
                String password = details.getPassword();
                String encryptedPassword = encryptorDecryptor.encryptText(password, publicKey);
                details.setPassword(encryptedPassword);
            }catch (Exception e) {
                e.printStackTrace();
            }*/
            samcoUserRepository.save(details);

            userRepository.updateSamcoDetailsId(confirmationToken.getUser().getUsername(),id);
            confirmationTokenService.setConfirmedAt(token);

            enableUser(confirmationToken.getUser().getEmail());
            return "confirmed";
        }
        else {
            throw new IllegalArgumentException("Incorrect samco credentials!");
        }
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
