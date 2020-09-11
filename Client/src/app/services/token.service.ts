import {Injectable} from '@angular/core';
import * as jwt_decode from 'jwt-decode';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private router: Router, private http: HttpClient) {
  }

  localLogin(token) {
    localStorage.setItem('token', token);
    localStorage.setItem('role', jwt_decode(token).role[0]);
    localStorage.setItem('subject', jwt_decode(token).sub);
  }

  get subject() {
    return localStorage.getItem('subject');
  }

  get authenticated() {
    return localStorage.getItem('token') !== null;
  }

  get token() {
    return localStorage.getItem('token');
  }

  get role() {
    return localStorage.getItem('role');
  }

  logout() {
    localStorage.clear();
    this.serviceLogout();

    this.router.navigateByUrl('/login').then();
  }

  private serviceLogout() {
    this.http.get(environment.logoutURL);
  }
}
