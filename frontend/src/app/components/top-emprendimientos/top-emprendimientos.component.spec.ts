import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopEmprendimientosComponent } from './top-emprendimientos.component';

describe('TopEmprendimientosComponent', () => {
  let component: TopEmprendimientosComponent;
  let fixture: ComponentFixture<TopEmprendimientosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopEmprendimientosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopEmprendimientosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
