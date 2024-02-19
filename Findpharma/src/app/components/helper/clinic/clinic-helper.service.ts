import { Injectable, AfterContentInit, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import clinic from '../../data/clinic/clinic.json';
import category from '../../data/category.json';
import authors from '../../data/doctor/doctor.json';

@Injectable({
  providedIn: 'root'
})
export class ClinicHelperService implements AfterContentInit, OnInit {
  // pagination
  page: number = 1;
  // Clinic
  public clinicblock = clinic;
  public clinicdetails = clinic;
  // Category
  public category = category;
  public cliniccategory = category;
  constructor(private route: ActivatedRoute) { }
  // Add Heart
  public favorite : boolean = false;
  favoriteTrigger(item: { favorite: boolean; }){
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
    var elems = authors.filter((item: { id: string; }) => {
      return items.includes(item.id)
    });
    return elems;
  }
  // Clinic Details
  public getClinic(id: any) {
    this.clinicdetails = clinic.filter((item: { id: any; }) => { return item.id == id });
  }
  // Category Filter
  public setCategory(id: any) {
    this.cliniccategory = id;
  }
  public getCategory() {
    return this.cliniccategory;
  }
  public getClinicsByCategory(catId: string) {
    return this.clinicblock = clinic.filter((item: { category: number[]; }) => { return item.category.includes(parseInt(catId)) });
  }
  // Fetch All filter
  public setClinics() {
    var clinicsByCategory = this.getCategory() != undefined ? this.getClinicsByCategory(this.getCategory()) : '';
    if ((clinicsByCategory != '' || clinicsByCategory != undefined || clinicsByCategory != null) && clinicsByCategory.length > 0) {
      this.clinicblock = clinicsByCategory;
    }
  }
  ngAfterContentInit(): void {
    this.setCategory(this.route.snapshot.params.catId);
    this.setClinics();
    this.getClinic(this.route.snapshot.params.id);
  }
  ngOnInit(): void {
    
  }
}
