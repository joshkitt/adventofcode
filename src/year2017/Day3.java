package year2017;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Day3 {
    private enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    private void changeDirection() {
        switch (direction) {
            case NORTH:
                direction = Direction.WEST;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
        }
    }

    // initial direction
    private Direction direction = Direction.EAST;
    private int x, y = 0;
    private int maxNorth, maxEast, maxSouth, maxWest = 0;

    @Test
    public void runTestInput1() {
        Assert.assertEquals(run(1), 1);
    }

    @Test
    public void runTestInput12() {
        Assert.assertEquals(run(12), 3);
    }

    @Test
    public void runTestInput23() {
        Assert.assertEquals(run(23), 2);
    }

    @Test
    public void runTestInput1024() {
        Assert.assertEquals(run(1024), 31);
    }

    @Test
    public void runInput() {
        int steps = run(368078);
        System.out.println("steps = " + steps);
    }

    public int run(int square) {
        for (int i = 1; i <= square; i++) {
            // move in direction
            System.out.println(direction.toString().substring(0, 1) + ":" + i);
            if (i == 1) {
                continue;
            }

            switch (direction) {
                case NORTH:
                    y += 1;
                    if (y > maxNorth) {
                        maxNorth = y;
                        changeDirection();
                    }
                    break;
                case EAST:
                    x += 1;
                    if (x > maxEast) {
                        maxEast = x;
                        changeDirection();
                    }
                    break;
                case SOUTH:
                    y -= 1;
                    if (y < maxSouth) {
                        maxSouth = y;
                        changeDirection();
                    }
                    break;
                case WEST:
                    x -= 1;
                    if (x < maxWest) {
                        maxWest = x;
                        changeDirection();
                    }
                    break;
            }

            System.out.println(x + "," + y);
        }

        // calculate manhattan distance
        int steps = Math.abs(x) + Math.abs(y);

        return steps;
    }
}
