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
  collectionDate: string;
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


/**
 * Service to manage milk collection.
 * Allows fetching milk collection data from the backend and updating milk collection records.
 * @author Edward Malte
 */
@Injectable({
  providedIn: 'root'
})
export class MilkCollectionService {

  private milkCollectionBaseUrl = 'http://localhost:8080';
  private personBaseUrl = 'http://localhost:8180';
  private financialManagementBaseUrl = 'http://localhost:8280';

  private fetchAllMilkCollectionUrl = `${this.milkCollectionBaseUrl}/MilkCollectionController/fetchAllMilkCollection`;
  private fetchAllMilkCollectionDetailsUrl = `${this.milkCollectionBaseUrl}/MilkCollectionController/fetchAllMilkCollectionDetails`;
  private createPersonUrl = `${this.personBaseUrl}/PersonController/createPerson`;
  private getAllPersonsUrl = `${this.personBaseUrl}/PersonController/getAllPersons`;
  private createMilkSupplierUrl = `${this.milkCollectionBaseUrl}/MilkSupplierController/createMilkSupplier`;
  private fetchAllMilkSupplierDetailsUrl = `${this.milkCollectionBaseUrl}/MilkSupplierController/fetchAllMilkSupplierDetails`;
  private fetchMilkSupplierCollectionByDateRangeUrl = `${this.milkCollectionBaseUrl}/MilkCollectionController/fetchMilkSupplierCollectionByDateRange`;
  private getBiweeklyInfoMilkSupplierPaymentUrl = `${this.financialManagementBaseUrl}/MilkSupplierPaymentController/getBiweeklyInfoMilkSupplierPayment`;
  private updateMilkCollectionUrl = `${this.milkCollectionBaseUrl}/MilkCollectionController/updateMilkCollection`;
  private createMilkCollectionUrl = `${this.milkCollectionBaseUrl}/MilkCollectionController/createMilkCollection`;
  private fetchAllMilkCollectionDetailsByCollectionDateUrl = `${this.milkCollectionBaseUrl}/MilkCollectionController/fetchAllMilkCollectionDetailsByCollectionDate`;
  private fetchMilkSupplierCollectionByCollectionDateRangeUrl = `${this.milkCollectionBaseUrl}/MilkCollectionController/fetchMilkSupplierCollectionByCollectionDateRange`;
  private deleteMilkSupplierByIdUrl = `${this.milkCollectionBaseUrl}/MilkSupplierController/deleteMilkSupplierById`;
  private deletePersonByIdUrl = `${this.personBaseUrl}/PersonController/deletePersonById`;
  private editPersonUrl = `${this.personBaseUrl}/PersonController/editPerson`;

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
    return this.http.get<MilkCollection[]>(this.fetchAllMilkCollectionUrl);
  }

  /**
   * Creates a new person
   * @param person The person data to create
   * @returns Observable with the created person
   */
  createPerson(person: Person): Observable<Person> {
    return this.http.post<Person>(this.createPersonUrl, person);
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
    return this.http.get<MilkCollectionDetails[]>(this.fetchAllMilkCollectionDetailsUrl);
  }

  /**
   * Updates an existing milk collection
   * @param milkCollectionId The ID of the milk collection to update
   * @param litersMilk The amount of milk collected
   * @returns Observable with the response
   */
  updateMilkCollection(milkCollectionId: number, litersMilk: number): Observable<any> {
    const body = {
      milkCollectionId: milkCollectionId,
      milkSupplierId: null,
      litersMilk: litersMilk
    };
    return this.http.post(this.updateMilkCollectionUrl, body);
  }

  /**
   * Creates a new milk collection
   * @param milkSupplierId The ID of the milk supplier
   * @param litersMilk The amount of milk collected
   * @returns Observable with the response
   */
  createMilkCollection(milkSupplierId: number, litersMilk: number, collectionDate: string): Observable<any> {
    const body = {
      milkCollectionId: null,
      milkSupplierId: milkSupplierId,
      litersMilk: litersMilk,
      collectionDate: collectionDate
    };
    return this.http.post(this.createMilkCollectionUrl, body);
  }

  /**
   * Fetches milk collection details for a specific date
   * @param date The date in YYYY-MM-DD format
   * @returns Observable with the list of milk collection details for the specified date
   */
  fetchAllMilkCollectionDetailsByCollectionDate(date: string): Observable<MilkCollectionDetails[]> {
    return this.http.get<MilkCollectionDetails[]>(this.fetchAllMilkCollectionDetailsByCollectionDateUrl + `?date=${date}`);
  }

  /**
   * Fetches milk collection details for a specific date range
   * @param startDate The start date in YYYY-MM-DD format
   * @param endDate The end date in YYYY-MM-DD format
   * @returns Observable with the list of milk collection details for the specified date range
   */
  fetchMilkSupplierCollectionByCollectionDateRange(startDate: string, endDate: string): Observable<MilkSupplierCollectionDTO[]> {
    const params = { startDate, endDate };
    return this.http.get<MilkSupplierCollectionDTO[]>(this.fetchMilkSupplierCollectionByCollectionDateRangeUrl, { params });
  }

  /**
   * Fetches biweekly milk supplier payments for a specific date range
   * @param startDate The start date in YYYY-MM-DD format
   * @param endDate The end date in YYYY-MM-DD format
   * @returns Observable with the list of biweekly milk supplier payments for the specified date range
   */
  getBiweeklyMilkSupplierPayments(startDate: string, endDate: string): Observable<InfoMilkSupplierPaymentDto[]> {
    return this.http.get<InfoMilkSupplierPaymentDto[]>(
      this.getBiweeklyInfoMilkSupplierPaymentUrl,
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
    return this.http.delete(this.deleteMilkSupplierByIdUrl + `?milkSupplierId=${milkSupplierId}`);
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
    return this.http.delete(this.deletePersonByIdUrl + `?personId=${personId}`);
  }

  /**
   * Updates an existing person
   * @param person The person data to update
   * @returns Observable with the updated person
   */
  editPerson(person: PersonOutDto): Observable<PersonOutDto> {
    return this.http.put<PersonOutDto>(this.editPersonUrl, person);
  }

}
