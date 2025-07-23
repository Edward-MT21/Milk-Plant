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
      }
      
];
