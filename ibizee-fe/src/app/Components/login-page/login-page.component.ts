import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../Services/user.service';
import { UserResponse } from '../../Models/UserResponse';
import { delay } from 'rxjs';
import { Router } from '@angular/router';
import { Login } from '../../Models/Login';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {

  constructor(private userService: UserService, private router: Router) { }

  // global variables
  login: Login = new Login;
  userResponse: UserResponse = new UserResponse;
  alertStatus: boolean = false
  alertClass: string = "alert alert-danger";
  alertText: string = "Something went wrong"

  // this functions works when started to changing fields in the form
  registerOnChange() {
    this.alertStatus = false
  }

  // this function works when clicking on the login button
  userLogin() {
    this.alertStatus = true

    if (this.login.userEmail == "") {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Please enter email"
    }
    else if (this.login.userPassword == "") {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Password cannot be empty"
    }
    else {
      try {
        this.userService.loginUser(this.login).subscribe((result: any) => {
          this.userResponse = result;

          if (this.userResponse.responseStatus == 200) {
            this.alertStatus = true
            this.alertClass = "alert alert-success"
            this.alertText = "Login successfull"

            console.log(result)

            // saving the user id in local storage
            localStorage.setItem('userId', result[0]);

            //
            console.log("userId local : " + localStorage.getItem('userId'));

            this.router.navigate(['main-pane/home'])
          }
          else {
            this.alertStatus = true
            this.alertClass = "alert alert-danger"
            this.alertText = this.userResponse.responseDescription
          }
        });
      } catch (error) {
        this.alertStatus = true
        this.alertClass = "alert alert-danger"
        this.alertText = "Something went wrong!"
      }

      
    }
  }
}
