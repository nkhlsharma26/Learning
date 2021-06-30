export class UserRegistrationModel {
      public fullName: String;
      public password: String;
      public email: String;
      public username: String;

      constructor(fullName: String, password: String, email: String, username: String){
            this.username = username;
            this.fullName = fullName;
            this.email = email;
            this.password = password;
      }
}