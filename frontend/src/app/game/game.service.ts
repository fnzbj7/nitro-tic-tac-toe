import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GameIdDto } from '../dto/game-id.dto';

@Injectable({providedIn: 'root'})
export class GameService {

  constructor(private http: HttpClient) {}

  createGame(): Observable<GameIdDto> {
    return this.http.post<GameIdDto>(`http://localhost:8080/api/tic-tac-toe/create-game`, null);
  }
}
