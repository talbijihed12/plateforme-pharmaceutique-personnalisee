import { Injectable, AfterContentInit, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import service from '../../data/service/service.json';
import category from '../../data/category.json';

@Injectable({
  providedIn: 'root'
})
export class ServiceHelperService implements AfterContentInit, OnInit {
  // pagination
  page: number = 1;
  // Service
  public serviceblock = service;
  public servicedetails = service;
  // Category
  public category = category;
  public servicecategory = category;
  constructor(private route: ActivatedRoute) { }
  // Category
  public getCategories(items: string | any[]) {
    var elems = category.filter((item: { id: string; }) => {
      return items.includes(item.id)
    });
    return elems;
  }
  // Count Category
  public setCategoriesCount() {
    for (var i = 0; i < this.servicecategory.length; i++) {
      var count = this.serviceblock.filter((item: { category: number[]; }) => { return item.category.includes(parseInt(this.servicecategory[i].id)) });
      count = count.length;
      this.servicecategory[i].count = count;
    }
  }
  // Service Details
  public getService(id: any) {
    this.servicedetails = service.filter((item: { id: any; }) => { return item.id == id });
  }
  // Recent service
  public changeToMonth(month: string | number | any) {
    var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    return months[month];
  }
  public setDemoDate() {
    var today = new Date();
    this.serviceblock.slice(0, 3).map((service: { timestamp: number; servicedate: string; }) => (
      service.timestamp = today.getTime(),
      // Remove this date on your live demo. This is only used for preview purposed. Your date should actually be updated
      // in the service.json object
      service.servicedate = `${this.changeToMonth(today.getMonth())} ${today.getDate()}, ${today.getFullYear()}`
    ));
  }
  public getRecentService() {
    var elems = service.filter((service: { timestamp: number | any; servicedate: string | number | Date; }) => {
      return service.timestamp > new Date(service.servicedate);
    });
    return elems;
  }
  // Category Filter
  public setCategory(id: any) {
    this.servicecategory = id;
  }
  public getCategory() {
    return this.servicecategory;
  }
  public getServicesByCategory(catId: string) {
    return this.serviceblock = service.filter((item: { category: number[]; }) => { return item.category.includes(parseInt(catId)) });
  }
  // Fetch All filter
  public setServices() {
    var servicesByCategory = this.getCategory() != undefined ? this.getServicesByCategory(this.getCategory()) : '';
    if ((servicesByCategory != '' || servicesByCategory != undefined || servicesByCategory != null) && servicesByCategory.length > 0) {
      this.serviceblock = servicesByCategory;
    }
  }
  ngAfterContentInit(): void {
    this.setCategory(this.route.snapshot.params.catId);
    this.setServices();
    this.getService(this.route.snapshot.params.id);
  }
  ngOnInit(): void {
    this.setCategoriesCount();
    this.setDemoDate();
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