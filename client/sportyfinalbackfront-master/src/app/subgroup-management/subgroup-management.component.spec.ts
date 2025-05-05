import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubgroupManagementComponent } from './subgroup-management.component';

describe('SubgroupManagementComponent', () => {
  let component: SubgroupManagementComponent;
  let fixture: ComponentFixture<SubgroupManagementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubgroupManagementComponent]
    });
    fixture = TestBed.createComponent(SubgroupManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
