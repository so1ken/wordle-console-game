package academy.fiveletters.console;

import academy.fiveletters.game.GameService;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.game.GameStatus;
import java.util.Scanner;

public final class GameRunner {
    private final GameService service;
    private final ConsoleUI ui;

    public GameRunner(GameService service, ConsoleUI ui) {
        this.service = service;
        this.ui = ui;
    }

    public void play(GameSession session, Scanner scanner) {
        while (!session.isFinished()) {
            String guess = scanner.nextLine();
            try {
                ui.printGuessResult(service.applyGuess(session, guess), guess);

                ui.printRemainingAttempts(session.getMaxAttempts() - session.getAttemptsUsed());

            } catch (IllegalArgumentException | IllegalStateException e) {
                String message = e.getMessage();
                ui.printError(message != null ? message : "Неизвестная ошибка");
            }
        }
        if (session.getStatus() == GameStatus.WIN) {
            ui.printWin(session.getAttemptsUsed());
        } else if (session.getStatus() == GameStatus.LOSE) {
            ui.printLose(session.getAnswer());
        }
    }
}
