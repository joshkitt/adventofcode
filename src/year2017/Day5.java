package year2017;

import org.testng.Assert;
import org.testng.annotations.Test;
import util.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 {
    public static void main(String[] args) throws IOException {
        File file = Utils.getFile("year2017/Day5-input.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        // read file into array list
        List<Integer> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            list.add(Integer.parseInt(line));
        }

        int jumps = executeJumps(list, MODE.TWO);
        System.out.println("jumps = " + jumps);
    }

    public enum MODE {
        ONE,
        TWO
    }

    @Test
    public void testExecuteJumpsModeOne() {
        Integer[] testList = {0, 3, 0, 1, -3};
        int i = executeJumps(Arrays.asList(testList), MODE.ONE);

        Assert.assertEquals(i, 5);
    }

    @Test
    public void testExecuteJumpsModeTwo() {
        Integer[] testList = {0, 3, 0, 1, -3};
        int i = executeJumps(Arrays.asList(testList), MODE.TWO);

        Assert.assertEquals(i, 10);
    }

    private static int executeJumps(List<Integer> list, MODE mode) {
        System.out.println("list = " + list);
        Integer index = 0;
        Integer value = 0;
        int count = 0;
        while (index > -1 && index < list.size()) {
            // get value for jump offset
            value = list.get(index);

            // increment old value
            if (mode == MODE.TWO && value >= 3) {
                list.set(index, value - 1);
            } else {
                list.set(index, value + 1);
            }

            // jump
            index += value;

            // incr counter
            count++;
        }

        System.out.println("list = " + list);
        System.out.println("index = " + index);
        return count;
    }
}
