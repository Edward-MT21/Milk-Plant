import { Component, OnInit } from '@angular/core';
import { NgForOf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MilkCollectionService, MilkSupplierCollectionDTO } from '../../services/milk-collection.service';

interface SupplierRow {
  milkSupplierId: number;
  names: string;
  lastNames: string;
  identificationNumber: string;
  litersByDay: { [day: number]: number };
}

@Component({
  selector: 'app-milk-collection-form',
  standalone: true,
  imports: [NgForOf, FormsModule],
  templateUrl: './milk-collection-form.component.html',
  styleUrl: './milk-collection-form.component.css'
})
export class MilkCollectionFormComponent implements OnInit {
  supplierRows: SupplierRow[] = [];
  selectedMonth: number = new Date().getMonth() + 1; // 1-12
  selectedYear: number = new Date().getFullYear();
  quincena: 1 | 2 = 1;
  days: number[] = [];
  loading = false;

  constructor(private milkCollectionService: MilkCollectionService) {}

  ngOnInit() {
    this.updateDays();
    this.loadData();
  }

  updateDays() {
    if (this.quincena === 1) {
      this.days = Array.from({ length: 15 }, (_, i) => i + 1);
    } else {
      const lastDay = new Date(this.selectedYear, this.selectedMonth, 0).getDate();
      this.days = Array.from({ length: lastDay - 15 }, (_, i) => i + 16);
    }
  }

  getMonthStartEnd(): { fechaInicio: string, fechaFin: string } {
    const fechaInicio = `${this.selectedYear}-${this.selectedMonth.toString().padStart(2, '0')}-01`;
    const lastDay = new Date(this.selectedYear, this.selectedMonth, 0).getDate();
    const fechaFin = `${this.selectedYear}-${this.selectedMonth.toString().padStart(2, '0')}-${lastDay}`;
    return { fechaInicio, fechaFin };
  }

  loadData() {
    this.loading = true;
    const { fechaInicio, fechaFin } = this.getMonthStartEnd();
    this.milkCollectionService.fetchAllMilkCollectionDetailsByDateRange(fechaInicio, fechaFin)
      .subscribe({
        next: (data: MilkSupplierCollectionDTO[]) => {
          this.supplierRows = this.processData(data);
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });
  }

  processData(data: MilkSupplierCollectionDTO[]): SupplierRow[] {
    // Mapeo de días del mes
    const allDays = Array.from({ length: new Date(this.selectedYear, this.selectedMonth, 0).getDate() }, (_, i) => i + 1);
    return data.map(supplier => {
      const { milkSupplierId, personOutDto, collections } = supplier;
      const litersByDay: { [day: number]: number } = {};
      allDays.forEach(day => { litersByDay[day] = 0; });
      collections.forEach(col => {
        const day = new Date(col.createdAt).getDate();
        litersByDay[day] = col.litersMilk;
      });
      return {
        milkSupplierId,
        names: personOutDto.names,
        lastNames: personOutDto.lastNames,
        identificationNumber: personOutDto.identificationNumber,
        litersByDay
      };
    });
  }

  prevQuincena() {
    if (this.quincena === 2) {
      this.quincena = 1;
    } else {
      // Cambiar a mes anterior
      if (this.selectedMonth === 1) {
        this.selectedMonth = 12;
        this.selectedYear--;
      } else {
        this.selectedMonth--;
      }
      this.quincena = 2;
    }
    this.updateDays();
    this.loadData();
  }

  nextQuincena() {
    const lastDay = new Date(this.selectedYear, this.selectedMonth, 0).getDate();
    if (this.quincena === 1) {
      this.quincena = 2;
    } else {
      // Cambiar a mes siguiente
      if (this.selectedMonth === 12) {
        this.selectedMonth = 1;
        this.selectedYear++;
      } else {
        this.selectedMonth++;
      }
      this.quincena = 1;
    }
    this.updateDays();
    this.loadData();
  }

  onMonthChange(event: any) {
    const [year, month] = event.target.value.split('-');
    this.selectedYear = +year;
    this.selectedMonth = +month;
    this.quincena = 1;
    this.updateDays();
    this.loadData();
  }

  get monthYearLabel() {
    return `${this.selectedYear}-${this.selectedMonth.toString().padStart(2, '0')}`;
  }

  get monthYearText() {
    const meses = [
      'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
      'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
    ];
    return `${meses[this.selectedMonth - 1]} del ${this.selectedYear}`;
  }
}
