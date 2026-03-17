import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MovieListComponent } from './movie-list.component';
import { MovieService } from '../../core/services/movie.service';
import { of } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { describe, it, expect, beforeEach, vi } from 'vitest';

describe('MovieListComponent', () => {
  let component: MovieListComponent;
  let fixture: ComponentFixture<MovieListComponent>;
  let movieServiceStub: Partial<MovieService>;

  const mockMovieResponse = {
    content: [
      { id: 1, year: 2020, title: 'Movie 1', winner: true, studios: [], producers: [] },
      { id: 2, year: 2021, title: 'Movie 2', winner: false, studios: [], producers: [] }
    ],
    totalPages: 5,
    totalElements: 75,
    size: 15,
    number: 0
  };

  beforeEach(async () => {
    movieServiceStub = {
      getMovies: vi.fn(() => of(mockMovieResponse as any))
    };

    await TestBed.configureTestingModule({
      imports: [MovieListComponent, FormsModule],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: MovieService, useValue: movieServiceStub }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MovieListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load movies on initialization', () => {
    expect(movieServiceStub.getMovies).toHaveBeenCalledWith(0, 15, undefined, undefined);
    expect(component.movies().length).toBe(2);
    expect(component.totalPages()).toBe(5);
  });

  it('should update current page and reload movies', () => {
    component.setPage(2);
    fixture.detectChanges();
    expect(component.currentPage()).toBe(2);
    expect(movieServiceStub.getMovies).toHaveBeenCalledWith(2, 15, undefined, undefined);
  });

  it('should trigger reload when filters change', () => {
    component.filterYear.set(2020);
    component.onFilterChange();
    fixture.detectChanges();
    
    expect(component.currentPage()).toBe(0);
    expect(movieServiceStub.getMovies).toHaveBeenCalled();
  });

  it('should calculate pages array correctly for pagination', () => {
    // This is tested via the private updatePagesArray through initial load
    expect(component.pagesArray()).toEqual([0, 1, 2, 3, 4]);
  });
});
