import { Routes } from '@angular/router';
import { roleGuard } from '../../core/guards/role.guard';

export const CATEGORIES_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./category-list/category-list.component').then(m => m.CategoryListComponent)
  },
  {
    path: 'new',
    canActivate: [roleGuard],
    loadComponent: () =>
      import('./category-form/category-form.component').then(m => m.CategoryFormComponent)
  },
  {
    path: ':id',
    loadComponent: () =>
      import('./category-detail/category-detail.component').then(m => m.CategoryDetailComponent)
  },
  {
    path: ':id/edit',
    canActivate: [roleGuard],
    loadComponent: () =>
      import('./category-form/category-form.component').then(m => m.CategoryFormComponent)
  }
];