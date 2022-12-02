import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmprendimientoComponentComponent } from './emprendimiento-component.component';

describe('EmprendimientoComponentComponent', () => {
  let component: EmprendimientoComponentComponent;
  let fixture: ComponentFixture<EmprendimientoComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmprendimientoComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmprendimientoComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
