import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';

interface SupplierPayment {
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
    MatIconModule,
    MatButtonModule,
    MatTableModule
  ],
  templateUrl: './milk-supplier-payment.component.html',
  styleUrls: ['./milk-supplier-payment.component.css']
})
export class MilkSupplierPaymentComponent implements OnInit {
  displayedColumns: string[] = ['id', 'fullName', 'totalLiters', 'accumulatedPayment', 'actions'];
  dataSource: SupplierPayment[] = [];

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Datos de ejemplo - reemplazar con llamada a tu servicio
    this.dataSource = [
      { id: '001', fullName: 'Juan Pérez', totalLiters: 150, accumulatedPayment: 4500 },
      { id: '002', fullName: 'María García', totalLiters: 230, accumulatedPayment: 6900 },
      { id: '003', fullName: 'Carlos López', totalLiters: 180, accumulatedPayment: 5400 },
    ];
  }

  goBack(): void {
    this.router.navigate(['/financial-management']);
  }

  viewDetails(supplierId: string): void {
    // Navegar a la vista de detalles del proveedor
    this.router.navigate([`/financial-management/supplier-details/${supplierId}`]);
  }
}
