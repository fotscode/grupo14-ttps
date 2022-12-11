import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEmprendimientoComponent } from './edit-emprendimiento.component';

describe('EditEmprendimientoComponent', () => {
  let component: EditEmprendimientoComponent;
  let fixture: ComponentFixture<EditEmprendimientoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditEmprendimientoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditEmprendimientoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
