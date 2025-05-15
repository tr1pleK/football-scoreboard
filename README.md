# Football Scoreboard Library

Простая и легковесная Java-библиотека для отслеживания футбольных матчей в реальном времени с возможностью управления счётом и генерации сводок.

## Установка

### Подключение через Maven

Добавьте в ваш pom.xml:

```
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/tr1pleK/football-scoreboard</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>org.example</groupId>
        <artifactId>football-scoreboard</artifactId>
        <version>1.0.2</version>
    </dependency>
</dependencies>
```
### Подключение через Gradle

Добавьте в build.gradle:

```
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/tr1pleK/football-scoreboard")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_USERNAME")
            password = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'org.example:football-scoreboard:1.0.2'
}
```

## Настройка аутентификации

1. Создайте GitHub Personal Access Token (https://github.com/settings/tokens) с правами:
   - repo (для доступа к репозиторию)
   - read:packages (для чтения пакетов)

2. Для Maven добавьте в ~/.m2/settings.xml:
```
<settings>
    <servers>
        <server>
            <id>github</id>
            <username>YOUR_GITHUB_USERNAME</username>
            <password>YOUR_GITHUB_TOKEN</password>
        </server>
    </servers>
</settings>
```
## Использование

### Основные операции
```
import com.football.FootballScoreboard;
import com.football.Match;

public class Main {
    public static void main(String[] args) {
        FootballScoreboard scoreboard = new FootballScoreboard();
        
        scoreboard.startGame("Мексика", "Канада");
        scoreboard.updateScore("Мексика", "Канада", 0, 5);
        
        List<Match> summary = scoreboard.getSummary();
        
        summary.forEach(match -> 
            System.out.printf("%s %d - %s %d%n",
                match.getHomeTeam(),
                match.getHomeScore(),
                match.getAwayTeam(),
                match.getAwayScore()));
        
        scoreboard.finishGame("Мексика", "Канада");
    }
}
```
### Пример вывода сводки

1. Уругвай 6 - Италия 6
2. Испания 10 - Бразилия 2
3. Мексика 0 - Канада 5

## Особенности

- Реальное время: автоматическая фиксация времени начала матча
- Валидация: защита от отрицательных счётов и дубликатов матчей
- Сортировка: 
  - По убыванию общего счёта
  - Новые матчи при равном счёте выводятся первыми
- In-memory хранилище: не требует внешних зависимостей


