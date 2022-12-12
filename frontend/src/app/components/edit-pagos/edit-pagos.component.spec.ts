import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPagosComponent } from './edit-pagos.component';

describe('EditPagosComponent', () => {
  let component: EditPagosComponent;
  let fixture: ComponentFixture<EditPagosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditPagosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditPagosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
