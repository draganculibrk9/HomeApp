import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {TokenService} from "../../services/token.service";
import {
  AccommodationRequestRequest,
  AccommodationRequestResponse,
  DecideAccommodationRequestRequest,
  ServiceByAdministratorRequest
} from "../../proto/generated/services_service_pb";
import {MatTableDataSource} from "@angular/material/table";
import {AccommodationRequestRow} from "../../model/accommodation-request-row";
import {grpc} from "@improbable-eng/grpc-web";
import {ServicesService} from "../../proto/generated/services_service_pb_service";
import {HouseholdRequest, HouseholdResponse, SuccessResponse} from "../../proto/generated/household_service_pb";
import {HouseholdService} from "../../proto/generated/household_service_pb_service";
import {SnackbarService} from "../../services/snackbar.service";
import {environment} from "../../../environments/environment";
import {HouseholdMessage} from "../../proto/generated/household_message_pb";
import * as moment from "moment";
import {AccommodationRequestStatusPipe} from "../../pipe/accommodation-request-status.pipe";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AccommodationRequestStatus} from "../../model/accommodation-request-status.enum";
import UnaryOutput = grpc.UnaryOutput;
import {AccommodationRequestMessage} from "../../proto/generated/accommodation_request_message_pb";

@Component({
  selector: 'app-accommodation-request',
  templateUrl: './accommodation-request.component.html',
  styleUrls: ['./accommodation-request.component.css']
})
export class AccommodationRequestComponent implements OnInit, AfterViewInit {
  dataSource: MatTableDataSource<AccommodationRequestRow>
  moment = moment;
  displayedColumns: string[];
  status = AccommodationRequestStatus;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(public tokenService: TokenService, private snackbarService: SnackbarService,
              private accommodationRequestStatusPipe: AccommodationRequestStatusPipe) {
  }

  ngOnInit(): void {
    if (this.tokenService.role === 'SERVICE_ADMINISTRATOR') {
      this.displayedColumns = ['id', 'accommodation', 'owner', 'requested-for', 'filed-on', 'accept', 'reject'];
    } else {
      this.displayedColumns = ['accommodation', 'requested-for', 'filed-on', 'status'];
    }
  }

  ngAfterViewInit() {
    if (this.tokenService.role === 'SERVICE_ADMINISTRATOR') {
      this.getAccommodationRequestsForAdministrator();
    } else {
      this.getAccommodationRequests();
    }
  }

  private getAccommodationRequests() {
    const request = new AccommodationRequestRequest();
    const household = this.getHousehold(this.tokenService.subject);

    if (household) {
      request.setHouseholdId(household.getId())

      const accommodation_requests: AccommodationRequestRow[] = [];

      grpc.invoke(ServicesService.GetAccommodationRequests, {
        request: request,
        host: environment.servicesServiceHost,
        onMessage: (res: AccommodationRequestResponse) => {
          const accommodation_request = res.getAccommodationRequest();
          accommodation_request.getStatus();

          accommodation_requests.push({
            accommodation: res.getAccommodationName(),
            filedOn: accommodation_request.getFiledOn(),
            id: accommodation_request.getId(),
            requestedFor: accommodation_request.getRequestedFor(),
            status: this.accommodationRequestStatusPipe.transform(accommodation_request.getStatus())
          });
        },
        onEnd: (code: grpc.Code, message: string) => {
          if (code === grpc.Code.OK) {
            this.dataSource = new MatTableDataSource(accommodation_requests);
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
          } else {
            this.snackbarService.displayMessage(message);
          }
        }
      });
    }
  }

  private getHousehold(owner: string): HouseholdMessage | void {
    const request = new HouseholdRequest();
    request.setOwner(owner);

    grpc.unary(HouseholdService.GetHousehold, {
      request: request,
      host: environment.householdServiceHost,
      onEnd: (output: UnaryOutput<HouseholdResponse>) => {
        if (output.status === grpc.Code.OK) {
          return output.message.getHousehold();
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      }
    });
  }

  private getAccommodationRequestsForAdministrator() {
    const request = new ServiceByAdministratorRequest();
    request.setAdministrator(this.tokenService.subject);

    const accommodation_requests: AccommodationRequestRow[] = [];

    grpc.invoke(ServicesService.GetAccommodationRequestsForAdministrator, {
      request: request,
      host: environment.servicesServiceHost,
      onMessage: (res: AccommodationRequestResponse) => {
        const accommodation_request = res.getAccommodationRequest();

        accommodation_requests.push({
          accommodation: res.getAccommodationName(),
          owner: res.getHouseholdOwner(),
          filedOn: accommodation_request.getFiledOn(),
          id: accommodation_request.getId(),
          requestedFor: accommodation_request.getRequestedFor(),
          status: this.accommodationRequestStatusPipe.transform(accommodation_request.getStatus())
        });
      },
      onEnd: (code: grpc.Code, message: string) => {
        if (code === grpc.Code.OK) {
          this.dataSource = new MatTableDataSource(accommodation_requests);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        } else {
          this.snackbarService.displayMessage(message);
        }
      }
    });
  }

  decideOnRequest(id: number, newStatus: AccommodationRequestStatus) {
    const request = new DecideAccommodationRequestRequest();
    request.setAccommodationRequestId(id);
    request.setStatus(newStatus === AccommodationRequestStatus.ACCEPTED
      ? AccommodationRequestMessage.Status.ACCEPTED : AccommodationRequestMessage.Status.REJECTED);

    grpc.unary(ServicesService.DecideAccommodationRequest, {
      request: request,
      host: environment.servicesServiceHost,
      onEnd: (output: UnaryOutput<SuccessResponse>) => {
        if (output.status === grpc.Code.OK) {
          this.getAccommodationRequestsForAdministrator();
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      },
    });
  }
}
