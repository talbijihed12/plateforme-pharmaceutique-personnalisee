import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/components/models/User';
import { UpdateRequest } from 'src/app/components/models/updateRequest';
import { LoginService } from 'src/app/services/userService/login.service';
import { UserService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {
  user!:User;
  signupForm!: FormGroup;
  signupRequestPayload!:UpdateRequest ;
  username!:string;
  constructor(private userService:UserService, private loginService:LoginService, private activatedRoute:ActivatedRoute,private router:Router,private formBuilder: FormBuilder) {
    this.signupForm = this.formBuilder.group({
      username: [this.loginService.getUsername(), Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
    });
  this.signupRequestPayload ={
    username:this.loginService.getUsername(),
    email:'',
    password:'',
    firstName: '',
    lastName: '',


  }
  }

  ngOnInit(): void {
    this.username=this.loginService.getUsername();

  }
  initSignUpForm() {
    this.signupForm = this.formBuilder.group({
      username: [this.loginService.getUsername, Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }
  onUpdate():void{
    if (confirm('this will update your profile?')) {
      if (confirm('Are you sure you want to update this profile?')){
  this.user = this.signupForm.value;
  this.userService.updateprofile(this.user).subscribe(
    data=>{
      console.log(data);
      alert("User updated Successfully");
      this.router.navigate(['']);



    },
    error=>console.log(error)
    )

    }
    }
   
    this.router.navigate(['']);
  }
}
