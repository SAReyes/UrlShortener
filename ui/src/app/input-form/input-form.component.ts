import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-input-form',
  templateUrl: './input-form.component.html',
  styleUrls: ['./input-form.component.css']
})
export class InputFormComponent implements OnInit {

  url;

  @Output() shortUrlEmitter: EventEmitter<ShortUrl> = new EventEmitter();
  private http: HttpClient

  constructor(private httpClient: HttpClient) {
    this.http = httpClient;
  }

  ngOnInit() {
  }

  getUrl(url: String) {
    const req = this.http.post("/api/link", {
      url: url,
      sponsor: "sponsor"
    }, { headers: { 'Content-Type': 'application/json' } }).subscribe(
      res => {
        this.shortUrlEmitter.emit(new ShortUrl(Object.create({ url: res['uri'], qr: res['qr'] })));
      },
      err => {
        // console.log(err);
        this.shortUrlEmitter.emit(new ShortUrl(Object.create({ err: true, errMsg: err.error.message })));
      });
  }
}
interface ShortUrlParams {
  url: String;
  qr: String;
  err: boolean;
  errMsg: String;
}
export class ShortUrl {
  url: String;
  qr: String;
  err: boolean;
  errMsg: String;

  constructor({ url, qr, err, errMsg }: ShortUrlParams) {
    this.url = url;
    this.qr = qr;
    this.err = err;
    this.errMsg = errMsg;
  }
}
