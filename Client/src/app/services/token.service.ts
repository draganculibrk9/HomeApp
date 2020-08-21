import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import * as jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  _token = new BehaviorSubject<string>(null);
  _role = new BehaviorSubject<string>(null);

  // renew authentication later...
  // private authFlag = 'isLoggedIn';

  constructor() {
  }

  localLogin(token) {
    this._token.next(token);
    this._role.next(jwt_decode(this._token.getValue()).role[0]);
  }

  get authenticated() {
    return this._token !== null;
  }

  get token() {
    return this._token.getValue();
  }

  get role() {
    return this._role.getValue();
  }

  logout() {
    this.localLogout();

    // handle service logout later;
  }

  private localLogout() {
    this._token.next(null);
    this._role.next(null);
  }
}
