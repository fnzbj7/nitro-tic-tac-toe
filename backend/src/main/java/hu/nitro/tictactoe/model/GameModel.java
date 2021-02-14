package hu.nitro.tictactoe.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class GameModel {

    /**
     * Egy 3x3as játék táblát reprezentál a 9 eleme
     *   0,1,2
     *   3,4,5
     *   6,7,8
     */
    private final List<GameFieldModel> gameField;
    private String actualPlayer = "X";
    private int moveCount = 0;
    private GameState gameState = GameState.UNDECIDED;

    public GameModel() {
        gameField = new ArrayList<>(9);
        for(int i = 0; i < 9; i++) {
            gameField.add(new GameFieldModel());
        }
    }

    public void changeToTheNextPlayer() {
        actualPlayer = "X".equals(actualPlayer) ? "O" : "X";
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
