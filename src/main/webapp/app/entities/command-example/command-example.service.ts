import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommandExample } from 'app/shared/model/command-example.model';

type EntityResponseType = HttpResponse<ICommandExample>;
type EntityArrayResponseType = HttpResponse<ICommandExample[]>;

@Injectable({ providedIn: 'root' })
export class CommandExampleService {
  public resourceUrl = SERVER_API_URL + 'api/command-examples';

  constructor(protected http: HttpClient) {}

  create(commandExample: ICommandExample): Observable<EntityResponseType> {
    return this.http.post<ICommandExample>(this.resourceUrl, commandExample, { observe: 'response' });
  }

  update(commandExample: ICommandExample): Observable<EntityResponseType> {
    return this.http.put<ICommandExample>(this.resourceUrl, commandExample, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommandExample>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommandExample[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
