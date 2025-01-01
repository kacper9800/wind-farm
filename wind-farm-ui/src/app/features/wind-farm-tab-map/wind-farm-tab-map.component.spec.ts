import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WindFarmTabMapComponent } from './wind-farm-tab-map.component';

describe('WindFarmTabMapComponent', () => {
  let component: WindFarmTabMapComponent;
  let fixture: ComponentFixture<WindFarmTabMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WindFarmTabMapComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WindFarmTabMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
