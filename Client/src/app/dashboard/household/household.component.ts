import {Component, OnInit} from '@angular/core';
import {TokenService} from "../../services/token.service";
import {HouseholdRequest, TransactionResponse, TransactionsRequest} from "../../proto/generated/household_service_pb";
import {HouseholdMessage} from "../../proto/generated/household_message_pb";
import {HouseholdService} from "../../proto/generated/household_service_pb_service";
import {SnackbarService} from "../../services/snackbar.service";
import {grpc} from "@improbable-eng/grpc-web";
import {TransactionMessage} from "../../proto/generated/transaction_message_pb";
import * as _ from 'lodash';

@Component({
  selector: 'app-household',
  templateUrl: './household.component.html',
  styleUrls: ['./household.component.css']
})
export class HouseholdComponent implements OnInit {
  household: HouseholdMessage;
  transactions: TransactionMessage[] = [];
  lodash = _;

  constructor(private tokenService: TokenService, private snackbarService: SnackbarService) {
  }

  ngOnInit(): void {
    this.getHousehold();
  }

  private getHousehold() {
    const household_request = new HouseholdRequest();
    household_request.setOwner(this.tokenService.subject);

    grpc.unary(HouseholdService.GetHousehold, {
      request: household_request,
      host: 'http://localhost:8079',
      onEnd: res => {
        switch (res.status) {
          case grpc.Code.OK:
            const {id, balance, owner} = res.message.toObject()['household'];
            this.household = new HouseholdMessage();
            this.household.setBalance(balance);
            this.household.setId(id);
            this.household.setOwner(owner);
            this.getTransactions();
            break;
          case grpc.Code.NotFound:
            this.snackbarService.displayMessage(res.statusMessage);
            break;
          default:
            console.log(res.statusMessage);
        }
      }
    });
  }

  private getTransactions() {
    const transaction_request = new TransactionsRequest();
    transaction_request.setHouseholdId(this.household.getId());

    grpc.invoke(HouseholdService.GetTransactions, {
      request: transaction_request,
      host: "http://localhost:8079",
      onMessage: (res: TransactionResponse) => {
        this.transactions.push(res.getTransaction());
      },
      onEnd: (code: grpc.Code, message: string) => {
        if (code !== grpc.Code.OK) {
          this.snackbarService.displayMessage(message);
          this.transactions = [];
        }
      }
    });
  }

  public isIncome(transaction: TransactionMessage) {
    return transaction.getTransactionType() === TransactionMessage.TransactionType.INCOME;
  }

  public openDialog() {
    console.log("paprika babura");
  }
}
