package year2017;

import org.testng.Assert;
import org.testng.annotations.Test;
import util.Utils;

import java.io.*;
import java.util.*;

public class Day7 {

    @Test
    public void findRootServer() throws IOException {
        File file = Utils.getFile("year2017/Day7-input.txt");

        // names list
        List<Server> servers = new ArrayList<>();
        List<String> rightNames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                Server server = parseLine(line);
                servers.add(server);
                // collect all sub-server names
                rightNames.addAll(server.subs);
            }
        }

        // part 1
        // check all server names to see if they are subs
        for (Server server : servers) {
            if (!rightNames.contains(server.name)) {
                System.out.println("server = " + server.name);
                break;
            }
        }

        // part2
        // create server dict for easier lookup
        Map<String, Server> map = new HashMap<>();
        for (Server server : servers) {
            map.put(server.name, server);
        }

        // expand sub servers
        for (Server server : servers) {
            List<Server> list = new ArrayList<>();
            for (String sub : server.subs) {
                list.add(map.get(sub));
            }
            server.servers = list;
        }

        // get root server
        Server root = map.get("vtzay");

        // find servers with unbalanced children
        findBalanced(root);

        // found last unbalanced server
        Server unbalancedServer = stack.pop();
        System.out.println("unbalancedServer = " + unbalancedServer);

        findAdjustedSize(unbalancedServer);
    }

    private void findAdjustedSize(Server unbalancedServer) {
        // we know child servers are balanced

        // find common load size
        int commonSize = 0;
        Set<Number> set = new HashSet<>();
        for (Server childServer : unbalancedServer.servers) {
            int thisSize = childServer.calcLoadSize();
            System.out.println("childServer = " + childServer);
            System.out.println("thisSize = " + thisSize);

            boolean add = set.add(thisSize);
            if (!add) {
                // this is the common size
                System.out.println("common size = " + thisSize);
                commonSize = thisSize;
            }
        }

        // find off-balance diff from common size
        for (Server childServer : unbalancedServer.servers) {
            int thisSize = childServer.calcLoadSize();
            if (thisSize != commonSize) {
                // found our server to adjust
                System.out.println("server to adjust = " + childServer);
                int diff = thisSize - commonSize;
                System.out.println("adjust by diff = " + diff);
                int newValue = childServer.size - diff;
                System.out.println("server should have new value: " + newValue);
            }
        }
    }

    Stack<Server> stack = new Stack<>();

    public void findBalanced(Server server) {
        // look at children of this server to see if they are balanced
        // report on balance of server
        int lastSize = -1;
        for (Server childServer : server.servers) {
            int thisSize = childServer.calcLoadSize();
            if (lastSize != -1 && thisSize != lastSize) {
                // this server is an unbalanced server
                stack.push(server);
            }
            lastSize = thisSize;
        }

        // recurse children
        for (Server childServer : server.servers) {
            findBalanced(childServer);
        }
    }

    @Test
    public void testParseLine() {
        Server server = parseLine("dihjv (2158) -> gausx, ncdmp, hozgrub");
        Assert.assertEquals(server.subs.size(), 3);
        Assert.assertEquals(server.name, "dihjv");
        Assert.assertEquals(server.size, 2158);
    }

    public Server parseLine(String line) {
        List<String> list = new ArrayList<>();

        Server server = new Server();
        String[] split = line.split("\\s");
        for (int i = 0; i < split.length; i++) {
            String s = split[i].replaceAll(",", "");
            if (s.substring(0, 1).matches("\\w")) {
                if (i == 0) {
                    server.name = s;
                } else {
                    list.add(s);
                }
            } else if (s.startsWith("("))  {
                s = s.substring(1, s.length() - 1);
                server.size = Integer.parseInt(s);
            }
            server.subs = list;
        }

        return server;
    }

    class Server {
        public String name;
        public List<String> subs;
        public int size;

        public List<Server> servers = new ArrayList<>();

        public int calcLoadSize() {
            int load = 0;

            for (Server server : servers) {
                load += server.calcLoadSize();
            }

            return load + size;
        }

        @Override
        public String toString() {
            return "Server{" +
                    "name='" + name + '\'' +
                    ", size=" + size +
                    '}';
        }
    }
}
