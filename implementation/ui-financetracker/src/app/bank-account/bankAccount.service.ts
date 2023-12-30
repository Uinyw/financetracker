import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BankAccount } from './bankAccount';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BankAccountService {

  BASE_URL = "http://localhost:8081"

  constructor(private httpClient: HttpClient) {
  }

  getAllBankAccounts(): Observable<BankAccount[]> {
    return this.httpClient.get<BankAccount[]>(`${this.BASE_URL}/bankAccounts`);
  }

  createBankAccount(bankAccount: BankAccount): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/bankAccounts`, bankAccount);
  }

  deleteBankAccount(bankAccountId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.BASE_URL}/bankAccounts/${bankAccountId}`);
  }

  editBankAccount(bankAccount: BankAccount): Observable<any> {
    return this.httpClient.patch<any>(`${this.BASE_URL}/bankAccounts/${bankAccount.id}`, bankAccount);
  }

}