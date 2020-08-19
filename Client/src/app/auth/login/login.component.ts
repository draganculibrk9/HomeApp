import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {SnackbarService} from '../../services/snackbar.service';
import {LoginRequest} from '../../proto/generated/auth_service_pb';
import {LoginMessage} from '../../proto/generated/login_message_pb';
import {grpc} from 'grpc-web-client';
import {AuthService} from '../../proto/generated/auth_service_pb_service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm = new FormGroup(
    {
      email: new FormControl('', [
        Validators.required,
        Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')
      ]),
      password: new FormControl('', [Validators.required])
    }
  );

  constructor(private snackbarService: SnackbarService) {
  }

  onSubmit() {
    const login_request = new LoginRequest();
    const login_message = new LoginMessage();
    login_message.setEmail(this.loginForm.controls.email.value);
    login_message.setPassword(this.loginForm.controls.email.value);
    login_request.setLogin(login_message);

    grpc.unary(AuthService.Login, {
        request: login_request,
        host: 'http://localhost:8080',
        onEnd: res => {
          if (res.status != 0) {
            this.snackbarService.displayMessage(res.statusMessage);
          }
        }
      }
    );
  }
}
