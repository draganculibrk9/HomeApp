import {Component, OnInit} from '@angular/core';
import {SnackbarService} from "../../services/snackbar.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RegistrationRequest} from "../../proto/generated/auth_service_pb";
import {Address, RegistrationMessage} from "../../proto/generated/registration_message_pb";
import {grpc} from "grpc-web-client";
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

  constructor(private snackbarService: SnackbarService) {
  }

  onSubmit() {
    const registration_request = new RegistrationRequest();
    const registration_message = new RegistrationMessage();
    const address = new Address();
    address.setCity(this.registrationForm.controls['city'].value);
    address.setCountry(this.registrationForm.controls['country'].value);
    address.setNumber(this.registrationForm.controls['number'].value);
    address.setStreet(this.registrationForm.controls['street'].value);

    registration_message.setAddress(address);
    registration_message.setEmail(this.registrationForm.controls['email'].value);
    registration_message.setFirstName(this.registrationForm.controls['firstName'].value);
    registration_message.setLastName(this.registrationForm.controls['lastName'].value);
    registration_message.setPassword(this.registrationForm.controls['password'].value);
    registration_message.setPhone(this.registrationForm.controls['phone'].value);

    if (this.registrationForm.controls['isServiceAdmin'].value) {
      registration_message.setRole(RegistrationMessage.Role.SERVICE_ADMINISTRATOR);
    } else {
      registration_message.setRole(RegistrationMessage.Role.USER);
    }

    grpc.unary(AuthService.Register, {
        request: registration_request,
        host: 'http://localhost:8080',
        onEnd: res => {
          if (res.status != 0) {
            this.snackbarService.displayMessage(res.statusMessage);
          } else {
            this.snackbarService.displayMessage(res.message.toObject().toString());
          }
        }
      }
    );
  }
}
