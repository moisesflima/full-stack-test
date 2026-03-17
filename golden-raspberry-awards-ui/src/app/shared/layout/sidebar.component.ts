import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  template: `
    <aside class="sidebar" style="height: 100%; border-right: none;">
      <nav>
        <a routerLink="/dashboard" routerLinkActive="active" class="sidebar-link">Dashboard</a>
        <a routerLink="/movies" routerLinkActive="active" class="sidebar-link">List</a>
      </nav>
    </aside>
  `,
  styles: [`
    .sidebar-link {
      display: block;
      padding: 5px 20px;
      text-decoration: none;
      color: #007bff;
      font-size: 14px;
    }
    .active {
      color: #333 !important;
      font-weight: bold;
      background: none !important;
    }
  `]
})
export class SidebarComponent {}
