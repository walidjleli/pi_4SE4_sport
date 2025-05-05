import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopUpdateComponent } from './shop-update.component';

describe('ShopUpdateComponent', () => {
  let component: ShopUpdateComponent;
  let fixture: ComponentFixture<ShopUpdateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShopUpdateComponent]
    });
    fixture = TestBed.createComponent(ShopUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
