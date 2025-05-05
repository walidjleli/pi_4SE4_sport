import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShoePricePredictionComponent } from './shoe-price-prediction.component';

describe('ShoePricePredictionComponent', () => {
  let component: ShoePricePredictionComponent;
  let fixture: ComponentFixture<ShoePricePredictionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShoePricePredictionComponent]
    });
    fixture = TestBed.createComponent(ShoePricePredictionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
