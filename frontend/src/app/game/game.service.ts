import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, } from 'rxjs/operators';
import { GameIdDto } from '../dto/game-id.dto';
import { NextStepDto } from '../dto/next-step.dto';
import { GameModel } from '../model/game-model';
import { GameModelDto } from '../dto/game-model.dto';
import { GameState } from '../model/game-state.enum';

@Injectable({providedIn: 'root'})
export class GameService {

  baseUrl = '/api';

  constructor(private http: HttpClient) {}

  createGame(): Observable<GameIdDto> {
    return this.http.post<GameIdDto>(`${this.baseUrl}/tic-tac-toe/create-game`, null);
  }

  setNextStep(nextStepDto: NextStepDto): Observable<GameModel>  {
    return this.http.put<GameModelDto>(`${this.baseUrl}/tic-tac-toe/next-step`, nextStepDto).pipe(map(gameModelDto => {
      // Simple mappling gameField.actualValue to a string array
      const gameModel: GameModel = {actualPlayer: gameModelDto.actualPlayer, gameState: gameModelDto.gameState,
        gameField: gameModelDto.gameField.map(gameField => gameField.actualValue)};
      return gameModel;
    }));
  }
}
