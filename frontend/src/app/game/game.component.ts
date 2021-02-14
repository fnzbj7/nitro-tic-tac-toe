import { Component, OnInit } from '@angular/core';
import { GameService } from './game.service';
import { GameState } from '../model/game-state.enum';
import { GameStats } from '../model/game-stats.model';

@Component({
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit{

  gameField: (string|null)[] = [null, null, null, null, null, null, null, null, null];
  actualPlayer = 'X';
  isGameOver = false;
  gameResult: GameState = GameState.UNDECIDED;
  gameId!: number;
  gameStats: GameStats = new GameStats();

  constructor(private gameService: GameService) {}

  ngOnInit(): void {
    this.gameService.createGame().subscribe(game => {
      console.log('New Game started:', game.id);
      this.gameId = game.id;
    });
  }

  onResetGame(): void {
    this.gameService.createGame().subscribe(game => {
      console.log('New Game started:', game.id);
      this.gameId = game.id;
      this.gameField = [null, null, null, null, null, null, null, null, null];
      this.actualPlayer = 'X';
      this.isGameOver = false;
    });
  }

  onMark(field: number): void {

    if (this.isGameOver) {
      console.warn('A játék véget ért, indíts egy újat');
      return;
    }

    if (this.gameField[field] !== null) {
      console.warn(`Erre a helyre már lépett valaki (0-8): ${field}`);
      return;
    }

    this.gameField[field] = this.actualPlayer;

    this.gameService.setNextStep({gameId: this.gameId, field, actualPlayer: this.actualPlayer}).subscribe(gameModel => {
      this.gameField = gameModel.gameField;
      this.actualPlayer = gameModel.actualPlayer;
      this.gameResult = gameModel.gameState;
      if (this.gameResult !== GameState.UNDECIDED) {
        this.isGameOver = true;
        this.addToTheStat(this.gameStats, this.gameResult);
      }
    });
  }

  addToTheStat(gameStat: GameStats, gameResult: GameState): void {
    switch (gameResult) {
      case GameState.WIN_X:
        gameStat.xWin++;
        break;
      case GameState.WIN_O:
        gameStat.oWin++;
        break;
      case GameState.DRAW:
        gameStat.draw++;
        break;
      default:
        console.error('Unkown GameState', gameResult);
        throw new Error('Unkown GameState' + gameResult, );
    }
  }
}
