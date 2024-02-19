import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from 'src/app/components/models/Topic';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private apiServiceUrl=environment.apiBaseUrl;
  constructor(private http: HttpClient) { }
  getAllSubplateforme(): Observable<Array<Topic>>{
    return this.http.get<Array<Topic>>(`${this.apiServiceUrl}/Topic`);
  }
  
  createSubplateforme(subplateformeModel: Topic):Observable<Topic> {
    return this.http.post<Topic>(`${this.apiServiceUrl}/Topic/add`,subplateformeModel);
  }
  
}
