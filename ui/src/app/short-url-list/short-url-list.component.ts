import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ShortUrl } from '../input-form/input-form.component'

@Component({
  selector: 'app-short-url-list',
  templateUrl: './short-url-list.component.html',
  styleUrls: ['./short-url-list.component.css']
})
export class ShortUrlListComponent implements OnInit {

  loaded: boolean = false;
  urls: ShortUrl[] = [];

  @Input() shortUrl: ShortUrl;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    this.loaded = !changes.shortUrl.firstChange
  }
}
