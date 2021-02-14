package hu.nitro.tictactoe.controller;

import hu.nitro.tictactoe.dto.GameIdDto;
import hu.nitro.tictactoe.dto.NextStepDto;
import hu.nitro.tictactoe.model.GameModel;
import hu.nitro.tictactoe.service.TicTacToeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tic-tac-toe")
public class TicTacToeController {

    private final TicTacToeService ticTacToeService;

    public TicTacToeController(TicTacToeService ticTacToeService) {
        this.ticTacToeService = ticTacToeService;
    }


    @PostMapping(path = "create-game")
    public ResponseEntity<GameIdDto> createGame() {

        GameIdDto gameIdDto = new GameIdDto();
        Long id = ticTacToeService.createGame();
        gameIdDto.setId(id);

        return ResponseEntity.ok(gameIdDto);
    }

    @PutMapping(path = "next-step")
    public ResponseEntity<GameModel> setNextStep(@RequestBody NextStepDto nextStepDto) {
        GameModel gameModel = ticTacToeService.processNextStep(nextStepDto);
        return ResponseEntity.ok(gameModel);
    }

}
