import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { DatePipe } from '@angular/common';
import { MilkCollectionService, InfoMilkSupplierPaymentDto } from '../../services/milk-collection.service';
import { NgModel } from '@angular/forms';

export interface SupplierPayment {
  id: string;
  fullName: string;
  totalLiters: number;
  accumulatedPayment: number;
}

@Component({
  selector: 'app-milk-supplier-payment',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatDatepickerModule,
    MatInputModule,
    MatFormFieldModule,
    MatNativeDateModule,
    DatePipe,
  ],
  templateUrl: './milk-supplier-payment.component.html',
  styleUrls: ['./milk-supplier-payment.component.css']
})
export class MilkSupplierPaymentComponent implements OnInit {
  displayedColumns: string[] = ['id', 'fullName', 'totalLiters', 'accumulatedPayment', 'actions'];
  dataSource: SupplierPayment[] = [];
  isLoading = true;
  error: string | null = null;
  currentDate = new Date();
  startDate: Date = new Date();
  endDate: Date = new Date();
  currentMonthName: string = '';
  currentFortnight: string = '';

  // Spanish month names
  private months = [
    'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
    'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
  ];

  constructor(
    private router: Router,
    private milkCollectionService: MilkCollectionService
  ) {
    this.setDateRange(new Date());
  }

  private setDateRange(date: Date): void {
    const currentDate = date.getDate();
    const currentMonth = date.getMonth();
    const currentYear = date.getFullYear();

    // Set month name
    this.currentMonthName = this.months[currentMonth];

    // Determine if we're in the first (1-15) or second (16-end of month) half of the month
    const isFirstFortnight = currentDate <= 15;

    if (isFirstFortnight) {
      // First fortnight: 1st to 15th of the current month
      this.startDate = new Date(currentYear, currentMonth, 1);
      this.endDate = new Date(currentYear, currentMonth, 15);
      this.currentFortnight = 'Primera quincena';
    } else {
      // Second fortnight: 16th to end of the current month
      this.startDate = new Date(currentYear, currentMonth, 16);
      // Set end date to the last day of the current month
      this.endDate = new Date(currentYear, currentMonth + 1, 0);
      this.currentFortnight = 'Segunda quincena';
    }
  }

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments(): void {
    this.isLoading = true;
    this.error = null;

    this.milkCollectionService
      .getBiweeklyMilkSupplierPayments(this.formatDate(this.startDate), this.formatDate(this.endDate))
      .subscribe({
        next: (data) => {
          this.dataSource = data.map(item => ({
            id: item.milkSupplierId.toString(),
            fullName: `${item.personOutDto.names} ${item.personOutDto.lastNames}`,
            totalLiters: item.totalLitersMilk,
            accumulatedPayment: item.totalAmount
          }));
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error loading payments:', err);
          this.error = 'Error al cargar los pagos. Por favor intente de nuevo.';
          this.isLoading = false;
        }
      });
  }

  navigateFortnight(direction: 'prev' | 'next'): void {
    const newDate = new Date(this.startDate);

    if (direction === 'prev') {
      // Go to previous fortnight
      if (this.currentFortnight === 'Primera quincena') {
        // If currently in first fortnight, go to second fortnight of previous month
        newDate.setMonth(newDate.getMonth() - 1);
        newDate.setDate(16);
      } else {
        // If currently in second fortnight, go to first fortnight of current month
        newDate.setDate(1);
      }
    } else {
      // Go to next fortnight
      if (this.currentFortnight === 'Segunda quincena') {
        // If currently in second fortnight, go to first fortnight of next month
        newDate.setMonth(newDate.getMonth() + 1);
        newDate.setDate(1);
      } else {
        // If currently in first fortnight, go to second fortnight of current month
        newDate.setDate(16);
      }
    }

    this.setDateRange(newDate);
    this.loadPayments();
  }

  onDateRangeChange(): void {
    this.loadPayments();
  }

  goBack(): void {
    this.router.navigate(['/financial-management']);
  }

  viewDetails(supplierId: string): void {
    this.router.navigate([`/financial-management/supplier-details/${supplierId}`]);
  }
}
