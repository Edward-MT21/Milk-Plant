import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MilkCollectionService, Person, MilkSupplierDetails, PersonOutDto } from '../services/milk-collection.service';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';

// Using MilkSupplierDetails from the service instead of local interface

// Person interface is now imported from MilkCollectionService



@Component({
  selector: 'app-milk-supplier',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './milk-supplier.component.html',
  styleUrl: './milk-supplier.component.css'
})
export class MilkSupplierComponent implements OnInit {
  constructor(private milkCollectionService: MilkCollectionService) {}

  loading = false;
  error: string | null = null;

  ngOnInit() {
    this.loadAvailablePeople();
    this.loadMilkSuppliers();
  }

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
  suppliers: MilkSupplierDetails[] = [];

  // Lista de personas disponibles para convertir en proveedores
  availablePeople: Person[] = [];

  selectedSupplier: MilkSupplierDetails | null = null;
  showAddSupplierModal: boolean = false;
  selectedPersonId: string | null = null; // Cambiado a string porque viene del HTML select
  selectedPersonDetails: Person | null = null;

  openDetails(supplier: MilkSupplierDetails) {
    this.selectedSupplier = supplier;
  }

  closeModal() {
    this.selectedSupplier = null;
  }

  // Métodos para el modal de agregar proveedor
  openAddSupplierModal() {
    this.showAddSupplierModal = true;
    this.selectedPersonId = null;
    this.selectedPersonDetails = null;
  }

  closeAddSupplierModal() {
    this.showAddSupplierModal = false;
    this.selectedPersonId = null;
    this.selectedPersonDetails = null;
  }

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

}
