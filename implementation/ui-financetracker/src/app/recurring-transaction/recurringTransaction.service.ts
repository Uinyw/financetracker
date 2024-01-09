import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RecurringTransaction, TransactionRecord } from './recurringTransaction';

@Injectable({
  providedIn: 'root',
})
export class RecurringTransactionService {

  BASE_URL = "http://localhost:8080"

  constructor(private httpClient: HttpClient) {
  }

  getAllRecurringTransactions(): Observable<RecurringTransaction[]> {
    return this.httpClient.get<RecurringTransaction[]>(`${this.BASE_URL}/transactions/recurring`);
  }

  getRecurringTransaction(transactionId: string): Observable<RecurringTransaction> {
    return this.httpClient.get<RecurringTransaction>(`${this.BASE_URL}/transactions/recurring/${transactionId}`);
  }

  createRecurringTransaction(transaction: RecurringTransaction): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/transactions/recurring`, transaction);
  }

  deleteRecurringTransaction(transactionId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.BASE_URL}/transactions/recurring/${transactionId}`);
  }

  editRecurringTransaction(transaction: RecurringTransaction): Observable<any> {
    return this.httpClient.patch<any>(`${this.BASE_URL}/transactions/recurring/${transaction.id}`, transaction);
  }

  createTransactionRecord(transactionId: string, transactionRecord: TransactionRecord): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/transactions/recurring/${transactionId}/records`, transactionRecord);
  }

  deleteTransactionRecord(transactionId: string, transactionRecordId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.BASE_URL}/transactions/recurring/${transactionId}/records/${transactionRecordId}`);
  }

  transferTransactionRecord(transactionId: string, transactionRecordId: string): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/transactions/recurring/${transactionId}/records/${transactionRecordId}/transfer`, null);
  }

}