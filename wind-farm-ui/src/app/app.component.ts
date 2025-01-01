import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {WindFarmComponent} from "./features/wind-farm/wind-farm.component";
import {CommonModule, NgOptimizedImage} from "@angular/common";
import {Button} from "primeng/button";
import {Sidebar} from "primeng/sidebar";

@Component({
  selector: 'app-root',
  imports: [CommonModule, Button, Sidebar, RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'wind-farm';

  isSidebarVisible = false;

  // Funkcja do przełączania widoczności menu
  toggleSidebar() {
    this.isSidebarVisible = !this.isSidebarVisible;
  }
}
