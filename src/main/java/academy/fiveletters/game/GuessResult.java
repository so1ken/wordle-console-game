package academy.fiveletters.game;

import java.util.List;

public final class GuessResult {
    private final List<LetterStatus> statuses;

    public GuessResult(List<LetterStatus> statuses) {
        this.statuses = List.copyOf(statuses);
    }

    public List<LetterStatus> getStatuses() {
        return statuses;
    }
}
