import { Routes } from '@angular/router';
import { MilkCollectionComponent } from './milk-collection/milk-collection.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'milk-collection',
        pathMatch: 'full'
      },
      {
        path: 'milk-collection',
        component: MilkCollectionComponent
      },
      {
        path: 'person-register',
        loadComponent: () =>
          import('./person-register/person-register.component').then(m => m.PersonRegisterComponent)
      },
      {
        path: 'milk-supplier',
        loadComponent: () =>
          import('./milk-supplier/milk-supplier.component').then(m => m.MilkSupplierComponent)
      },
      {
        path: 'milk-collection-form',
        loadComponent: () =>
          import('./milk-collection/milk-collection-form/milk-collection-form.component').then(m => m.MilkCollectionFormComponent)
      },
      {
        path: 'financial-management',
        loadComponent: () =>
          import('./financial-management/financial-management.component').then(m => m.FinancialManagementComponent)
      },
      {
        path: 'financial-management/milk-supplier-payment',
        loadComponent: () =>
          import('./financial-management/milk-supplier-payment/milk-supplier-payment.component').then(m => m.MilkSupplierPaymentComponent)
      }
      
];
