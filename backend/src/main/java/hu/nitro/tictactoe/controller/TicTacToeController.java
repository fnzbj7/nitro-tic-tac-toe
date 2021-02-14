package hu.nitro.tictactoe.controller;

import hu.nitro.tictactoe.dto.DummyDto;
import hu.nitro.tictactoe.dto.GameIdDto;
import hu.nitro.tictactoe.dto.NextStepDto;
import hu.nitro.tictactoe.service.TicTacToeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tic-tac-toe")
@CrossOrigin(origins = "http://localhost:4200")
public class TicTacToeController {

    private final TicTacToeService ticTacToeService;

    public TicTacToeController(TicTacToeService ticTacToeService) {
        this.ticTacToeService = ticTacToeService;
    }


    @PostMapping(path = "create-game")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<GameIdDto> createGame() {

        GameIdDto gameIdDto = new GameIdDto();
        Long id = ticTacToeService.createGame();
        gameIdDto.setId(id);

        return ResponseEntity.ok(gameIdDto);
    }

    @PutMapping(path = "next-step")
    public void setNextStep(NextStepDto nextStepDto) {
        ticTacToeService.processNextStep(nextStepDto);
    }

}
