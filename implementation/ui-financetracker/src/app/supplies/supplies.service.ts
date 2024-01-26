import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Supplies } from './supplies';
import { ProductEntry } from '../shopping-cart/shoppingCart';

@Injectable({
  providedIn: 'root',
})
export class SuppliesService {

  BASE_URL = "http://localhost:8083"

  constructor(private httpClient: HttpClient) {
  }

  getSupplies(): Observable<Supplies> {
    return this.httpClient.get<Supplies>(`${this.BASE_URL}/supplies`);
  }
  
  createProductEntry(productEntry: ProductEntry): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/supplies/entries`, productEntry);
  }

  deleteProductEntry(productEntryId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.BASE_URL}/supplies/entries/${productEntryId}`);
  }

  editProductEntry(productEntry: ProductEntry): Observable<any> {
    return this.httpClient.patch<any>(`${this.BASE_URL}/supplies/entries/${productEntry.id}`, productEntry);
  }

  addRequiredProductsToShoppingCart(): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/supplies/shop`, null);
  }

}