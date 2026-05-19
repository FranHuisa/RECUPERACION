import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../../core/services/product.service';
import { AuthService } from '../../../core/services/auth.service';
import { Product } from '../../../core/models/product.model';
import { SpinnerComponent } from '../../../shared/components/spinner/spinner.component';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, RouterLink, SpinnerComponent, ConfirmDialogComponent],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h1>Productos</h1>
        <a *ngIf="auth.isAdmin()" routerLink="/products/new" class="btn-primary">+ Nuevo producto</a>
      </div>

      <app-spinner *ngIf="loading" />

      <div class="alert-error" *ngIf="errorMsg">{{ errorMsg }}</div>
      <div class="alert-success" *ngIf="successMsg">{{ successMsg }}</div>

      <div class="grid" *ngIf="!loading">
        <div class="card" *ngFor="let product of products">
          <h3>{{ product.name }}</h3>
          <p class="card-meta">{{ product.category?.name }}</p>
          <p class="card-price">{{ product.price | currency:'EUR' }}</p>
          <p class="card-stock">Stock: {{ product.stock }}</p>
          <div class="card-actions">
            <a [routerLink]="['/products', product.id]" class="btn-secondary">Ver</a>
            <a *ngIf="auth.isAdmin()" [routerLink]="['/products', product.id, 'edit']" class="btn-secondary">Editar</a>
            <button *ngIf="auth.isAdmin()" (click)="askDelete(product)" class="btn-danger">Eliminar</button>
          </div>
        </div>

        <p *ngIf="products.length === 0" class="empty-state">No hay productos disponibles.</p>
      </div>

      <app-confirm-dialog
        *ngIf="showConfirm"
        title="Eliminar producto"
        [message]="'¿Eliminar ' + selectedProduct?.name + '?'"
        (confirm)="confirmDelete()"
        (cancel)="showConfirm = false"
      />
    </div>
  `
})
export class ProductListComponent implements OnInit {
  private productService = inject(ProductService);
  auth = inject(AuthService);

  products: Product[] = [];
  loading = false;
  errorMsg = '';
  successMsg = '';
  showConfirm = false;
  selectedProduct: Product | null = null;

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.productService.getAll().subscribe({
      next: data => {
        this.products = data;
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Error al cargar los productos.';
        this.loading = false;
      }
    });
  }

  askDelete(product: Product): void {
    this.selectedProduct = product;
    this.showConfirm = true;
  }

  confirmDelete(): void {
    if (!this.selectedProduct) return;
    this.showConfirm = false;
    this.productService.delete(this.selectedProduct.id).subscribe({
      next: () => {
        this.successMsg = 'Producto eliminado correctamente.';
        this.load();
      },
      error: () => {
        this.errorMsg = 'Error al eliminar el producto.';
      }
    });
  }
}