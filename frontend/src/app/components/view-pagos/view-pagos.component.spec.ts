import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPagosComponent } from './view-pagos.component';

describe('ViewPagosComponent', () => {
  let component: ViewPagosComponent;
  let fixture: ComponentFixture<ViewPagosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewPagosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewPagosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
