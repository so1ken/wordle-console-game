package academy.fiveletters;

import academy.fiveletters.console.ConsoleUI;
import academy.fiveletters.game.GameService;
import academy.fiveletters.model.Dictionary;
import academy.fiveletters.model.DictionaryReader;
import java.io.IOException;

/** Точка входа в игру «5 букв». */
public final class Main {

    private Main() {}

    public static void main(String[] args) {
        try {
            Dictionary dict = new Dictionary(DictionaryReader.read("/dictionary.txt"));
            GameService service = new GameService(dict);
            ConsoleUI ui = new ConsoleUI();

            new App(service, ui).run(args);
        } catch (IOException e) {
            System.err.println("Не удалось загрузить словарь: " + e.getMessage());
        }
    }
}
