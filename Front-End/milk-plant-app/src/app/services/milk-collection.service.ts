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
  age: number;
  gender: string;
  email: string;
  mobileNumber: string;
}

export interface PersonOutDto {
  idPerson: number;
  names: string;
  lastNames: string;
  identificationNumber: string;
  age: number;
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

  constructor(private http: HttpClient) {}

  fetchAllMilkCollection(): Observable<MilkCollection[]> {
    return this.http.get<MilkCollection[]>(this.milkCollectionUrl);
  }

  createPerson(person: Person): Observable<Person> {
    return this.http.post<Person>(`${this.personUrl}/createPerson`, person);
  }

  getAllPersons(): Observable<Person[]> {
    return this.http.get<Person[]>(this.getAllPersonsUrl);
  }

  createMilkSupplier(personId: number): Observable<any> {
    const payload = {
      milkSupplierId: null,
      personId: personId
    };
    return this.http.post(this.createMilkSupplierUrl, payload);
  }

  fetchAllMilkSupplierDetails(): Observable<MilkSupplierDetails[]> {
    return this.http.get<MilkSupplierDetails[]>(this.fetchAllMilkSupplierDetailsUrl);
  }

  fetchAllMilkCollectionDetails(): Observable<MilkCollectionDetails[]> {
    return this.http.get<MilkCollectionDetails[]>(this.milkCollectionDetailsUrl);
  }

  updateMilkCollection(milkCollectionId: number, litersMilk: number): Observable<any> {
    const url = `${this.baseUrl}/MilkCollectionController/update-milk-collection`;
    const body = {
      milkCollectionId: milkCollectionId,
      milkSupplierId: null,
      litersMilk: litersMilk
    };
    return this.http.post(url, body);
  }

  createMilkCollection(milkSupplierId: number, litersMilk: number): Observable<any> {
    const url = `${this.baseUrl}/MilkCollectionController/create-milk-collection`;
    const body = {
      milkCollectionId: null,
      milkSupplierId: milkSupplierId,
      litersMilk: litersMilk
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
  
  // ...otros métodos...

}
