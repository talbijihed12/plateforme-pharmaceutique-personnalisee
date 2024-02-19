import { Injectable, AfterContentInit, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import authors from '../../data/doctor/doctor.json';
import blog from '../../data/blog/blog.json';
import blogcategory from '../../data/category.json';
import blogtags from '../../data/tags.json';
import { PostModel } from '../../models/PostModel';
import { PostService } from 'src/app/services/postService/post.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CommentPayload } from '../../models/Comment-payload';
import { CommentService } from 'src/app/services/postService/comment.service';
import { throwError } from 'rxjs';
import { LoginService } from 'src/app/services/userService/login.service';
import { AuthConfig, NullValidationHandler } from 'angular-oauth2-oidc';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Injectable({
  providedIn: 'root'
})
export class BlogHelperService implements AfterContentInit, OnInit {

  // pagination
  page: number = 1;
  loading = true;


   // Magasin
    posts !:PostModel[];
    comments!: CommentPayload[];
    commentForm!: FormGroup;
    name!: string;
    postLength!: number;
    commentLength!: number;
    commentPayload!: CommentPayload;
    postId!: number;
    post!: PostModel;
    idComment!: number;
    selectedUserId!: string;
    text: string = '';
    @ViewChild('commentModal') commentModal: any; 











  // Blog
 
  public blogblock = blog;
  public blogdetails = blog;
  // Category
  public category = blogcategory;
  public blogcategory = blogcategory;
  // Tags
  public tags = blogtags;
  public blogtags = blogtags;
  // Authors
  public author = authors;
  // Extra
  public searchText: string;
  public searchQuery: string;
  constructor(private router: Router, private route: ActivatedRoute, private sanitizer: DomSanitizer,    private postService:PostService   ,private commentService: CommentService, private activateRoute: ActivatedRoute,private loginService:LoginService ,private modalService: NgbModal) {
    this.searchText = '';
    this.searchQuery = '';
    this.postId = this.activateRoute.snapshot.params.id;
    if (this.loginService.getIsLoggedIn()) {
      this.name = this.loginService.getUsername();
    }
    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required)
    });
    this.commentPayload = {
      text: '',
      postId: this.postId,
      
    };
    
    this.postService.getAllPostsByUser(this.name).subscribe(data => {
      this.posts = data;
      this.postLength = data.length;
    });
    this.commentService.getAllCommentsByUser(this.name).subscribe(data => {
      this.comments = data;
      this.commentLength = data.length;
    });
    
  }
  
 
  loadListPosts() {
    this.loading = true;
    setTimeout(() => {
      this.postService.getAllPosts().subscribe(
        (data) => {
          this.posts = data;
          this.loading = false;
          console.log('/********* LIST USERS *****************/');
          console.log(this.posts);
        },
        (error) => {
          console.log(error);
        }
      );
    }, 3000);
  }
  postComment() {
    this.commentPayload.text = this.commentForm.get('text')!.value;
    this.commentService.postComment(this.commentPayload).subscribe(data => {
    this.commentForm.get('text')!.setValue('');
    this.getCommentsForPost();
    }, error => {
    throwError(error);
    console.error('Error:', error);

    })
    }
    private getPostById() {
      this.postService.getPost(this.postId).subscribe(data => {
      this.post = data;
      }, error => {
      throwError(error);
      });
      }

      private getCommentsForPost() {
        this.commentService.getAllCommentsForPost(this.postId).subscribe(data => {
        this.comments = data;
        }, error => {
        throwError(error);
        });
        }
        onDelete(id:number):void{
          if (confirm('this will delete this comment?')) {
            if (confirm('Are you sure you want to delete this comment?')){
          this.commentService.deleteComment(id).subscribe(
            data=>{
              console.log(data);
            },
            error =>console.log(error)
          )
        }
      }
      }
      DeletePost(id:number):void{
        if (confirm('this will delete this post?')) {
          if (confirm('Are you sure you want to delete this post?')){
        this.postService.deletePost(id).subscribe(
          data=>{
            console.log(data);
          },
          error =>console.log(error)
        )
      }
    }
    }
    openEditCommentModal(id: number, text: string) {
      this.idComment = id;
      this.text = text; 
      this.modalService.open(this.commentModal, { centered: true }).result.then(
        (result) => {
          if (result === 'save') {
            this.updateComment(this.idComment,text);
            this.modalService.dismissAll();

          }
        },
        (reason) => {
        }
      );
    }
    updateComment(idComment: number, text: string): void {
      this.commentService.updateComment(idComment, text).subscribe(
        (response) => {
          console.log('Comment updated:', response);
          this.modalService.dismissAll();

        }
        
      );
      this.modalService.dismissAll();

    }
  
  // Category
  public getCategories(items: string | any[]) {
    var elems = blogcategory.filter((item: { id: string; }) => {
      return items.includes(item.id)
    });
    return elems;
  }
  // Tags
  public getTags(items: string | any[]) {
    var elems = blogtags.filter((item: { id: string; }) => {
      return items.includes(item.id)
    });
    return elems;
  }
  // Author
  public getAuthor(items: string | any[]) {
    var elems = authors.filter((item: { id: string; }) => {
      return items.includes(item.id)
    });
    return elems;
  }
  // Count Category
  public setCategoriesCount() {
    for (var i = 0; i < this.blogcategory.length; i++) {
      var count = this.blogblock.filter((post: { category: number[]; }) => { return post.category.includes(parseInt(this.blogcategory[i].id)) });
      count = count.length;
      this.blogcategory[i].count = count;
    }
  }
  // Related post
  public getPostByCategory(items: string | any[]) {
    var elems = blog.filter((post: { id: string; category: any[]; }) => { return parseInt(post.id) !== parseInt(this.route.snapshot.params.id) && post.category.some(r => items.includes(r)) });
    return elems;
  }
  // Post Details
  public setPost(id: any) {
    this.blogdetails = blog.filter((item: { id: any; }) => { return item.id == id });
  }
  // sanitize url
  public sanitnizeAudioURL(url: string) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url)
  }
  // Recent post
  public changeToMonth(month: string | number | any) {
    var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    return months[month];
  }
  public setDemoDate() {
    var today = new Date();
    this.blogblock.slice(0, 3).map((post: { timestamp: number; postdate: string; }) => (
      post.timestamp = today.getTime(),
      // Remove this date on your live demo. This is only used for preview purposed. Your date should actually be updated
      // in the blog.json object
      post.postdate = `${today.getDate()} ${this.changeToMonth(today.getMonth())}, ${today.getFullYear()}`
    ));
  }
  public getRecentPost() {
    var elems = blog.filter((post: { timestamp: number | any; postdate: string | number | Date; }) => {
      return post.timestamp > new Date(post.postdate);
    });
    return elems;
  }
  // get Name
  public getNameInitials(string: string){
    var names = string.split(' '),
        initials = names[0].substring(0, 1).toUpperCase();
    if (names.length > 1) {
        initials += names[names.length - 1].substring(0, 1).toUpperCase();
    }
    return initials;
  }
  // Search Filter
  onSubmit() {
    if (this.searchText === "") {
      return;
    } else {
      this.router.navigate(['blog/search', this.searchText]);
    }
  }
  // Filter
  // Category Filter
  public setCategory(id: any) {
    this.blogcategory = id;
  }
  public getCategory() {
    return this.blogcategory;
  }
  public getPostsByCategory(catId: string) {
    return this.blogblock = blog.filter((item: { category: number[]; }) => { return item.category.includes(parseInt(catId)) });
  }
  // Tag Filter
  public setTag(id: any) {
    this.blogtags = id;
  }
  public getTag() {
    return this.blogtags;
  }
  public getPostsByTags(tagId: string) {
    return this.blogblock = blog.filter((item: { tags: number[]; }) => { return item.tags.includes(parseInt(tagId)) });
  }
  // Author Filter
  public setAuthor(id: any) {
    this.author = id;
  }
  public getAuthorPost() {
    return this.author;
  }
  public getPostsByAuthors(authorId: string) {
    return this.blogblock = blog.filter((item: { author: number[]; }) => { return item.author.includes(parseInt(authorId)) });
  }
  // Search Filter
  public setSearch(query: string) {
    this.searchQuery = query;
  }
  public getSearch() {
    return this.searchQuery;
  }
  public getPostsBySearch(query: string) {
    return this.blogblock = blog.filter((item: { title: (string) }) => {
      return item.title.toLowerCase().includes(query.toLowerCase())
    });
  }
  // Fetch All filter
  public setPosts() {
    var postsByCategory = this.getCategory() != undefined ? this.getPostsByCategory(this.getCategory()) : '',
      postsByTags = this.getTag() != undefined ? this.getPostsByTags(this.getTag()) : '',
      postsByAuthor = this.getAuthorPost() != undefined ? this.getPostsByAuthors(this.getAuthorPost()) : '',
      postsBySearch = this.getSearch() != undefined ? this.getPostsBySearch(this.getSearch()) : '';

    if ((postsByCategory != '' || postsByCategory != undefined || postsByCategory != null) && postsByCategory.length > 0) {
      this.blogblock = postsByCategory;
    } else if ((postsByTags != '' || postsByTags != undefined || postsByTags != null) && postsByTags.length > 0) {
      this.blogblock = postsByTags;
    } else if ((postsByAuthor != '' || postsByAuthor != undefined || postsByAuthor != null) && postsByAuthor.length > 0) {
      this.blogblock = postsByAuthor;
    } else if ((postsBySearch != '' || postsBySearch != undefined || postsBySearch != null) && postsBySearch.length > 0) {
      this.blogblock = postsBySearch;
    } 
  }
  ngAfterContentInit(): void {
    this.setCategory(this.route.snapshot.params.catId);
    this.setTag(this.route.snapshot.params.tagId);
    this.setAuthor(this.route.snapshot.params.authorId);
    this.setSearch(this.route.snapshot.params.query);
    this.setPosts();
    this.setPost(this.route.snapshot.params.id);
  }
  ngOnInit(): void {
    this.setCategoriesCount();
    this.setDemoDate();
    this.loadListPosts();
    this.getPostById();
    this.getCommentsForPost();


  }
  
  // Social Share
  public pageUrl = window.location.href;
  public socialShare(title: string) {
    var socialIcons = [
      {
        title: "facebook",
        iconClass: "fab fa-facebook-f",
        iconStyle:"fb",
        link: "https://www.facebook.com/sharer/sharer.php?u=" + encodeURIComponent(this.pageUrl) + ""
      },
      {
        title: "twitter",
        iconClass: "fab fa-twitter",
        iconStyle:"tw",
        link: "http://twitter.com/intent/tweet?text=" + encodeURIComponent(title) + "&" + encodeURIComponent(this.pageUrl) + ""
      },
      {
        title: "linkedin",
        iconClass: "fab fa-linkedin-in",
        iconStyle:"ln",
        link: "https://www.linkedin.com/shareArticle?mini=true&url=" + encodeURIComponent(this.pageUrl) + "&title=" + encodeURIComponent(title) + ""
      },
      {
        title: "pinterest",
        iconClass: "fab fa-pinterest-p",
        iconStyle:"gg",
        link: "http://pinterest.com/pin/create/button/?url=" + encodeURIComponent(this.pageUrl) + ""
      }
    ];
    return socialIcons;
  }
  openSocialPopup(social: any) {
    window.open(social.link, "MsgWindow", "width=600,height=600")
  }
}
