import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CategoryService } from '../../../core/services/category.service';
import { AuthService } from '../../../core/services/auth.service';
import { Category } from '../../../core/models/category.model';
import { SpinnerComponent } from '../../../shared/components/spinner/spinner.component';

@Component({
  selector: 'app-category-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, SpinnerComponent],
  template: `
    <div class="page-container">
      <a routerLink="/categories" class="btn-back">← Volver a categorías</a>

      <app-spinner *ngIf="loading" />
      <div class="alert-error" *ngIf="errorMsg">{{ errorMsg }}</div>

      <div class="detail-card" *ngIf="category && !loading">
        <h1>{{ category.name }}</h1>
        <p class="detail-description">{{ category.description }}</p>
        <div class="card-actions" *ngIf="auth.isAdmin()">
          <a [routerLink]="['/categories', category.id, 'edit']" class="btn-primary">Editar</a>
        </div>
      </div>
    </div>
  `
})
export class CategoryDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private categoryService = inject(CategoryService);
  auth = inject(AuthService);

  category: Category | null = null;
  loading = false;
  errorMsg = '';

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.loading = true;
    this.categoryService.getById(id).subscribe({
      next: data => {
        this.category = data;
        this.loading = false;
      },
      error: () => {
        this.errorMsg = 'Categoría no encontrada.';
        this.loading = false;
      }
    });
  }
}