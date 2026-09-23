package academy.fiveletters.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.fiveletters.game.GameService;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.game.GuessResult;
import academy.fiveletters.model.Dictionary;
import academy.fiveletters.model.DictionaryReader;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MR2. Валидация ввода")
class GuessValidationTest {

    private static final int MAX_ATTEMPTS = 6;

    @Test
    @DisplayName("Некорректный ввод не тратит попытку")
    void invalidInputDoesNotSpendAttempt() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        GameSession session = new GameSession("озеро", MAX_ATTEMPTS);

        int attemptsBefore = session.getAttemptsUsed();

        // Слишком короткое слово
        assertThrows(IllegalArgumentException.class, () -> service.applyGuess(session, "дом"));
        // Слишком длинное
        assertThrows(IllegalArgumentException.class, () -> service.applyGuess(session, "озерко"));
        // Не буквы
        assertThrows(IllegalArgumentException.class, () -> service.applyGuess(session, "12345"));
        // Слова нет в словаре
        assertThrows(IllegalArgumentException.class, () -> service.applyGuess(session, "фывап"));

        assertEquals(attemptsBefore, session.getAttemptsUsed());
        assertTrue(session.getAttemptsHistory().isEmpty());
    }

    @Test
    @DisplayName("Ввод не зависит от регистра: \"ОЗЕРО\" и \"озеро\" обрабатываются одинаково")
    void inputIsCaseInsensitive() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);

        // Две отдельные сессии с одинаковым ответом
        GameSession session1 = new GameSession("озеро", MAX_ATTEMPTS);
        GameSession session2 = new GameSession("озеро", MAX_ATTEMPTS);

        GuessResult upper = service.applyGuess(session1, "ОЗЕРО");
        GuessResult lower = service.applyGuess(session2, "озеро");

        // Результаты должны быть одинаковые
        assertEquals(lower.getStatuses(), upper.getStatuses());
    }

    @Test
    @DisplayName("Слово, которого нет в словаре, отклоняется")
    void unknownWordIsRejected() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        GameSession session = new GameSession("озеро", MAX_ATTEMPTS);

        assertThrows(IllegalArgumentException.class, () -> service.applyGuess(session, "фывап"));

        assertEquals(0, session.getAttemptsUsed());
    }
}
