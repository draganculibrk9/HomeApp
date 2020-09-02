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
  SearchServiceRequest,
  ServiceByAdministratorRequest,
  ServiceResponse
} from "../../proto/generated/services_service_pb";
import {ServicesService} from "../../proto/generated/services_service_pb_service";

@Component({
  selector: 'app-service',
  templateUrl: './service.component.html',
  styleUrls: ['./service.component.css']
})
export class ServiceComponent implements OnInit, AfterViewInit {
  dataSource: MatTableDataSource<ServiceRow>;

  displayedColumns: string[] = ['name', 'address', 'phone', 'email', 'website', 'service-view'];

  searchForm = new FormGroup({
    name: new FormControl(''),
    city: new FormControl(''),
    min_price: new FormControl(null),
    max_price: new FormControl(null),
    type: new FormControl(null)
  });

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private snackbarService: SnackbarService, public tokenService: TokenService) {
  }

  ngOnInit(): void {
    if (this.tokenService.role === 'SERVICE_ADMINISTRATOR') {
      this.displayedColumns.push('delete');
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
      host: 'http://localhost:8078',
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

  private getServices() {
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
      host: 'http://localhost:8078',
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
    console.log('details ' + id);
  }

  delete(id: number) {

  }
}
