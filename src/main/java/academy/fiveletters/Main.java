package academy.fiveletters;

import academy.fiveletters.model.Dictionary;
import academy.fiveletters.model.DictionaryReader;
import java.io.IOException;
import java.util.List;

/** Точка входа в игру «5 букв». */
public final class Main {

    private Main() {}

    public static void main(String[] args) {
        try {
            List<String> words = DictionaryReader.read("/dictionary.txt");
            Dictionary dict = new Dictionary(words);
            System.out.println("Словарь загружен: " + dict.size() + " слов");
        } catch (IOException e) {
            System.err.println("Не удалось загрузить словарь: " + e.getMessage());
        }
    }
}
