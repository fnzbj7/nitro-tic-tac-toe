import { GameState } from './game-state.enum';

export class GameModel {
  gameField!: string[];
  actualPlayer!: string;
  gameState!: GameState;
}
