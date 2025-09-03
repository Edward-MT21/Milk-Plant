import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { MilkCollectionService, MilkCollection, MilkCollectionDetails } from '../services/milk-collection.service';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';

/**
 * Component to manage milk collection.
 * Allows navigation between biweekly periods, fetching data from the backend,
 * and displaying daily milk collection per supplier.
 * @author Edward Malte
 */
@Component({
  selector: 'app-milk-collection',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './milk-collection.component.html',
  styleUrls: ['./milk-collection.component.scss']
})
export class MilkCollectionComponent implements OnInit {

  /** Service for retrieving milk collection data */
  private milkService = inject(MilkCollectionService);

  /** Array of milk collection details */
  collections: MilkCollectionDetails[] = [];

  /** Loading state for data fetching */
  loading = false;

  /** Error message */
  error: string | null = null;
  
  /** Selected milk collection */
  selectedCollection: MilkCollectionDetails | null = null;
  
  /** Selected date */
  selectedDate: string = new Date().toISOString().split('T')[0];
  
  /** Description of the selected day */
  dayDescription: string = '';
  
  /** Show new collection modal */
  showNewCollectionModal = false;

  /** New collection form */
  newCollection = {
    supplierId: null as number | null,
    litersMilk: 0,
    date: new Date().toISOString().split('T')[0]
  };

  /** Array of suppliers */
  suppliers: {id: number, name: string}[] = [];

  /** New liters of milk */
  newLiters: number = 0;

  /** There is an error message */
  thereErrorMessage: boolean = false;

  /** Lifecycle hook that initializes the component */
  ngOnInit(): void {
    this.updateDayDescription(this.selectedDate);
    this.loadMilkCollections();
    this.extractSuppliers();
  }

  /**
   * Updates the selected date and loads milk collections for the new date.
   * @param event The date change event.
   */
  onDateChange(event: any) {
    this.selectedDate = event.target.value;
    this.updateDayDescription(this.selectedDate);
    this.loadMilkCollections();
  }

  /**
   * Updates the day description based on the selected date.
   * @param dateString The selected date in YYYY-MM-DD format.
   */
  updateDayDescription(dateString: string) {
    // Parse the date string in YYYY-MM-DD format, adding T12:00:00 to avoid timezone issues
    const [year, month, day] = dateString.split('-').map(Number);
    const date = new Date(year, month - 1, day);
    
    const days = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
    const months = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
    
    this.dayDescription = `${days[date.getDay()]}, ${date.getDate()} de ${months[date.getMonth()]}`;
  }

  /**
   * Loads milk collections for the selected date.
   */
  loadMilkCollections(): void {
    this.loading = true;
    this.error = null;
    
    this.milkService.fetchMilkCollectionDetailsByDate(this.selectedDate)
      .pipe(
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe({
        next: (collections: MilkCollectionDetails[]) => {
          this.collections = collections;
        },
        error: (error: any) => {
          console.error('Error al cargar las recolecciones:', error);
          this.error = 'Error al cargar las recolecciones. Por favor, intente nuevamente.';
        }
      });
  }

  /**
   * Opens the modal for editing a milk collection.
   * @param registro The milk collection to edit.
   */
  openModal(registro: MilkCollectionDetails) {
    this.selectedCollection = registro;
    this.newLiters = registro.litersMilk ?? 0;
  }

  /** Closes the modal */
  closeModal() {
    this.selectedCollection = null;
    this.newLiters = 0;
  }

  /** Opens the new collection modal */
  openNewCollectionModal() {
    this.showNewCollectionModal = true;
    this.newCollection.date = this.selectedDate;

  }

  /** Closes the new collection modal */
  closeNewCollectionModal() {
    this.showNewCollectionModal = false;
    this.resetNewCollectionForm();
  }

  /** Resets the new collection form */
  resetNewCollectionForm() {
    this.newCollection = {
      supplierId: null,
      litersMilk: 0,
      date: new Date().toISOString().split('T')[0]
    };
  }

  /** Handles the change of the supplier selector */
  onSupplierSelect() {
    // This method will be called when a supplier is selected
  }

  /** Extracts suppliers from the backend */
  extractSuppliers() {
    this.loading = true;
    this.milkService.fetchAllMilkSupplierDetails().pipe(
      finalize(() => {
        this.loading = false;
      })
    ).subscribe({
      next: (suppliers) => {
        this.suppliers = suppliers.map(supplier => ({
          id: supplier.milkSupplierId,
          name: `${supplier.personOutDto.names} ${supplier.personOutDto.lastNames}`
        }));
      },
      error: (error) => {
        console.error('Error al cargar los proveedores:', error);
        this.error = 'Error al cargar la lista de proveedores. Por favor, intente nuevamente.';
      }
    });
  }

  /** Submits a new milk collection */
  submitNewCollection() {
    if (this.newCollection.supplierId && this.newCollection.litersMilk >= 0) {
      this.loading = true;
      this.error = null; // Clear previous errors
      this.thereErrorMessage = false;
      
      this.milkService.createMilkCollection(
        this.newCollection.supplierId,
        this.newCollection.litersMilk,
        this.newCollection.date
      ).pipe(
        finalize(() => {
          this.loading = false;
        })
      ).subscribe({
        next: (response: any) => {
          this.loadMilkCollections();
          this.closeNewCollectionModal();
          // Show success message if available
          if (response && response.statusMsg) {
            this.error = response.statusMsg; // Show success message
            this.thereErrorMessage = false; // This is a success message, not an error
          }
        },
        error: (error) => {
          this.thereErrorMessage = true;
          console.error('Error al guardar la recolección:', error);
          // Use the error message from the response if available, otherwise use a default message
          this.error = error.error?.errorMessage || 'Error al guardar la recolección. Por favor, intente nuevamente.';
        }
      });
    }
  }

  /** Submits a milk collection */
  submitUpdateMilkCollection() {
    if (this.selectedCollection && this.newLiters >= 0) {
      this.loading = true;
      
      this.milkService.updateMilkCollection(
        this.selectedCollection.milkCollectionId,
        this.newLiters
      ).pipe(
        finalize(() => {
          this.loading = false;
        })
      ).subscribe({
        next: () => {
          // Update the local collection with the new value
          const updatedCollection = this.collections.find(
            c => c.milkCollectionId === this.selectedCollection?.milkCollectionId
          );
          
          if (updatedCollection) {
            updatedCollection.litersMilk = this.newLiters;
          }
          
          this.closeModal();
          // Optional: Show success message
          console.log('Registro actualizado exitosamente');
        },
        error: (error) => {
          console.error('Error al actualizar el registro:', error);
          this.error = 'Error al actualizar el registro. Por favor, intente nuevamente.';
        }
      });
    }
  }



}
