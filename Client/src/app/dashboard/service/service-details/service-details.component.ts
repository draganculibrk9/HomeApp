import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SnackbarService} from "../../../services/snackbar.service";
import {ServiceRequest, ServiceResponse} from "../../../proto/generated/services_service_pb";
import {ServicesService} from "../../../proto/generated/services_service_pb_service";
import {grpc} from "@improbable-eng/grpc-web";
import UnaryOutput = grpc.UnaryOutput;
import {ServiceMessage} from "../../../proto/generated/service_message_pb";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TokenService} from "../../../services/token.service";

@Component({
  selector: 'app-service-details',
  templateUrl: './service-details.component.html',
  styleUrls: ['./service-details.component.css']
})
export class ServiceDetailsComponent implements OnInit {
  service: ServiceMessage;
  serviceForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<ServiceDetailsComponent>, @Inject(MAT_DIALOG_DATA) public service_id: number,
              private snackbarService: SnackbarService, public tokenService: TokenService) {
  }

  ngOnInit(): void {
    this.getService();
  }

  private initializeForm() {
    this.serviceForm = new FormGroup({
      name: new FormControl({
          value: this.service.getName(),
          disabled: this.tokenService.role === 'USER'
        },
        [
          Validators.required
        ]),
      email: new FormControl({
          value: this.service.getContact().getEmail(),
          disabled: this.tokenService.role === 'USER'
        },
        [
          Validators.required,
          Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')
        ]),
      phone: new FormControl({
          value: this.service.getContact().getPhone(),
          disabled: this.tokenService.role === 'USER'
        },
        [
          Validators.required
        ]),
      website: new FormControl({
          value: this.service.getContact().getWebsite(),
          disabled: this.tokenService.role === 'USER'
        },
        [
          Validators.required,
          Validators.pattern('(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?')
        ]),
      country: new FormControl({
          value: this.service.getContact().getAddress().getCountry(),
          disabled: this.tokenService.role === 'USER'
        },
        [
          Validators.required
        ]),
      city: new FormControl({
          value: this.service.getContact().getAddress().getCity(),
          disabled: this.tokenService.role === 'USER'
        },
        [
          Validators.required
        ]),
      street: new FormControl({
          value: this.service.getContact().getAddress().getStreet(),
          disabled: this.tokenService.role === 'USER'
        },
        [
          Validators.required
        ]),
      number: new FormControl({
          value: this.service.getContact().getAddress().getNumber(),
          disabled: this.tokenService.role === 'USER'
        },
        [
          Validators.required
        ])
    });
  }

  private getService() {
    const request = new ServiceRequest();
    request.setId(this.service_id);

    grpc.unary(ServicesService.GetService, {
      request: request,
      host: 'http://localhost:8078',
      onEnd: (output: UnaryOutput<ServiceResponse>) => {
        if (output.status === grpc.Code.OK) {
          this.service = output.message.getService();
          this.initializeForm();
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      },
    });
  }

  saveService() {

  }
}
