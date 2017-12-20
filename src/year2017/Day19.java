package year2017;

import org.testng.annotations.Test;
import util.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day19 {


    @Test
    public void run() throws IOException {
        File file = Utils.getFile("year2017/Day19-input.txt");

        List<String> lines = new ArrayList<>();

        // read input
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        // init;
        // find start of puzzle line index
        int index = lines.get(0).indexOf('|');
        int lineNumber = 0;
        DIR dir = DIR.SOUTH;
        StringBuffer milePosts = new StringBuffer();

        while (true) {
            switch (dir) {
                case NORTH:
                    lineNumber--;
                    break;
                case SOUTH:
                    lineNumber++;
                    break;
                case EAST:
                    index++;
                    break;
                case WEST:
                    index--;
                    break;
            }
//            System.out.println("coords: " + index + "," + lineNumber);

            // track mileposts
            char c = lines.get(lineNumber).charAt(index);
//            System.out.println("c = " + c);
            if (c != '+' && c != '-' && c != '|') {
                milePosts.append(c);
            }

            // check for end of puzzle
            if (c == ' ') {
                break;
            }

            // traverse puzzle
            if (c == '+') {
                // need to change direction
                // look for next direction
                switch (dir) {
                    case NORTH:
                        // look east, west
                        dir = lines.get(lineNumber).charAt(index + 1) == '-' ? DIR.EAST : DIR.WEST;
                        break;
                    case SOUTH:
                        // look east, west
                        dir = lines.get(lineNumber).charAt(index + 1) == '-' ? DIR.EAST : DIR.WEST;
                        break;
                    case EAST:
                        // look north, south
                        dir = lines.get(lineNumber + 1).charAt(index) == '|' ? DIR.SOUTH : DIR.NORTH;
                        break;
                    case WEST:
                        // look north, south
                        dir = lines.get(lineNumber + 1).charAt(index) == '|' ? DIR.SOUTH : DIR.NORTH;
                        break;
                }
//                System.out.println("dir = " + dir);
            }
        }

        System.out.println("milePosts = " + milePosts);
    }

    enum DIR {
        NORTH, SOUTH, EAST, WEST
    }
}
