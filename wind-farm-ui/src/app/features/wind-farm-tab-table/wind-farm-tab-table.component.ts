import {Component, Input} from '@angular/core';
import {Button} from "primeng/button";
import {TableModule} from "primeng/table";
import {WindFarmService} from "../../core/services/wind-farm.service";
import {ElectricityProductionService} from "../../core/services/electricity-production.service";
import {WindFarm} from "../../core/models/wind-farm.model";

@Component({
  selector: 'app-wind-farm-tab-table',
  imports: [
    Button,
    TableModule
  ],
  templateUrl: './wind-farm-tab-table.component.html',
  styleUrl: './wind-farm-tab-table.component.css'
})
export class WindFarmTabTableComponent {
  @Input()
  windFarms: WindFarm[] = [];

  constructor(
      private windFarmService: WindFarmService,
      private electricityProductionService: ElectricityProductionService,
  ) {}

  ngOnInit(): void {
  }

  loadInitialData(): void {
    this.windFarmService.getWindFarms()
        .subscribe((data: WindFarm[]) => {
          this.windFarms = data.map((wf: WindFarm) => ({
            ...wf,
          }));
        });
  }

  openElectricityProductionDialog(id: number) {
    this.electricityProductionService.getElectricityProduction(id, new Date(), new Date()).subscribe((data: any) => {
      console.log(data);
    });
  }
}
