import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomefrontComponent } from './homefront.component';

describe('HomefrontComponent', () => {
  let component: HomefrontComponent;
  let fixture: ComponentFixture<HomefrontComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomefrontComponent]
    });
    fixture = TestBed.createComponent(HomefrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
