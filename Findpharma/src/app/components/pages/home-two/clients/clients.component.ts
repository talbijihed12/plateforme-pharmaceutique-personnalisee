import { Component, OnInit } from '@angular/core';
import data from "../../../data/clients.json";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {
  public clients = data;
  constructor() { }

  ngOnInit(): void {
  }

}
