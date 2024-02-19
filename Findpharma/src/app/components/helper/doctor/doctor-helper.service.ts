import { Injectable, AfterContentInit, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import doctor from '../../data/doctor/doctor.json';
import category from '../../data/category.json';

@Injectable({
  providedIn: 'root'
})
export class DoctorHelperService implements AfterContentInit, OnInit {
  // pagination
  page: number = 1;
  // Doctor
  public doctorblock = doctor;
  public doctordetails = doctor;
  // Category
  public category = category;
  public doctorcategory = category;
  constructor(private router: ActivatedRoute) {}
  // Add Heart
  public favorite: boolean = false;
  favoriteTrigger(item: { favorite: boolean; }) {
    item.favorite = !item.favorite;
  }
  // Category
  public getCategories(items: string | any[]) {
    var elems = category.filter((item: { id: string; }) => {
      return items.includes(item.id)
    });
    return elems;
  }
  // Author
  public getAuthor(items: string | any[]) {
    var elems = doctor.filter((item: { id: string; }) => {
      return items.includes(item.id)
    });
    return elems;
  }
  // Doctor Details
  public getDoctor(id: any) {
    this.doctordetails = doctor.filter((item: { id: any; }) => { return item.id == id });
  }
  // Category Filter
  public setCategory(id: any) {
    this.doctorcategory = id;
  }
  public getCategory() {
    return this.doctorcategory;
  }
  public getDoctorsByCategory(catId: string) {
    return this.doctorblock = doctor.filter((item: { category: number[]; }) => { return item.category.includes(parseInt(catId)) });
  }
  // Fetch All filter
  public setDoctors() {
    var doctorsByCategory = this.getCategory() != undefined ? this.getDoctorsByCategory(this.getCategory()) : '';
    if ((doctorsByCategory != '' || doctorsByCategory != undefined || doctorsByCategory != null) && doctorsByCategory.length > 0) {
      this.doctorblock = doctorsByCategory;
    } 
  }
  ngAfterContentInit(): void {
    this.setCategory(this.router.snapshot.params.catId);
    this.setDoctors();
    this.getDoctor(this.router.snapshot.params.id);
  }
  ngOnInit(): void {

  }
}
