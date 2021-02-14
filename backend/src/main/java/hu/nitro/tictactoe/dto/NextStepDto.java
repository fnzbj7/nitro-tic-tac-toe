package hu.nitro.tictactoe.dto;

import lombok.Data;

@Data
public class NextStepDto {
    Long gameId;
    Integer field;
    String actualPlayer;
}
