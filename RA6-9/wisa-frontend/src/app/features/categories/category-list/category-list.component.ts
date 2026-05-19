import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CategoryService } from '../../../core/services/category.service';
import { AuthService } from '../../../core/services/auth.service';
import { Category } from '../../../core/models/category.model';
import { SpinnerComponent } from '../../../shared/components/spinner/spinner.component';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [CommonModule, RouterLink, SpinnerComponent, ConfirmDialogComponent],
  template: `
    <div class="page-container">
      <div class="page-header">
        <h1>Categorías</h1>
        <a *ngIf="auth.isAdmin()" routerLink="/categories/new" class="btn-primary">+ Nueva categoría</a>
      </div>

      <app-spinner *ngIf="loading" />
      <div class="alert-error" *ngIf="errorMsg">{{ errorMsg }}</div>
      <div class="alert-success" *ngIf="successMsg">{{ successMsg }}</div>

      <div class="grid" *ngIf="!loading">
        <div class="card" *ngFor="let category of categories">
          <h3>{{ category.name }}</h3>
          <p>{{ category.description }}</p>
          <div class="card-actions">
            <a [routerLink]="['/categories', category.id]" class="btn-secondary">Ver</a>
            <a *ngIf="auth.isAdmin()" [routerLink]="['/categories', category.id, 'edit']" class="btn-secondary">Editar</a>
            <button *ngIf="auth.isAdmin()" (click)="askDelete(category)" class="btn-danger">Eliminar</button>
          </div>
        </div>

        <p *ngIf="categories.length === 0" class="empty-state">No hay categorías disponibles.</p>
      </div>

      <app-confirm-dialog
        *ngIf="showConfirm"
        title="Eliminar categoría"
        [message]="'¿Eliminar ' + selectedCategory?.name + '?'"
        (confirm)="confirmDelete()"
        (cancel)="showConfirm = false"
      />
    </div>
  `
})
export class CategoryListComponent implements OnInit {
  private categoryService = inject(CategoryService);
  auth = inject(AuthService);

  categories: Category[] = [];
  loading = false;
  errorMsg = '';
  successMsg = '';
  showConfirm = false;
  selectedCategory: Category | null = null;

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.categoryService.getAll().subscribe({
      next: data => {
        this.categories = data;
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Error al cargar las categorías.';
        this.loading = false;
      }
    });
  }

  askDelete(category: Category): void {
    this.selectedCategory = category;
    this.showConfirm = true;
  }

  confirmDelete(): void {
    if (!this.selectedCategory) return;
    this.showConfirm = false;
    this.categoryService.delete(this.selectedCategory.id).subscribe({
      next: () => {
        this.successMsg = 'Categoría eliminada correctamente.';
        this.load();
      },
      error: () => {
        this.errorMsg = 'Error al eliminar la categoría.';
      }
    });
  }
}