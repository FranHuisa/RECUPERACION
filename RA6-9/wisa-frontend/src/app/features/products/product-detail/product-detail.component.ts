import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../../core/services/product.service';
import { AuthService } from '../../../core/services/auth.service';
import { Product } from '../../../core/models/product.model';
import { SpinnerComponent } from '../../../shared/components/spinner/spinner.component';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, SpinnerComponent],
  template: `
    <div class="page-container">
      <a routerLink="/products" class="btn-back">← Volver a productos</a>

      <app-spinner *ngIf="loading" />
      <div class="alert-error" *ngIf="errorMsg">{{ errorMsg }}</div>

      <div class="detail-card" *ngIf="product && !loading">
        <h1>{{ product.name }}</h1>
        <span class="badge">{{ product.category?.name }}</span>
        <p class="detail-description">{{ product.description }}</p>
        <div class="detail-meta">
          <span class="detail-price">{{ product.price | currency:'EUR' }}</span>
          <span class="detail-stock">Stock: {{ product.stock }} unidades</span>
        </div>
        <div class="card-actions" *ngIf="auth.isAdmin()">
          <a [routerLink]="['/products', product.id, 'edit']" class="btn-primary">Editar</a>
        </div>
      </div>
    </div>
  `
})
export class ProductDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private productService = inject(ProductService);
  auth = inject(AuthService);

  product: Product | null = null;
  loading = false;
  errorMsg = '';

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.loading = true;
    this.productService.getById(id).subscribe({
      next: data => {
        this.product = data;
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Producto no encontrado.';
        this.loading = false;
      }
    });
  }
}