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

@NgModule({
  declarations: [
    AppComponent,
    HomeComponentComponent,
    EmprendimientoComponentComponent,
    EditPostsComponentComponent,
    ViewPostsComponentComponent,
    EditEmprendimientoComponentComponent,
    NavigationHeaderComponent,
    ContactFooterComponent
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
