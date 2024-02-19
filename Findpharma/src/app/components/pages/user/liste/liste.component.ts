import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/app/components/models/User';
import { LoginService } from 'src/app/services/userService/login.service';
import { UserService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-liste',
  templateUrl: './liste.component.html',
  styleUrls: ['./liste.component.css']
})
export class ListeComponent implements OnInit {
  @ViewChild('usermodal') usermodal: any; 
  users:User[]=[];
  page: number = 1;
  searchText!: string;
  selectedUserId!: string;
  selectedRole!: string;


  constructor(private userService:UserService,private loginService:LoginService,private modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadUsers();
  }
  openEditRoleModal(userId: string, currentRole: string) {
    this.selectedUserId = userId;
    this.selectedRole = currentRole;

    this.modalService.open(this.usermodal, { centered: true }).result.then(
      (result) => {
        if (result === 'save') {
          this.assignRole(this.selectedUserId, this.selectedRole);
        }
      },
      (reason) => {
      }
    );
  }

  assignRole(id: string, roleName: string) {
    this.userService.assignRoleToUser(id, roleName).subscribe(
      (response) => {
        console.log('Role assigned successfully', response);

        this.modalService.dismissAll();
      },
      (error) => {
        console.error('Failed to assign role', error);
        this.modalService.dismissAll();
      }
      
    );
  }
  onRoleFormSubmit() {
    if (this.selectedUserId && this.selectedRole) {
        this.userService.assignRoleToUser(this.selectedUserId, this.selectedRole).subscribe(
        (response) => {
          console.log('Role assigned successfully', response);
            this.modalService.dismissAll();
        },
        (error) => {
          }
      );
    } else {
    }
  }

  openPopupForm(content: TemplateRef<any>) {
    this.modalService.open(content, { size: 'lg' });
  }
  loadUsers():void{
    this.userService.list().subscribe(
      data =>{
        this.users=data;
      },
      error => console.log(error)
    );

  }
  onDelete(id:number):void{
    if (confirm('this will delete this profile?')) {
      if (confirm('Are you sure you want to delete this profile?')){
    this.userService.delete(id).subscribe(
      data=>{
        console.log(data);
        this.loadUsers();
      },
      error =>console.log(error)
    )
  }
}
}

}
