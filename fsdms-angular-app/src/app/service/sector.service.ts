import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ApiResponse } from '../model/api.response';
import { Observable } from 'rxjs/index';
import { Sector } from '../model/sector.model';


@Injectable({
  providedIn: 'root'
})
export class SectorService {


  constructor(private http: HttpClient) { }

  baseUrl: string = environment.getBaseUrl('sct');

  newSector(sector: Sector): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'sector/admin/new', sector);
  }

  updateSector(sectorName: string, sector: Sector): Observable<ApiResponse> {
    if (!sectorName) { return new Observable(); }
    return this.http.post<ApiResponse>(this.baseUrl + 'sector/admin/upt/' + sectorName, sector);
  }

  getSectors(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + 'sector/all');
  }

  getSector(sectorName: string): Observable<ApiResponse> {
    if (!sectorName) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'sector/' + sectorName);
  }

  getSectorsLikeDebounce(sectorName: string): Observable<ApiResponse> {
    if (!sectorName) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'sector/nameDebounce/' + sectorName);
  }

  getCompaniesBySectorName(sectorName: string): Observable<ApiResponse> { // return companyDtlsList
    if (!sectorName) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'sector/' + sectorName + '/companylist');
  }

  // @ Don't be useful for now
  // get stock prices for mutiple companies
  // return stockPriceDtlsList
  // params: {
  //   "stockCodeList": ["500112","500113","500114"]
  // }
  getStockPricesByStockCodes(params: object): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'sector/stockprice/allcode', params);
  }

  // @ Don't be useful for now
  // get stock prices for mutiple companies and between datetime
  // return stockPriceDtlsList
  // params: {
  // 	"stockCodeList": ["500112","500113","500114"],
  // 	"start": "2019-06-08 10:30:00",
  // 	"end": "2019-06-08 11:30:00"
  // }
  getStockPricesByStockCodesAndDateTime(params: object): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'sector/stockprice/allcodebetween', params);
  }

  // get sectorDtls for sectorName
  // return sectorDtlsList
  getSectorPricesBySectorName(sectorName: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + 'sector/' + sectorName + '/sectorprice');
  }

  // get sectorDtls for sectorName and between datetime
  // return sectorDtlsList
  // params: { // If blank params, it will down to same with getSectorPricesBySectorName()
  //   "start": "2019-06-08 11:20:00",
  //   "end": "2019-06-08 11:30:00"
  // }
  getSectorPricesBySectorNameAndStockDateTime(sectorName: string, params: object): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'sector/' + sectorName + '/sectorpricebetween', params);
  }

}
