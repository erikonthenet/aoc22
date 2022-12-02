package day2_2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {

    static Map<String, Shape> ABC_MAP = Map.of(
        "A", Shape.ROCK,
        "B", Shape.PAPERS,
        "C", Shape.SCISSORS);
    static Map<String, Outcome> XYZ_MAP = Map.of(
        "X", Outcome.LOST,
        "Y", Outcome.DRAW,
        "Z", Outcome.WON);

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("day2/input.txt"));
        var shapeScore = new AtomicInteger(0);
        var outcomeScore = new AtomicInteger(0);
        lines.forEach(line -> {
            var pair = line.split(" ");
            var round = new Round(
                ABC_MAP.get(pair[0]),
                XYZ_MAP.get(pair[1])
            );

            shapeScore.addAndGet(calculateMyChoice(round));
            outcomeScore.addAndGet(round.outcome.value);
        });

        System.out.println("Shape score = " + shapeScore.get());
        System.out.println("Outcome score = " + outcomeScore.get());
        System.out.println("Total score = " + (shapeScore.get() + outcomeScore.get()));
    }

    private static int calculateMyChoice(Round round) {
        var oppChoice = round.oppChoice;
        var outcome = round.outcome;

        if (outcome == Outcome.DRAW) {
            return oppChoice.value;
        }

        if (  (oppChoice == Shape.ROCK && outcome == Outcome.WON)
           || (oppChoice == Shape.SCISSORS && outcome == Outcome.LOST)) {
            return Shape.PAPERS.value;
        }
        if (  (oppChoice == Shape.PAPERS && outcome == Outcome.WON)
           || (oppChoice == Shape.ROCK && outcome == Outcome.LOST)) {
            return Shape.SCISSORS.value;
        }

        return Shape.ROCK.value;
    }

    static record Round(Shape oppChoice, Outcome outcome) {};

    static enum Shape {
        ROCK(1),
        PAPERS(2),
        SCISSORS(3);

        int value;

        Shape(int value) {
            this.value = value;
        }
    }

    static enum Outcome {
        LOST(0),
        DRAW(3),
        WON(6);

        int value;

        Outcome(int value) {
            this.value = value;
        }
    }
}
