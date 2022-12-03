package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Solution {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("day3/input.txt"));

        // part 1
        int totalPrioritiesPart1 = 0;
        for(String line: lines) {
            var left = line.substring(0, (line.length() / 2));
            var right = line.substring(line.length() / 2);

            totalPrioritiesPart1 += Solution.getPriority(Solution.getDuplicateItem(left, right));
        };
        
        System.out.println("=== Part 1 ===");
        System.out.println("Total priorities " + totalPrioritiesPart1);

        // part 2
        int totalPrioritiesPart2 = 0;
        for(int i = 0; i < lines.size(); i++) {
            if ((i + 1) % 3 == 0) {
                totalPrioritiesPart2 += Solution.getPriority(Solution.getDuplicateItem(lines.get(i-2), lines.get(i-1), lines.get(i)));
            }
        }

        System.out.println("=== Part 2 ===");
        System.out.println("Total priorities " + totalPrioritiesPart2);
    }

    static String getDuplicateItem(String left, String right) {
        for (int j = 0; j < left.length(); j++) {
            var currentItem = left.substring(j, j+1);
            if (right.contains(currentItem)) {
                return currentItem;
            }
        }
        throw new IllegalStateException("No duplicate item in bag");
    }

    static String getDuplicateItem(String first, String second, String third) {
        for (int j = 0; j < first.length(); j++) {
            var currentChar = first.substring(j, j+1);
            if (second.contains(currentChar) && third.contains(currentChar)) {
                return currentChar;
            }
        }
        throw new IllegalStateException("No duplicate item in bag");
    }

    static int getPriority(String s) {
        int i = s.charAt(0);
        if (i>=65 && i<=90) {
            return i - 38;
        } else {
            return i - 96;
        }
    }
}
