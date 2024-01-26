import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BankAccountComponent } from './bank-account/bank-account.component';
import { OneTimeTransactionComponent } from './one-time-transaction/one-time-transaction.component';
import { RecurringTransactionComponent } from './recurring-transaction/recurring-transaction.component';
import { TransactionRecordComponent } from './transaction-record/transaction-record.component';
import { BankAccountDetailComponent } from './bank-account-detail/bank-account-detail.component';
import { ProductComponent } from './product/product.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { SuppliesComponent } from './supplies/supplies.component';

const routes: Routes = [
  { path: 'bank-accounts', component: BankAccountComponent },
  { path: 'bank-accounts/:id', component: BankAccountDetailComponent },
  { path: 'transactions/one-time', component: OneTimeTransactionComponent },
  { path: 'transactions/recurring', component: RecurringTransactionComponent },
  { path: 'transactions/recurring/:id', component: TransactionRecordComponent },
  { path: 'products', component: ProductComponent },
  { path: 'shopping-cart', component: ShoppingCartComponent },
  { path: 'supplies', component: SuppliesComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
