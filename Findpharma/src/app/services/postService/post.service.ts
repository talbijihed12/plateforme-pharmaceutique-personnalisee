import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostModel } from 'src/app/components/models/PostModel';
import { CreatePostPayload } from 'src/app/components/models/post-create-payload';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiServiceUrl=environment.apiBaseUrl;
  constructor(private http: HttpClient) { }
  getAllPosts(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>(`${this.apiServiceUrl}/posts`);
  }

  createPost(postPayload: CreatePostPayload): Observable<any> {
    return this.http.post(`${this.apiServiceUrl}/posts/add`, postPayload);
  }

  getPost(id: number): Observable<PostModel>{
    return this.http.get<PostModel>(`${this.apiServiceUrl}/posts/` + id);
  }

  getAllPostsByUser(name: string): Observable<PostModel[]> {
    return this.http.get<PostModel[]>(`${this.apiServiceUrl}/posts/byUsername/` + name);
  }
  getPostsBySubplateforme(id: number): Observable<PostModel[]> {
    return this.http.get<PostModel[]>(`${this.apiServiceUrl}/posts/by-subplateforme/` + id)
  }
  deletePost(id: number) {
    return this.http.delete(`${this.apiServiceUrl}/posts/delete/` + id);
  }
}
