import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from './product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {

  BASE_URL = "http://localhost:8083"

  constructor(private httpClient: HttpClient) {
  }

  getAllProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.BASE_URL}/products`);
  }
  
  createProduct(product: Product): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/products`, product);
  }

  deleteProduct(productId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.BASE_URL}/products/${productId}`);
  }

  editProduct(product: Product): Observable<any> {
    return this.httpClient.patch<any>(`${this.BASE_URL}/products/${product.id}`, product);
  }

}