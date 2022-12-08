import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { EmprendimientoComponentComponent } from './components/emprendimiento-component/emprendimiento-component.component'
import { HomeComponentComponent } from './components/home-component/home-component.component'
import { LoginComponent } from './components/login/login.component'
import { SignupComponent } from './components/signup/signup.component'
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
  { path: '**', redirectTo: 'Home' },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
