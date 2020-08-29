import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Period, TransactionMessage} from '../../../proto/generated/transaction_message_pb';
import {MatDialog} from '@angular/material/dialog';
import {EditTransactionComponent} from '../edit-transaction/edit-transaction.component';
import {GetOrDeleteTransactionRequest} from '../../../proto/generated/household_service_pb';
import {grpc} from '@improbable-eng/grpc-web';
import {HouseholdService} from '../../../proto/generated/household_service_pb_service';
import {SnackbarService} from '../../../services/snackbar.service';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {
  @Input() transaction: TransactionMessage;
  @Input() householdId: number;
  type = TransactionMessage.TransactionType;
  period = Period;

  @Output() reloadTransactions = new EventEmitter();

  constructor(private dialog: MatDialog, private snackbarService: SnackbarService) {
  }

  ngOnInit(): void {
  }

  openDialog() {
    const dialogRef = this.dialog.open(EditTransactionComponent, {
      width: '250px',
      data: {
        household_id: this.householdId,
        transaction: this.transaction
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.reloadTransactions.emit();
      }
    });
  }

  public deleteTransaction() {
    const request = new GetOrDeleteTransactionRequest();
    request.setTransactionId(this.transaction.getId());

    grpc.unary(HouseholdService.DeleteTransaction, {
      request,
      host: 'http://localhost:8079',
      onEnd: res => {
        switch (res.status) {
          case grpc.Code.OK:
            this.reloadTransactions.emit();
            return;
          case grpc.Code.InvalidArgument || grpc.Code.NotFound:
            this.snackbarService.displayMessage(res.statusMessage);
            return;
          default:
            this.snackbarService.displayMessage('Failed to delete transaction');
            return;
        }
      }
    });
  }
}
