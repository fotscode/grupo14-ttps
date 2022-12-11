import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { CategoriesComponent } from './components/categories/categories.component'
import { DonateComponent } from './components/donate/donate.component'
import { EditPostsComponent } from './components/edit-posts/edit-posts.component'
import { EmprendimientoComponent } from './components/emprendimiento/emprendimiento.component'
import { HomeComponent } from './components/home/home.component'
import { LoginComponent } from './components/login/login.component'
import { SignupComponent } from './components/signup/signup.component'
import { ViewPostsComponent } from './components/view-posts/view-posts.component'
import { AdminGuard } from './guards/admin.guard'
import { AuthGuard } from './guards/auth.guard'
import { LoggedInGuard } from './guards/logged-in.guard'

const routes: Routes = [
  { path: 'Home', component: HomeComponent },
  {
    path: 'emprendimiento/:domain',
    component: EmprendimientoComponent,
    canActivate: [AuthGuard],
  },
  { path: 'login', component: LoginComponent, canActivate: [LoggedInGuard] },
  { path: 'registro', component: SignupComponent, canActivate: [LoggedInGuard] },
  { path: 'editarPosts', component:EditPostsComponent, canActivate:[AuthGuard]},
  { path: 'posts/:domain', component:ViewPostsComponent},
  { path: 'donar', component:DonateComponent},
  { path: 'etiquetas', component:CategoriesComponent, canActivate:[AuthGuard,AdminGuard]},
  { path: '**', redirectTo: 'Home' },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
