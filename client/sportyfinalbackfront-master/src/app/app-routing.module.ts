import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllTemplateBackComponent } from './BackOffice/all-template-back/all-template-back.component';
import { HomeComponent } from './BackOffice/home/home.component';
import { WidgetsComponent } from './BackOffice/widgets/widgets.component';
import { FormsComponent } from './BackOffice/forms/forms.component';
import { AllTemplateFrontComponent } from './FrontOffice/all-template-front/all-template-front.component';
import { HomefrontComponent } from './FrontOffice/homefront/homefront.component';
import { MatchesComponent } from './FrontOffice/matches/matches.component';
import { PlayersComponent } from './FrontOffice/players/players.component';
import { BlogsComponent } from './FrontOffice/blogs/blogs.component';
import { ContactComponent } from './FrontOffice/contact/contact.component';
import { AddStatsComponent } from './add-stats/add-stats.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component'; 
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component'; 
import { authGuard } from './auth/auth.guard';
import { TeamManagementComponent } from './BackOffice/team-management/team-management.component';

const routes: Routes = [
  { path: '', redirectTo: '/front-office', pathMatch: 'full' }, 

  {
    path: 'front-office',
    component: AllTemplateFrontComponent,
    children: [
      { path: '', component: HomefrontComponent },
      { path: 'matches', component: MatchesComponent },
      { path: 'players', component: PlayersComponent },
      { path: 'blogs', component: BlogsComponent },
      { path: 'contact', component: ContactComponent }
    ]
  },
  {
    path: 'back-office',
    component: AllTemplateBackComponent,
    canActivate: [authGuard], // 🔥 Protection
    children: [
      { path: '', component: HomeComponent },
      { path: 'widget', component: WidgetsComponent },
      { path: 'forms', component: FormsComponent },
      { path: 'add-stats', component: AddStatsComponent },
      { path: 'teams', component: TeamManagementComponent }
    ]
  },

  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: '/front-office' } // 🔥 Redirection vers Home si la route n'existe pas
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
