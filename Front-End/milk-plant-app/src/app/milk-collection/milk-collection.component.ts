import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { MilkCollectionService, MilkCollection, MilkCollectionDetails } from '../services/milk-collection.service';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-milk-collection',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './milk-collection.component.html',
  styleUrl: './milk-collection.component.css'
})
export class MilkCollectionComponent implements OnInit {

  private milkService = inject(MilkCollectionService);

  collections: MilkCollectionDetails[] = [];
  loading = false;
  error: string | null = null;
  
  selectedCollection: MilkCollectionDetails | null = null;
  selectedDate: string = new Date().toISOString().split('T')[0];
  dayDescription: string = '';
  showNewCollectionModal = false;
  newCollection = {
    supplierId: null as number | null,
    litersMilk: 0,
    date: new Date().toISOString().split('T')[0]
  };
  suppliers: {id: number, name: string}[] = [];
  newLiters: number = 0;

  
    ngOnInit(): void {
    this.updateDayDescription(this.selectedDate);
    this.loadMilkCollections();
    this.extractSuppliers();
  }

  onDateChange(event: any) {
    this.selectedDate = event.target.value;
    this.updateDayDescription(this.selectedDate);
    this.loadMilkCollections();
  }

  updateDayDescription(dateString: string) {
    // Parse the date string in YYYY-MM-DD format, adding T12:00:00 to avoid timezone issues
    const [year, month, day] = dateString.split('-').map(Number);
    const date = new Date(year, month - 1, day);
    
    const days = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
    const months = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
    
    this.dayDescription = `${days[date.getDay()]}, ${date.getDate()} de ${months[date.getMonth()]}`;
  }

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


  openModal(registro: MilkCollectionDetails) {
    this.selectedCollection = registro;
    this.newLiters = registro.litersMilk ?? 0;
  }

  closeModal() {
    this.selectedCollection = null;
    this.newLiters = 0;
  }

  openNewCollectionModal() {
    this.showNewCollectionModal = true;
  }

  closeNewCollectionModal() {
    this.showNewCollectionModal = false;
    this.resetNewCollectionForm();
  }

  resetNewCollectionForm() {
    this.newCollection = {
      supplierId: null,
      litersMilk: 0,
      date: new Date().toISOString().split('T')[0]
    };
  }

  onSupplierSelect() {
    // This method will be called when a supplier is selected
  }

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

  submitNewCollection() {
    if (this.newCollection.supplierId && this.newCollection.litersMilk > 0) {
      this.loading = true;
      this.milkService.createMilkCollection(
        this.newCollection.supplierId,
        this.newCollection.litersMilk
      ).pipe(
        finalize(() => {
          this.loading = false;
        })
      ).subscribe({
        next: () => {
          this.loadMilkCollections();
          this.closeNewCollectionModal();
        },
        error: (error) => {
          console.error('Error al guardar la recolección:', error);
          this.error = 'Error al guardar la recolección. Por favor, intente nuevamente.';
        }
      });
    }
  }

  submitCollection() {
    if (this.selectedCollection && this.newLiters > 0) {
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
