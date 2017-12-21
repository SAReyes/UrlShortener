import { Component } from '@angular/core';
import { ShortUrl } from './input-form/input-form.component'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  ShortUrl: ShortUrl;

  shortUrlHandler(shortUrl: ShortUrl) {
    this.ShortUrl = shortUrl
  }
}

export class UrlService {
  shortUrl: ShortUrl
}