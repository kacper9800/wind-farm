import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WindFarmComponent } from './wind-farm.component';

describe('WindFarmComponent', () => {
  let component: WindFarmComponent;
  let fixture: ComponentFixture<WindFarmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WindFarmComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WindFarmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
