import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductEntry, ShoppingCart } from './shoppingCart';

@Injectable({
  providedIn: 'root',
})
export class ShoppingCartService {

  BASE_URL = "http://localhost:8083"

  constructor(private httpClient: HttpClient) {
  }

  getShoppingCart(): Observable<ShoppingCart> {
    return this.httpClient.get<ShoppingCart>(`${this.BASE_URL}/shopping-cart`);
  }
  
  createProductEntry(productEntry: ProductEntry): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/shopping-cart/entries`, productEntry);
  }

  deleteProductEntry(productEntryId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.BASE_URL}/shopping-cart/entries/${productEntryId}`);
  }

  editProductEntry(productEntry: ProductEntry): Observable<any> {
    return this.httpClient.patch<any>(`${this.BASE_URL}/shopping-cart/entries/${productEntry.id}`, productEntry);
  }

  markProductEntryAsPurchased(productEntryId: string): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/shopping-cart/entries/${productEntryId}/mark`, null);
  }

  purchaseShoppingCart(bankAccountId: string): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/shopping-cart/purchase`, { sourceBankAccountId: bankAccountId});
  }

}