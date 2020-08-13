import {Component, OnInit} from '@angular/core';
import {LoginRequest} from "./proto/generated/auth_service_pb";
import {grpc} from "grpc-web-client";
import {AuthService} from "./proto/generated/auth_service_pb_service";
import {LoginMessage} from "./proto/generated/login_message_pb";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Client';

  ngOnInit(): void {
    const login_request = new LoginRequest();
    const login_message = new LoginMessage();
    login_message.setEmail("test@gmail.com");
    login_message.setPassword("test");
    login_request.setLogin(login_message);

    grpc.unary(AuthService.Login, {
        request: login_request,
        host: "http://localhost:9091",
        onEnd: res => {
          console.log(res);
        }
      }
    );
  }
}
