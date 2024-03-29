import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon'
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { NgApexchartsModule } from 'ng-apexcharts'
import { MatSortModule } from '@angular/material/sort'
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialogModule } from "@angular/material/dialog";
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { HttpClientModule } from '@angular/common/http';
import { BankAccountComponent } from './bank-account/bank-account.component';
import { BankAccountDialogComponent } from './bank-account-dialog/bank-account-dialog.component';
import { OneTimeTransactionComponent } from './one-time-transaction/one-time-transaction.component';
import { OneTimeTransactionDialogComponent } from './one-time-transaction-dialog/one-time-transaction-dialog.component';
import { RecurringTransactionComponent } from './recurring-transaction/recurring-transaction.component';
import { RecurringTransactionDialogComponent } from './recurring-transaction-dialog/recurring-transaction-dialog.component';
import { TransactionRecordComponent } from './transaction-record/transaction-record.component';
import { TransactionRecordDialogComponent } from './transaction-record-dialog/transaction-record-dialog.component';
import { BankAccountDetailComponent } from './bank-account-detail/bank-account-detail.component';
import { ProductComponent } from './product/product.component';
import { ProductDialogComponent } from './product-dialog/product-dialog.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { ShoppingCartDialogComponent } from './shopping-cart-dialog/shopping-cart-dialog.component';
import { SuppliesComponent } from './supplies/supplies.component';
import { SuppliesDialogComponent } from './supplies-dialog/supplies-dialog.component';
import { SavingsGoalRuleBasedComponent } from './savings-goal-rule-based/savings-goal-rule-based.component';
import { SavingsGoalRuleBasedDialogComponent } from './savings-goal-rule-based-dialog/savings-goal-rule-based-dialog.component';
import { SavingsGoalRuleBasedDetailComponent } from './savings-goal-rule-based-detail/savings-goal-rule-based-detail.component';
import { SavingsGoalRuleBasedDetailDialogComponent } from './savings-goal-rule-based-detail-dialog/savings-goal-rule-based-detail-dialog.component';
import { SavingsGoalPeriodicalComponent } from './savings-goal-periodical/savings-goal-periodical.component';
import { SavingsGoalPeriodicalDetailComponent } from './savings-goal-periodical-detail/savings-goal-periodical-detail.component';
import { SavingsGoalPeriodicalDialogComponent } from './savings-goal-periodical-dialog/savings-goal-periodical-dialog.component';
import { AnalyticsComponent } from './analytics/analytics.component';



@NgModule({
  declarations: [
    AppComponent,
    SidenavComponent,
    BankAccountComponent,
    BankAccountDialogComponent,
    OneTimeTransactionComponent,
    OneTimeTransactionDialogComponent,
    RecurringTransactionComponent,
    RecurringTransactionDialogComponent,
    TransactionRecordComponent,
    TransactionRecordDialogComponent,
    BankAccountDetailComponent,
    ProductComponent,
    ProductDialogComponent,
    ShoppingCartComponent,
    ShoppingCartDialogComponent,
    SuppliesComponent,
    SuppliesDialogComponent,
    SavingsGoalRuleBasedComponent,
    SavingsGoalRuleBasedDialogComponent,
    SavingsGoalRuleBasedDetailComponent,
    SavingsGoalRuleBasedDetailDialogComponent,
    SavingsGoalPeriodicalComponent,
    SavingsGoalPeriodicalDetailComponent,
    SavingsGoalPeriodicalDialogComponent,
    AnalyticsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatTabsModule,
    MatToolbarModule,
    MatTableModule,
    MatFormFieldModule,
    MatSortModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatPaginatorModule,
    MatCheckboxModule,
    MatButtonModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    BrowserAnimationsModule,
    NgApexchartsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  exports: [MatSidenavModule]
})
export class AppModule { }
