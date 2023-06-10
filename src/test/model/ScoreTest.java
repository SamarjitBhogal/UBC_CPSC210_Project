package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    Score testScore;

    @BeforeEach
    void setUp() {
        testScore = new Score();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testScore.getScore());
    }

    @Test
    void increaseScoreTest() {
        testScore.increaseScore();
        assertEquals(100, testScore.getScore());
        testScore.increaseScore();
        assertEquals(200, testScore.getScore());
    }

    @Test
    void decreaseScoreTest() {
        testScore.increaseScore();
        testScore.decreaseScore();
        assertEquals(0, testScore.getScore());

        testScore.increaseScore();
        testScore.increaseScore();
        testScore.decreaseScore();
        assertEquals(100, testScore.getScore());
    }

    @Test
    void decreaseScoreLessThanZeroTest() {
        testScore.increaseScore();
        testScore.decreaseScore();
        testScore.decreaseScore();
        testScore.decreaseScore();

        assertEquals(0, testScore.getScore());
    }

    @Test
    void decreaseScoreOnSkipTest() {
        testScore.increaseScore();
        testScore.decreaseScoreOnSkip();
        assertEquals(50, testScore.getScore());

        testScore.decreaseScoreOnSkip();
        assertEquals(0, testScore.getScore());
    }

    @Test
    void decreaseScoreOnSkipLessThanZeroTest() {
        testScore.increaseScore();
        testScore.decreaseScoreOnSkip();
        testScore.decreaseScoreOnSkip();
        testScore.decreaseScoreOnSkip();

        assertEquals(0, testScore.getScore());
    }

    @Test
    void decreaseScoreBothWays() {
        testScore.increaseScore();
        testScore.decreaseScoreOnSkip();
        testScore.decreaseScore();
        assertEquals(0, testScore.getScore());

        testScore.increaseScore();
        testScore.increaseScore();
        testScore.decreaseScoreOnSkip();
        testScore.decreaseScore();
        assertEquals(50, testScore.getScore());
    }

    @Test
    void resetScoreOnZero() {
        testScore.resetScore();
        assertEquals(0, testScore.getScore());
    }

    @Test
    void resetScore() {
        testScore.setScore(1000);
        testScore.resetScore();
        assertEquals(0, testScore.getScore());
    }

    @Test
    void makeJsonTest() {
        int score = testScore.getScore();

        assertEquals(score, testScore.makeJsonObject().get("score"));
    }
}
