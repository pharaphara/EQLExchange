import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CurrencyService } from '../explorer/service/currency.service';
import { Currency } from '../explorer/state/currency';
import { PaymentService } from '../refill/service/payment.service';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.scss']
})
export class TransferComponent implements OnInit {

  constructor(private currencyService: CurrencyService, private paymentService: PaymentService) {}

  public form!: FormGroup;
  currencies!: Currency[];

  ngOnInit(): void {
    this.getCurrencies();
    this.form = new FormGroup({
      userEmail: new FormControl(sessionStorage.getItem('email')),
      currencyTicker: new FormControl(),
      walletAddress: new FormControl(),
      montant: new FormControl()
    });
  }

  doTransfer(){
    this.paymentService.doTransfer(this.form.value).subscribe({
      error: (error: HttpErrorResponse) => {
        alert(error)      
    },
    complete: () => {
      console.log('ok');
    }
  }
  )
  }

  public getCurrencies(): void {
    this.currencyService.getAllCurrencies().subscribe({
        next: (response: Currency[]) => {
          this.currencies = response || [];
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        }
      }
    );
  }

}
