package hu.nitro.tictactoe.service;

import hu.nitro.tictactoe.dto.NextStepDto;
import hu.nitro.tictactoe.model.GameFieldModel;
import hu.nitro.tictactoe.model.GameModel;
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

    public void processNextStep(NextStepDto nextStepDto) {

        GameModel gameModel = getGameForId(nextStepDto.getGameId());

        isNextStepDtoValid(nextStepDto, gameModel);

        List<GameFieldModel> gameField = gameModel.getGameField();

        GameFieldModel gfm = gameField.get(nextStepDto.getField());
        gfm.setActualValue(nextStepDto.getActualPlayer());

    }

    private void isNextStepDtoValid(NextStepDto nextStepDto, GameModel gameModel) {
        if(!gameModel.getNextFigure().equals(nextStepDto.getActualPlayer())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nem az aktuális játékos, kapott játékos: " + nextStepDto.getActualPlayer() +
                    ", elvárt játékos: " + gameModel.getNextFigure());
        }

        if(nextStepDto.getField() == null || nextStepDto.getField() < MIN_FIELD || nextStepDto.getField() > MAX_FIELD) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rossz mező hossz: " + nextStepDto.getField());
        }

        if(gameModel.getGameField().get(nextStepDto.getField()) != null) {
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
