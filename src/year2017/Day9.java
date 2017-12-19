package year2017;

import org.testng.Assert;
import org.testng.annotations.Test;
import util.Utils;

import java.io.*;

public class Day9 {

    @Test
    public void run() throws IOException {
        File file = Utils.getFile("year2017/Day9-input.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String input = br.readLine();
//        System.out.println("input = " + input);
//
//        String ignored = stripIgnored(input);
//        System.out.println("ignored = " + ignored);
//
//        String stripped = stripGarbage(ignored);
//        System.out.println("garbage = " + stripped);

        int score = parseScore(stripGarbage(stripIgnored(input)));
        System.out.println("score = " + score);
    }

    @Test
    public void testParse() {
        String test;
        int score;

        test = "{{{},{},{{}}}}";
        Assert.assertEquals(parseScore(test), 16);

        test = "{{<ab>},{<ab>},{<ab>},{<ab>}}";
        Assert.assertEquals(parseScore(stripGarbage(stripIgnored(test))), 9);

        test = "{{{}}}";
        Assert.assertEquals(parseScore(stripGarbage(stripIgnored(test))), 6);

        test = "{{<a!>},{<a!>},{<a!>},{<ab>}}";
        Assert.assertEquals(parseScore(stripGarbage(stripIgnored(test))), 3);

        test = "{{<!!>},{<!!>},{<!!>},{<!!>}}";
        test = stripIgnored(test);
        System.out.println("test = " + test);
        test = stripGarbage(test);
        System.out.println("test = " + test);
        Assert.assertEquals(parseScore(test), 9);
    }

    @Test
    public void testParse2() {
        String test = "{{<a!>},{<a!>},{<a!>},{<ab>}}";
        test = stripIgnored(test);
        System.out.println("test = " + test);
        test = stripGarbage(test);
        System.out.println("test = " + test);
        Assert.assertEquals(parseScore(stripGarbage(stripIgnored(test))), 3);
    }

    public int parseScore(String input) {
        int level = 0;
        int score = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '{') {
                // starting new level / group
                level++;
//                System.out.println("start level " + level);
            }
            if (input.charAt(i) == '}') {
                // closing group or level
//                System.out.println("close level " + level);
                // add to score
                score += level;
                level--;
            }
        }

        return score;
    }


    @Test
    public void testStripGarbage() {
        String test;

        test = "1234<garbage>5678";
        Assert.assertEquals(stripGarbage(test), "12345678");

        test = "1234<garbage>56781234<garbage>5678";
        Assert.assertEquals(stripGarbage(test), "1234567812345678");

        test = "<{!>}>";
        Assert.assertEquals(stripGarbage(stripIgnored(test)), "");

        test = "{{<a},{<a},{<a},{<ab>}}";
        Assert.assertEquals(stripGarbage(test), "{{}}");

        test = "<{o\"i,<{i<a>";
        Assert.assertEquals(stripGarbage(test), "");
    }


    private String stripGarbage(String input) {
        StringBuilder buff = new StringBuilder();
        int garbageCount = 0;

        boolean garbage = false;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '<' && !garbage) {
                garbage = true;
                continue;
            } else if (input.charAt(i) == '>') {
                garbage = false;
                // end of garbage
                continue;
            }

            if (!garbage) {
                buff.append(input.charAt(i));
            } else {
                garbageCount++;
            }
        }

        System.out.println("garbageCount = " + garbageCount);

        return buff.toString();
    }

    @Test
    public void testStripIgnored() {
        String test;

        test = "<{!>}>";
        Assert.assertEquals(stripIgnored(test), "<{}>");

        test = "<!!>";
        Assert.assertEquals(stripIgnored(test), "<>");

        test = "<!!!>>";
        Assert.assertEquals(stripIgnored(test), "<>");

        test = "<{o\"i!a,<{i<a>";
        Assert.assertEquals(stripIgnored(test), "<{o\"i,<{i<a>");

        test = "{{<a!>},{<a!>},{<a!>},{<ab>}";
        Assert.assertEquals(stripIgnored(test), "{{<a},{<a},{<a},{<ab>}");
    }

    private String stripIgnored(String input) {
        input = input.replaceAll("\\!.", "");

        return input;
    }
}
