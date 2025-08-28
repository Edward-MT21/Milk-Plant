import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MilkCollectionService, Person, MilkSupplierDetails, PersonOutDto } from '../services/milk-collection.service';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import Swal from 'sweetalert2';

/**
 * Component to manage milk suppliers.
 * Allows adding new suppliers, viewing existing suppliers,
 * and deleting suppliers.
 * @author Edward Malte
 */
@Component({
  selector: 'app-milk-supplier',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './milk-supplier.component.html',
  styleUrl: './milk-supplier.component.css'
})
export class MilkSupplierComponent implements OnInit {

  /** Service for retrieving milk supplier data */
  private milkCollectionService = inject(MilkCollectionService);

  /** Loading state for data fetching */
  loading = false;

  /** Error message */
  error: string | null = null;

  /** Array of milk suppliers */
  suppliers: MilkSupplierDetails[] = [];

  /** Array of available people */
  availablePeople: PersonOutDto[] = [];

  /** Selected milk supplier */
  selectedSupplier: MilkSupplierDetails | null = null;

  /** Show add supplier modal */
  showAddSupplierModal: boolean = false;

  /** Selected person ID */
  selectedPersonId: string | null = null; // Cambiado a string porque viene del HTML select

  /** Selected person details */
  selectedPersonDetails: PersonOutDto | null = null;

  /** Lifecycle hook that initializes the component by loading available people and milk suppliers.*/
  ngOnInit() {
    this.loadAvailablePeople();
    this.loadMilkSuppliers();
  }

  /**
   * Loads milk supplier data from the backend.
   * Fetches data from the backend and processes it into a format suitable for display.
   */
  loadMilkSuppliers() {
    this.loading = true;
    this.error = null;
    this.milkCollectionService.fetchAllMilkSupplierDetails()
      .pipe(
        catchError(error => {
          console.error('Error loading milk suppliers:', error);
          this.error = 'Error al cargar los proveedores de leche. Por favor, intente nuevamente.';
          return of([]);
        }),
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe(suppliers => {
        this.suppliers = suppliers;
      });
  }

  /**
   * Loads available people data from the backend.
   * Fetches data from the backend and processes it into a format suitable for display.
   */
  loadAvailablePeople() {
    this.milkCollectionService.getAllPersons()
      .pipe(
        catchError(error => {
          console.error('Error loading persons:', error);
          // Return an empty array in case of error
          return of([]);
        })
      )
      .subscribe(persons => {
        this.availablePeople = persons;
      });
  }

  /** Opens the details modal */
  openDetails(supplier: MilkSupplierDetails) {
    this.selectedSupplier = supplier;
  }

  /** Closes the details modal */
  closeModal() {
    this.selectedSupplier = null;
  }

  /** Opens the add supplier modal */
  openAddSupplierModal() {
    this.showAddSupplierModal = true;
    this.selectedPersonId = null;
    this.selectedPersonDetails = null;
  }

  /** Closes the add supplier modal */
  closeAddSupplierModal() {
    this.showAddSupplierModal = false;
    this.selectedPersonId = null;
    this.selectedPersonDetails = null;
  }

  /** Handles the change of the person selector */
  onPersonSelected() {
    if (this.selectedPersonId) {
      // Convertir selectedPersonId a number para la comparación
      const personId = Number(this.selectedPersonId);
      this.selectedPersonDetails = this.availablePeople.find(
        person => person.idPerson === personId
      ) || null;
      
      console.log('Persona seleccionada:', this.selectedPersonDetails); // Debug
    } else {
      this.selectedPersonDetails = null;
    }
  }

  /** Adds a new supplier */
  addNewSupplier() {
    if (this.selectedPersonDetails && this.selectedPersonDetails.idPerson !== null) {
      this.loading = true;
      this.error = null;
      
      // Call the service to create a new milk supplier
      this.milkCollectionService.createMilkSupplier(this.selectedPersonDetails.idPerson)
        .subscribe({
          next: (response) => {
            console.log('Proveedor creado exitosamente:', response);
            
            // Refresh the suppliers list from the server
            this.loadMilkSuppliers();
            
            // Remove the person from the available list
            this.availablePeople = this.availablePeople.filter(
              person => person.idPerson !== this.selectedPersonDetails!.idPerson
            );
            
            this.closeAddSupplierModal();
          },
          error: (error) => {
            console.error('Error al crear el proveedor:', error);
            // Aquí podrías mostrar un mensaje de error al usuario
          }
        });
    }
  }

  /** Confirms the deletion of a supplier */
  async confirmDelete(milkSupplierId: number, event: Event) {
    event.stopPropagation(); // Prevent row click event
    
    const result = await Swal.fire({
      title: '¿Está seguro?',
      text: '¿Desea eliminar este proveedor? Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#6c757d',
    });

    if (result.isConfirmed) {
      this.deleteSupplier(milkSupplierId);
    }
  }

  /** Deletes a supplier by ID */
  private deleteSupplier(milkSupplierId: number) {
    this.loading = true;
    this.error = null;
    
    this.milkCollectionService.deleteMilkSupplier(milkSupplierId)
      .pipe(
        catchError(error => {
          console.error('Error deleting milk supplier:', error);
          this.error = 'Error al eliminar el proveedor. Por favor, intente nuevamente.';
          return of(null);
        }),
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe(response => {
        if (response) {
          // Remove the deleted supplier from the local array
          this.suppliers = this.suppliers.filter(s => s.milkSupplierId !== milkSupplierId);
          console.log('Proveedor eliminado exitosamente');
        }
      });
  }

}
