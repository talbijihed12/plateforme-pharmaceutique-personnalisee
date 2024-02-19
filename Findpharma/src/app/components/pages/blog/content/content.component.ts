import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { throwError } from 'rxjs';
import { BlogHelperService } from 'src/app/components/helper/blog/blog-helper.service';
import { CommentPayload } from 'src/app/components/models/Comment-payload';
import { PostModel } from 'src/app/components/models/PostModel';
import { CommentService } from 'src/app/services/postService/comment.service';
import { PostService } from 'src/app/services/postService/post.service';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent extends BlogHelperService{
  
  



  

}
