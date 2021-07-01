export class LoginResponse{
      public accessToken: string;
      public tokenType: string;

      constructor(accessToken: string, tokenType: string ){
            this.accessToken = accessToken;
            this.tokenType = tokenType;
      }
}