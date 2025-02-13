import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllTemplateBackComponent } from './BackOffice/all-template-back/all-template-back.component';
import { HomeComponent } from './BackOffice/home/home.component';
import { WidgetsComponent } from './BackOffice/widgets/widgets.component';
import { TablesComponent } from './BackOffice/tables/tables.component';
import { FormsComponent } from './BackOffice/forms/forms.component';
import { AllTemplateFrontComponent } from './FrontOffice/all-template-front/all-template-front.component';
import { HomefrontComponent } from './FrontOffice/homefront/homefront.component'; 
import { MatchesComponent } from './FrontOffice/matches/matches.component'; 
import { PlayersComponent } from './FrontOffice/players/players.component'; 
import { BlogsComponent } from './FrontOffice/blogs/blogs.component'; 
import { ContactComponent } from './FrontOffice/contact/contact.component'; 

const routes: Routes = [
  {
    path: 'back-office',
    component: AllTemplateBackComponent,
    children: [
      { path: '', component: HomeComponent },
      { path: 'widget', component: WidgetsComponent },
      { path: 'tables', component: TablesComponent },
      { path: 'forms', component: FormsComponent }
    ]
  },
  {
    path: 'front-office',
    component: AllTemplateFrontComponent,
    children: [
      { path: '', component: HomefrontComponent } ,{ path: 'matches', component: MatchesComponent } ,{ path: 'players', component: PlayersComponent },{ path: 'blogs', component: BlogsComponent },{ path: 'contact', component: ContactComponent }
    ]
  },
  { path: '', redirectTo: 'back-office', pathMatch: 'full' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
