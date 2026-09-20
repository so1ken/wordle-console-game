package academy.fiveletters.game;

import academy.fiveletters.model.Dictionary;
import java.util.Random;

public class GameService {
    public GameSession startGame(Dictionary dict, int maxAttempts, long seed) {
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException("Количество попыток <= 0");
        }

        Random random = new Random(seed);
        int index = random.nextInt(dict.size());

        String answer = dict.getWord(index);

        return new GameSession(answer, maxAttempts);
    }
}
