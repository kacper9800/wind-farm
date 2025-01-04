import {Component} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {CommonModule} from "@angular/common";
import {Sidebar} from "primeng/sidebar";
import {PrimeNG} from "primeng/config";

@Component({
  selector: 'app-root',
  imports: [CommonModule, Sidebar, RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'wind-farm';

  isSidebarVisible = false;

  constructor(private primeng: PrimeNG) {}

  // Funkcja do przełączania widoczności menu
  toggleSidebar() {
    this.isSidebarVisible = !this.isSidebarVisible;
  }

  ngOnInit() {
    this.primeng.zIndex = {
      modal: 1100,    // dialog, sidebar
      overlay: 1000,  // dropdown, overlaypanel
      menu: 1000,     // overlay menus
      tooltip: 1100   // tooltip
    };
  }
}
