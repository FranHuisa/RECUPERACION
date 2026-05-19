import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { SpinnerComponent } from '../../../shared/components/spinner/spinner.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink, SpinnerComponent],
  template: `
    <div class="auth-container">
      <div class="auth-card">
        <h2>Crear cuenta</h2>

        <app-spinner *ngIf="loading" />

        <div class="alert-error" *ngIf="errorMsg">{{ errorMsg }}</div>
        <div class="alert-success" *ngIf="successMsg">{{ successMsg }}</div>

        <form [formGroup]="form" (ngSubmit)="onSubmit()">
          <div class="form-group">
            <label>Usuario</label>
            <input formControlName="username" type="text" placeholder="Elige un nombre de usuario" />
            <span class="field-error" *ngIf="form.get('username')?.invalid && form.get('username')?.touched">
              El usuario es obligatorio (mín. 3 caracteres)
            </span>
          </div>

          <div class="form-group">
            <label>Email</label>
            <input formControlName="email" type="email" placeholder="tu@email.com" />
            <span class="field-error" *ngIf="form.get('email')?.invalid && form.get('email')?.touched">
              Introduce un email válido
            </span>
          </div>

          <div class="form-group">
            <label>Contraseña</label>
            <input formControlName="password" type="password" placeholder="Mínimo 6 caracteres" />
            <span class="field-error" *ngIf="form.get('password')?.invalid && form.get('password')?.touched">
              La contraseña debe tener al menos 6 caracteres
            </span>
          </div>

          <button type="submit" [disabled]="form.invalid || loading" class="btn-primary btn-full">
            Registrarse
          </button>
        </form>

        <p class="auth-link">¿Ya tienes cuenta? <a routerLink="/auth/login">Inicia sesión</a></p>
      </div>
    </div>
  `
})
export class RegisterComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  loading = false;
  errorMsg = '';
  successMsg = '';

  form = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  onSubmit(): void {
    if (this.form.invalid) return;

    this.loading = true;
    this.errorMsg = '';

    this.authService.register(this.form.value as any).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/products']);
      },
      error: () => {
        this.loading = false;
        this.errorMsg = 'No se pudo registrar el usuario. Inténtalo de nuevo.';
      }
    });
  }
}