import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { SpinnerComponent } from '../../../shared/components/spinner/spinner.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink, SpinnerComponent],
  template: `
    <div class="auth-container">
      <div class="auth-card">
        <h2>Iniciar sesión</h2>

        <app-spinner *ngIf="loading" />

        <div class="alert-error" *ngIf="errorMsg">{{ errorMsg }}</div>

        <form [formGroup]="form" (ngSubmit)="onSubmit()">
          <div class="form-group">
            <label>Usuario</label>
            <input formControlName="username" type="text" placeholder="Tu usuario" />
            <span class="field-error" *ngIf="form.get('username')?.invalid && form.get('username')?.touched">
              El usuario es obligatorio
            </span>
          </div>

          <div class="form-group">
            <label>Contraseña</label>
            <input formControlName="password" type="password" placeholder="Tu contraseña" />
            <span class="field-error" *ngIf="form.get('password')?.invalid && form.get('password')?.touched">
              La contraseña es obligatoria (mín. 6 caracteres)
            </span>
          </div>

          <button type="submit" [disabled]="form.invalid || loading" class="btn-primary btn-full">
            Entrar
          </button>
        </form>

        <p class="auth-link">¿No tienes cuenta? <a routerLink="/auth/register">Regístrate</a></p>
      </div>
    </div>
  `
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  loading = false;
  errorMsg = '';

  form = this.fb.group({
    username: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  onSubmit(): void {
    if (this.form.invalid) return;

    this.loading = true;
    this.errorMsg = '';

    this.authService.login(this.form.value as any).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/products']);
      },
      error: () => {
        this.loading = false;
        this.errorMsg = 'Credenciales incorrectas. Inténtalo de nuevo.';
      }
    });
  }
}