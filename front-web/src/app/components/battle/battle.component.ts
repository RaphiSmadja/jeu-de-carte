import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Card } from '../../models/Card';
import { CardService } from '../../services/card.service';
import { CardComponent } from '../card/card.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-battle',
  standalone: true,
  imports: [CardComponent, CommonModule, MatButtonModule],
  templateUrl: './battle.component.html',
  styleUrl: './battle.component.scss'
})
export class BattleComponent implements OnInit {
  @Output() battleComplete = new EventEmitter<void>();

  playerCard: Card | null = null;
  computerCard: Card | null = null;
  battleResult: string | null = null;
  battleInProgress: boolean = false;
  playerScore: number = 0;
  computerScore: number = 0;

  constructor(private cardService: CardService) {}

  ngOnInit(): void {}

  startBattle(): void {
    if (this.battleInProgress) return;
    this.battleInProgress = true;

    this.cardService.drawTwoCards().subscribe({
      next: ([playerCard, computerCard]) => {
        this.playerCard = playerCard;
        this.computerCard = computerCard;

        const playerValue = this.getCardValue(playerCard.value);
        const computerValue = this.getCardValue(computerCard.value);

        // Résultat de la bataille
        if (playerValue > computerValue) {
          this.battleResult = 'You win!';
          this.playerScore++; // Incrémente le score du joueur
        } else if (playerValue < computerValue) {
          this.battleResult = 'Computer wins!';
          this.computerScore++; // Incrémente le score de l'ordinateur
        } else {
          this.battleResult = 'Tie! Drawing extra cards...';
          this.handleTie();
        }

        this.battleInProgress = false;
        this.battleComplete.emit();
      },
      error: (err) => {
        console.error('Error during battle:', err);
        this.battleInProgress = false;
      },
    });
  }

  handleTie(): void {
    this.cardService.drawTwoCards().subscribe({
      next: ([playerCard, computerCard]) => {
        this.playerCard = playerCard;
        this.computerCard = computerCard;

        const playerValue = this.getCardValue(playerCard.value);
        const computerValue = this.getCardValue(computerCard.value);

        if (playerValue > computerValue) {
          this.battleResult = 'You win the tie!';
          this.playerScore++;
        } else if (playerValue < computerValue) {
          this.battleResult = 'Computer wins the tie!';
          this.computerScore++;
        } else {
          this.battleResult = 'Another tie! Try again.';
        }
      },
      error: (err) => {
        console.error('Error during tie resolution:', err);
      },
    });
  }

  getCardValue(value: string): number {
    const mapping: { [key: string]: number } = {
      jack: 11,
      queen: 12,
      king: 13,
      ace: 14,
    };
    return mapping[value] || parseInt(value, 10) || 0;
  }
}