## MR1 — Данные и игровая сессия

### Что реализовано
- `Dictionary` — неизменяемая модель словаря с валидацией (≥50 слов и каждое ровно 5 букв).
- `DictionaryReader` — загрузка слов из classpath (`/dictionary.txt`), пропуск пустых строк и комментариев `#`.
- `GameStatus` — enum со статусами `IN_PROGRESS`, `WIN`, `LOSE`.
- `GameSession` — модель одной партии (answer, maxAttempts, attemptsUsed, attemptsHistory, status).
- `GameService.startGame(dict, maxAttempts, seed)` — детерминированный выбор слова по seed.

### Запуск
- Игра: `./gradlew run`
- Тесты: `./gradlew check`
- Воспроизводимость: seed задаётся в вызове `startGame`, одинаковый seed → одинаковое слово.

### Принятые решения
- **Структура пакетов:** `model` — данные (словарь), `game` — игровая логика (сессия, сервис).
- **Чтение словаря:** через `getResourceAsStream("/dictionary.txt")` с явной кодировкой UTF-8.
- **Инкапсуляция:** `Dictionary` хранит `List.copyOf(input)` — нельзя изменить содержимое.
