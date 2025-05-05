import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopSelectionComponent } from './shop-selection.component';

describe('ShopSelectionComponent', () => {
  let component: ShopSelectionComponent;
  let fixture: ComponentFixture<ShopSelectionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShopSelectionComponent]
    });
    fixture = TestBed.createComponent(ShopSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
