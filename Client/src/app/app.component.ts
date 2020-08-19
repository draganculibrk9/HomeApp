import {Component, OnInit} from '@angular/core';
import {LoginRequest} from './proto/generated/auth_service_pb';
import {grpc} from 'grpc-web-client';
import {AuthService} from './proto/generated/auth_service_pb_service';
import {LoginMessage} from './proto/generated/login_message_pb';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Client';

  ngOnInit(): void {
  }
}
