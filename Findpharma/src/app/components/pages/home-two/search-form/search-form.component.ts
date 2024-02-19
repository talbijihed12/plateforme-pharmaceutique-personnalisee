import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({ 
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  constructor() { }
  searchForm = new FormGroup({
    topic: new FormControl('', Validators.required),
    location: new FormControl('', Validators.required)
  });
  onSubmit() {
    console.warn(this.searchForm.value);
    this.searchForm.reset();
  }
  ngOnInit(): void {
  }

}
