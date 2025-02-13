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
import { TablesComponent } from './BackOffice/tables/tables.component';
import { FormsComponent } from './BackOffice/forms/forms.component';
import { HeaderComponent } from './FrontOffice/header/header.component';
import { FooterFrontComponent } from './FrontOffice/footer-front/footer-front.component';
import { AllTemplateFrontComponent } from './FrontOffice/all-template-front/all-template-front.component';
import { HomefrontComponent } from './FrontOffice/homefront/homefront.component';
import { MatchesComponent } from './FrontOffice/matches/matches.component';
import { PlayersComponent } from './FrontOffice/players/players.component';
import { BlogsComponent } from './FrontOffice/blogs/blogs.component';
import { ContactComponent } from './FrontOffice/contact/contact.component';

@NgModule({
  declarations: [
    AppComponent,
    AllTemplateBackComponent,
    FooterBackComponent,
    NavbarBackComponent,
    SidebarBackComponent,
    HomeComponent,
    WidgetsComponent,
    TablesComponent,
    FormsComponent,
    HeaderComponent,
    FooterFrontComponent,
    AllTemplateFrontComponent,
    HomefrontComponent,
    MatchesComponent,
    PlayersComponent,
    BlogsComponent,
    ContactComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
