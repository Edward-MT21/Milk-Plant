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

/**
 * Component responsible for displaying and managing the milk collection form.
 * Allows navigation between biweekly periods, fetching data from the backend,
 * and displaying daily milk collection per supplier.
 * @author Edward Malte
 */
@Component({
  selector: 'app-milk-collection-form',
  standalone: true,
  imports: [NgForOf, FormsModule],
  templateUrl: './milk-collection-form.component.html',
  styleUrl: './milk-collection-form.component.css'
})
export class MilkCollectionFormComponent implements OnInit {

  /** List of suppliers with their personal data and daily milk collection */
  supplierRows: SupplierRow[] = [];

  /** Selected month (1–12) */
  selectedMonth: number = new Date().getMonth() + 1;

  /** Selected year */
  selectedYear: number = new Date().getFullYear();

  /** Selected biweekly period: 1 (days 1–15) or 2 (days 16–end of month) */
  quincena: 1 | 2 = 1;

  /** Array of days to be displayed based on the selected biweekly period */
  days: number[] = [];

  /** Loading state for data fetching */
  loading = false;

  /**
   * @param milkCollectionService Service for retrieving milk collection data
   */
  constructor(private milkCollectionService: MilkCollectionService) {}

  
  /**
   * Lifecycle hook that initializes the component by setting up days and loading data.
   */
  ngOnInit() {
    this.updateDays();
    this.loadData();
  }

  /**
   * Updates the `days` array based on the selected biweekly period.
   * First half: days 1–15. Second half: days 16–end of month.
   */
  updateDays() {
    if (this.quincena === 1) {
      this.days = Array.from({ length: 15 }, (_, i) => i + 1);
    } else {
      const lastDay = new Date(this.selectedYear, this.selectedMonth, 0).getDate();
      this.days = Array.from({ length: lastDay - 15 }, (_, i) => i + 16);
    }
  }

  /**
   * Returns the start and end dates of the selected month.
   * @returns An object containing the start and end dates in YYYY-MM-DD format.
   */
  getMonthStartEnd(): { fechaInicio: string, fechaFin: string } {
    const fechaInicio = `${this.selectedYear}-${this.selectedMonth.toString().padStart(2, '0')}-01`;
    const lastDay = new Date(this.selectedYear, this.selectedMonth, 0).getDate();
    const fechaFin = `${this.selectedYear}-${this.selectedMonth.toString().padStart(2, '0')}-${lastDay}`;
    return { fechaInicio, fechaFin };
  }

  /**
   * Loads milk collection data for the selected month and biweekly period.
   * Fetches data from the backend and processes it into a format suitable for display.
   */
  loadData() {
    this.loading = true;
    const { fechaInicio, fechaFin } = this.getMonthStartEnd();
    this.milkCollectionService.fetchMilkSupplierCollectionByCollectionDateRange(fechaInicio, fechaFin)
      .subscribe({
        next: (data: MilkSupplierCollectionDTO[]) => {
          this.supplierRows = this.processData(data);
          this.loading = false;
        },
        error: () => { this.loading = false; }
      });
  }

  /**
   * Processes the raw milk collection data into a format suitable for display.
   * @param data Array of MilkSupplierCollectionDTO objects containing the raw data.
   * @returns An array of SupplierRow objects with the processed data.
   */
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

  /**
   * Navigates to the previous biweekly period.
   * If the current period is the second half of the month, it changes to the first half.
   * Otherwise, it changes to the previous month and sets the period to the second half.
   */
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

  /**
   * Navigates to the next biweekly period.
   * If the current period is the first half of the month, it changes to the second half.
   * Otherwise, it changes to the next month and sets the period to the first half.
   */
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

  /**
   * Handles the change of the month selector.
   * Updates the selected year and month, resets the quincena to the first half,
   * updates the days array, and reloads the data.
   * @param event The change event containing the selected month and year.
   */
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
