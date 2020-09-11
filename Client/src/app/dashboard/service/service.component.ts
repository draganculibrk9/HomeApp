import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {FormControl, FormGroup} from "@angular/forms";
import {grpc} from "@improbable-eng/grpc-web";
import {SnackbarService} from "../../services/snackbar.service";
import {ServiceRow} from "../../model/service-row";
import {TokenService} from "../../services/token.service";
import {
  AccommodationResponse, DeleteAccommodationRequest,
  SearchServiceRequest,
  ServiceByAdministratorRequest, ServiceRequest,
  ServiceResponse
} from "../../proto/generated/services_service_pb";
import {ServicesService} from "../../proto/generated/services_service_pb_service";
import {MatDialog} from "@angular/material/dialog";
import {ServiceDetailsComponent} from "./service-details/service-details.component";
import {CreateServiceComponent} from "./create-service/create-service.component";
import {environment} from "../../../environments/environment";
import {SuccessResponse} from "../../proto/generated/household_service_pb";
import UnaryOutput = grpc.UnaryOutput;
import {AccommodationRow} from "../../model/accommodation-row";
import {AccommodationType} from "../../model/accommodation-type.enum";
import {LabelType, Options} from "ng5-slider";
import {AccommodationTypePipe} from "../../pipe/accommodation-type.pipe";
import {AccommodationMessage} from "../../proto/generated/accommodation_message_pb";
import {CreateOrEditAccommodationComponent} from "./create-or-edit-accommodation/create-or-edit-accommodation.component";
import {RequestAccommodationComponent} from "./request-accommodation/request-accommodation.component";

@Component({
  selector: 'app-service',
  templateUrl: './service.component.html',
  styleUrls: ['./service.component.css']
})
export class ServiceComponent implements OnInit, AfterViewInit {
  dataSource: MatTableDataSource<ServiceRow>;
  accommodationsSource: MatTableDataSource<AccommodationRow>;

  displayedColumns: string[];
  accommodationColumns: string[];

  type = AccommodationType;
  _ = Object;
  options: Options = {
    floor: 0,
    ceil: 100000,
    translate: (value: number, label: LabelType): string => {
      switch (label) {
        case LabelType.Low:
          return '<b>Min price:</b> RSD: ' + value;
        case LabelType.High:
          return '<b>Max price:</b> RSD: ' + value;
        default:
          return 'RSD: ' + value;
      }
    }
  }

  minValue = 0;
  maxValue = 100000;

  searchForm = new FormGroup({
    name: new FormControl(''),
    city: new FormControl(''),
    type: new FormControl(null)
  });

