package academy.fiveletters.model;

import java.util.Collections;
import java.util.List;

// Класс словаря
public class Dictionary {
    private static final int MIN_DICTIONARY_SIZE = 50;
    private static final int WORD_LENGTH = 5;

    private final List<String> dict;

    public Dictionary(List<String> input) {

        // Проверка словаря минимум 50 слов по 5 букв
        if (input.size() < MIN_DICTIONARY_SIZE) {
            throw new IllegalArgumentException("Размер словаря слишком мал");
        }
        for (String str : input) {
            if (str.length() != WORD_LENGTH) {
                throw new IllegalArgumentException("Размер слов в словаре отличен от 5");
            }
        }
        // Создаем неизменяемый словарь
        this.dict = List.copyOf(input);
    }

    // Геттер
    public List<String> getDict() {
        return Collections.unmodifiableList(dict);
    }

    public int size() {
        return dict.size();
    }

    public String getWord(int index) {
        return dict.get(index);
    }

    // Проверяем есть ли слово в словаре
    public boolean contains(String word) {
        return dict.contains(word);
    }
}
