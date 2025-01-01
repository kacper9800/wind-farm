import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WindFarmTabTableComponent } from './wind-farm-tab-table.component';

describe('WindFarmTabTableComponent', () => {
  let component: WindFarmTabTableComponent;
  let fixture: ComponentFixture<WindFarmTabTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WindFarmTabTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WindFarmTabTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
