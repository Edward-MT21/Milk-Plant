import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface MilkCollection {
  milkCollectionId: number;
  milkSupplierId: string;
  litersMilk: number;
}

export interface Person {
  idPerson: number | null;
  names: string;
  lastNames: string;
  identificationNumber: string;
  birthdate: string; // Changed from 'age: number' to 'birthDate: string' to match backend LocalDate
  gender: string;
  email: string;
  mobileNumber: string;
}

export interface PersonOutDto {
  idPerson: number;
  names: string;
  lastNames: string;
  identificationNumber: string;
  birthdate: string;  // Changed from birthDate to match the backend
  gender: string;
  email: string;
  mobileNumber: string;
}

export interface MilkSupplierDetails {
  milkSupplierId: number;
  personOutDto: PersonOutDto;
  greetingFinancialManagement?: string;
}

export interface MilkCollectionDetails {
  milkCollectionId: number;
  milkSupplierDetailsDto: MilkSupplierDetails;
  litersMilk: number;
  createdAt: string; // Fecha de la recolección (ISO string)
}

export interface MilkCollectionRecordDTO {
  createdAt: string; // ISO string
  litersMilk: number;
}

export interface MilkSupplierCollectionDTO {
  milkSupplierId: number;
  personOutDto: PersonOutDto;
  collections: MilkCollectionRecordDTO[];
}

export interface InfoMilkSupplierPaymentDto {
  milkSupplierId: number;
  personOutDto: PersonOutDto;
  collections: MilkCollectionRecordDTO[];
  totalLitersMilk: number;
  totalAmount: number;
}

@Injectable({
  providedIn: 'root'
})
export class MilkCollectionService {

  private baseUrl = 'http://localhost:8080';
  private milkCollectionUrl = `${this.baseUrl}/MilkCollectionController/fetch-all-milk-collection`;
  private milkCollectionDetailsUrl = `${this.baseUrl}/MilkCollectionController/fetch-all-milk-collection-details`;
  private personUrl = 'http://localhost:8180/PersonController';
  private getAllPersonsUrl = `${this.personUrl}/getAllPersons`;
  private milkSupplierUrl = 'http://localhost:8080/MilkSupplierController';
  private createMilkSupplierUrl = `${this.milkSupplierUrl}/createMilkSupplier`;
  private fetchAllMilkSupplierDetailsUrl = `${this.milkSupplierUrl}/fetchAllMilkSupplierDetails`;
  private fetchMilkSupplierCollectionByDateRangeUrl = `${this.baseUrl}/MilkCollectionController/fetch-milk-supplier-collection-by-date-range`;
  private milkSupplierPaymentUrl = `http://localhost:8280/MilkSupplierPaymentController`;

  /**
   * Constructor
   * @param http The HttpClient to make HTTP requests
   */
  constructor(private http: HttpClient) {}

  /**
   * Fetches all milk collection
   * @returns Observable with the list of milk collection
   */
  fetchAllMilkCollection(): Observable<MilkCollection[]> {
    return this.http.get<MilkCollection[]>(this.milkCollectionUrl);
  }

  /**
   * Creates a new person
   * @param person The person data to create
   * @returns Observable with the created person
   */
  createPerson(person: Person): Observable<Person> {
    return this.http.post<Person>(`${this.personUrl}/createPerson`, person);
  }

  /**
   * Fetches all persons
   * @returns Observable with the list of persons
   */
  getAllPersons(): Observable<PersonOutDto[]> {
    return this.http.get<PersonOutDto[]>(this.getAllPersonsUrl);
  }

  /**
   * Creates a new milk supplier
   * @param personId The ID of the person to create the milk supplier for
   * @returns Observable with the response
   */
  createMilkSupplier(personId: number): Observable<any> {
    const payload = {
      milkSupplierId: null,
      personId: personId
    };
    return this.http.post(this.createMilkSupplierUrl, payload);
  }

  /**
   * Fetches all milk supplier details
   * @returns Observable with the list of milk supplier details
   */
  fetchAllMilkSupplierDetails(): Observable<MilkSupplierDetails[]> {
    return this.http.get<MilkSupplierDetails[]>(this.fetchAllMilkSupplierDetailsUrl);
  }

