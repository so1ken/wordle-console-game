package academy.fiveletters.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import academy.fiveletters.game.GameService;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.game.GameStatus;
import academy.fiveletters.model.Dictionary;
import academy.fiveletters.model.DictionaryReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MR2. Победа и поражение")
class GameOutcomeTest {

    private static final int MAX_ATTEMPTS = 6;

    @Test
    @DisplayName("Угаданное слово переводит сессию в статус WIN")
    void correctGuessWins() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        GameSession session = new GameSession("озеро", MAX_ATTEMPTS);

        service.applyGuess(session, "озеро");

        assertEquals(GameStatus.WIN, session.getStatus());
    }

    @Test
    @DisplayName("После 6 неудачных попыток сессия переходит в статус LOSE")
    void sixFailedAttemptsLose() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        GameSession session = new GameSession("озеро", MAX_ATTEMPTS);

        // Заведомо неправильные слова, которые есть в словаре
        List<String> wrongGuesses = DictionaryReader.read("/dictionary.txt").stream()
                .filter(w -> !w.equals("озеро"))
                .limit(MAX_ATTEMPTS)
                .toList();
        for (String guess : wrongGuesses) {
            if (session.getStatus() == GameStatus.IN_PROGRESS) {
                service.applyGuess(session, guess);
            }
        }

        assertEquals(GameStatus.LOSE, session.getStatus());
        assertEquals(MAX_ATTEMPTS, session.getAttemptsUsed());
    }

    @Test
    @DisplayName("Завершённая партия больше не принимает попытки")
    void finishedGameRejectsNewAttempts() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        GameSession session = new GameSession("озеро", MAX_ATTEMPTS);

        service.applyGuess(session, "озеро");
        assertEquals(GameStatus.WIN, session.getStatus());

        // Новая попытка должна бросить исключение
        assertThrows(IllegalStateException.class, () -> service.applyGuess(session, "арбуз"));
    }

    @Test
    @DisplayName("При поражении показывается загаданное слово")
    void answerIsShownOnLoss() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        GameSession session = new GameSession("озеро", MAX_ATTEMPTS);

        List<String> wrongGuesses = DictionaryReader.read("/dictionary.txt").stream()
                .filter(w -> !w.equals("озеро"))
                .limit(MAX_ATTEMPTS)
                .toList();
        for (String guess : wrongGuesses) {
            if (session.getStatus() == GameStatus.IN_PROGRESS) {
                service.applyGuess(session, guess);
            }
        }

        assertEquals(GameStatus.LOSE, session.getStatus());
        assertEquals("озеро", session.getAnswer());
    }
}
