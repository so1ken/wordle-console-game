package academy.fiveletters.mandatory.mr3;

import static org.assertj.core.api.Assertions.assertThat;

import academy.fiveletters.support.CliRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: меню и режим автопроверки. */
@DisplayName("MR3. Меню и детерминированный режим")
class MenuTest {

    @Test
    @DisplayName("Некорректный пункт меню не роняет программу")
    void invalidMenuChoiceDoesNotCrash() {
        var result = CliRunner.runWithInput("abc\n0\n");

        assertThat(result.exitCode()).isZero();
        assertThat(result.stdout()).contains("Неверный пункт меню");
    }

    @Test
    @DisplayName("Можно сыграть несколько партий подряд без перезапуска")
    void severalGamesInARow() {
        var result = CliRunner.runWithInput("1\n" + "арбуз\n".repeat(6) + "1\n" + "арбуз\n".repeat(6) + "0\n");

        assertThat(result.exitCode()).isZero();

        long menuCount = result.stdout()
                .lines()
                .filter(line -> line.contains("Новая игра"))
                .count();
        assertThat(menuCount).isGreaterThanOrEqualTo(3);
    }

    @Test
    @DisplayName("Детерминированный режим даёт предсказуемый вывод для автопроверки")
    void deterministicModeProducesPredictableOutput() {
        var result = CliRunner.run("check", "--answer", "озеро", "--guess", "арбуз");

        assertThat(result.exitCode()).isZero();
        assertThat(result.stdout()).contains("❌🟡❌❌🟡 арбуз");
    }
}
