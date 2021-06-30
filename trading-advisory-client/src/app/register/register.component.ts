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

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private registrationService: RegistrationService) {   }

  ngOnInit() {
  }

  handleRegistration() {
     this.registrationService.registrationService(this.fullName, this.password, this.email, this.email).subscribe((result)=> {
     this.invalidRegistration = false;
     this.registrationSuccess = true;
     this.successMessage = 'Registration Successful.';
     this.router.navigate(['/hello-world']);
     }, () => {
       this.invalidRegistration = true;
       this.registrationSuccess = false;
     });
  }
}
