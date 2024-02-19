import { Component, OnInit } from '@angular/core';
import { ContactHelperService } from 'src/app/components/helper/contact/contact-helper.service';
import { Contact } from '../../../models/contact/contact';

@Component({
  selector: 'app-quote',
  templateUrl: './quote.component.html',
  styleUrls: ['./quote.component.css']
})
export class QuoteComponent implements OnInit {

  model = new Contact;
  submitted = false;
  error: {} | undefined;
  constructor(private contactService: ContactHelperService) { }
  onSubmit() {
    this.submitted = true;
    return this.contactService.contactForm(this.model).subscribe(
      data => this.model = data,
      error => this.error = error
    );
  }; 
  resolved(captchaResponse: string) {
    console.log(`Resolved response token: ${captchaResponse}`);
  }

  ngOnInit(): void {
  }

}
