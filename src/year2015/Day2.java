package year2015;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by josh on 12/18/15.
 */
public class Day2 {
    public static void main(String[] args) throws IOException {
        File file = new File("/tmp/input.txt");

        int total = 0;

        int totalRibbon = 0;


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("line = " + line);

                String[] sizes = line.split("x");

                List<Integer> list = new ArrayList();
                list.add(Integer.parseInt(sizes[0]));
                list.add(Integer.parseInt(sizes[1]));
                list.add(Integer.parseInt(sizes[2]));

                Collections.sort(list);

                System.out.println("list = " + list);

                int paper = 2 * list.get(0) * list.get(1);
                paper += 2 * list.get(1) * list.get(2);
                paper += 2 * list.get(0) * list.get(2);

                // now get the extra
                paper += list.get(0) * list.get(1);

                System.out.println("paper = " + paper);

                total += paper;


                int ribbon = 0;
                ribbon += list.get(0) + list.get(0) + list.get(1) + list.get(1);

                // volume
                ribbon += list.get(0) * list.get(1) * list.get(2);


                totalRibbon += ribbon;

            }
        }

        System.out.println("total = " + total);
        System.out.println("totalRibbon = " + totalRibbon);
    }
}
