import org.example.FootballScoreboard;
import org.example.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FootballScoreboardIntegrationTest {
    private FootballScoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new FootballScoreboard();
    }

    @Test
    void fullMatchLifecycleTest() {
        scoreboard.startGame("Mexico", "Canada");
        List<Match> matches = scoreboard.getSummary();
        assertEquals(1, matches.size());

        Match match = matches.get(0);
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
        assertFalse(match.getStartTime().isAfter(LocalDateTime.now()));

        scoreboard.updateScore("Mexico", "Canada", 2, 3);
        matches = scoreboard.getSummary();
        assertEquals(2, matches.get(0).getHomeScore());
        assertEquals(3, matches.get(0).getAwayScore());

        scoreboard.finishGame("Mexico", "Canada");
        assertTrue(scoreboard.getSummary().isEmpty());
    }

    @Test
    void multipleMatchesOrderTest() {
        scoreboard.startGame("Uruguay", "Italy");
        scoreboard.startGame("Spain", "Brazil");
        scoreboard.startGame("Germany", "France");

        scoreboard.updateScore("Uruguay", "Italy", 6, 6);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Germany", "France", 2, 2);

        List<Match> summary = scoreboard.getSummary();

        assertEquals(3, summary.size());
        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Germany", summary.get(2).getHomeTeam());

        assertEquals(12, summary.get(0).getHomeScore() + summary.get(0).getAwayScore());
        assertEquals(12, summary.get(1).getHomeScore() + summary.get(1).getAwayScore());
        assertEquals(4, summary.get(2).getHomeScore() + summary.get(2).getAwayScore());
    }

    @Test
    void sameScoreDifferentTimeTest() {
        scoreboard.startGame("TeamA", "TeamB");
        LocalDateTime firstMatchTime = scoreboard.getSummary().get(0).getStartTime();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        scoreboard.startGame("TeamC", "TeamD");
        scoreboard.updateScore("TeamA", "TeamB", 2, 2);
        scoreboard.updateScore("TeamC", "TeamD", 2, 2);

        List<Match> summary = scoreboard.getSummary();

        assertTrue(summary.get(0).getStartTime().isAfter(summary.get(1).getStartTime()));
        assertEquals("TeamC", summary.get(0).getHomeTeam());
    }

    @Test
    void invalidOperationsTest() {
        assertThrows(IllegalStateException.class, () ->
                scoreboard.updateScore("Invalid", "Match", 1, 1));

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.finishGame("Invalid", "Match"));

        scoreboard.startGame("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startGame("Mexico", "Canada"));
    }

    @Test
    void negativeScoreTest() {
        scoreboard.startGame("France", "Brazil");

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("France", "Brazil", -1, 0));

        assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("France", "Brazil", 0, -1));
    }

    @Test
    void concurrentOperationsTest() throws InterruptedException {
        scoreboard.startGame("A", "B");
        Thread.sleep(1);
        scoreboard.startGame("C", "D");
        Thread.sleep(1);
        scoreboard.startGame("E", "F");

        scoreboard.updateScore("A", "B", 3, 2);
        scoreboard.updateScore("C", "D", 1, 1);
        scoreboard.updateScore("E", "F", 5, 0);

        scoreboard.finishGame("C", "D");

        List<Match> summary = scoreboard.getSummary();
        assertEquals(2, summary.size());

        assertEquals("E", summary.get(0).getHomeTeam());
        assertEquals("A", summary.get(1).getHomeTeam());
    }

    @Test
    void matchSummaryAfterMultipleUpdatesTest() {
        scoreboard.startGame("Italy", "Spain");

        scoreboard.updateScore("Italy", "Spain", 1, 0);
        scoreboard.updateScore("Italy", "Spain", 2, 0);
        scoreboard.updateScore("Italy", "Spain", 2, 1);
        scoreboard.updateScore("Italy", "Spain", 2, 2);

        Match match = scoreboard.getSummary().get(0);
        assertEquals(2, match.getHomeScore());
        assertEquals(2, match.getAwayScore());
    }

    @Test
    void startFinishMultipleTimesTest() {
        scoreboard.startGame("Russia", "Qatar");
        scoreboard.finishGame("Russia", "Qatar");

        scoreboard.startGame("Russia", "Qatar");
        assertEquals(1, scoreboard.getSummary().size());
    }
}
