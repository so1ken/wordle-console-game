package academy.fiveletters.mandatory.mr1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.fiveletters.model.Dictionary;
import academy.fiveletters.model.DictionaryReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: словарь. */
@DisplayName("MR1. Словарь")
class DictionaryTest {

    @Test
    @DisplayName("Словарь содержит не менее 50 слов")
    void dictionaryContainsAtLeastFiftyWords() throws IOException {
        List<String> words = DictionaryReader.read("/dictionary.txt");
        Dictionary dictionary = new Dictionary(words);
        assertTrue(dictionary.size() >= 50, "В словаре должно быть минимум 50 слов");
    }

    @Test
    @DisplayName("Все слова словаря состоят ровно из 5 букв")
    void allWordsAreExactlyFiveLettersLong() throws IOException {
        List<String> words = DictionaryReader.read("/dictionary.txt");
        Dictionary dictionary = new Dictionary(words);

        for (int i = 0; i < dictionary.size(); i++) {
            String word = dictionary.getWord(i);
            assertEquals(5, word.length(), "Слово не из 5 букв: " + word);
        }
    }

    @Test
    @DisplayName("Пустой словарь приводит к ошибке, а не к запуску игры без слова")
    void emptyDictionaryIsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Dictionary(List.of()),
                "Пустой словарь должен приводить к IllegalArgumentException");
    }
}
