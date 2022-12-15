import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HomeComponent } from './components/home/home.component';
import { EmprendimientoComponent } from './components/emprendimiento/emprendimiento.component';
import { EditPostsComponent } from './components/edit-posts/edit-posts.component';
import { ViewPostsComponent } from './components/view-posts/view-posts.component';
import { NavigationHeaderComponent } from './components/navigation-header/navigation-header.component';
import { ContactFooterComponent } from './components/contact-footer/contact-footer.component';
import { MaterialModule } from './material/material.module';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { AuthGuard } from './guards/auth.guard';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms'
import { FormsModule } from '@angular/forms';
import { SignupComponent } from './components/signup/signup.component';
import { DonateComponent } from './components/donate/donate.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { CommonModule } from '@angular/common';
import { PostsDialog } from './components/edit-posts/posts-dialog.component';
import { CategoriesComponent } from './components/categories/categories.component';
import { EditPagosComponent } from './components/edit-pagos/edit-pagos.component';
import { DonateDialogComponent } from './components/donate-dialog/donate-dialog.component';
import { DonatePlanDialogComponent } from './components/donate-plan-dialog/donate-plan-dialog.component';
import { ViewPagosComponent } from './components/view-pagos/view-pagos.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    EmprendimientoComponent,
    EditPostsComponent,
    ViewPostsComponent,
    NavigationHeaderComponent,
    ContactFooterComponent,
    LoginComponent,
    SignupComponent,
    DonateComponent,
    PostsDialog,
    CategoriesComponent,
    EditPagosComponent,
    DonateDialogComponent,
    DonatePlanDialogComponent,
    ViewPagosComponent
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    AppRoutingModule,
    MaterialModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
  ],
  providers: [
    AuthGuard,
    {
      provide:HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true,
    },
    { provide: MatDialogRef, useValue: {} },
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MAT_DATE_LOCALE, useValue: 'es-AR' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
