package academy.fiveletters.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// Класс позволяющий считать текстовый файл
public final class DictionaryReader {
    public static List<String> read(String path) throws IOException {
        List<String> dict = new ArrayList<>();
        InputStream is = DictionaryReader.class.getResourceAsStream(path);
        if (is == null) {
            throw new IOException("Не найден ресурс: " + path);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty() && !line.trim().startsWith("#")) {
                    dict.add(line.trim());
                }
            }
        }

        return dict;
    }

    private DictionaryReader() {}
}
