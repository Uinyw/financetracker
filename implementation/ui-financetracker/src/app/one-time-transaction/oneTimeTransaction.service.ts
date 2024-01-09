import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OneTimeTransaction } from './oneTimeTransaction';

@Injectable({
  providedIn: 'root',
})
export class OneTimeTransactionService {

  BASE_URL = "http://localhost:8080"

  constructor(private httpClient: HttpClient) {
  }

  getAllOneTimeTransactions(): Observable<OneTimeTransaction[]> {
    return this.httpClient.get<OneTimeTransaction[]>(`${this.BASE_URL}/transactions/onetime`);
  }

  getAllOneTimeTransactionsBySource(sourceBankAccountId: string): Observable<OneTimeTransaction[]> {
    return this.httpClient.get<OneTimeTransaction[]>(`${this.BASE_URL}/transactions/onetime?sourceBankAccount=${sourceBankAccountId}`);
  }

  getAllOneTimeTransactionsByTarget(sourceBankAccountId: string): Observable<OneTimeTransaction[]> {
    return this.httpClient.get<OneTimeTransaction[]>(`${this.BASE_URL}/transactions/onetime?targetBankAccount=${sourceBankAccountId}`);
  }

  createOneTimeTransaction(transaction: OneTimeTransaction): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/transactions/onetime`, transaction);
  }

  deleteOneTimeTransaction(transactionId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.BASE_URL}/transactions/onetime/${transactionId}`);
  }

  editOneTimeTransaction(transaction: OneTimeTransaction): Observable<any> {
    return this.httpClient.patch<any>(`${this.BASE_URL}/transactions/onetime/${transaction.id}`, transaction);
  }

  transferOneTimeTransaction(transactionId: string): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/transactions/onetime/${transactionId}/transfer`, null);
  }

}