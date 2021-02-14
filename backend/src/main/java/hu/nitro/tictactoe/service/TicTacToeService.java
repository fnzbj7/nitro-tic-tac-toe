package hu.nitro.tictactoe.service;

import hu.nitro.tictactoe.dto.NextStepDto;
import hu.nitro.tictactoe.model.GameFieldModel;
import hu.nitro.tictactoe.model.GameModel;
import hu.nitro.tictactoe.model.GameState;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicTacToeService {


    private final Map<Long, GameModel> games = new HashMap<>();
    private Long actualGameId = 1L;
    private final int SIZE = 3;
    private static final Integer MIN_FIELD = 0;
    private static final Integer MAX_FIELD = 8;

    public Long createGame() {
        GameModel gameModel = new GameModel();
        Long nextGameId = getNextGameId();
        games.put(nextGameId, gameModel);

        return nextGameId;
    }

    public GameModel getGameStatus(Long id) {
        return getGameForId(id);
    }

    public GameModel processNextStep(NextStepDto nextStepDto) {

        GameModel gameModel = getGameForId(nextStepDto.getGameId());

        isStepPossibleAndValid(nextStepDto, gameModel);

        List<GameFieldModel> gameField = gameModel.getGameField();

        GameFieldModel gfm = gameField.get(nextStepDto.getField());
        gfm.setActualValue(nextStepDto.getActualPlayer());
        gameModel.increaseMoveCount();

        GameState gameState = checkWin(gameModel, nextStepDto.getField());
        gameModel.setGameState(gameState);
        if(GameState.UNDECIDED.equals(gameState)) {
            gameModel.changeToTheNextPlayer();
        }

        return gameModel;
    }

    private GameState checkWin(GameModel gameModel, Integer newField) {
        int n = SIZE;
        int x = newField / SIZE;
        int y = newField % SIZE;
        String s = gameModel.getActualPlayer();
        List<GameFieldModel> gameField = gameModel.getGameField();

        //check col
        for(int i = 0; i < n; i++){
            if(!s.equals(getBoardField(gameField,x,i)))
                break;
            if(i == n-1){
                //report win for s
                return "X".equals(gameModel.getActualPlayer()) ? GameState.WIN_X : GameState.WIN_O;
            }
        }

        //check row
        for(int i = 0; i < n; i++){
            if(!s.equals(getBoardField(gameField,i,y)))
                break;
            if(i == n-1){
                //report win for s
                return "X".equals(gameModel.getActualPlayer()) ? GameState.WIN_X : GameState.WIN_O;
            }
        }

        //check diag
        if(x == y){
            //we're on a diagonal
            for(int i = 0; i < n; i++){
                if(!s.equals(getBoardField(gameField,i,i)))
                    break;
                if(i == n-1){
                    //report win for s
                    return "X".equals(s) ? GameState.WIN_X : GameState.WIN_O;
                }
            }
        }

        //check anti diag
        if(x + y == n - 1){
            for(int i = 0; i < n; i++){
                if(!s.equals(getBoardField(gameField,i,(n-1)-i)))
                    break;
                if(i == n-1){
                    //report win for s
                    return "X".equals(gameModel.getActualPlayer()) ? GameState.WIN_X : GameState.WIN_O;
                }
            }
        }

        //check draw
        if(gameModel.getMoveCount() == (Math.pow(n, 2))){
            //report draw
            return GameState.DRAW;
        }
        return GameState.UNDECIDED;
    }

    private String getBoardField(List<GameFieldModel> gameField, int i, int j) {
        return gameField.get((i*SIZE) + j).getActualValue();
    }

    private void isStepPossibleAndValid(NextStepDto nextStepDto, GameModel gameModel) {
        if(!GameState.UNDECIDED.equals(gameModel.getGameState())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A játéknak vége van");
        }


        if(!gameModel.getActualPlayer().equals(nextStepDto.getActualPlayer())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nem az aktuális játékos, kapott játékos: " + nextStepDto.getActualPlayer() +
                    ", elvárt játékos: " + gameModel.getActualPlayer());
        }

        if(nextStepDto.getField() == null || nextStepDto.getField() < MIN_FIELD || nextStepDto.getField() > MAX_FIELD) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rossz mező hossz: " + nextStepDto.getField());
        }

        if(gameModel.getGameField().get(nextStepDto.getField()).getActualValue() != null) {
            // TODO 4xx hiba, foglalt a mező
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foglalt a mező: " + nextStepDto.getField());
        }
    }

    private GameModel getGameForId(Long gameId) {
        GameModel gameModel = games.get(gameId);
        if(gameModel == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Játék nem található a következő id-val: " + gameId);
        }

        return gameModel;
    }

    public void surrenderGame() {

    }

    public void processAiNextStep() {

    }

    private Long getNextGameId() {
        return actualGameId++;
    }
}
