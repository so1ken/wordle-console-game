package academy.fiveletters.game;

import academy.fiveletters.model.Dictionary;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameService {
    private static final int WORD_LENGTH = 5;

    private final Dictionary dict;

    public GameService(Dictionary dict) {
        this.dict = dict;
    }

    public GameSession startGame(int maxAttempts, long seed) {
        Random random = new Random(seed);
        int index = random.nextInt(dict.size());

        String answer = dict.getWord(index);

        return new GameSession(answer, maxAttempts);
    }

    public GuessResult applyGuess(GameSession session, String guess) {
        if (guess == null || guess.length() != WORD_LENGTH) {
            throw new IllegalArgumentException("Неверный формат ответа");
        }

        for (int i = 0; i < guess.length(); i++) {
            if (!Character.isLetter(guess.charAt(i))) {
                throw new IllegalArgumentException("Ввод должен содержать только буквы");
            }
        }

        String normalized = guess.toLowerCase();
        if (!dict.contains(normalized)) {
            throw new IllegalArgumentException("Такого слова нет");
        }

        if (session.isFinished()) {
            throw new IllegalStateException("Игра уже завершена");
        }

        if (normalized.equals(session.getAnswer())) {
            session.recordAttempt(normalized);
            session.finishGame(GameStatus.WIN);
            return new GuessResult(List.of(
                    LetterStatus.CORRECT,
                    LetterStatus.CORRECT,
                    LetterStatus.CORRECT,
                    LetterStatus.CORRECT,
                    LetterStatus.CORRECT));
        }

        List<LetterStatus> statuses = computeStatuses(session.getAnswer(), normalized);
        session.recordAttempt(normalized);
        if (session.getAttemptsUsed() >= session.getMaxAttempts()) {
            session.finishGame(GameStatus.LOSE);
        }

        return new GuessResult(statuses);
    }

    public List<LetterStatus> computeStatuses(String answer, String guess) {
        List<LetterStatus> statuses = new ArrayList<>();
        for (int i = 0; i < WORD_LENGTH; i++) {
            statuses.add(LetterStatus.ABSENT);
        }
        boolean[] used = new boolean[WORD_LENGTH];

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                statuses.set(i, LetterStatus.CORRECT);
                used[i] = true;
            }
        }

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (statuses.get(i) == LetterStatus.CORRECT) {
                continue;
            }
            for (int j = 0; j < WORD_LENGTH; j++) {
                if (!used[j] && answer.charAt(j) == guess.charAt(i)) {
                    statuses.set(i, LetterStatus.PRESENT);
                    used[j] = true;
                    break;
                }
            }
        }

        return statuses;
    }
}
