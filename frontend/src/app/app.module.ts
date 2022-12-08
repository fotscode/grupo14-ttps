import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HomeComponentComponent } from './components/home-component/home-component.component';
import { EmprendimientoComponentComponent } from './components/emprendimiento-component/emprendimiento-component.component';
import { EditPostsComponentComponent } from './components/edit-posts-component/edit-posts-component.component';
import { ViewPostsComponentComponent } from './components/view-posts-component/view-posts-component.component';
import { EditEmprendimientoComponentComponent } from './components/edit-emprendimiento-component/edit-emprendimiento-component.component';
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
import { SignupComponent } from './components/signup/signup.component'

@NgModule({
  declarations: [
    AppComponent,
    HomeComponentComponent,
    EmprendimientoComponentComponent,
    EditPostsComponentComponent,
    ViewPostsComponentComponent,
    EditEmprendimientoComponentComponent,
    NavigationHeaderComponent,
    ContactFooterComponent,
    LoginComponent,
    SignupComponent
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
  ],
  providers: [
    AuthGuard,
    {
      provide:HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
