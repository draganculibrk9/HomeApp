import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm = new FormGroup(
    {
      email: new FormControl('', [
        Validators.required,
        Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')
      ]),
      password: new FormControl('', [Validators.required])
    }
  )

  constructor(private snackbarService: SnackbarService) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log("Submitted!");
  }
}
