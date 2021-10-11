import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Aid } from './aid';
import { House } from './house';

@Injectable({
  providedIn: 'root'
})
export class AidService {

  private baseUrl = 'http://Servicemanagement-env.eba-cndjvmwh.us-east-2.elasticbeanstalk.com/v1';

  constructor(private http: HttpClient) { }

  createAid(house: House, aid: Aid): Observable<Object>{
    return this.http.post(`${this.baseUrl}/${house.houseId}/addAid`, aid);
  }

  getAidList(house: House) : Observable<any>{
    return this.http.get(`${this.baseUrl}/${house.houseId}/getAllAidsForHouse`);
  }

  getAid(aid: Aid) : Observable<any>{
    return this.http.get(`${this.baseUrl}/getAid/${aid.aidId}`);
  }

  updateAid(houseId: number, aid: Aid): Observable<any>{
    return this.http.put(`${this.baseUrl}/${houseId}/updateAid`, aid);
  }
}
