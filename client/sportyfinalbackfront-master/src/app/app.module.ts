import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AllTemplateBackComponent } from './BackOffice/all-template-back/all-template-back.component';
import { FooterBackComponent } from './BackOffice/footer-back/footer-back.component';
import { NavbarBackComponent } from './BackOffice/navbar-back/navbar-back.component';
import { SidebarBackComponent } from './BackOffice/sidebar-back/sidebar-back.component';
import { HomeComponent } from './BackOffice/home/home.component';
import { WidgetsComponent } from './BackOffice/widgets/widgets.component';
import { FormsComponent } from './BackOffice/forms/forms.component';
import { HeaderComponent } from './FrontOffice/header/header.component';
import { FooterFrontComponent } from './FrontOffice/footer-front/footer-front.component';
import { AllTemplateFrontComponent } from './FrontOffice/all-template-front/all-template-front.component';
import { HomefrontComponent } from './FrontOffice/homefront/homefront.component';
import { MatchesComponent } from './FrontOffice/matches/matches.component';
import { PlayersComponent } from './FrontOffice/players/players.component';
import { BlogsComponent } from './FrontOffice/blogs/blogs.component';
import { ContactComponent } from './FrontOffice/contact/contact.component';
import { HttpClientModule } from '@angular/common/http';
import { AddStatsComponent } from './add-stats/add-stats.component';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth/auth.interceptor';





@NgModule({
  declarations: [
    AppComponent,
    AllTemplateBackComponent,
    FooterBackComponent,
    NavbarBackComponent,
    SidebarBackComponent,
    HomeComponent,
    WidgetsComponent,
    FormsComponent,
    HeaderComponent,
    FooterFrontComponent,
    AllTemplateFrontComponent,
    HomefrontComponent,
    MatchesComponent,
    PlayersComponent,
    BlogsComponent,
    ContactComponent,
    AddStatsComponent,
    LoginComponent,
    RegisterComponent,
    
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule  
  ],
  providers: [ { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
