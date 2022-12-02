import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEmprendimientoComponentComponent } from './edit-emprendimiento-component.component';

describe('EditEmprendimientoComponentComponent', () => {
  let component: EditEmprendimientoComponentComponent;
  let fixture: ComponentFixture<EditEmprendimientoComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditEmprendimientoComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditEmprendimientoComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
