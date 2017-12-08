package year2017;

import org.testng.Assert;
import org.testng.annotations.Test;
import util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day8 {

    private Map<String, Integer> map = new HashMap<>();
    private Integer highestEver = 0;

    @Test
    public void run() throws IOException {
        File file = Utils.getFile("year2017/Day8-input.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        }

        // part 1
        // find highest value in map
        Integer highest = 0;
        for (Integer i : map.values()) {
            if (i > highest) {
                highest = i;
            }
        }
        System.out.println("highest = " + highest);

        // part 2
        System.out.println("highestEver = " + highestEver);
    }

    private void processLine(String line) {
        // init register and target
        String register = getRegister(line);
        if (!map.containsKey(register)) {
            // init register
            map.put(register, 0);
        }

        String target = getTarget(line);
        if (!map.containsKey(target)) {
            // init register
            map.put(target, 0);
        }

        Integer targetValue = map.get(target);

        Integer conditionVal = getConditionVal(line);
        // check condition
        boolean process = false;
        String condition = getCondition(line);
        switch (condition) {
            case "!=":
                process = !Objects.equals(targetValue, conditionVal);
                break;
            case "==":
                process = Objects.equals(targetValue, conditionVal);
                break;
            case ">":
                process = targetValue > conditionVal;
                break;
            case "<":
                process = targetValue < conditionVal;
                break;
            case ">=":
                process = targetValue >= conditionVal;
                break;
            case "<=":
                process = targetValue <= conditionVal;
                break;
            default:
                throw new RuntimeException("encountered unknown operation");
        }

        if (process) {
            OPER operation = getOperation(line);
            Integer value = 0;
            switch (operation) {
                case inc:
                    value = map.get(register) + getAmount(line);
                    map.put(register, value);
                    break;
                case dec:
                    value = map.get(register) - getAmount(line);
                    map.put(register, value);
                    break;
            }

            storeHighest(value);
        }
    }

    // part 2
    private void storeHighest(Integer value) {
        // see if this value just stored is the highest ever
        if (value > highestEver) {
            highestEver = value;
        }
    }

    @Test
    public void testParseLine() {
        String line = "a inc -1 if b == 0";
        Assert.assertEquals(getRegister(line), "a");
        Assert.assertEquals(OPER.inc, getOperation(line));
        Assert.assertEquals(getAmount(line), new Integer(-1));
        Assert.assertEquals(getTarget(line), "b");
        Assert.assertEquals(getCondition(line), "==");
        Assert.assertEquals(getConditionVal(line), new Integer(0));
    }

    @Test
    public void testProcessLine() {
        String line = "a inc -1 if b == 0";
        processLine(line);
    }

    private String getRegister(String line) {
        return line.split("\\s")[0];
    }

    private OPER getOperation(String line) {
        return OPER.valueOf(line.split("\\s")[1]);
    }

    private Integer getAmount(String line) {
        return Integer.parseInt(line.split("\\s")[2]);
    }

    private String getTarget(String line) {
        return line.split("\\s")[4];
    }

    private String getCondition(String line) {
        return line.split("\\s")[5];
    }

    private Integer getConditionVal(String line) {
        return Integer.parseInt(line.split("\\s")[6]);
    }

    enum OPER {
        inc,
        dec
    }
}
