package year2017;

import org.testng.Assert;
import org.testng.annotations.Test;
import util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day16 {


    @Test
    public void run() throws IOException {
        String programs = "abcdefghijklmnop";
        String orig = programs;

        File file = Utils.getFile("year2017/Day16-input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input = br.readLine();
        System.out.println("input = " + input);

        String[] ops = input.split(",");

        // star 2 - find num iterations to get back in original order
        // start 2 - run modulo times to find final order after 1 billion iterations
        for (int i = 0; i < 34; i++) {
            programs = process(programs, ops);
            if (programs.equals(orig)) {
                System.out.println("orig found at: " + i);
                break;
            }
        }

        System.out.println("programs = " + programs);
        System.out.println("done");
    }

    @Test
    public void findMod() {
        // star 2 - num iterations after the last time original order was found before 1 billion
        System.out.println(1000000000 % 42);
    }

    @Test
    public void testTestInput() {
        String programs = "abcde";
        String input = "s1,x3/4,pe/b";

        Assert.assertEquals(process(programs, input.split(",")), "baedc");
    }

    public String process(String programs, String[] ops) {
        for (String op : ops) {
            programs = handleOp(op, programs);
        }

        return programs;
    }


    @Test
    public void testSpin() {
        String test = "abcde";
        Assert.assertEquals(spin(test, 1), "eabcd");
    }

    @Test
    public void testExchange() {
        String test = "eabcd";
        Assert.assertEquals(exchange(test, 3, 4), "eabdc");

        handleOp("x3/4", test);
    }

    @Test
    public void testPartner() {
        String test = "asdf";
        Assert.assertEquals(partner(test, "a", "s"), "sadf");
    }

    @Test
    public void testHandleOp() {
        String test = "abcde";

        // test spin
        Assert.assertEquals(handleOp("s1", test), "eabcd");

        // test exchange
        test = "asdf";
        Assert.assertEquals(handleOp("x2/3", test), "asfd");

        test = "abcdefghijklmnop";
        Assert.assertEquals(handleOp("x10/11", test), "abcdefghijlkmnop");

        test = "asdf";
        // test partner
        Assert.assertEquals(handleOp("pa/s", test), "sadf");
    }

    public String handleOp(String op, String programs) {
        if (op.startsWith("s")) {
            return spin(programs, Integer.parseInt(op.substring(1)));
        } else if (op.startsWith("p")) {
            return partner(programs, String.valueOf(op.charAt(1)), String.valueOf(op.charAt(3)));
        } else if (op.startsWith("x")) {
            String[] tokens = op.split("/");
            return exchange(programs, Integer.parseInt(tokens[0].substring(1)), Integer.parseInt(tokens[1]));
        }

        return null;
    }

    public String partner(String programs, String a, String b) {
        programs = programs.replace(a, "0");
        programs = programs.replace(b, a);
        programs = programs.replace("0", b);

        return programs;
    }

    public String spin(String programs, int num) {
        StringBuilder buff = new StringBuilder();
        buff.append(programs.substring(programs.length() - num));
        buff.append(programs.substring(0, programs.length() - num));
        return buff.toString();
    }

    public String exchange(String programs, int indexA, int indexB) {
        char a = programs.charAt(indexA);
        char b = programs.charAt(indexB);

        StringBuilder buff = new StringBuilder(programs);
        buff.replace(indexA, indexA + 1, String.valueOf(b));
        buff.replace(indexB, indexB + 1, String.valueOf(a));

        return buff.toString();
    }
}
