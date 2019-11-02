import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReceiptExample } from 'app/shared/model/receipt-example.model';

type EntityResponseType = HttpResponse<IReceiptExample>;
type EntityArrayResponseType = HttpResponse<IReceiptExample[]>;

@Injectable({ providedIn: 'root' })
export class ReceiptExampleService {
  public resourceUrl = SERVER_API_URL + 'api/receipt-examples';

  constructor(protected http: HttpClient) {}

  create(receiptExample: IReceiptExample): Observable<EntityResponseType> {
    return this.http.post<IReceiptExample>(this.resourceUrl, receiptExample, { observe: 'response' });
  }

  update(receiptExample: IReceiptExample): Observable<EntityResponseType> {
    return this.http.put<IReceiptExample>(this.resourceUrl, receiptExample, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReceiptExample>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReceiptExample[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
