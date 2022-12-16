import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEmprendimientoComponent } from './view-emprendimiento.component';

describe('ViewEmprendimientoComponent', () => {
  let component: ViewEmprendimientoComponent;
  let fixture: ComponentFixture<ViewEmprendimientoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewEmprendimientoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewEmprendimientoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
