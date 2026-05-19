import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  template: `
    <nav class="navbar">
      <div class="navbar-brand">
        <a routerLink="/">Wisa</a>
      </div>
      <div class="navbar-links" *ngIf="auth.isAuthenticated()">
        <a routerLink="/products" routerLinkActive="active">Productos</a>
        <a routerLink="/categories" routerLinkActive="active">Categorías</a>
      </div>
      <div class="navbar-actions">
        <ng-container *ngIf="auth.isAuthenticated(); else guestTpl">
          <span class="navbar-user">{{ auth.currentUser()?.username }}</span>
          <span *ngIf="auth.isAdmin()" class="badge-admin">Admin</span>
          <button (click)="auth.logout()" class="btn-logout">Cerrar sesión</button>
        </ng-container>
        <ng-template #guestTpl>
          <a routerLink="/auth/login" class="btn-login">Iniciar sesión</a>
          <a routerLink="/auth/register" class="btn-register">Registrarse</a>
        </ng-template>
      </div>
    </nav>
  `
})
export class NavbarComponent {
  auth = inject(AuthService);
}