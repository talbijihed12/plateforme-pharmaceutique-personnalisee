import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreatePostPayload } from '../../models/post-create-payload';
import { Topic } from '../../models/Topic';
import { Router } from '@angular/router';
import { PostService } from 'src/app/services/postService/post.service';
import { ToastrService } from 'ngx-toastr';
import { TopicService } from 'src/app/services/postService/topic.service';
import { throwError } from 'rxjs';
import { MessageService } from 'primeng/api';
import { FileUpload } from 'primeng/fileupload';



@Component({
  selector: 'app-blog-create',
  templateUrl: './blog-create.component.html',
  styleUrls: ['./blog-create.component.css']
})
export class BlogCreateComponent implements OnInit {
  createPostForm!: FormGroup;
  postPayload!: CreatePostPayload;
  topics!: Array<Topic>;
  selectedState: any = null;
  uploadedFiles: any[] = [];

  states: any[] = [
    { name: "Arizona", code: "Arizona" },
    { name: "California", value: "California" },
    { name: "Florida", code: "Florida" },
    { name: "Ohio", code: "Ohio" },
    { name: "Washington", code: "Washington" }
  ];
  constructor(
    private router: Router,
    private postService: PostService,
    private subplateformeService: TopicService,
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private messageService: MessageService

  ) {}

  ngOnInit(): void {
    this.initCreatePostForm();
    this.initListSubPlateforms();
  }
  initCreatePostForm() {
    this.createPostForm = this.formBuilder.group({
      postName: ['', Validators.required],
      topic: ['', Validators.required],
      hashtag: ['', Validators.required],
      description: ['', Validators.required],
      
    });
  }
  initListSubPlateforms() {
    this.subplateformeService.getAllSubplateforme().subscribe(
      (data) => {
        this.topics = data;
      },
      (error) => {
        throwError(error);
      }
    );
  }
  

  createPost() {
    if (this.createPostForm.valid) {
      this.postPayload = this.createPostForm.value;
      this.postService.createPost(this.postPayload).subscribe(
        (data) => {
          this.router.navigateByUrl('');
        },
        (error) => {
          throwError(error);
          this.toastr.error('Adding Post Failed! Please try again');
        }
      );
    } else {
      this.toastr.error('Adding Post Failed! Please try again');
    }
  }

  discardPost() {
    this.router.navigateByUrl('');
  }
  onUpload(event: { files:  FileUpload[]; }) {
    for(let file of event.files) {
        this.uploadedFiles.push(file);
    }

    this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: ''});
}

}
