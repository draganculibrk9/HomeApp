import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {ServiceMessage} from "../../proto/generated/service_message_pb";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {SearchServiceRequest, ServiceResponse} from "../../proto/generated/services_service_pb";
import {FormControl, FormGroup} from "@angular/forms";
import {grpc} from "@improbable-eng/grpc-web";
import {ServicesService} from "../../proto/generated/services_service_pb_service";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-service',
  templateUrl: './service.component.html',
  styleUrls: ['./service.component.css']
})
export class ServiceComponent implements OnInit {
  dataSource: MatTableDataSource<ServiceMessage>;

  displayedColumns: string[] = ['name', 'address', 'phone', 'email', 'website'];

  searchForm = new FormGroup({
    name: new FormControl(''),
    city: new FormControl(''),
    min_price: new FormControl(null),
    max_price: new FormControl(null),
    type: new FormControl(null)
  });

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private snackbarService: SnackbarService) {
  }

  ngOnInit(): void {
    const request = new SearchServiceRequest();
    request.setMinimumPrice(null);
    request.setMaximumPrice(null);
    request.setType(null);
    request.setCity('');
    request.setName('');

    this.searchServices(request);
  }

  private searchServices(request: SearchServiceRequest) {
    const services = [];

    grpc.invoke(ServicesService.SearchServices, {
      request: request,
      host: 'http://localhost:8078',
      onMessage: (res: ServiceResponse) => {
        services.push(res.getService());
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

}
