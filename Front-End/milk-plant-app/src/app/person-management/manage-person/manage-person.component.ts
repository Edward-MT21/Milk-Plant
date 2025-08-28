import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MilkCollectionService, PersonOutDto, Person } from '../../services/milk-collection.service';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import Swal from 'sweetalert2';

/**
 * Component to manage persons.
 * Allows adding new persons, viewing existing persons,
 * and deleting persons.
 * @author Edward Malte
 */
@Component({
  selector: 'app-manage-person',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-person.component.html',
  styleUrls: ['./manage-person.component.css']
})
export class ManagePersonComponent implements OnInit {

  /** Service for retrieving person data */
  private milkCollectionService = inject(MilkCollectionService);
  
  /** Loading state for data fetching */
  loading = false;
  
  /** Error message */
  error: string | null = null;
  
  /** Array of persons */
  persons: PersonOutDto[] = [];

  /** Selected person for details view */
  selectedPerson: PersonOutDto | null = null;
  
  /** Person being edited */
  editingPerson: PersonOutDto | null = null;

  /** Lifecycle hook that initializes the component by loading persons. */
  ngOnInit() {
    this.loadPersons();
  }

  /**
   * Opens the edit modal for a person
   * @param person The person to edit
   */
  openEditModal(person: PersonOutDto) {
    // Create a deep copy of the person object to avoid modifying the original
    this.editingPerson = { ...person };
  }

  /**
   * Saves the edited person
   */
  saveEdit() {
    if (!this.editingPerson) return;
    
    this.loading = true;
    this.error = null;
    
    this.milkCollectionService.editPerson(this.editingPerson)
      .pipe(
        catchError(error => {
          console.error('Error updating person:', error);
          this.error = 'Error al actualizar la persona. Por favor, intente nuevamente.';
          return of(null);
        }),
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe(updatedPerson => {
        if (updatedPerson) {
          this.loadPersons();
          this.closeEditModal();
        }
      });
  }

  /**
   * Closes the edit modal
   */
  closeEditModal() {
    this.editingPerson = null;
  }

  /** Loads persons from the backend. */
  loadPersons() {
    this.loading = true;
    this.error = null;
    
    this.milkCollectionService.getAllPersons()
      .pipe(
        catchError(error => {
          console.error('Error loading persons:', error);
          this.error = 'Error al cargar las personas. Por favor, intente nuevamente.';
          return of([]);
        }),
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe(persons => {
        this.persons = persons;
      });
  }

  /** Opens the details modal for a person. */
  openDetails(person: PersonOutDto) {
    this.selectedPerson = person;
  }

  /** Closes the details modal. */
  closeModal() {
    this.selectedPerson = null;
  }

  /** Confirms the deletion of a person. */
  async confirmDelete(personId: number, event: Event) {
    event.stopPropagation();
    
    const result = await Swal.fire({
      title: '¿Está seguro?',
      text: '¿Desea eliminar esta persona? Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#6c757d',
    });

    if (result.isConfirmed) {
      this.deletePerson(personId);
    }
  }

  /** Deletes a person by ID. */
  private deletePerson(personId: number) {
    this.loading = true;
    this.error = null;
    
    // Note: You'll need to implement the deletePerson method in the MilkCollectionService
    this.milkCollectionService.deletePerson(personId)
      .pipe(
        catchError(error => {
          console.error('Error deleting person:', error);
          this.error = 'Error al eliminar la persona. Por favor, intente nuevamente.';
          return of(null);
        }),
        finalize(() => {
          this.loading = false;
        })
      )
      .subscribe(response => {
        if (response) {
          this.loadPersons();
        }
      });
  }
}
