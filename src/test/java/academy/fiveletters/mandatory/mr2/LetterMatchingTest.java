package academy.fiveletters.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import academy.fiveletters.game.GameService;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.game.GuessResult;
import academy.fiveletters.game.LetterStatus;
import academy.fiveletters.model.Dictionary;
import academy.fiveletters.model.DictionaryReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MR2. Проверка букв")
class LetterMatchingTest {

    private static final int MAX_ATTEMPTS = 6;

    @Test
    @DisplayName("Базовый случай: загадано \"озеро\", ввод \"арбуз\" -> ❌🟡❌❌🟡")
    void baseCase() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        GameSession session = new GameSession("озеро", MAX_ATTEMPTS);

        GuessResult result = service.applyGuess(session, "арбуз");

        assertEquals(
                List.of(
                        LetterStatus.ABSENT,
                        LetterStatus.PRESENT,
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT,
                        LetterStatus.PRESENT),
                result.getStatuses());
    }

    @Test
    @DisplayName("Ни одна буква не подошла: все позиции ❌")
    void noLettersMatch() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        GameSession session = new GameSession("озеро", MAX_ATTEMPTS);

        GuessResult result = service.applyGuess(session, "кабан");

        assertEquals(
                List.of(
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT,
                        LetterStatus.ABSENT),
                result.getStatuses());
    }

    @Test
    @DisplayName("Повторяющиеся буквы: загадано \"сорок\", ввод \"оооом\" -> ❌✅❌✅❌")
    void repeatedLettersAreNotDoubleCounted() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        List<LetterStatus> statuses = service.computeStatuses("сорок", "оооом");

        assertEquals(
                List.of(
                        LetterStatus.ABSENT,
                        LetterStatus.CORRECT,
                        LetterStatus.ABSENT,
                        LetterStatus.CORRECT,
                        LetterStatus.ABSENT),
                statuses);
    }

    @Test
    @DisplayName("Полное совпадение: загадано \"озеро\", ввод \"озеро\" -> ✅✅✅✅✅")
    void fullMatch() throws IOException {
        Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dict);
        GameSession session = new GameSession("озеро", MAX_ATTEMPTS);

        GuessResult result = service.applyGuess(session, "озеро");

        assertEquals(
                List.of(
                        LetterStatus.CORRECT,
                        LetterStatus.CORRECT,
                        LetterStatus.CORRECT,
                        LetterStatus.CORRECT,
                        LetterStatus.CORRECT),
                result.getStatuses());
    }
}
