import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PushupCounterComponent } from './pushup-counter.component';

describe('PushupCounterComponent', () => {
  let component: PushupCounterComponent;
  let fixture: ComponentFixture<PushupCounterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PushupCounterComponent]
    });
    fixture = TestBed.createComponent(PushupCounterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
