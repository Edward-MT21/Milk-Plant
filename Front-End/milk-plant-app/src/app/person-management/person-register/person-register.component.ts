import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MilkCollectionService, Person } from '../../services/milk-collection.service';

@Component({
  selector: 'app-person-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './person-register.component.html',
  styleUrl: './person-register.component.css'
})
export class PersonRegisterComponent {

  private milkService = inject(MilkCollectionService);

  names = '';
  lastNames = '';
  identificationNumber = '';
  age: number | null = null;
  gender = '';
  email = '';
  mobileNumber = '';
  
  // Estados para feedback al usuario
  isLoading = false;
  successMessage = '';
  errorMessage = '';

  guardar() {
    // Validar que todos los campos estén llenos
    if (!this.names || !this.lastNames || !this.identificationNumber || 
        !this.age || !this.gender || !this.email || !this.mobileNumber) {
      this.errorMessage = 'Por favor, complete todos los campos.';
      this.successMessage = '';
      return;
    }

    // Preparar los datos para enviar al backend
    const personData: Person = {
      idPerson: null, // Se genera automáticamente en el backend
      names: this.names,
      lastNames: this.lastNames,
      identificationNumber: this.identificationNumber,
      age: this.age,
      gender: this.gender.toUpperCase(), // El backend espera género en mayúsculas
      email: this.email,
      mobileNumber: this.mobileNumber
    };

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    // Llamar al servicio para guardar la persona
    this.milkService.createPerson(personData).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMessage = `Persona registrada exitosamente con ID: ${response.idPerson}`;
        console.log('Persona creada:', response);
        
        // Limpiar el formulario después del éxito
        this.limpiarFormulario();
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = 'Error al registrar la persona. Por favor, intente nuevamente.';
        console.error('Error al crear persona:', error);
      }
    });
  }

  cancelar() {
    this.limpiarFormulario();
  }

  private limpiarFormulario() {
    this.names = '';
    this.lastNames = '';
    this.identificationNumber = '';
    this.age = null;
    this.gender = '';
    this.email = '';
    this.mobileNumber = '';
    this.successMessage = '';
    this.errorMessage = '';
  }

}
