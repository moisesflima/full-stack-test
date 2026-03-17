import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { 
  MovieResponse, 
  YearsWithMultipleWinnersResponse, 
  StudiosWithWinCountResponse, 
  MaxMinWinIntervalResponse, 
  Movie
} from '../models/movie.model';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'https://challenge.outsera.tech/api/movies';

  getMovies(page: number, size: number, winner?: boolean, year?: number): Observable<MovieResponse> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (winner !== undefined) {
      params = params.set('winner', winner.toString());
    }
    if (year) {
      params = params.set('year', year.toString());
    }

    return this.http.get<MovieResponse>(this.baseUrl, { params });
  }

  getYearsWithMultipleWinners(): Observable<YearsWithMultipleWinnersResponse> {
    return this.http.get<YearsWithMultipleWinnersResponse>(`${this.baseUrl}/yearsWithMultipleWinners`);
  }

  getStudiosWithWinCount(): Observable<StudiosWithWinCountResponse> {
    return this.http.get<StudiosWithWinCountResponse>(`${this.baseUrl}/studiosWithWinCount`);
  }

  getMaxMinWinIntervalForProducers(): Observable<MaxMinWinIntervalResponse> {
    return this.http.get<MaxMinWinIntervalResponse>(`${this.baseUrl}/maxMinWinIntervalForProducers`);
  }

  getWinnersByYear(year: number): Observable<Movie[]> {
    const params = new HttpParams().set('year', year.toString());
    return this.http.get<Movie[]>(`${this.baseUrl}/winnersByYear`, { params });
  }
}
