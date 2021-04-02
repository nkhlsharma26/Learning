import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs';
import { Employee } from './employee';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private apiSreverUrl:string = environment.apiBaseUrl;
  constructor(private http : HttpClient) { }

  public getEmployees():Observable<Employee[]>{
    return this.http.get<Employee[]>(`${this.apiSreverUrl}/employee/findAll`);
  }

  public addEmployee(employee:Employee):Observable<Employee>{
    return this.http.post<Employee>(`${this.apiSreverUrl}/employee/add`, employee);
  }

  public updateEmployee(employee:Employee):Observable<Employee>{
    return this.http.put<Employee>(`${this.apiSreverUrl}/employee/update`, employee);
  }

  public deleteEmployee(id:number):Observable<void>{
    return this.http.delete<void>(`${this.apiSreverUrl}/employee/delete/${id}`);
  }
}
