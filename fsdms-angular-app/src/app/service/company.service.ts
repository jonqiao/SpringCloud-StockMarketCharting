import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ApiResponse } from '../model/api.response';
import { Observable } from 'rxjs/index';
import { Ipo } from '../model/ipo.model';
import { Company } from '../model/company.model';



@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http: HttpClient) { }

  baseUrl: string = environment.getBaseUrl('com');

  // Company Component - companyService
  newCompany(company: Company): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'company/admin/new', company);
  }

  updateCompany(companyName: string, company: Company): Observable<ApiResponse> {
    if (!companyName) { return new Observable(); }
    return this.http.post<ApiResponse>(this.baseUrl + 'company/admin/upt/' + companyName, company);
  }

  activeCompany(companyName: string): Observable<ApiResponse> {
    if (!companyName) { return new Observable(); }
    return this.http.post<ApiResponse>(this.baseUrl + 'company/admin/active/' + companyName, '');
  }

  deactiveCompany(companyName: string): Observable<ApiResponse> {
    if (!companyName) { return new Observable(); }
    return this.http.post<ApiResponse>(this.baseUrl + 'company/admin/deactive/' + companyName, '');
  }

  getCompanies(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + 'company/all');
  }

  getByCompanyName(companyName: string): Observable<ApiResponse> {
    if (!companyName) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'company/name/' + companyName);
  }

  getByCompanyStockCode(companyStockCode: string): Observable<ApiResponse> {
    if (!companyStockCode) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'company/code/' + companyStockCode);
  }

  getCompaniesBySectorName(sectorName: string): Observable<ApiResponse> {
    if (!sectorName) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'company/sector/' + sectorName);
  }

  getCompaniesByStockExchange(stockExchange: string): Observable<ApiResponse> {
    if (!stockExchange) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'company/exchange/' + stockExchange);
  }

  getCompaniesLikeCompanyName(companyName: string): Observable<ApiResponse> {
    if (!companyName) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'company/nameDebounce/' + companyName);
  }

  getCompaniesLikeCompanyStockCode(companyStockCode: string): Observable<ApiResponse> {
    if (!companyStockCode) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'company/codeDebounce/' + companyStockCode);
  }

  getCompaniesLikeDebounce(debounce: string): Observable<ApiResponse> {
    if (!debounce) { return new Observable(); }
    if (debounce.match(/^\d/)) {
      console.log('getCompaniesLikeCriteria-number search');
      return this.getCompaniesLikeCompanyStockCode(debounce);
    } else {
      console.log('getCompaniesLikeCriteria-string search');
      return this.getCompaniesLikeCompanyName(debounce);
    }
  }

  // Ipo Component - ipoDetailsService
  newIpo(ipo: Ipo): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'ipodetails/admin/new', ipo);
  }

  updateIpo(companyName: string, ipo: Ipo): Observable<ApiResponse> {
    if (!companyName) { return new Observable(); }
    return this.http.post<ApiResponse>(this.baseUrl + 'ipodetails/admin/upt/' + companyName, ipo);
  }

  getIpos(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + 'ipodetails/all');
  }

  getIpoByCompanyName(companyName: string): Observable<ApiResponse> {
    if (!companyName) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'ipodetails/' + companyName);
  }

  // get latest stock price for one company
  // compare-component - stockPriceService
  getLatestPriceByStockCode(stockCode: string): Observable<ApiResponse> {
    if (!stockCode) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'stockprice/current/' + stockCode);
  }

  // get stock prices for one company
  getPricesByStockCode(stockCode: string): Observable<ApiResponse> {
    if (!stockCode) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'stockprice/all/' + stockCode);
  }

  // get stock prices for one company and between datetime
  // params: {
  //   "start": "2019-06-08 11:20:00",
  //   "end": "2019-06-08 11:30:00"
  // }
  getPricesByStockCodeAndDateTime(stockCode: string, params: object): Observable<ApiResponse> {
    if (!stockCode) { return new Observable(); }
    return this.http.post<ApiResponse>(this.baseUrl + 'stockprice/allbetween/' + stockCode, params);
  }

}
