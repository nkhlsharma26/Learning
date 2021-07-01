import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { RegistrationService } from './registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  fullName: string;
  email: string
  password : string;
  confirmPassword : string;
  errorMessage = 'Invalid Credentials';
  successMessage: string;
  invalidRegistration = false;
  registrationSuccess = false;
  clicked = false;
  loading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private registrationService: RegistrationService) {   }

  ngOnInit() {
  }

  handleRegistration() {
     this.clicked = true;
     this.loading = true;
     if(this.validatePasswordAndEmail(this.password, this.confirmPassword, this.email)){
      this.registrationService.registrationService(this.fullName, this.password, this.email, this.email).subscribe((result)=> {
        this.loading = false;
      this.invalidRegistration = false;
      this.registrationSuccess = true;
      this.successMessage = 'Registration Successful.';
      this.router.navigate(['/app-register-result']);
      }, () => {
        this.clicked = false;
        this.loading = false;
        this.invalidRegistration = true;
        this.registrationSuccess = false;
      });
     }
     
  }

  validatePasswordAndEmail(password: String, confirmPassword: String, email: String): boolean{
      if(password != confirmPassword){
        this.invalidRegistration = true;
        this.loading = false;
        this.errorMessage = 'Password and confirm password do not match!'
        this.clicked = false;
        return false;
      }
      if(email.length<=0 || !email.includes(".com") || !email.includes("@")){
        this.invalidRegistration = true;
        this.loading = false;
        this.errorMessage = 'Email is not valid!'
        this.clicked = false;
        return false;
      }
      return true;
  }
}
