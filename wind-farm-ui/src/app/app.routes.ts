import { Routes } from '@angular/router';
import {WindFarmComponent} from "./features/wind-farm/wind-farm.component";
import {HomeComponent} from "./features/home/home.component";

export const routes: Routes = [
    {title: 'Home', path: '', component: HomeComponent},
    {title: 'Wind Farms', path: 'wind-farms', component: WindFarmComponent},
];
