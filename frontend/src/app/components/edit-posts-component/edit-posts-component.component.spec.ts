import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPostsComponentComponent } from './edit-posts-component.component';

describe('EditPostsComponentComponent', () => {
  let component: EditPostsComponentComponent;
  let fixture: ComponentFixture<EditPostsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditPostsComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditPostsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
