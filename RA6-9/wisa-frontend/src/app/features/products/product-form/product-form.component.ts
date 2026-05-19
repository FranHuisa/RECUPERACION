import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../../core/services/product.service';
import { CategoryService } from '../../../core/services/category.service';
import { Category } from '../../../core/models/category.model';
import { SpinnerComponent } from '../../../shared/components/spinner/spinner.component';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink, SpinnerComponent],
  template: `
    <div class="page-container">
      <a routerLink="/products" class="btn-back">← Volver a productos</a>
      <h1>{{ isEdit ? 'Editar producto' : 'Nuevo producto' }}</h1>

      <app-spinner *ngIf="loading" />
      <div class="alert-error" *ngIf="errorMsg">{{ errorMsg }}</div>
      <div class="alert-success" *ngIf="successMsg">{{ successMsg }}</div>

      <form [formGroup]="form" (ngSubmit)="onSubmit()" class="form-card">
        <div class="form-group">
          <label>Nombre</label>
          <input formControlName="name" type="text" placeholder="Nombre del producto" />
          <span class="field-error" *ngIf="form.get('name')?.invalid && form.get('name')?.touched">
            El nombre es obligatorio
          </span>
        </div>

        <div class="form-group">
          <label>Descripción</label>
          <textarea formControlName="description" rows="3" placeholder="Descripción del producto"></textarea>
          <span class="field-error" *ngIf="form.get('description')?.invalid && form.get('description')?.touched">
            La descripción es obligatoria
          </span>
        </div>

        <div class="form-group">
          <label>Precio (€)</label>
          <input formControlName="price" type="number" min="0" step="0.01" />
          <span class="field-error" *ngIf="form.get('price')?.invalid && form.get('price')?.touched">
            Introduce un precio válido mayor que 0
          </span>
        </div>

        <div class="form-group">
          <label>Stock</label>
          <input formControlName="stock" type="number" min="0" />
          <span class="field-error" *ngIf="form.get('stock')?.invalid && form.get('stock')?.touched">
            El stock no puede ser negativo
          </span>
        </div>

        <div class="form-group">
          <label>Categoría</label>
          <select formControlName="categoryId">
            <option value="" disabled>Selecciona una categoría</option>
            <option *ngFor="let cat of categories" [value]="cat.id">{{ cat.name }}</option>
          </select>
          <span class="field-error" *ngIf="form.get('categoryId')?.invalid && form.get('categoryId')?.touched">
            Selecciona una categoría
          </span>
        </div>

        <div class="form-actions">
          <a routerLink="/products" class="btn-secondary">Cancelar</a>
          <button type="submit" [disabled]="form.invalid || loading" class="btn-primary">
            {{ isEdit ? 'Guardar cambios' : 'Crear producto' }}
          </button>
        </div>
      </form>
    </div>
  `
})
export class ProductFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private productService = inject(ProductService);
  private categoryService = inject(CategoryService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  isEdit = false;
  productId: number | null = null;
  categories: Category[] = [];
  loading = false;
  errorMsg = '';
  successMsg = '';

  form = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    price: [0, [Validators.required, Validators.min(0.01)]],
    stock: [0, [Validators.required, Validators.min(0)]],
    categoryId: ['', Validators.required]
  });

  ngOnInit(): void {
    this.categoryService.getAll().subscribe(cats => (this.categories = cats));

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.productId = Number(id);
      this.loading = true;
      this.productService.getById(this.productId).subscribe({
        next: product => {
          this.form.patchValue({
            name: product.name,
            description: product.description,
            price: product.price,
            stock: product.stock,
            categoryId: String(product.category?.id)
          });
          this.loading = false;
        },
        error: () => {
          this.errorMsg = 'Error al cargar el producto.';
          this.loading = false;
        }
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    this.loading = true;
    this.errorMsg = '';
    const payload = { ...this.form.value, categoryId: Number(this.form.value.categoryId) } as any;

    const request$ = this.isEdit
      ? this.productService.update(this.productId!, payload)
      : this.productService.create(payload);

    request$.subscribe({
      next: () => {
        this.loading = false;
        this.successMsg = this.isEdit ? 'Producto actualizado.' : 'Producto creado.';
        setTimeout(() => this.router.navigate(['/products']), 1200);
      },
      error: () => {
        this.loading = false;
        this.errorMsg = 'Error al guardar el producto.';
      }
    });
  }
}