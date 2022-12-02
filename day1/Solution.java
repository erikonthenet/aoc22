package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Solution {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("day1/input.txt"));
        List<Integer> elfTotalCalories = new ArrayList<>();
        
        int currentElfTotalCalories = 0;
        int aantalLegeRegels = 0;

        for(int i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals("")) {
                aantalLegeRegels++;
                elfTotalCalories.add(currentElfTotalCalories);
                currentElfTotalCalories = 0;
            } else {
                currentElfTotalCalories += Integer.parseInt(lines.get(i));
            }
        }
        elfTotalCalories.add(currentElfTotalCalories);

        System.out.println("Aantal regels: " + lines.size());
        System.out.println("Aantal lege regels: " + aantalLegeRegels);
        System.out.println("Aantal elven: " + elfTotalCalories.size());
        System.out.println(elfTotalCalories.stream().max(Comparator.naturalOrder()));
        System.out.println(elfTotalCalories.stream().min(Comparator.naturalOrder()));

        var som = elfTotalCalories.stream()
                                    .sorted(Comparator.reverseOrder())
                                    .peek(System.out::println)
                                    .limit(3)
                                    .reduce(Integer::sum);
        System.out.println("Top 3 heeft " + som);
    }
}
