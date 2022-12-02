package day2;
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
    static Map<String, Shape> XYZ_MAP_1 = Map.of(
        "X", Shape.ROCK,
        "Y", Shape.PAPERS,
        "Z", Shape.SCISSORS);
    static Map<String, Outcome> XYZ_MAP_2 = Map.of(
        "X", Outcome.LOST,
        "Y", Outcome.DRAW,
        "Z", Outcome.WON);

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("day2/input.txt"));
        var shapeScore = new AtomicInteger(0);
        var outcomeScore = new AtomicInteger(0);

        // part 1
        lines.forEach(line -> {
            var pair = line.split(" ");
            var round_1 = new Round1(
                ABC_MAP.get(pair[0]),
                XYZ_MAP_1.get(pair[1])
            );

            shapeScore.addAndGet(round_1.myChoice.value);
            outcomeScore.addAndGet(calculateOutcome(round_1));
        });

        System.out.println("=== Part 1 ===");
        System.out.println("Shape score = " + shapeScore.get());
        System.out.println("Outcome score = " + outcomeScore.get());
        System.out.println("Total score = " + (shapeScore.get() + outcomeScore.get()));

        // part 2
        shapeScore.set(0);
        outcomeScore.set(0);

        lines.forEach(line -> {
            var pair = line.split(" ");
            var round = new Round2(
                ABC_MAP.get(pair[0]),
                XYZ_MAP_2.get(pair[1])
            );

            shapeScore.addAndGet(calculateMyChoice(round));
            outcomeScore.addAndGet(round.outcome.value);
        });

        System.out.println("=== Part 2 ===");
        System.out.println("Shape score = " + shapeScore.get());
        System.out.println("Outcome score = " + outcomeScore.get());
        System.out.println("Total score = " + (shapeScore.get() + outcomeScore.get()));
    }

    private static int calculateOutcome(Round1 round) {
        var oppChoice = round.oppChoice;
        var myChoice = round.myChoice;

        if (oppChoice == myChoice) {
            return Outcome.DRAW.value;
        }
        if (  (oppChoice == Shape.ROCK && myChoice == Shape.PAPERS)
           || (oppChoice == Shape.PAPERS && myChoice == Shape.SCISSORS)
           || (oppChoice == Shape.SCISSORS && myChoice == Shape.ROCK)) {
            return Outcome.WON.value;
        }

        return Outcome.LOST.value;
    }

    private static int calculateMyChoice(Round2 round) {
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

    static record Round1(Shape oppChoice, Shape myChoice) {};
    static record Round2(Shape oppChoice, Outcome outcome) {};


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
