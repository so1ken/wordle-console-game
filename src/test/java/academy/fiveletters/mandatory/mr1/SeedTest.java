package academy.fiveletters.mandatory.mr1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.fiveletters.game.GameService;
import academy.fiveletters.game.GameSession;
import academy.fiveletters.model.Dictionary;
import academy.fiveletters.model.DictionaryReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: одинаковый seed обязан давать одинаковое загаданное слово. */
@DisplayName("MR1. Воспроизводимость по seed")
class SeedTest {

    private static final int MAX_ATTEMPTS = 6;

    @Test
    @DisplayName("Одинаковый seed даёт одинаковое загаданное слово")
    void sameSeedProducesSameAnswer() throws IOException {
        Dictionary dictionary = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dictionary);

        GameSession first = service.startGame(MAX_ATTEMPTS, 42L);
        GameSession second = service.startGame(MAX_ATTEMPTS, 42L);

        assertEquals(first.getAnswer(), second.getAnswer());
    }

    @Test
    @DisplayName("Разные seed'ы дают разные слова хотя бы иногда")
    void differentSeedsProduceDifferentAnswers() throws IOException {
        Dictionary dictionary = new Dictionary(DictionaryReader.read("/dictionary.txt"));
        GameService service = new GameService(dictionary);

        Set<String> answers = new HashSet<>();
        for (long seed = 1; seed <= 10; seed++) {
            GameSession session = service.startGame(MAX_ATTEMPTS, seed);
            answers.add(session.getAnswer());
        }

        assertTrue(answers.size() > 1, "10 разных seed'ов дали только одно слово — seed не влияет");
    }
}
