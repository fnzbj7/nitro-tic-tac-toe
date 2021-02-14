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

    private String nextFigure = "X";


    public GameModel() {
        gameField = new ArrayList<>(9);
        for(int i = 0; i < 9; i++) {
            gameField.add(new GameFieldModel());
        }
    }

    void changeToTheNextPlayer() {
        nextFigure = "X".equals(nextFigure) ? "O" : "X";
    }
}
