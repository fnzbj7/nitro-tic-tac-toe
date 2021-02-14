import { GameState } from '../model/game-state.enum';
import { GameFieldModelDto } from './game-field-model.dto';

export class GameModelDto {
  gameField!: GameFieldModelDto[];
  actualPlayer!: string;
  gameState!: GameState;
}
