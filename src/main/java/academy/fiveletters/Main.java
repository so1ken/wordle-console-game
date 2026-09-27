package academy.fiveletters;

import academy.fiveletters.console.ConsoleUI;
import academy.fiveletters.console.GameRunner;
import academy.fiveletters.game.GameService;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.game.GuessResult;
import academy.fiveletters.model.Dictionary;
import academy.fiveletters.model.DictionaryReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

/** Точка входа в игру «5 букв». */
public final class Main {

    private static final int DEFAULT_MAX_ATTEMPTS = 6;
    private static final String CHECK_MODE = "check";
    private static final String ANSWER_FLAG = "--answer";
    private static final String GUESS_FLAG = "--guess";

    private Main() {}

    public static void main(String[] args) {
        try {
            Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
            GameService service = new GameService(dict);
            ConsoleUI ui = new ConsoleUI();

            if (args.length > 0 && CHECK_MODE.equals(args[0])) {
                runCheckMode(args, service, ui);
            } else {
                runInteractiveMode(service, ui);
            }
        } catch (IOException e) {
            System.err.println("Не удалось загрузить словарь: " + e.getMessage());
        }
    }

    private static void runCheckMode(String[] args, GameService service, ConsoleUI ui) {
        Optional<String> answer = parseFlag(args, ANSWER_FLAG);
        Optional<String> guess = parseFlag(args, GUESS_FLAG);

        if (answer.isEmpty() || guess.isEmpty()) {
            System.err.println("Использование: check --answer <слово> --guess <слово>");
            return;
        }

        GameSession session = new GameSession(answer.get(), DEFAULT_MAX_ATTEMPTS);
        GuessResult result = service.applyGuess(session, guess.get());
        ui.printGuessResult(result, guess.get());
    }

    private static void runInteractiveMode(GameService service, ConsoleUI ui) {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            GameRunner runner = new GameRunner(service, ui);
            while (true) {
                ui.printMenu();

                String input;
                try {
                    input = scanner.nextLine();
                } catch (NoSuchElementException e) {
                    return;
                }

                if ("0".equals(input)) {
                    return;
                }
                if ("1".equals(input)) {
                    GameSession session = service.startGame(DEFAULT_MAX_ATTEMPTS, System.currentTimeMillis());
                    runner.play(session, scanner);
                } else {
                    ui.printError("Неверный пункт меню");
                }
            }
        }
    }

    private static Optional<String> parseFlag(String[] args, String flag) {
        for (int i = 0; i < args.length - 1; i++) {
            if (flag.equals(args[i])) {
                return Optional.of(args[i + 1]);
            }
        }
        return Optional.empty();
    }
}
