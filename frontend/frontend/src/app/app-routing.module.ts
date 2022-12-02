import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmprendimientoComponentComponent } from './components/emprendimiento-component/emprendimiento-component.component';
import { HomeComponentComponent } from './components/home-component/home-component.component';

const routes: Routes = [
  {path:'Home', component: HomeComponentComponent},
  {path:'miEmprendimiento', component:EmprendimientoComponentComponent},
  {path:'**', redirectTo:'Home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
