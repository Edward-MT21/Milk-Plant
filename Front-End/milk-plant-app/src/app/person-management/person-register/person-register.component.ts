import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
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
  private router = inject(Router);

  names = '';
  lastNames = '';
  identificationNumber = '';
  birthdate: string = '';
  gender: 'MASCULINO' | 'FEMENINO' = 'MASCULINO';
  email = '';
  mobileNumber = '';
  
  // Opciones de género
  genderOptions = ['MASCULINO', 'FEMENINO'];
  
  // Estados para feedback al usuario
  isLoading = false;
  successMessage = '';
  errorMessage = '';
  today = new Date().toISOString().split('T')[0]; // Formato YYYY-MM-DD para el input type="date"

  // Formatear la fecha para el pipe
  get maxDate(): string {
    return this.today;
  }

  guardar() {
    // Validar que todos los campos estén llenos
    if (!this.names || !this.lastNames || !this.identificationNumber || 
        !this.birthdate || !this.gender || !this.email || !this.mobileNumber) {
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
      birthdate: this.birthdate, // Enviamos la fecha directamente en formato YYYY-MM-DD
      gender: this.gender,
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
        
        // Limpiar el formulario después de 5 segundos
        setTimeout(() => {
          this.limpiarFormulario();
        }, 5000);
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = 'Error al registrar la persona. Por favor, intente nuevamente.';
        console.error('Error al crear persona:', error);
      }
    });
  }

  cancelar() {
    this.router.navigate(['/person-management/manage-person']);
  }

  private limpiarFormulario() {
    this.names = '';
    this.lastNames = '';
    this.identificationNumber = '';
    this.birthdate = '';
    this.gender = 'MASCULINO';
    this.email = '';
    this.mobileNumber = '';
    this.successMessage = '';
    this.errorMessage = '';
  }

}
