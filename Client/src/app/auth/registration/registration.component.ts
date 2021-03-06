import {Component} from '@angular/core';
import {SnackbarService} from '../../services/snackbar.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {grpc} from '@improbable-eng/grpc-web';
import {UserMessage} from "../../proto/generated/user_message_pb";
import {RegistrationRequest} from "../../proto/generated/auth_service_pb";
import {AddressMessage} from "../../proto/generated/address_message_pb";
import {AuthService} from "../../proto/generated/auth_service_pb_service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  registrationForm = new FormGroup(
    {
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [
        Validators.required,
        Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')
      ]),
      password: new FormControl('', [Validators.required]),
      phone: new FormControl('', [
        Validators.required,
        Validators.pattern('^\\+[0-9]{3}\\s[0-9]{8,9}$')
      ]),
      country: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      street: new FormControl('', [Validators.required]),
      number: new FormControl('', [Validators.required]),
      isServiceAdmin: new FormControl(false, Validators.required)
    }
  );

  hide = true;

  constructor(private snackbarService: SnackbarService, private router: Router) {
  }

  onSubmit() {
    if (this.registrationForm.invalid) {
      this.snackbarService.displayMessage('Some form fields are invalid');
      return;
    }

    const registration_request = new RegistrationRequest();
    const registration_message = new UserMessage();
    const address = new AddressMessage();
    address.setCity(this.registrationForm.controls.city.value);
    address.setCountry(this.registrationForm.controls.country.value);
    address.setNumber(this.registrationForm.controls.number.value);
    address.setStreet(this.registrationForm.controls.street.value);

    registration_message.setAddress(address);
    registration_message.setEmail(this.registrationForm.controls.email.value);
    registration_message.setFirstName(this.registrationForm.controls.firstName.value);
    registration_message.setLastName(this.registrationForm.controls.lastName.value);
    registration_message.setPassword(this.registrationForm.controls.password.value);
    registration_message.setPhone(this.registrationForm.controls.phone.value);
    registration_message.setBlocked(false);

    if (this.registrationForm.controls.isServiceAdmin.value) {
      registration_message.setRole(UserMessage.Role.SERVICE_ADMINISTRATOR);
    } else {
      registration_message.setRole(UserMessage.Role.USER);
    }

    registration_request.setRegistration(registration_message);

    grpc.unary(AuthService.Register, {
        request: registration_request,
        host: 'http://localhost:8080',
        onEnd: res => {
          switch (res.status) {
            case grpc.Code.OK:
              this.snackbarService.displayMessage('Registration successful');
              this.router.navigateByUrl('/login').then();
              break;
            case grpc.Code.AlreadyExists:
              this.snackbarService.displayMessage(res.statusMessage);
              break;
            default:
              this.snackbarService.displayMessage('Failed to register');
          }
        }
      }
    );
  }
}
