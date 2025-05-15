import org.example.FootballScoreboard;
import org.example.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FootballScoreboardTest {
    private FootballScoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new FootballScoreboard();
    }

    @Test
    void startGame_ShouldAddNewMatch() {
        scoreboard.startGame("Mexico", "Canada");
        assertEquals(1, scoreboard.getSummary().size());
    }

    @Test
    void finishGame_ShouldRemoveMatch() {
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.finishGame("Mexico", "Canada");
        assertEquals(0, scoreboard.getSummary().size());
    }

    @Test
    void updateScore_ShouldChangeScores() {
        scoreboard.startGame("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        Match match = scoreboard.getSummary().get(0);
        assertEquals(0, match.getHomeScore());
        assertEquals(5, match.getAwayScore());
    }
    @Test
    void startGame_ShouldSetInitialScoreToZero() {
        scoreboard.startGame("Mexico", "Canada");
        Match match = scoreboard.getSummary().get(0);
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void getSummary_ShouldOrderCorrectly() {
        scoreboard.startGame("Uruguay", "Italy");
        scoreboard.startGame("Spain", "Brazil");
        scoreboard.startGame("Mexico", "Canada");

        scoreboard.updateScore("Uruguay", "Italy", 6, 6);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        List<Match> summary = scoreboard.getSummary();
        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Mexico", summary.get(2).getHomeTeam());
    }
}
