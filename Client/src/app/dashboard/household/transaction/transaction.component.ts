import {Component, Input, OnInit} from '@angular/core';
import {Period, TransactionMessage} from "../../../proto/generated/transaction_message_pb";

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {
  @Input() transaction: TransactionMessage;
  type = TransactionMessage.TransactionType;
  period = Period;

  constructor() { }

  ngOnInit(): void {
  }

}
