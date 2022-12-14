import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { CategoriesComponent } from './components/categories/categories.component'
import { DonateComponent } from './components/donate/donate.component'
import { EditPagosComponent } from './components/edit-pagos/edit-pagos.component'
import { EditPostsComponent } from './components/edit-posts/edit-posts.component'
import { EmprendimientoComponent } from './components/emprendimiento/emprendimiento.component'
import { ErrorNotFoundComponent } from './components/error-not-found/error-not-found.component'
import { HomeComponent } from './components/home/home.component'
import { LoginComponent } from './components/login/login.component'
import { SignupComponent } from './components/signup/signup.component'
import { TopEmprendimientosComponent } from './components/top-emprendimientos/top-emprendimientos.component'
import { ViewEmprendimientoComponent } from './components/view-emprendimiento/view-emprendimiento.component'
import { ViewPagosComponent } from './components/view-pagos/view-pagos.component'
import { ViewPostsComponent } from './components/view-posts/view-posts.component'
import { AdminGuard } from './guards/admin.guard'
import { AuthGuard } from './guards/auth.guard'
import { LoggedInGuard } from './guards/logged-in.guard'
import { NegateAdminGuard } from './guards/negate-admin.guard'

const routes: Routes = [
  { path: 'Home', component: HomeComponent },
  {
    path: 'emprendimiento',
    component: EmprendimientoComponent,
    canActivate: [AuthGuard, NegateAdminGuard],
  },
  { path: 'top', component: TopEmprendimientosComponent },
  {
    path: 'verEmprendimiento/:domain',
    component: ViewEmprendimientoComponent,
  },
  { path: 'login', component: LoginComponent, canActivate: [LoggedInGuard] },
  {
    path: 'registro',
    component: SignupComponent,
    canActivate: [LoggedInGuard],
  },
  {
    path: 'editarPosts',
    component: EditPostsComponent,
    canActivate: [AuthGuard, NegateAdminGuard],
  },
  { path: 'posts/:domain', component: ViewPostsComponent },
  {
    path: 'editarPagos',
    component: EditPagosComponent,
    canActivate: [AuthGuard, NegateAdminGuard],
  },
  {
    path: 'verPagos',
    component: ViewPagosComponent,
    canActivate: [AuthGuard, NegateAdminGuard],
  },
  { path: 'donar/:domain', component: DonateComponent },
  {
    path: 'etiquetas',
    component: CategoriesComponent,
    canActivate: [AuthGuard, AdminGuard],
  },
  { path: '404', component: ErrorNotFoundComponent },
  { path: '**', redirectTo: 'Home' },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
