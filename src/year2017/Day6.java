package year2017;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Day6 {

    private static final Integer[] testInput = {0, 2, 7, 0};
    private static final Integer[] input = {4, 10, 4, 1, 8, 4, 9, 14, 5, 1, 14, 15, 0, 15, 3, 5};
    private static final List<Integer> inputList;
    private static final List<Integer> testInputList;

    private List<String> hashs = new ArrayList<>();

    static {
        inputList = Arrays.asList(input);
        testInputList = Arrays.asList(testInput);
    }

    public void allocate(List<Integer> input) {
        boolean run = true;
        Integer blocks = 0;
        int loopCount = 0;

        while (run) {
            // find index for largest item
            int index = findLargestItemIndex(input);
            blocks = input.get(index);
            // set item to 0
            input.set(index, 0);
            // distribute the item across other banks - loop around if needed
            for (int i = 0; i < blocks; i++) {
                // move to next index
                index++;
                // loop around if we need to
                if (index == input.size()) {
                    index = 0;
                }
                // increment bank by 1
                Integer item = input.get(index);
                item += 1;
                input.set(index, item);
            }
            // calc hash
            String hash = calcDigest(input.toArray(new Integer[]{}));
            // check for hash in hashs - if hash in hashs, we're done
            if (hashs.contains(hash)) {
                run = false;
            } else {
                // if not done, add hash to hashs
                hashs.add(hash);
            }

//            System.out.println("inputList = " + input);
            loopCount++;
        }

        System.out.println("loopCount = " + loopCount);
    }

    @Test
    public void run() {
        allocate(inputList);
    }

    @Test
    public void testTestInput() {
        allocate(testInputList);
    }

    @Test
    public void testChecksum() {
        String s = calcDigest(input);
        System.out.println("s = " + s);
    }

    @Test
    public void testFindLargestItemIndex() {
        int index = findLargestItemIndex(inputList);
        Assert.assertEquals(index, 11);
    }

    public int findLargestItemIndex(List<Integer> list) {
        int size = 0;
        int index = 0;

        for (int i = 0; i < list.size(); i++) {
            Integer item = list.get(i);
            if (item > size) {
                // update size
                size = item;
                index = i;
            }
        }

        return index;
    }

    public static String calcDigest(Integer[] input) {
        byte[] b = Arrays.toString(input).getBytes();
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return DatatypeConverter.printHexBinary(hash);
    }
}
