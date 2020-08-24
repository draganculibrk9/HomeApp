import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthModule} from './auth/auth.module';
import {AppRoutingModule} from './routing/app-routing.module';
import {ServicesModule} from './services/services.module';
import {DashboardModule} from "./dashboard/dashboard.module";
import {PipeModule} from "./pipe/pipe.module";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AuthModule,
    AppRoutingModule,
    ServicesModule,
    DashboardModule,
    PipeModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
