import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
// import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { FileUploadModule } from 'ng2-file-upload';
import { HighchartsChartModule } from 'highcharts-angular';

import { httpInterceptorProviders } from './interceptor';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TopBarComponent } from './common/top-bar/top-bar.component';
import { SignInComponent } from './common/sign-in/sign-in.component';
import { SignUpComponent } from './common/sign-up/sign-up.component';
import { WelcomeComponent } from './common/welcome/welcome.component';
import { PageNotFoundComponent } from './common/page-not-found/page-not-found.component';
import { PageForbiddenComponent } from './common/page-forbidden/page-forbidden.component';
import { InternalServerErrorComponent } from './common/internal-server-error/internal-server-error.component';
import { AdminComponent } from './component/admin/admin/admin.component';
import { UploadComponent } from './component/admin/upload/upload.component';
import { CompanyComponent } from './component/admin/company/company.component';
import { StockExchangeComponent } from './component/admin/stock-exchange/stock-exchange.component';
import { IpoComponent } from './component/admin/ipo/ipo.component';
import { UserComponent } from './component/user/user/user.component';
import { ProfileComponent } from './component/user/profile/profile.component';
import { IpoDetailsComponent } from './component/user/ipo-details/ipo-details.component';
import { CompanyCompareComponent } from './component/user/company-compare/company-compare.component';
import { SectorCompareComponent } from './component/user/sector-compare/sector-compare.component';
import { SectorComponent } from './component/admin/sector/sector.component';
import { GenerateChartComponent } from './component/user/generate-chart/generate-chart.component';


@NgModule({
  declarations: [
    AppComponent,
    TopBarComponent,
    SignInComponent,
    SignUpComponent,
    PageNotFoundComponent,
    PageForbiddenComponent,
    WelcomeComponent,
    AdminComponent,
    UploadComponent,
    CompanyComponent,
    StockExchangeComponent,
    IpoComponent,
    UserComponent,
    ProfileComponent,
    IpoDetailsComponent,
    CompanyCompareComponent,
    SectorCompareComponent,
    InternalServerErrorComponent,
    SectorComponent,
    GenerateChartComponent
  ],
  imports: [BrowserModule, AppRoutingModule, ReactiveFormsModule, FormsModule, HttpClientModule, FileUploadModule, HighchartsChartModule],
  providers: [DatePipe, httpInterceptorProviders,
    // {provide: LocationStrategy, useClass: HashLocationStrategy}
  ],
  bootstrap: [AppComponent],
  schemas: []
})
export class AppModule { }
