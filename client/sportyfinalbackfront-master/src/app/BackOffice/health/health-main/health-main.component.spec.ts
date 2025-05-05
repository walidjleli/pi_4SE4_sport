import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthMainComponent } from './health-main.component';

describe('HealthMainComponent', () => {
  let component: HealthMainComponent;
  let fixture: ComponentFixture<HealthMainComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HealthMainComponent]
    });
    fixture = TestBed.createComponent(HealthMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
