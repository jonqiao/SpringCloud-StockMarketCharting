import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

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
import { SectorComponent } from './component/admin/sector/sector.component';
import { IpoComponent } from './component/admin/ipo/ipo.component';
import { UserComponent } from './component/user/user/user.component';
import { ProfileComponent } from './component/user/profile/profile.component';
import { IpoDetailsComponent } from './component/user/ipo-details/ipo-details.component';
import { CompanyCompareComponent } from './component/user/company-compare/company-compare.component';
import { SectorCompareComponent } from './component/user/sector-compare/sector-compare.component';

import { AdminAuthGuard } from './guard/admin-auth.guard';
import { UserAuthGuard } from './guard/user-auth.guard';

// TODO: add AuthGuard
const appRoutes: Routes = [
  { path: 'signin', component: SignInComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'welcome', component: WelcomeComponent },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AdminAuthGuard],
    children: [
      {
        path: '', // Component-less route which makes it easier to guard child routes.
        canActivateChild: [AdminAuthGuard],
        children: [
          { path: 'upload', component: UploadComponent },
          { path: 'company', component: CompanyComponent },
          { path: 'stock-exchange', component: StockExchangeComponent },
          { path: 'sector', component: SectorComponent },
          { path: 'ipo', component: IpoComponent },
          { path: '', component: UploadComponent }
        ],
      }
    ]
  },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [UserAuthGuard],
    children: [
      {
        path: '',
        canActivateChild: [UserAuthGuard],
        children: [
          { path: 'ipo-details', component: IpoDetailsComponent },
          { path: 'company-compare', component: CompanyCompareComponent },
          { path: 'sector-compare', component: SectorCompareComponent },
          { path: 'profile', component: ProfileComponent },
          { path: '', component: IpoDetailsComponent }
        ]
      }
    ]
  },
  { path: '403', component: PageForbiddenComponent },
  { path: '500', component: InternalServerErrorComponent },
  { path: '', redirectTo: 'welcome', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes
      // { enableTracing: true } // <-- debugging purposes only
    )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
