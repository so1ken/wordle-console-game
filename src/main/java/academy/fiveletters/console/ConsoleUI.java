package academy.fiveletters.console;

import academy.fiveletters.game.GuessResult;
import academy.fiveletters.game.LetterStatus;

public final class ConsoleUI {
    private String toEmoji(LetterStatus letterStatus) {
        return switch (letterStatus) {
            case CORRECT -> "✅";
            case PRESENT -> "\uD83D\uDFE1";
            case ABSENT -> "❌";
        };
    }

    public void printGuessResult(GuessResult result, String guess) {
        StringBuilder line = new StringBuilder();
        for (LetterStatus status : result.getStatuses()) {
            line.append(toEmoji(status));
        }
        line.append(' ').append(guess);
        System.out.println(line);
    }

    public void printRemainingAttempts(int remaining) {
        System.out.println("Осталось попыток: " + remaining);
    }

    public void printWin(int attemptsUsed) {
        System.out.println("Победа! Слово угадано за " + attemptsUsed + " попытки");
    }

    public void printLose(String answer) {
        System.out.println("Неудача. Загаданное слово: " + answer);
    }

    public void printMenu() {
        System.out.println("1. Новая игра");
        System.out.println("0. Выход");
    }

    public void printError(String error) {
        System.out.println(error);
    }
}