  private selectedService: number;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  @ViewChild(MatPaginator, {static: true}) accommodationPaginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) accommodationSort: MatSort;

  constructor(private snackbarService: SnackbarService, public tokenService: TokenService,
              private dialog: MatDialog, private accommodationTypePipe: AccommodationTypePipe) {
  }

  ngOnInit(): void {
    if (this.tokenService.role === 'SERVICE_ADMINISTRATOR') {
      this.displayedColumns = ['id', 'name', 'address', 'phone', 'email', 'website', 'service-view', 'delete'];
      this.accommodationColumns = ['id', 'name', 'type', 'price', 'available', 'edit', 'delete'];
    } else {
      this.displayedColumns = ['name', 'address', 'phone', 'email', 'website', 'service-view'];
      this.accommodationColumns = ['name', 'type', 'price', 'available', 'request'];
    }
  }

  ngAfterViewInit() {
    if (this.tokenService.role === 'USER') {
      this.getServices();
    } else {
      this.getServicesByAdministrator();
    }
  }

  private getServicesByAdministrator() {
    const request = new ServiceByAdministratorRequest();
    request.setAdministrator(this.tokenService.subject);

    const services: ServiceRow[] = [];

    grpc.invoke(ServicesService.GetServicesByAdministrator, {
      request: request,
      host: environment.servicesServiceHost,
      metadata: {Authorization: `Bearer ${this.tokenService.token}`},
      onMessage: (res: ServiceResponse) => {
        const service = res.getService();
        services.push({
          address: service.getContact().getAddress().getCity() + ", " + service.getContact().getAddress().getCountry(),
          email: service.getContact().getEmail(),
          id: service.getId(),
          name: service.getName(),
          phone: service.getContact().getPhone(),
          website: service.getContact().getWebsite()
        });
      },
      onEnd: (code: grpc.Code, message: string) => {
        if (code === grpc.Code.OK) {
          this.dataSource = new MatTableDataSource(services);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        } else {
          this.snackbarService.displayMessage(message);
        }
      }
    })
  }

  getServices() {
    const request = new SearchServiceRequest();
    request.setMinimumPrice(null);
    request.setMaximumPrice(null);
    request.setType(null);
    request.setCity('');
    request.setName('');

    this.searchServices(request);
  }

  private searchServices(request: SearchServiceRequest) {
    const services: ServiceRow[] = [];

    grpc.invoke(ServicesService.SearchServices, {
      request: request,
      host: environment.servicesServiceHost,
      metadata: {Authorization: `Bearer ${this.tokenService.token}`},
      onMessage: (res: ServiceResponse) => {
        const service = res.getService();
        services.push({
          address: service.getContact().getAddress().getCity() + ", " + service.getContact().getAddress().getCountry(),
          email: service.getContact().getEmail(),
          id: service.getId(),
          name: service.getName(),
          phone: service.getContact().getPhone(),
          website: service.getContact().getWebsite()
        });
      },
      onEnd: (code: grpc.Code, message: string) => {
        if (code === grpc.Code.OK) {
          this.dataSource = new MatTableDataSource(services);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        } else {
          this.snackbarService.displayMessage(message);
        }
      }
    });
  }

  openDetailedView(id: number) {
    const dialogRef = this.dialog.open(ServiceDetailsComponent, {
      width: '500px',
      data: id
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.getServicesByAdministrator();
      }
    });
  }

  delete(id: number) {
    const request = new ServiceRequest();
    request.setId(id);

    grpc.unary(ServicesService.DeleteService, {
      request: request,
      host: environment.servicesServiceHost,
      metadata: {Authorization: `Bearer ${this.tokenService.token}`},
      onEnd: (output: UnaryOutput<SuccessResponse>) => {
        if (output.status === grpc.Code.OK) {
          if (output.message.getSuccess()) {
            this.snackbarService.displayMessage('Service deleted successfully');
            this.getServicesByAdministrator();
          }
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      },
    })
  }

  createService() {
    const dialogRef = this.dialog.open(CreateServiceComponent, {
      width: '700px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.getServicesByAdministrator();
      }
    });
  }

  initializeSearch() {
    const request = new SearchServiceRequest();
    request.setMinimumPrice(this.minValue !== 0 || this.maxValue !== 100000 ? this.minValue : null);
    request.setMaximumPrice(this.minValue !== 0 || this.maxValue !== 100000 ? this.maxValue : null);
    request.setType(this.determineType());
    request.setCity(this.searchForm.controls.city.value);
    request.setName(this.searchForm.controls.name.value);

    this.searchServices(request);
  }

  private determineType() {
    switch (this.searchForm.controls.type.value) {
      case AccommodationType.CATERING:
        return AccommodationMessage.AccommodationType.CATERING;
      case AccommodationType.HYGIENE:
        return AccommodationMessage.AccommodationType.HYGIENE;
      case AccommodationType.REPAIRS:
        return AccommodationMessage.AccommodationType.REPAIRS;
      default:
        return null;
    }
  }

  getAccommodations(id: number) {
    const request = new ServiceRequest();
    request.setId(id);

    const accommodations: AccommodationRow[] = [];

    grpc.invoke(ServicesService.GetAccommodations, {
      request: request,
      host: environment.servicesServiceHost,
      metadata: {Authorization: `Bearer ${this.tokenService.token}`},
      onMessage: (res: AccommodationResponse) => {
        const accommodation = res.getAccommodation();

        accommodations.push({
          available: accommodation.getAvailable(),
          id: accommodation.getId(),
          name: accommodation.getName(),
          price: accommodation.getPrice(),
          type: this.accommodationTypePipe.transform(accommodation.getType())
        });
      },
      onEnd: (code: grpc.Code, message: string) => {
        if (code === grpc.Code.OK) {
          this.accommodationsSource = new MatTableDataSource(accommodations);
          this.accommodationsSource.paginator = this.accommodationPaginator;
          this.accommodationsSource.sort = this.accommodationSort;
          this.selectedService = id;
        } else {
          this.snackbarService.displayMessage(message);
        }
      }
    });
  }

  openEditAccommodation(row: AccommodationRow) {
    const dialogRef = this.dialog.open(CreateOrEditAccommodationComponent, {
      width: '300px',
      data: {
        service_id: this.selectedService,
        accommodation: row
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.getAccommodations(this.selectedService);
      }
    });
  }

  deleteAccommodation(id: number) {
    const request = new DeleteAccommodationRequest();
    request.setAccommodationId(id);
    request.setServiceId(this.selectedService);

    grpc.unary(ServicesService.DeleteAccommodation, {
      request: request,
      host: environment.servicesServiceHost,
      metadata: {Authorization: `Bearer ${this.tokenService.token}`},
      onEnd: (output: UnaryOutput<SuccessResponse>) => {
        if (output.status === grpc.Code.OK && output.message.getSuccess()) {
          this.snackbarService.displayMessage('Accommodation deleted successfully');
          this.getAccommodations(this.selectedService);
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      }
    });
  }

  createAccommodation() {
    const dialogRef = this.dialog.open(CreateOrEditAccommodationComponent, {
      width: '300px',
      data: {
        service_id: this.selectedService,
        accommodation: null
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.getAccommodations(this.selectedService);
      }
    });
  }

  requestAccommodation(row: AccommodationRow) {
    this.dialog.open(RequestAccommodationComponent, {
      width: '300px',
      data: row
    });
  }
}
