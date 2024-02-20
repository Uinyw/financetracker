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
import { SavingsGoalRuleBasedComponent } from './savings-goal-rule-based/savings-goal-rule-based.component';
import { SavingsGoalRuleBasedDetailComponent } from './savings-goal-rule-based-detail/savings-goal-rule-based-detail.component';
import { SavingsGoalPeriodicalComponent } from './savings-goal-periodical/savings-goal-periodical.component';
import { SavingsGoalPeriodicalDetailComponent } from './savings-goal-periodical-detail/savings-goal-periodical-detail.component';

const routes: Routes = [
  { path: 'bank-accounts', component: BankAccountComponent },
  { path: 'bank-accounts/:id', component: BankAccountDetailComponent },
  { path: 'transactions/one-time', component: OneTimeTransactionComponent },
  { path: 'transactions/recurring', component: RecurringTransactionComponent },
  { path: 'transactions/recurring/:id', component: TransactionRecordComponent },
  { path: 'products', component: ProductComponent },
  { path: 'shopping-cart', component: ShoppingCartComponent },
  { path: 'supplies', component: SuppliesComponent },
  { path: 'savings-goals/rule-based', component: SavingsGoalRuleBasedComponent },
  { path: 'savings-goals/rule-based/:id', component: SavingsGoalRuleBasedDetailComponent },
  { path: 'savings-goals/periodical', component: SavingsGoalPeriodicalComponent },
  { path: 'savings-goals/periodical/:id', component: SavingsGoalPeriodicalDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
