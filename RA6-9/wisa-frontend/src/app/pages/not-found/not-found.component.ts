import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="not-found">
      <h1>404</h1>
      <p>La página que buscas no existe o no tienes permisos para acceder.</p>
      <a routerLink="/" class="btn-primary">Volver al inicio</a>
    </div>
  `
})
export class NotFoundComponent {}