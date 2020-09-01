import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {TransactionType} from '../../../model/transaction-type.enum';
import * as _moment from 'moment';
import {DateAdapter} from '@angular/material/core';
import {grpc} from '@improbable-eng/grpc-web';
import {SnackbarService} from '../../../services/snackbar.service';
import {Period, TransactionMessage} from "../../../proto/generated/transaction_message_pb";
import {CreateOrEditTransactionRequest} from "../../../proto/generated/household_service_pb";
import {HouseholdService} from "../../../proto/generated/household_service_pb_service";

@Component({
  selector: 'app-create-transaction',
  templateUrl: './create-transaction.component.html',
  styleUrls: ['./create-transaction.component.css']
})
export class CreateTransactionComponent implements OnInit {
  transactionType = TransactionMessage.TransactionType;
  transactionSubtype = TransactionType;
  _ = Object;
  minDate: _moment.Moment;
  maxDate: _moment.Moment;
  period = Period;

  transactionForm = new FormGroup(
    {
      transactionType: new FormControl(null, [
        Validators.required
      ]),
      transactionSubtype: new FormControl(null, [
        Validators.required
      ]),
      name: new FormControl({value: '', disabled: true}, [
        Validators.required
      ]),
      amount: new FormControl({value: null, disabled: true}, [
        Validators.required,
        Validators.min(1)
      ]),
      date: new FormControl({value: _moment(), disabled: true}, [
        Validators.required
      ]),
      numberOfTimes: new FormControl({value: null, disabled: true}, [
        Validators.min(1)
      ]),
      period: new FormControl({value: null, disabled: true})
    }
  );

  constructor(public dialogRef: MatDialogRef<CreateTransactionComponent>, private adapter: DateAdapter<any>,
              private snackbarService: SnackbarService, @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit(): void {
    this.adapter.setLocale('sr');
    this.minDate = _moment();
    this.maxDate = _moment(this.minDate).add(1, 'year');
  }

  public createTransaction() {
    if (this.transactionForm.invalid) {
      this.snackbarService.displayMessage('Some form fields are invalid');
      return;
    }

    const request = new CreateOrEditTransactionRequest();
    const transaction = new TransactionMessage();

    request.setHouseholdId(this.data.household_id);

    transaction.setAmount(this.transactionForm.controls.amount.value);
    transaction.setName(this.transactionForm.controls.name.value);
    transaction.setDate(this.transactionForm.controls.date.value.unix());
    transaction.setTransactionType(this.transactionForm.controls.transactionType.value);

    switch (this.transactionForm.controls.transactionSubtype.value) {
      case this.transactionSubtype.PERIODICAL_TRANSACTION:
        transaction.setPeriod(this.transactionForm.controls.period.value);
        break;
      case this.transactionSubtype.MULTIPLE_TIMES_TRANSACTION:
        transaction.setNumberOfTimes(this.transactionForm.controls.numberOfTimes.value);
        break;
    }

    request.setTransaction(transaction);

    grpc.unary(HouseholdService.CreateTransaction, {
      request,
      host: 'http://localhost:8079',
      onEnd: res => {
        if (res.status === grpc.Code.OK) {
          this.dialogRef.close(true);
        } else {
          this.snackbarService.displayMessage(res.statusMessage);
        }
      }
    });
  }
}
