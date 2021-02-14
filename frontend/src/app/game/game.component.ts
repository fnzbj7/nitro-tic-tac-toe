import { Component, OnInit } from '@angular/core';
import { GameService } from './game.service';

@Component({
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit{

  constructor(private gameService: GameService) {}

  gameField = ['', '', '', '', '', '', '', '', ''];

  nextFigure = 'X';

  isGameOver = false;


  ngOnInit(): void {
    this.gameService.createGame().subscribe(x => console.log('Csak meglett:', x));
  }

  onResetGame(): void {
    this.gameField =  ['', '', '', '', '', '', '', '', ''];
    this.nextFigure = 'X';
  }

  onMark(cellNum: number): void {

    if (this.isGameOver) {
      console.warn('A játék véget ért, indíts egy újat');
    }

    if (this.gameField[cellNum] === '' ) {
      this.gameField[cellNum] = this.nextFigure;

      this.nextFigure = this.nextFigure === 'X' ? 'O' : 'X';
    } else {
      console.warn(`Erre a helyre már lépett valaki (0-8): ${cellNum}`);
    }
  }
}
