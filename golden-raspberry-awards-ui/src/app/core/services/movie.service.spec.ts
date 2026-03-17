import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { MovieService } from './movie.service';
import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest';

describe('MovieService', () => {
  let service: MovieService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        MovieService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(MovieService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch movies with pagination', () => {
    const mockResponse = {
      content: [],
      pageable: { pageNumber: 0, pageSize: 15 },
      totalPages: 1
    };

    service.getMovies(0, 15).subscribe(res => {
      expect(res).toEqual(mockResponse as any);
    });

    const req = httpMock.expectOne(req => 
      req.url === 'https://challenge.outsera.tech/api/movies' && 
      req.params.get('page') === '0' &&
      req.params.get('size') === '15'
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should fetch winners by year', () => {
    const mockMovies = [{ id: 1, title: 'Test Movie', year: 2018 }];

    service.getWinnersByYear(2018).subscribe(movies => {
      expect(movies).toEqual(mockMovies as any);
    });

    const req = httpMock.expectOne(req => req.url === 'https://challenge.outsera.tech/api/movies/winnersByYear' && req.params.get('year') === '2018');
    req.flush(mockMovies);
  });
});
