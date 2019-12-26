import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ApiResponse } from '../model/api.response';
import { Observable } from 'rxjs/index';
import { Exchange } from '../model/exchange.model';


@Injectable({
  providedIn: 'root'
})
export class ExchangeService {

  constructor(private http: HttpClient) { }

  baseUrl: string = environment.getBaseUrl('exc');

  newExchange(exchange: Exchange): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'exchange/admin/new', exchange);
  }

  updateExchange(stockExchange: string, exchange: Exchange): Observable<ApiResponse> {
    if (!stockExchange) { return new Observable(); }
    return this.http.post<ApiResponse>(this.baseUrl + 'exchange/admin/upt/' + stockExchange, exchange);
  }

  getExchanges(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + 'exchange/admin/all');
  }

  getExchange(stockExchange: string): Observable<ApiResponse> {
    if (!stockExchange) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'exchange/admin/' + stockExchange);
  }

}
