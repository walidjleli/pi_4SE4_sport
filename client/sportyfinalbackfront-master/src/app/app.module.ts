import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { ReactiveFormsModule } from '@angular/forms';

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
import { TeamManagementComponent } from './BackOffice/team-management/team-management.component';
import { TeamService } from './services/team.service';
import { StatsService } from './services/stats.service'; // ✅ Ajout du service des stats
import { ResetPasswordComponent } from './auth/reset-password/reset-password.component';
import { ForgotPasswordComponent } from './auth/forgot-password/forgot-password.component';
import { UserService } from './services/user.service';
import { SubgroupManagementComponent } from './subgroup-management/subgroup-management.component';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
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
    TeamManagementComponent,
    ResetPasswordComponent,
    ForgotPasswordComponent,
    SubgroupManagementComponent,
    TrainingComponent,
    PushupCounterComponent,
    AddProductComponent,
    ProductListComponent,
    ProductUpdateComponent,
    ProductDetailsComponent,
    AddShopComponent,
    ShopListComponent,
    ShopUpdateComponent,
    ShopSelectionComponent,
    ShopDetailsComponent,
    WishlistComponent,
    ChatComponent,
    ShoePricePredictionComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule ,
    ToastrModule.forRoot() ,
    BrowserAnimationsModule,
    ReactiveFormsModule,
  ],
  providers: [
    TeamService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    StatsService, // ✅ Correction ici (ajout de la virgule)
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