  /**
   * Fetches all milk collection details
   * @returns Observable with the list of milk collection details
   */
  fetchAllMilkCollectionDetails(): Observable<MilkCollectionDetails[]> {
    return this.http.get<MilkCollectionDetails[]>(this.milkCollectionDetailsUrl);
  }

  /**
   * Updates an existing milk collection
   * @param milkCollectionId The ID of the milk collection to update
   * @param litersMilk The amount of milk collected
   * @returns Observable with the response
   */
  updateMilkCollection(milkCollectionId: number, litersMilk: number): Observable<any> {
    const url = `${this.baseUrl}/MilkCollectionController/update-milk-collection`;
    const body = {
      milkCollectionId: milkCollectionId,
      milkSupplierId: null,
      litersMilk: litersMilk
    };
    return this.http.post(url, body);
  }

  /**
   * Creates a new milk collection
   * @param milkSupplierId The ID of the milk supplier
   * @param litersMilk The amount of milk collected
   * @returns Observable with the response
   */
  createMilkCollection(milkSupplierId: number, litersMilk: number, collectionDate: string): Observable<any> {
    const url = `${this.baseUrl}/MilkCollectionController/create-milk-collection`;
    const body = {
      milkCollectionId: null,
      milkSupplierId: milkSupplierId,
      litersMilk: litersMilk,
      collectionDate: collectionDate
    };
    return this.http.post(url, body);
  }

  /**
   * Fetches milk collection details for a specific date
   * @param date The date in YYYY-MM-DD format
   * @returns Observable with the list of milk collection details for the specified date
   */
  fetchMilkCollectionDetailsByDate(date: string): Observable<MilkCollectionDetails[]> {
    const url = `${this.baseUrl}/MilkCollectionController/fetch-milk-collection-details-by-date?date=${date}`;
    return this.http.get<MilkCollectionDetails[]>(url);
  }


  /**
   * Obtiene las colecciones de proveedores en un rango de fechas
   */
  fetchAllMilkCollectionDetailsByDateRange(startDate: string, endDate: string): Observable<MilkSupplierCollectionDTO[]> {
    const params = { startDate, endDate };
    return this.http.get<MilkSupplierCollectionDTO[]>(this.fetchMilkSupplierCollectionByDateRangeUrl, { params });
  }

  // Obtiene la información de pagos quincenales de los proveedores
  getBiweeklyMilkSupplierPayments(startDate: string, endDate: string): Observable<InfoMilkSupplierPaymentDto[]> {
    return this.http.get<InfoMilkSupplierPaymentDto[]>(
      `${this.milkSupplierPaymentUrl}/getBiweeklyInfoMilkSupplierPayment`,
      {
        params: {
          startDate: startDate,
          endDate: endDate
        }
      }
    );
  }

  /**
   * Deletes a milk supplier by ID
   * @param milkSupplierId The ID of the milk supplier to delete
   * @returns Observable with the response
   */
  deleteMilkSupplier(milkSupplierId: number): Observable<any> {
    return this.http.delete(`${this.milkSupplierUrl}/deleteMilkSupplierById?milkSupplierId=${milkSupplierId}`);
  }

  /**
   * Fetches milk supplier collection by date range
   * @param startDate The start date in YYYY-MM-DD format
   * @param endDate The end date in YYYY-MM-DD format
   * @returns Observable with the list of milk supplier collections for the specified date range
   */
  fetchMilkSupplierCollectionByDateRange(
    startDate: string,
    endDate: string
  ): Observable<MilkSupplierCollectionDTO[]> {
    const params = { startDate, endDate };
    return this.http.get<MilkSupplierCollectionDTO[]>(
      this.fetchMilkSupplierCollectionByDateRangeUrl,
      { params }
    );
  }

  /**
   * Deletes a person by ID
   * @param personId The ID of the person to delete
   * @returns Observable with the response
   */
  deletePerson(personId: number): Observable<any> {
    return this.http.delete(`${this.personUrl}/deletePersonById?personId=${personId}`);
  }

  /**
   * Updates an existing person
   * @param person The person data to update
   * @returns Observable with the updated person
   */
  editPerson(person: PersonOutDto): Observable<PersonOutDto> {
    return this.http.put<PersonOutDto>(`${this.personUrl}/editPerson`, person);
  }

}
