import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Category, CategoryRequest } from '../models/category.model';

@Injectable({ providedIn: 'root' })
export class CategoryService {
  private readonly url = `${environment.apiUrl}/categories`;

  constructor(private http: HttpClient) {}

  /**
   * Obtiene el listado completo de categorías.
   */
  getAll(): Observable<Category[]> {
    return this.http.get<Category[]>(this.url);
  }

  /**
   * Obtiene una categoría por su ID.
   * @param id - Identificador de la categoría
   */
  getById(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.url}/${id}`);
  }

  /**
   * Crea una nueva categoría (solo admin).
   * @param data - Datos de la categoría a crear
   */
  create(data: CategoryRequest): Observable<Category> {
    return this.http.post<Category>(this.url, data);
  }

  /**
   * Actualiza una categoría existente (solo admin).
   * @param id - Identificador de la categoría
   * @param data - Datos actualizados
   */
  update(id: number, data: CategoryRequest): Observable<Category> {
    return this.http.put<Category>(`${this.url}/${id}`, data);
  }

  /**
   * Elimina una categoría (solo admin).
   * @param id - Identificador de la categoría
   */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}