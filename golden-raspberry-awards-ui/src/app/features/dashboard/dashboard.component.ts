import { Component, inject, signal, computed, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovieService } from '../../core/services/movie.service';
import { 
  YearWithMultipleWinners, 
  StudioWithWinCount, 
  ProducerWinInterval, 
  Movie
} from '../../core/models/movie.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styles: [`
    .dashboard-grid { 
      display: grid; 
      grid-template-columns: 1fr 1fr; 
      gap: 20px; 
    }
    @media (max-width: 1024px) {
      .dashboard-grid {
        grid-template-columns: 1fr;
      }
    }
  `]
})
export class DashboardComponent {
  private readonly movieService = inject(MovieService);

  // Panel 1: Years with multiple winners
  yearsWithMultipleWinners = signal<YearWithMultipleWinners[]>([]);
  
  // Panel 2: Top 3 studios
  topStudios = signal<StudioWithWinCount[]>([]);

  // Panel 3: Intervals
  maxIntervals = signal<ProducerWinInterval[]>([]);
  minIntervals = signal<ProducerWinInterval[]>([]);

  // Panel 4: Filter winners by year
  searchYear = signal<number | null>(null);
  yearWinners = signal<Movie[]>([]);

  constructor() {
    this.loadInitialData();
  }

  private loadInitialData() {
    this.movieService.getYearsWithMultipleWinners().subscribe(res => {
      this.yearsWithMultipleWinners.set(res.years);
    });

    this.movieService.getStudiosWithWinCount().subscribe(res => {
      // API returns all, requirement says top 3
      this.topStudios.set(res.studios.slice(0, 3));
    });

    this.movieService.getMaxMinWinIntervalForProducers().subscribe(res => {
      this.maxIntervals.set(res.max);
      this.minIntervals.set(res.min);
    });
  }

  onYearSearch() {
    const year = this.searchYear();
    if (year) {
      this.movieService.getWinnersByYear(year).subscribe(res => {
        this.yearWinners.set(res);
      });
    } else {
      this.yearWinners.set([]);
    }
  }
}
