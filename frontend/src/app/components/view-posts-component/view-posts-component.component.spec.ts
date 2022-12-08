import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPostsComponentComponent } from './view-posts-component.component';

describe('ViewPostsComponentComponent', () => {
  let component: ViewPostsComponentComponent;
  let fixture: ComponentFixture<ViewPostsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewPostsComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewPostsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
