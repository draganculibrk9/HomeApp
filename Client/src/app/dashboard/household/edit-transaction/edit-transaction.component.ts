import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {DateAdapter} from '@angular/material/core';
import {SnackbarService} from '../../../services/snackbar.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Period, TransactionMessage} from '../../../proto/generated/transaction_message_pb';
import * as moment from 'moment';
import {CreateOrEditTransactionRequest} from '../../../proto/generated/household_service_pb';
import {grpc} from '@improbable-eng/grpc-web';
import {HouseholdService} from '../../../proto/generated/household_service_pb_service';

@Component({
  selector: 'app-edit-transaction',
  templateUrl: './edit-transaction.component.html',
  styleUrls: ['./edit-transaction.component.css']
})
export class EditTransactionComponent implements OnInit {
  transactionForm: FormGroup;
  minDate: moment.Moment;
  maxDate: moment.Moment;
  period = Period;

  constructor(public dialogRef: MatDialogRef<EditTransactionComponent>, private adapter: DateAdapter<any>,
              private snackbarService: SnackbarService, @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit(): void {
    this.adapter.setLocale('sr');
    this.transactionForm = new FormGroup({
      name: new FormControl(this.data.transaction.getName(), [Validators.required]),
      amount: new FormControl(this.data.transaction.getAmount(), [
        Validators.required,
        Validators.min(1)
      ]),
      date: new FormControl(moment(this.data.transaction.getDate()), [Validators.required])
    });

    if (this.data.transaction.getPeriod() !== this.period.UNKNOWN) {
      this.transactionForm.addControl('period', new FormControl(this.data.transaction.getPeriod(), [Validators.required]));
    } else if (this.data.transaction.getNumberOfTimes() !== 0) {
      this.transactionForm.addControl('numberOfTimes', new FormControl(this.data.transaction.getNumberOfTimes(), [Validators.required, Validators.min(0)]));
    }

    this.minDate = moment();
    this.maxDate = moment(this.minDate).add(1, 'year');
  }

  editTransaction() {
    if (this.transactionForm.invalid) {
      this.snackbarService.displayMessage('Some form fields are invalid');
      return;
    }

    const request = new CreateOrEditTransactionRequest();
    request.setHouseholdId(this.data.household_id);
    const transaction = new TransactionMessage();

    transaction.setId(this.data.transaction.getId());
    transaction.setName(this.transactionForm.controls.name.value);
    transaction.setTransactionType(this.data.transaction.getTransactionType());
    transaction.setDate(this.transactionForm.controls.date.value.unix());
    transaction.setAmount(this.transactionForm.controls.amount.value);

    if (this.transactionForm.contains('numberOfTimes')) {
      transaction.setNumberOfTimes(this.transactionForm.controls.numberOfTimes.value);
    } else if (this.transactionForm.contains('period')) {
      transaction.setPeriod(this.transactionForm.controls.period.value);
    }
    request.setTransaction(transaction);

    grpc.unary(HouseholdService.EditTransaction, {
      request,
      host: 'http://localhost:8079',
      onEnd: res => {
        if (res.status === grpc.Code.OK) {
          this.snackbarService.displayMessage('Transaction edited successfully');
          this.dialogRef.close(true);
        } else {
          this.snackbarService.displayMessage(res.statusMessage);
        }
      }
    });
  }
}
