import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-financial-management',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './financial-management.component.html',
  styleUrls: ['./financial-management.component.css']
})
export class FinancialManagementComponent {
  
  constructor(private router: Router) {}

  navigateToPayments(): void {
    this.router.navigate(['/financial-management/milk-supplier-payment']);
  }
}
