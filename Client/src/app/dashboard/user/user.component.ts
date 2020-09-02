import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {grpc} from "@improbable-eng/grpc-web";
import {AuthService} from "../../proto/generated/auth_service_pb_service";
import {GetUsersRequest, ToggleBlockRequest, UserResponse} from "../../proto/generated/auth_service_pb";
import {SnackbarService} from "../../services/snackbar.service";
import {SuccessResponse} from "../../proto/generated/household_service_pb";
import UnaryOutput = grpc.UnaryOutput;
import {UserRow} from "../../model/user-row";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements AfterViewInit {
  usersData: MatTableDataSource<UserRow>;
  displayedColumns: string[] = ['id', 'email', 'first_name', 'last_name', 'blocked'];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private snackbarService: SnackbarService) {
  }

  ngAfterViewInit() {
    this.getUsers();
  }

  private getUsers() {
    const request = new GetUsersRequest();
    const users: UserRow[] = [];

    grpc.invoke(AuthService.GetUsers, {
      request: request,
      host: 'http://localhost:8080',
      onMessage: (res: UserResponse) => {
        users.push({
          id: res.getUser().getId(),
          blocked: res.getUser().getBlocked(),
          email: res.getUser().getEmail(),
          first_name: res.getUser().getFirstName(),
          last_name: res.getUser().getLastName()
        });
      },
      onEnd: (code: grpc.Code, message: string) => {
        if (code === grpc.Code.OK) {
          this.usersData = new MatTableDataSource(users);
          this.usersData.paginator = this.paginator;
          this.usersData.sort = this.sort;
        } else {
          this.snackbarService.displayMessage(message);
        }
      }
    });
  }

  public toggleBlockOnUser(id: number) {
    const request = new ToggleBlockRequest();
    request.setUserId(id);

    grpc.unary(AuthService.ToggleBlockOnUser, {
      request: request,
      host: 'http://localhost:8080',
      onEnd: (output: UnaryOutput<SuccessResponse>) => {
        if (output.message.getSuccess()) {
          this.snackbarService.displayMessage("User block status toggled successfully");
          this.getUsers();
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      }
    });
  }

}
