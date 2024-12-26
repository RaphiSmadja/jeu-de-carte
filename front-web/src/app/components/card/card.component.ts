import { Component, Input } from '@angular/core';
import { Card } from '../../models/Card';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [MatCardModule, CommonModule],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  @Input() card: Card | null = null; // Autorise null ou undefined
  getSuitSymbol(): string {
    if (!this.card || !this.card.suit) {
      return ''; // Retourne une chaîne vide si la carte n'est pas définie
    }
    switch (this.card.suit.toLowerCase()) {
      case 'heart':
        return '♥';
      case 'diamond':
        return '♦';
      case 'club':
        return '♣';
      case 'spade':
        return '♠';
      default:
        return '';
    }
  }

  getSuitClass(): string {
    if (!this.card || !this.card.suit) {
      return ''; // Retourne une chaîne vide si la carte n'est pas définie
    }
    switch (this.card.suit.toLowerCase()) {
      case 'heart':
      case 'diamond':
        return 'red';
      case 'club':
      case 'spade':
        return 'black';
      default:
        return '';
    }
  }

  getValueAsNumber(): number {
    if (!this.card || !this.card.suit) {
      return 0;
    }
    const mapping: { [key: string]: number } = {
      jack: 11,
      queen: 12,
      king: 13,
      ace: 14,
    };
    return mapping[this.card.value] || parseInt(this.card.value, 10) || 0;
  }
}
