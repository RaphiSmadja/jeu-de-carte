import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';

import { CardComponent } from "./card/card.component";
import { Card } from './models/Card';
import { CardService } from './services/card.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MatCardModule, CardComponent, MatToolbarModule, MatButtonModule, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  cards: Card[] = [];
  loading: boolean = false;

  constructor(private cardService: CardService) {}

  ngOnInit(): void {
    this.fetchAllCards();
  }

  fetchAllCards(): void {
    this.loading = true;
    this.cardService.getAllCards().subscribe({
      next: (cards) => {
        this.cards = cards;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching cards:', err);
        this.loading = false;
      },
    });
  }

  shuffleDeck(): void {
    this.loading = true;
    this.cardService.shuffleDeck().subscribe({
      next: (shuffledCards) => {
        console.log(shuffledCards)
        this.cards = shuffledCards;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error shuffling deck:', err);
        this.loading = false;
      },
    });
  }
}