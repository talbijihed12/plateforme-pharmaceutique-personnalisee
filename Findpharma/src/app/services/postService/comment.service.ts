import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentPayload } from 'src/app/components/models/Comment-payload';
import { CommentModel } from 'src/app/components/models/CommentModel';
import { UpdateComment } from 'src/app/components/models/UpdateComment';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiServiceUrl=environment.apiBaseUrl;
  constructor(private httpClient: HttpClient) {}

  getAllCommentsForPost(postId: number): Observable<CommentPayload[]> {
    return this.httpClient.get<CommentPayload[]>(`${this.apiServiceUrl}/comments/by-post/` + postId);
  }

  postComment(commentPayload: CommentPayload): Observable<any> {
    return this.httpClient.post<any>(`${this.apiServiceUrl}/comments`,commentPayload);
  }

  //getAllCommentsByUser(name: string) {
    //return this.httpClient.get<CommentPayload[]>(
    //  'http://localhost:8080/api/comments/by-user/' + name
   // );
  //}
  getComment(idComment: number) {
    return this.httpClient.get<CommentModel>(
      `${this.apiServiceUrl}/comments`+ idComment
    );
  }
  deleteComment(id: number) {
    return this.httpClient.delete(
      `${this.apiServiceUrl}/comments/delete/` + id
    );
  }
  getAllCommentsByUser(name: string): Observable<CommentModel[]> {
    return this.httpClient.get<CommentModel[]>(`${this.apiServiceUrl}/comments/byUsername/` + name);
  }
  updateComment(idComment: number, updatedText: string): Observable<any> {
    const url = `${this.apiServiceUrl}/comments/update/${idComment}`;
    
    const commentTO = { text: updatedText };
    
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    return this.httpClient.put(url, commentTO, { headers });
  }
}
