package year2017;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) throws IOException {
        File file = getFile("year2017/Day2-input.txt");

        calculateChecksum(file);
        calculateEvenDivisible(file);

    }

    private static void calculateEvenDivisible(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            List<Integer> values = new ArrayList<>();

            while ((line = br.readLine()) != null) {
//                System.out.println("line = " + line);
                List<Integer> sortedList = getSortedList(line);
                Integer[] numbers = sortedList.toArray(new Integer[sortedList.size()]);

                Integer value = 0;
                for (int i = 1; i < numbers.length; i++) {
//                    System.out.println("i = " + numbers[i]);
                    for (int j = 0; j < i; j++) {
//                        System.out.println("j = " + numbers[j]);
                        // if evenly divisible, add to values and done for this list
                        if ((numbers[j] != 0) && numbers[i] % numbers[j] == 0) {
                            value = numbers[i] / numbers[j];
                            System.out.println("" + numbers[i] + " / " + numbers[j] + " = " + value);
                            values.add(value);
                            break;
                        }
                    }
                }
            }

            System.out.println("values = " + values);
            int sum = values.stream().mapToInt(Integer::intValue).sum();
            System.out.println("sum = " + sum);
        }
    }

    private static void calculateChecksum(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            List<Integer> values = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                System.out.println("line = " + line);

                List<Integer> sortedList = getSortedList(line);


                // calc diff between lowest and highest values
                Integer diff = sortedList.get(sortedList.size() - 1) - sortedList.get(0);
                System.out.println("diff = " + diff);

                values.add(diff);
            }

            System.out.println("values = " + values);

            int sum = values.stream().mapToInt(Integer::intValue).sum();
            System.out.println("sum = " + sum);
        }
    }

    private static List<Integer> getSortedList(String line) {
        // put items into list
        List<Integer> list = new ArrayList<>();
        String[] split = line.split("\\s");
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }

        // sort
        List<Integer> collect = list.stream().sorted().collect(Collectors.toList());
        System.out.println("collect = " + collect);
        return collect;
    }

    private static File getFile(String filename) {
        File file = new File(Day2.class.getClassLoader().getResource(filename).getFile());

        System.out.println("file = " + file + " exists " + file.exists());

        return file;
    }
}
