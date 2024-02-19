import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  constructor(private formbuild: FormBuilder) { }
  appointmentForm = this.formbuild.group({
    fullname: ['', Validators.required],
    email: ['', Validators.required],
    dateofbirth: ['', Validators.required],
    phoneno: ['', Validators.required],
    gender: ['', Validators.required],
    hospital: ['', Validators.required],
    service: ['', Validators.required],
    date: ['', Validators.required],
    doctor: ['', Validators.required],
    remarks: ['', Validators.required],
    cardName: ['', Validators.required],
    cardNumber: ['', Validators.required],
    expDate: ['', Validators.required],
    cardCvv: ['', Validators.required],
    condition: ['', Validators.required],
  });
  onAppointmentFormSubmit() {
    console.warn(this.appointmentForm.value);
    this.appointmentForm.reset();
  }
  ngOnInit(): void {
    
  }

}
