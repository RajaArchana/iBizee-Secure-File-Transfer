import { Component } from '@angular/core';
import { Users } from '../../Models/Users';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../Services/user.service';
import { UserResponse } from '../../Models/UserResponse';
import { delay } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.css'
})
export class RegisterPageComponent {

  // global variables
  users: Users = new Users;
  userResponse: UserResponse = new UserResponse;
  confirmPassword: string = "";
  alertStatus: boolean = false
  alertClass: string = "alert alert-danger";
  alertText: string = "Something went wrong"

  constructor(private userService: UserService, private router: Router) {}

  // this functions works when started to changing fields in the form
  registerOnChange() {
    this.alertStatus = false
  }

  // this functions works when clicking on the Register button
  registerUser() {

    // basic form validations
    if (this.users.fullName == "") {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Full name cannot be empty"
    }
    else if (this.users.userEmail == "") {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Email cannot be empty"
    }
    else if (!this.users.userEmail.includes("@") || !this.users.userEmail.includes(".com")) {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Please enter an valid email"
    }
    else if (this.users.departmentId == undefined) {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Please select the department that you want to share"
    }
    else if (this.users.userPassword == "" || this.users.userPassword.length < 4) {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Please enter an valid password with at least 5 characters long"
    }
    else if (this.confirmPassword == "") {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Please confirm the password"
    }
    else if (this.confirmPassword != this.users.userPassword) {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Passwords do not match"
      
      // empting the password fields
      this.users.userPassword = ""
      this.confirmPassword = ""
    }
    else {
      try {
        this.userService.registerUser(this.users).subscribe((result: any) => {
          this.userResponse = result;
          
          if (this.userResponse.responseStatus == 200) {
            this.alertStatus = true
            this.alertClass = "alert alert-success"
            this.alertText = "User registration successfull"

            delay(1000)

            this.router.navigate(['main-pane/login'])
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
        this.alertText = "User registration failed!"
      }
      
    }
  }
}
