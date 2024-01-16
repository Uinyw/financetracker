import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon'
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort'
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialogModule } from "@angular/material/dialog";
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatToolbarModule,
    MatTableModule,
    MatFormFieldModule,
    MatSortModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatPaginatorModule,
    MatButtonModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  exports: [MatSidenavModule]
})
export class AppModule { }
