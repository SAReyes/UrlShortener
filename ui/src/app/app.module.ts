import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


import { AppComponent } from './app.component';
import { InputFormComponent } from './input-form/input-form.component';
import { ShortUrlListComponent } from './short-url-list/short-url-list.component';


@NgModule({
  declarations: [
    AppComponent,
    InputFormComponent,
    ShortUrlListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
