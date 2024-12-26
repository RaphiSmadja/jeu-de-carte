import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';

import { CardComponent } from "./components/card/card.component";
import { Card } from './models/Card';
import { CardService } from './services/card.service';
import { HttpClientModule } from '@angular/common/http';
import { BattleComponent } from './components/battle/battle.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, MatCardModule, CardComponent, MatToolbarModule, MatButtonModule, HttpClientModule, BattleComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  cards: Card[] = [];
  loading: boolean = false;
  firstCard: Card | null = null;
  currentView: 'home' | 'battle' = 'home';

  navigateTo(view: 'home' | 'battle'): void {
    this.currentView = view;
  }
  
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

  drawFirstCard(): void {
    this.loading = true;
    this.cardService.getFirstCard().subscribe({
      next: (card) => {
        this.firstCard = card;
        this.loading = false;
        console.log('First card:', card);
      },
      error: (err) => {
        console.error('Error drawing the first card:', err);
        this.loading = false;
      },
    });
  }

  drawTwoCards(): void {
    this.loading = true;
    this.cardService.drawTwoCards().subscribe({
      next: (twoCards) => {
        this.cards = twoCards;
        this.loading = false;
        console.log('Two cards drawn:', twoCards);
      },
      error: (err) => {
        console.error('Error drawing two cards:', err);
        this.loading = false;
      },
    });
  }

  onBattleComplete(): void {
    console.log('Battle finished!');
  }
}