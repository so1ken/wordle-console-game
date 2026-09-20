package academy.fiveletters.game;

import java.util.ArrayList;
import java.util.List;

// Класс состояния конкретной игровой сессии. Хранит только состояния
public final class GameSession {
    private static final int WORD_LENGTH = 5;

    private final String answer;
    private final int maxAttempts;
    private int attemptsUsed;
    private final List<String> attemptsHistory;
    private GameStatus status;

    public GameSession(String answer, int maxAttempts) {
        if (answer == null || answer.length() != WORD_LENGTH) {
            throw new IllegalArgumentException("Не верный формат ответа");
        }
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("Количество попыток должно быть больше 0");
        }

        this.answer = answer;
        this.maxAttempts = maxAttempts;
        this.attemptsUsed = 0;
        this.attemptsHistory = new ArrayList<>();
        this.status = GameStatus.IN_PROGRESS;
    }

    public String getAnswer() {
        return answer;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    public List<String> getAttemptsHistory() {
        return List.copyOf(attemptsHistory);
    }

    public GameStatus getStatus() {
        return status;
    }
}
