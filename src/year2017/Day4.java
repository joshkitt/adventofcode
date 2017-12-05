package year2017;

import org.testng.annotations.Test;
import util.Utils;

import java.io.*;
import java.util.*;

public class Day4 {
    public static void main(String[] args) throws IOException {
        File file = Utils.getFile("year2017/Day4-input.txt");

        calcPolicy1(file);
    }

    @Test
    public static void testHasAnagrams() {
        Set<String> set = new HashSet<>();

        set.add("sil");
        set.add("lsi");

        System.out.println(hasAnagrams(set));
    }

    private static void calcPolicy1(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            int policy1Count = 0;
            int policy2Count = 0;

            while ((line = br.readLine()) != null) {
                Set<String> set = new HashSet<>();

                // just add all strings to the set to filter dupes
                String[] strings = line.split("\\s");
                set.addAll(Arrays.asList(strings));

                // check for bad input
                for (String string : strings) {
                    if (string.length() == 0) {
                        throw new RuntimeException("bad input: " + string);
                    }
                }

                // check set size vs number of words
                if (strings.length == set.size()) {
//                    System.out.println("match");
                    policy1Count++;

                    // additional anagram check for policy 2
                    if (!hasAnagrams(set)) {
//                        System.out.println("No anagrams: " + set);
                        policy2Count++;
                    } else {
//                        System.out.println("Has anagrams: " + set);
                    }
                } else {
//                    System.out.println("duplicate: [" + line + "] -> " + set);
                }
            }

            System.out.println("policy1Count = " + policy1Count);
            System.out.println("policy2Count = " + policy2Count);
        }
    }

    /**
     * Make a master list, with a list of characters for each word - that way i can use the containsall method
     */
    private static boolean hasAnagrams(Set<String> wordSet) {
        boolean hasAnagrams = false;
        List<List<String>> masterList = new ArrayList<>();

        for (String s : wordSet) {
            if (hasAnagrams) {
                break;
            }
            String[] chars = s.split("");

            // check if a list already has all strings
            for (List<String> wordCharList : masterList) {
                if (wordCharList.size() == chars.length && wordCharList.containsAll(Arrays.asList(chars))) {
                    hasAnagrams = true;
                    break;
                }
            }

            if (!hasAnagrams) {
                // add to list
                masterList.add(Arrays.asList(chars));
            }

        }
        return hasAnagrams;
    }
}
