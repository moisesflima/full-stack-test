import { Component, inject, signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MovieService } from '../../core/services/movie.service';
import { Movie } from '../../core/models/movie.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-movie-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './movie-list.component.html',
  styles: [`
    .filter-row input, .filter-row select {
      width: 100%;
      padding: 4px;
      border: 1px solid #ccc;
      border-radius: 3px;
      box-sizing: border-box;
    }
    .pagination { 
      display: flex; 
      justify-content: center; 
      margin-top: 15px; 
      background: #f8f9fa; 
      padding: 10px; 
      border: 1px solid #ddd; 
      border-top: none; 
      gap: 0;
    }
    .page-btn { 
      padding: 5px 12px; 
      border: 1px solid #ddd; 
      background: white; 
      color: #007bff; 
      cursor: pointer;
      margin: 0;
    }
    .page-btn.active { background-color: #007bff; color: white; }
    .page-btn:disabled { color: #ccc; cursor: not-allowed; }
  `]
})
export class MovieListComponent {
  private readonly movieService = inject(MovieService);

  movies = signal<Movie[]>([]);
  
  // Filters
  filterYear = signal<number | undefined>(undefined);
  filterWinner = signal<boolean | undefined>(undefined);
  
  // Pagination
  currentPage = signal(0);
  pageSize = signal(15);
  totalPages = signal(0);
  pagesArray = signal<number[]>([]);

  constructor() {
    // Reload when page or filters change
    effect(() => {
      this.loadMovies();
    });
  }

  loadMovies() {
    this.movieService.getMovies(
      this.currentPage(), 
      this.pageSize(), 
      this.filterWinner(), 
      this.filterYear()
    ).subscribe(res => {
      this.movies.set(res.content);
      this.totalPages.set(res.totalPages);
      this.updatePagesArray(res.totalPages, res.number);
    });
  }

  onFilterChange() {
    this.currentPage.set(0); // Reset to first page
    // effect will trigger loadMovies
  }

  setPage(page: number) {
    if (page >= 0 && page < this.totalPages()) {
      this.currentPage.set(page);
    }
  }

  private updatePagesArray(total: number, current: number) {
    // Show a window of pages
    const maxVisible = 5;
    let start = Math.max(0, current - Math.floor(maxVisible / 2));
    let end = Math.min(total, start + maxVisible);
    
    if (end - start < maxVisible) {
      start = Math.max(0, end - maxVisible);
    }
    
    const pages = [];
    for (let i = start; i < end; i++) {
      pages.push(i);
    }
    this.pagesArray.set(pages);
  }
}
