import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MilkCollectionService, PersonOutDto, Person } from '../../services/milk-collection.service';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';

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
    
    this.milkCollectionService.updatePerson(this.editingPerson)
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
          // Update the person in the local array
          const index = this.persons.findIndex(p => p.idPerson === updatedPerson.idPerson);
          if (index !== -1) {
            this.persons[index] = updatedPerson;
          }
          this.closeEditModal();
          console.log('Persona actualizada exitosamente');
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
  confirmDelete(personId: number, event: Event) {
    event.stopPropagation();
    
    if (confirm('¿Está seguro de que desea eliminar esta persona? Esta acción no se puede deshacer.')) {
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
          // Remove the deleted person from the local array
          this.persons = this.persons.filter(p => p.idPerson !== personId);
          console.log('Persona eliminada exitosamente');
        }
      });
  }
}
