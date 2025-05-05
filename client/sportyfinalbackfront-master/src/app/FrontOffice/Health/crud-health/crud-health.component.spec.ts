import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrudHealthComponent } from './crud-health.component';

describe('CrudHealthComponent', () => {
  let component: CrudHealthComponent;
  let fixture: ComponentFixture<CrudHealthComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CrudHealthComponent]
    });
    fixture = TestBed.createComponent(CrudHealthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
