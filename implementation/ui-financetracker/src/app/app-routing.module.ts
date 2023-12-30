import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BankAccountComponent } from './bank-account/bank-account.component';
import { OneTimeTransactionComponent } from './one-time-transaction/one-time-transaction.component';

const routes: Routes = [
  { path: 'bank-accounts', component: BankAccountComponent },
  { path: 'transactions/one-time', component: OneTimeTransactionComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
