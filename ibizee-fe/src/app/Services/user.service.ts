import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Users } from '../Models/Users';
import { Login } from '../Models/Login';

const headeroption = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  BASE_URL = 'http://127.0.0.1:8080/';

  constructor(private http: HttpClient) { }

  // function to connect to the backend and send user registration details
  registerUser(user: Users) {
    return this.http.post(this.BASE_URL + 'user/register', user);
  }

  // function to connect and send login details to the backend
  loginUser(login: Login) {
    return this.http.post(this.BASE_URL + 'user/login', login)
  }
}
