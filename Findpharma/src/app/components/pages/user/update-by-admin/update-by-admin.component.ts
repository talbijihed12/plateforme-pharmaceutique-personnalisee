import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/components/models/User';
import { UserService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-update-by-admin',
  templateUrl: './update-by-admin.component.html',
  styleUrls: ['./update-by-admin.component.css']
})
export class UpdateByAdminComponent implements OnInit {
  user!:User;
  firstname!:string;
  lastname!:string;
  emailId!:string;
  passwordd!:string;
  constructor(private userService:UserService, private activatedRoute:ActivatedRoute,private router:Router) { }

  ngOnInit(): void {
    const id=this.activatedRoute.snapshot.params.id;
    this.userService.detail(id).subscribe(
      data=>{
        this.user=data;
      },
      error=>console.log(error)
    )
  }
  onUpdate():void{
    if (confirm('this will update this profile?')) {
      if (confirm('Are you sure you want to update this profile?')){
    const id=this.activatedRoute.snapshot.params.id;
    this.userService.update(id,this.user).subscribe(
      data=>{
        console.log(data);
        this.back();
        this.router.navigate(['/liste']);
      },
      error=>console.log(error)
      )
      this.router.navigate(['/liste'])
  }
    }
  }
  back():void{
    this.router.navigate(['/liste']);
  }


}
