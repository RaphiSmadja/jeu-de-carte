import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Card } from '../models/Card';

@Injectable({
  providedIn: 'root'
})
export class CardService {
  private readonly apiUrl = 'http://localhost:8080/cards';
  
  constructor(private http: HttpClient) {}

  getAllCards(): Observable<Card[]> {
    return this.http.get<Card[]>(this.apiUrl);
  }

  shuffleDeck(): Observable<Card[]> {
    return this.http.post<Card[]>(`${this.apiUrl}/shuffle`, {});
  }

  getFirstCard(): Observable<Card | null> {
    return this.http.get<Card | null>(`${this.apiUrl}/first`);
  }

  drawTwoCards(): Observable<Card[]> {
    return this.http.get<Card[]>(`${this.apiUrl}/draw/2`);
  }

  filterCards(suit?: string, value?: string): Observable<Card[]> {
    const params: { [key: string]: string } = {};
    if (suit) params['suit'] = suit;
    if (value) params['value'] = value;
    return this.http.get<Card[]>(`${this.apiUrl}/filter`, { params });
  }
}