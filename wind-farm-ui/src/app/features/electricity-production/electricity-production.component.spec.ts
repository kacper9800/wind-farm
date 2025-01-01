import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElectricityProductionComponent } from './electricity-production.component';

describe('ElectricityProductionComponent', () => {
  let component: ElectricityProductionComponent;
  let fixture: ComponentFixture<ElectricityProductionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElectricityProductionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElectricityProductionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
