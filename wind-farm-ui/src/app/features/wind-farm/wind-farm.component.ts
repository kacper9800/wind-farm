import {Component, OnInit} from '@angular/core';
import {WindFarmService} from "../../core/services/wind-farm.service";
import {WindFarm} from "../../core/models/wind-farm.model";
import {TableModule} from "primeng/table";
import {CommonModule} from "@angular/common";
import {WindFarmTabMapComponent} from "../wind-farm-tab-map/wind-farm-tab-map.component";
import {WindFarmTabTableComponent} from "../wind-farm-tab-table/wind-farm-tab-table.component";
import {TabPanel, TabView} from "primeng/tabview";
import {Drawer} from "primeng/drawer";

@Component({
    selector: 'app-wind-farm',
    imports: [
        TableModule,
        CommonModule,
        TabPanel,
        WindFarmTabMapComponent,
        WindFarmTabTableComponent,
        TabView,
        TabPanel,
    ],
    templateUrl: './wind-farm.component.html',
    styleUrl: './wind-farm.component.css'
})
export class WindFarmComponent implements OnInit {
    windFarms: WindFarm[] = [];
    activeTab: number = 0;
    mapTabLoaded = false;
    visible= true;

    ngOnInit(): void {
        this.loadInitialData();
    }

    constructor(private windFarmService: WindFarmService) {
    }

    loadInitialData(): void {
        this.windFarmService.getWindFarms()
            .subscribe((data: WindFarm[]) => {
                this.windFarms = data.map((wf: WindFarm) => ({
                    ...wf,
                }));
            });
    }

    onTabChange(event: any) {
        this.activeTab = event.index;

        if (this.activeTab === 1 && !this.mapTabLoaded) {
            this.mapTabLoaded = true;
        }
    }
}