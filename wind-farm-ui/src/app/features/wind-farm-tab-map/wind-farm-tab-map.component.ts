import {AfterViewInit, Component, ElementRef, Input, ViewChild} from '@angular/core';
import * as L from "leaflet";
import {WindFarm} from "../../core/models/wind-farm.model";

@Component({
    selector: 'app-wind-farm-tab-map',
    imports: [],
    templateUrl: './wind-farm-tab-map.component.html',
    styleUrls: ['./wind-farm-tab-map.component.css']
})
export class WindFarmTabMapComponent implements AfterViewInit {
    @Input()
    windFarms: WindFarm[] = [];

    @ViewChild('map')
    mapElementRef: ElementRef = null!;

    map: L.Map = null!;

    ngAfterViewInit() {
        this.initMap();
    }

    private initMap() {
        this.map = L.map(this.mapElementRef.nativeElement).setView([20.00, 0.00], 2); // Ustawienie domyślnego widoku

        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            noWrap: true,
            maxZoom: 19,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(this.map);

        this.windFarms.forEach(windFarm => {
            const location = windFarm.location.split(' ');
            const marker = L.circleMarker([Number.parseFloat(location[0]), Number.parseFloat(location[1])], {
                radius: 8,
                color: 'blue',
                opacity: 1,
                weight: 4
            }).addTo(this.map);
            marker.bindPopup(windFarm.description + ' - capacity (' + windFarm.capacityMW + ' MW )');
        });
    }
}