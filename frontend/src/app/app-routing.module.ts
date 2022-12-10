import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { CategoriesComponent } from './components/categories/categories.component'
import { DonateComponent } from './components/donate/donate.component'
import { EditPostsComponentComponent } from './components/edit-posts-component/edit-posts-component.component'
import { EmprendimientoComponentComponent } from './components/emprendimiento-component/emprendimiento-component.component'
import { HomeComponentComponent } from './components/home-component/home-component.component'
import { LoginComponent } from './components/login/login.component'
import { SignupComponent } from './components/signup/signup.component'
import { ViewPostsComponentComponent } from './components/view-posts-component/view-posts-component.component'
import { AdminGuard } from './guards/admin.guard'
import { AuthGuard } from './guards/auth.guard'
import { LoggedInGuard } from './guards/logged-in.guard'

const routes: Routes = [
  { path: 'Home', component: HomeComponentComponent },
  {
    path: 'miEmprendimiento',
    component: EmprendimientoComponentComponent,
    canActivate: [AuthGuard],
  },
  { path: 'login', component: LoginComponent, canActivate: [LoggedInGuard] },
  { path: 'registro', component: SignupComponent, canActivate: [LoggedInGuard] },
  { path: 'editarPosts', component:EditPostsComponentComponent, canActivate:[AuthGuard]},
  { path: 'verPosts', component:ViewPostsComponentComponent},
  { path: 'donar', component:DonateComponent},
  { path: 'etiquetas', component:CategoriesComponent, canActivate:[AuthGuard,AdminGuard]},
  { path: '**', redirectTo: 'Home' },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
