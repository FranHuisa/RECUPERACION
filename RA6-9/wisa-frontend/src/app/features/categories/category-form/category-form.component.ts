import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CategoryService } from '../../../core/services/category.service';
import { SpinnerComponent } from '../../../shared/components/spinner/spinner.component';

@Component({
  selector: 'app-category-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink, SpinnerComponent],
  template: `
    <div class="page-container">
      <a routerLink="/categories" class="btn-back">← Volver a categorías</a>
      <h1>{{ isEdit ? 'Editar categoría' : 'Nueva categoría' }}</h1>

      <app-spinner *ngIf="loading" />
      <div class="alert-error" *ngIf="errorMsg">{{ errorMsg }}</div>
      <div class="alert-success" *ngIf="successMsg">{{ successMsg }}</div>

      <form [formGroup]="form" (ngSubmit)="onSubmit()" class="form-card">
        <div class="form-group">
          <label>Nombre</label>
          <input formControlName="name" type="text" placeholder="Nombre de la categoría" />
          <span class="field-error" *ngIf="form.get('name')?.invalid && form.get('name')?.touched">
            El nombre es obligatorio
          </span>
        </div>

        <div class="form-group">
          <label>Descripción</label>
          <textarea formControlName="description" rows="3" placeholder="Descripción de la categoría"></textarea>
          <span class="field-error" *ngIf="form.get('description')?.invalid && form.get('description')?.touched">
            La descripción es obligatoria
          </span>
        </div>

        <div class="form-actions">
          <a routerLink="/categories" class="btn-secondary">Cancelar</a>
          <button type="submit" [disabled]="form.invalid || loading" class="btn-primary">
            {{ isEdit ? 'Guardar cambios' : 'Crear categoría' }}
          </button>
        </div>
      </form>
    </div>
  `
})
export class CategoryFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private categoryService = inject(CategoryService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  isEdit = false;
  categoryId: number | null = null;
  loading = false;
  errorMsg = '';
  successMsg = '';

  form = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required]
  });

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.categoryId = Number(id);
      this.loading = true;
      this.categoryService.getById(this.categoryId).subscribe({
        next: category => {
          this.form.patchValue({ name: category.name, description: category.description });
          this.loading = false;
        },
        error: () => {
          this.errorMsg = 'Error al cargar la categoría.';
          this.loading = false;
        }
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    this.loading = true;
    this.errorMsg = '';
    const payload = this.form.value as any;

    const request$ = this.isEdit
      ? this.categoryService.update(this.categoryId!, payload)
      : this.categoryService.create(payload);

    request$.subscribe({
      next: () => {
        this.loading = false;
        this.successMsg = this.isEdit ? 'Categoría actualizada.' : 'Categoría creada.';
        setTimeout(() => this.router.navigate(['/categories']), 1200);
      },
      error: () => {
        this.loading = false;
        this.errorMsg = 'Error al guardar la categoría.';
      }
    });
  }
}