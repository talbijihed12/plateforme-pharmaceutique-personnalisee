import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/components/models/User';
import { UserService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  user!:User;
  username!:string;

  constructor( private userService:UserService, private activatedRoute:ActivatedRoute,private router:Router) { }

  ngOnInit(): void {
    const id=this.activatedRoute.snapshot.params.id;
    this.userService.detail(id).subscribe(
      data=>{
        this.user=data;
      },
      error=>console.log(error)
    )
  }
  back():void{
    this.router.navigate(['/liste']);
  }

}
