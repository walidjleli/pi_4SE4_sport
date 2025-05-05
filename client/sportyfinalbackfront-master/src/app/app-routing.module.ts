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
import { SubgroupManagementComponent } from './subgroup-management/subgroup-management.component';  // Assurez-vous que le chemin est correct
import { TrainingComponent } from './training/training.component';
import { PushupCounterComponent } from './pushup-counter/pushup-counter.component';
import { AddProductComponent } from './add-product/add-product.component';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductUpdateComponent } from './product-update/product-update.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { AddShopComponent } from './add-shop/add-shop.component';
import { ShopListComponent } from './shop-list/shop-list.component';
import { ShopUpdateComponent } from './shop-update/shop-update.component';
import { ShopSelectionComponent } from './shop-selection/shop-selection.component';
import { ShopDetailsComponent } from './shop-details/shop-details.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { ChatComponent } from './chat/chat.component';
import { ShoePricePredictionComponent } from './shoe-price-prediction/shoe-price-prediction.component';



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
      { path: 'contact', component: ContactComponent },
      { path: 'push-up', component: PushupCounterComponent },
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
      { path: 'teams', component: TeamManagementComponent },
      { path: 'subgroups', component: SubgroupManagementComponent },
      { path: 'training', component: TrainingComponent },
      { path: 'products', component: ProductListComponent } ,
      { path: 'product-update/:id', component: ProductUpdateComponent },
      { path: 'product-details/:id', component: ProductDetailsComponent } ,
      { path: 'shop', component: AddShopComponent } ,
      { path: 'shoplist', component: ShopListComponent } ,
      { path: 'shopupdate/:id', component: ShopUpdateComponent } ,
      { path: 'shop-selection/:productId', component: ShopSelectionComponent } ,
      { path: 'shop-details/:id', component: ShopDetailsComponent } ,
      { path: 'addpr', component: AddProductComponent } ,
      { path: 'wishlist', component: WishlistComponent } ,
      { path: 'chat', component: ChatComponent } ,
      { path: 'predict-shoe-price', component: ShoePricePredictionComponent }


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
