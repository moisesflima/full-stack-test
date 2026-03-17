import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardComponent } from './dashboard.component';
import { MovieService } from '../../core/services/movie.service';
import { of } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { describe, it, expect, beforeEach, vi } from 'vitest';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let movieServiceStub: Partial<MovieService>;

  beforeEach(async () => {
    movieServiceStub = {
      getYearsWithMultipleWinners: vi.fn(() => of({ years: [] })),
      getStudiosWithWinCount: vi.fn(() => of({ studios: [] })),
      getMaxMinWinIntervalForProducers: vi.fn(() => of({ min: [], max: [] })),
      getWinnersByYear: vi.fn(() => of([]))
    };

    await TestBed.configureTestingModule({
      imports: [DashboardComponent, FormsModule],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: MovieService, useValue: movieServiceStub }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load initial data on init', () => {
    expect(movieServiceStub.getYearsWithMultipleWinners).toHaveBeenCalled();
    expect(movieServiceStub.getStudiosWithWinCount).toHaveBeenCalled();
    expect(movieServiceStub.getMaxMinWinIntervalForProducers).toHaveBeenCalled();
  });

  it('should search for winners by year', () => {
    component.searchYear.set(2018);
    component.onYearSearch();
    expect(movieServiceStub.getWinnersByYear).toHaveBeenCalledWith(2018);
  });
});
