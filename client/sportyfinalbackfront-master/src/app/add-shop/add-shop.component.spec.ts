import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddShopComponent } from './add-shop.component';

describe('AddShopComponent', () => {
  let component: AddShopComponent;
  let fixture: ComponentFixture<AddShopComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddShopComponent]
    });
    fixture = TestBed.createComponent(AddShopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
