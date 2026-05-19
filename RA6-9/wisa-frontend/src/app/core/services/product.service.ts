import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Product, ProductRequest } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly url = `${environment.apiUrl}/products`;

  constructor(private http: HttpClient) {}

  /**
   * Obtiene el listado completo de productos.
   */
  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.url);
  }

  /**
   * Obtiene un producto por su ID.
   * @param id - Identificador del producto
   */
  getById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.url}/${id}`);
  }

  /**
   * Crea un nuevo producto (solo admin).
   * @param data - Datos del producto a crear
   */
  create(data: ProductRequest): Observable<Product> {
    return this.http.post<Product>(this.url, data);
  }

  /**
   * Actualiza un producto existente (solo admin).
   * @param id - Identificador del producto
   * @param data - Datos actualizados
   */
  update(id: number, data: ProductRequest): Observable<Product> {
    return this.http.put<Product>(`${this.url}/${id}`, data);
  }

  /**
   * Elimina un producto (solo admin).
   * @param id - Identificador del producto
   */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}