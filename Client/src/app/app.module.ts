import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AuthModule} from './auth/auth.module';
import {AppRoutingModule} from './routing/app-routing.module';
import {ServicesModule} from './services/services.module';
import {DashboardModule} from './dashboard/dashboard.module';
import {PipeModule} from './pipe/pipe.module';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {
  MAT_MOMENT_DATE_FORMATS,
  MomentDateAdapter
} from '@angular/material-moment-adapter';
import {GuardsModule} from "./guards/guards.module";
import {DirectiveModule} from "./directives/directive.module";


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AuthModule,
    AppRoutingModule,
    ServicesModule,
    DashboardModule,
    PipeModule,
    GuardsModule,
    DirectiveModule
  ],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE]
    },
    {
      provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
