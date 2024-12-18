import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {

    private static Map<String, Map<Integer, BigInteger>> memo = new HashMap<>();
    static char[][] grid;
    static int atrow=0;
    static int atcol=0;
    static int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static long ra;
    static long rb;
    static long rc;
    static BigInteger raval = BigInteger.ONE;
    static BigInteger bra;
    static BigInteger brb;
    static BigInteger brc;
    static int[] program;

    public static List<String> read(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("in/" + filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return lines;
        }
    }

    public static char[][] readBlock(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("in/" + filename))) {
            List<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                lines.add(line.trim());
            }

            char[][] grid = new char[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                grid[i] = lines.get(i).toCharArray();
            }

            return grid;
        } catch (IOException e) {
            e.printStackTrace();
            return new char[0][];
        }
    }

    public static int oneA(String filename) {
        List<String> list = read(filename);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (String s : list) {
            String [] sp = s.split("\\s+");
            left.add(Integer.parseInt(sp[0]));
            right.add(Integer.parseInt(sp[1]));
        }
        Collections.sort(left);
        Collections.sort(right);
        int sum = 0;
        for (int i = 0; i < left.size(); i ++) {
            int dist = left.get(i) - right.get(i);
            if (dist < 0) {
                dist *= -1;
            }
            sum += dist;
        }
        return sum;
    }

    public static int oneB(String filename) {
        List<String> list = read(filename);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (String s : list) {
            String [] sp = s.split("\\s+");
            left.add(Integer.parseInt(sp[0]));
            right.add(Integer.parseInt(sp[1]));
        }

        int sum = 0;
        for (int i : left) {
            int m = i * Collections.frequency(right, i);
            sum += m;
        }
        return sum;
    }

    public static int twoA(String filename) {
        List<String> list = read(filename);
        int safei = 0;
        int safed = 0;
        for (String l : list) {
            int subi = 0;
            int subd = 0;
            String [] row = l.split(" ");
            for (int i=1; i<row.length; i++) {
                int j = Integer.parseInt(row[i-1]);
                int k = Integer.parseInt(row[i]);
                int diff = k - j;
                if (diff > 0 && diff < 4) {
                    subi++;
                } else if (diff < 0 && diff > -4) {
                    subd ++;
                }
            }
            if (subd == 0) {
                safei++;
            } else if (subi == 0) {
                safed++;
            }
        }
        return (safei + safed);
    }

    public static boolean checkrow (String [] row) {
        int subi = 0;
        int subd = 0;
        int subunsafe = 0;
        for (int i=1; i<row.length; i++) {
            int j = Integer.parseInt(row[i-1]);
            int k = Integer.parseInt(row[i]);
            int diff = k - j;
            if (diff > 0 && diff < 4) {
                subi++;
            } else if (diff < 0 && diff > -4) {
                subd ++;
            }
            else {
                subunsafe++;
            }
        }
        if (subunsafe > 0) {
            return false;
        } else return subd == 0 || subi == 0;
    }

    public static int twoB(String filename) {
        List<String> list = read(filename);
        int safei = 0;
        for (String l : list) {
            String[] row = l.split(" ");

            if (checkrow(row)) {
                safei++;
            } else {
                String[][] subarrays = new String[row.length][];
                for (int i = 0; i < row.length; i++) {
                    List<String> subarray = new ArrayList<>(Arrays.asList(row));
                    subarray.remove(i);
                    subarrays[i] = subarray.toArray(new String[0]);
                }
                for (String[] subrow : subarrays) {
                    if (checkrow(subrow)) {
                        safei++;
                        break;
                    }
                }
            }
        }
        return safei;
    }

    public static boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    public static int threeA(String filename) {
        List<String> list = read(filename);
        int sum = 0;
        for (String line : list){
            for (int i=0; i<line.length(); i++) {
                if (line.charAt(i) == 'm' && line.charAt(i+1) == 'u' && line.charAt(i+2) == 'l' && line.charAt(i+3) == '('){
                    int num1 = 0;
                    int num2 = 0;
                    int j = i+4;
                    while (isNum(line.charAt(j))) {
                        num1 *= 10;
                        num1 += Integer.parseInt(line.charAt(j)+"");
                        j++;
                    }
                    if (line.charAt(j) == ',') {
                        j++;
                    } else {
                        continue;
                    }
                    while (isNum(line.charAt(j))) {
                        num2 *= 10;
                        num2 += Integer.parseInt(line.charAt(j)+"");
                        j++;
                    }
                    if (line.charAt(j) == ')') {
                        int num = num1 * num2;
                        sum += num;
                    }
                }
            }
        }

        return sum;
    }

    public static int threeB(String filename) {
        List<String> list = read(filename);
        boolean mul = true;
        int sum = 0;
        for (String line : list){
            for (int i=0; i<line.length(); i++) {
                if (line.charAt(i) == 'd' && line.charAt(i+1) == 'o' && line.charAt(i+2) == '(' && line.charAt(i+3) == ')') {
                    mul = true;
                }
                if (line.charAt(i) == 'd' && line.charAt(i+1) == 'o' && line.charAt(i+2) == 'n' && line.charAt(i+3) == '\'' && line.charAt(i+4) == 't' && line.charAt(i+5) == '(' && line.charAt(i+6) == ')') {
                    mul = false;
                }
                if (mul && line.charAt(i) == 'm' && line.charAt(i+1) == 'u' && line.charAt(i+2) == 'l' && line.charAt(i+3) == '('){
                    int num1 = 0;
                    int num2 = 0;
                    int j = i+4;
                    while (isNum(line.charAt(j))) {
                        num1 *= 10;
                        num1 += Integer.parseInt(line.charAt(j)+"");
                        j++;
                    }
                    if (line.charAt(j) == ',') {
                        j++;
                    } else {
                        continue;
                    }
                    while (isNum(line.charAt(j))) {
                        num2 *= 10;
                        num2 += Integer.parseInt(line.charAt(j)+"");
                        j++;
                    }
                    if (line.charAt(j) == ')') {
                        int num = num1 * num2;
                        sum += num;
                    }
                }
            }
        }

        return sum;
    }

    public static int fourA(String filename) {
        char[][] list = readBlock(filename);
        int sum = 0;
        // check hor
        for (char [] line : list) {
            for (int i=0; i<line.length; i++) {
                if (i < line.length-2 && line[i] == 'X' && line[i+1] == 'M' && line[i+2] == 'A' && line[i+3] == 'S') {
                    sum++;
                }
                if (i > 2 && line[i] == 'X' && line[i-1] == 'M' && line[i-2] == 'A' && line[i-3] == 'S') {
                    sum++;
                }
            }
        }

        // check vert
        for (int col = 0; col < list[0].length; col++) {
            for (int row = 0; row < list.length; row++) {
                if (row < list[0].length-3 && list[row][col] == 'X' && list[row+1][col] == 'M' && list[row+2][col] == 'A' && list[row+3][col] == 'S') {
                    sum++;
                }
                if (row > 2 && list[row][col] == 'X' && list[row-1][col] == 'M' && list[row-2][col] == 'A' && list[row-3][col] == 'S') {
                    sum++;
                }

                // diag
                if (col < list[0].length-3 && row < list.length-3 && list[row][col] == 'X' && list[row+1][col+1] == 'M' && list[row+2][col+2] == 'A' && list[row+3][col+3] == 'S') {
                    sum++;
                }
                if (col > 2 && row > 2 && list[row][col] == 'X' && list[row-1][col-1] == 'M' && list[row-2][col-2] == 'A' && list[row-3][col-3] == 'S') {
                    sum++;
                }

                // anti diag
                if (col < list[0].length-3 && row > 2 && list[row][col] == 'X' && list[row-1][col+1] == 'M' && list[row-2][col+2] == 'A' && list[row-3][col+3] == 'S') {
                    sum++;
                }
                if (col > 2 && row < list.length-3 && list[row][col] == 'X' && list[row+1][col-1] == 'M' && list[row+2][col-2] == 'A' && list[row+3][col-3] == 'S') {
                    sum++;
                }
            }
        }

        return sum;
    }

    public static int fourB(String filename) {
        char[][] list = readBlock(filename);
        int sum = 0;

        for (int col = 1; col < list[0].length - 1; col++) {
            for (int row = 1; row < list.length - 1; row++) {
                if (list[row][col] == 'A' && ((list[row+1][col+1] == 'M' && list[row-1][col-1] == 'S') || (list[row+1][col+1] == 'S' && list[row-1][col-1] == 'M')) && ((list[row+1][col-1] == 'M' && list[row-1][col+1] == 'S') || (list[row+1][col-1] == 'S' && list[row-1][col+1] == 'M'))) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public static void print(String [] a) {
        for (String s : a) {
            System.out.println(s);
        }
    }

    public static void print(char [][] a) {
        for (int y=0; y<a.length; y++) {
            for (int x=0; x<a[y].length; x++) {
                System.out.print(a[x][y]);
            }
            System.out.println();
        }
    }

    public static void print(List<String> a) {
        for (String b : a) {
            System.out.print(b + " ");
        }
        System.out.println();
    }

    public static int fiveA(String filename) {
        List<String> list = read(filename);
        List<String[]> rules = new ArrayList<>();
        List<String[]> pages = new ArrayList<>();
        int sum = 0;
        boolean tempRead = false;
        for (String l : list) {
            if (l.isEmpty() || l.isBlank()) {
                tempRead = true;
                continue;
            }
            if (!tempRead) {
                rules.add(l.split("\\|"));
            } else {
                pages.add(l.split(","));
            }
        }

        for (String [] page : pages) {
            boolean rulespass = true;
            for (String [] rule : rules) {
                int index1 = Arrays.asList(page).indexOf(rule[0]);
                int index2 = Arrays.asList(page).indexOf(rule[1]);
                if (index1 == -1 || index2 == -1) {
                    continue;
                }
                if (index2 < index1) {
                    rulespass = false;
                    break;
                }
            }
            if (rulespass) {
                double mid = page.length;
                mid = mid/2;
                int m = (int) mid;
                sum += Integer.parseInt(page[m]);
            }
        }

        return sum;
    }

    public static int fiveB(String filename) {
        List<String> list = read(filename);
        List<String[]> rules = new ArrayList<>();
        List<String[]> pages = new ArrayList<>();
        List<String[]> altpages = new ArrayList<>();
        int sum = 0;
        boolean tempRead = false;
        for (String l : list) {
            if (l.isEmpty() || l.isBlank()) {
                tempRead = true;
                continue;
            }
            if (!tempRead) {
                rules.add(l.split("\\|"));
            } else {
                pages.add(l.split(","));
            }
        }

        for (String [] page : pages) {
            for (String [] rule : rules) {
                int index1 = Arrays.asList(page).indexOf(rule[0]);
                int index2 = Arrays.asList(page).indexOf(rule[1]);
                if (index1 == -1 || index2 == -1) {
                    continue;
                }
                if (index2 < index1) {
                    altpages.add(page);
                    break;
                }
            }
        }

        for (String [] page : altpages) {
            List<String> altpage = new ArrayList<>(Arrays.asList(page));
            boolean go = true;
            while (go) {
                go = false;
                for (String [] rule : rules) {
                    int index1 = altpage.indexOf(rule[0]);
                    int index2 = altpage.indexOf(rule[1]);
                    if (index1 == -1 || index2 == -1) {
                        continue;
                    }
                    if (index2 < index1) {
                        go = true;
                        String r = altpage.remove(index2);
                        if (index1 == altpage.size()) {
                            altpage.add(r);
                        } else {
                            altpage.add(index1, r);
                        }
                    }
                }
            }
            sum += Integer.parseInt(altpage.get(page.length/2));
        }

        return sum;
    }

    public static int sixA(String filename) {
        char[][] list = readBlock(filename);
        int sum = 0;
        int x = 0;
        int y = 0;

        for (int row = 0; row < list.length ; row++) {
            for (int col = 0; col < list[row].length ; col++) {
                if (list[row][col] == '^') {
                    list[row][col] = 'X';
                    sum++;
                    x = row;
                    y = col;
                    break;
                }
            }
            if (sum > 0) {
                break;
            }
        }

        int[][]dirs = {{-1,0}, {0,1}, {1, 0}, {0, -1}};
        int dir = 0;

        while (list[x][y] != '#') {
            if (list[x][y] == '.') {
                sum++;
                list[x][y] = 'X';
            }
            if (x+dirs[dir][0] < 0 || x+dirs[dir][0] > list.length || y+dirs[dir][1] < 0 || y+dirs[dir][1] > list[0].length) {
                return sum;
            }
            try {
                while (list[x + dirs[dir][0]][y + dirs[dir][1]] == '#') {
                    dir = (dir + 1) % 4;
                    if (x + dirs[dir][0] < 0 || x + dirs[dir][0] > list.length || y + dirs[dir][1] < 0 || y + dirs[dir][1] > list[0].length) {
                        return sum;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return sum;
            }
            x = x+dirs[dir][0];
            y = y+dirs[dir][1];
        }

        return sum;
    }

    public static boolean createsLoop(char[][] map, int startRow, int startCol, int startDir) {
        int rows = map.length, cols = map[0].length;
        Set<String> visited = new HashSet<>();
        int r = startRow, c = startCol, dir = startDir;
        int[][]directions = {{-1,0}, {0,1}, {1, 0}, {0, -1}};

        while (true) {
            String state = r + "," + c + "," + dir;
            if (visited.contains(state)) {
                return true; // Loop detected
            }
            visited.add(state);

            int nextR = r + directions[dir][0];
            int nextC = c + directions[dir][1];
            if (nextR < 0 || nextR >= rows || nextC < 0 || nextC >= cols) {
                return false;
            }
            if (map[nextR][nextC] == '#') {
                dir = (dir + 1) % 4;
            } else {
                // Move forward
                r = nextR;
                c = nextC;
            }
        }
    }

    public static int sixB(String filename) {
        char[][] list = readBlock(filename);
        int sum = 0;
        int x = 0;
        int y = 0;
        int loops = 0;

        for (int row = 0; row < list.length ; row++) {
            for (int col = 0; col < list[row].length ; col++) {
                if (list[row][col] == '^') {
                    list[row][col] = 'X';
                    sum++;
                    x = row;
                    y = col;
                    break;
                }
            }
            if (sum > 0) {
                break;
            }
        }

        int dir = 0;

        for (int row = 0; row < list.length ; row++) {
            for (int col = 0; col < list[row].length ; col++) {
                if (list[row][col] == '.') {
                    list[row][col] = '#';
                    if (createsLoop(list, x, y, dir)) {
                        loops++;
                    }
                    list[row][col] = '.';
                }
            }
        }
        return loops;
    }

    public static int calc(int a, int b, int op) {
        return switch (op) {
          case 0 -> a+b;
          case 1 -> a*b;
//          case 2 -> a-b;
//          case 3 -> a/b;
            default -> throw new IllegalStateException("Unexpected value: " + op);
        };

    }

    public static boolean evaluateOperators(BigInteger[] numbers, int index, BigInteger currentValue, BigInteger target) {
        if (index == numbers.length - 1) {
            return currentValue.equals(target);
        }

        BigInteger nextValue = numbers[index + 1];

        if (evaluateOperators(numbers, index + 1, currentValue.add(nextValue), target)) {
            return true;
        }

        if (evaluateOperators(numbers, index + 1, currentValue.multiply(nextValue), target)) {
            return true;
        }

        return false;
    }

    public static BigInteger sevenA(String filename) {
        List<String> list = read(filename);
        BigInteger sum = new BigInteger("0");
        for (String line : list) {
            String[] parts = line.split(":");
            BigInteger ans = new BigInteger(parts[0].trim());
            String[] intStrings = parts[1].trim().split(" ");
            BigInteger[] intArr = Arrays.stream(intStrings).map(BigInteger::new).toArray(BigInteger[]::new);
            if (evaluateOperators(intArr, 0, intArr[0], ans)) {
                sum = sum.add(new BigInteger(parts[0]));
            }

        }

        return sum;
    }

    public static int sevenB(String filename) {
        List<String> list = read(filename);

        return 0;
    }

    public static Map<Character, List<int[]>> findAntennas(char[][] list) {
        Map<Character, List<int[]>> antennas = new HashMap<>();
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                if (list[i][j] != '.') {
                    if (!antennas.containsKey(list[i][j])) {
                        List<int[]> coord = new ArrayList<>();
                        coord.add(new int[]{i, j});
                        antennas.put(list[i][j], coord);
                    } else {
                        antennas.get(list[i][j]).add(new int[]{i, j});
                    }
                }
            }
        }
        return antennas;
    }

    public static boolean checkBound(int[] c, char[][] grid) {
        return c[0] >= 0 && c[0] < grid.length && c[1] >= 0 && c[1] < grid[0].length;
    }

    public static int eightA(String filename) {
        char[][] list = readBlock(filename);
        Map<Character, List<int[]>> antennas = findAntennas(list);
        Set<String> anti = new HashSet<>();
        for (Character freq : antennas.keySet()) {
            List<int[]> positions = antennas.get(freq);
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i+1; j < positions.size(); j++) {
                    int x1 = positions.get(i)[0];
                    int y1 = positions.get(i)[1];
                    int x2 = positions.get(j)[0];
                    int y2 = positions.get(j)[1];
                    int ax1 = 2*x1-x2;
                    int ay1 = 2*y1-y2;
                    int ax2 = 2*x2-x1;
                    int ay2 = 2*y2-y1;

                    if ((ax1 >=0 && ax1 < list.length && ay1 >=0 && ay1 < list[0].length)) {
                        list[ax1][ay1] = '#';
                        String coord = ax1+","+ay1;
                        anti.add(coord);
                    }
                    if ((ax2 >= 0 && ax2 < list.length && ay2 >= 0 && ay2 < list[0].length)) {
                        list[ax2][ay2] = '#';
                        String coord = ax2+","+ay2;
                        anti.add(coord);
                    }
                }
            }
        }

//        print(list);
        return anti.size();
    }

    public static int eightB(String filename) {
        char[][] list = readBlock(filename);
        Map<Character, List<int[]>> antennas = findAntennas(list);
        Set<String> anti = new HashSet<>();
        for (Character freq : antennas.keySet()) {
            List<int[]> positions = antennas.get(freq);
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i+1; j < positions.size(); j++) {
                    int x1 = positions.get(i)[0];
                    int y1 = positions.get(i)[1];
                    int x2 = positions.get(j)[0];
                    int y2 = positions.get(j)[1];
                    int ax1 = 2*x1-x2;
                    int ay1 = 2*y1-y2;
                    int ax2 = 2*x2-x1;
                    int ay2 = 2*y2-y1;

                    while (ax1 >=0 && ax1 < list.length && ay1 >=0 && ay1 < list[0].length) {
                        list[ax1][ay1] = '#';
                        String coord = ax1+","+ay1;
                        anti.add(coord);
                        ax1 = ax1+x1-x2;
                        ay1 = ay1+y1-y2;
                    }
                    while (ax2 >= 0 && ax2 < list.length && ay2 >= 0 && ay2 < list[0].length) {
                        list[ax2][ay2] = '#';
                        String coord = ax2+","+ay2;
                        anti.add(coord);
                        ax2 = ax2+x2-x1;
                        ay2 = ay2+y2-y1;
                    }
                    String coord = x1+","+y1;
                    anti.add(coord);
                    list[x1][y1] = '#';
                    coord = x2+","+y2;
                    anti.add(coord);
                    list[x2][y2] = '#';
                }
            }
        }

//        print(list);
        return anti.size();
    }

    public static BigInteger nineA(String filename) {
        List<String> list = read(filename);
        String input = list.get(0);
        List<BigInteger> round1 = new ArrayList<>();
        boolean data = true;
        BigInteger id = BigInteger.ZERO;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (data) {
                for (int j = 0; j < Integer.parseInt(c + ""); j++) {
                    round1.add(id);
                }
                id = id.add(BigInteger.ONE);
                data = !data;
            } else {
                for (int j = 0; j < Integer.parseInt(c + ""); j++) {
                    round1.add(new BigInteger("-1"));
                }
                data = !data;
            }
        }

        List<BigInteger> round2 = new ArrayList<>();
        int index = round1.size()-1;
        for (int i = 0; i <= index; i++) {
            if (!round1.get(i).equals(new BigInteger("-1"))) {
                round2.add(round1.get(i));
            } else {
                while (round1.get(index).equals(new BigInteger("-1"))) {
                    index--;
                }
                round2.add(round1.get(index));
                index--;
            }
        }

        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < round2.size(); i++) {
            BigInteger prod = BigInteger.valueOf(i).multiply(round2.get(i));
            sum = sum.add(prod);
        }
        return sum;
    }

    public static BigInteger nineB(String filename) {
        List<String> list = read(filename);
        String input = list.get(0);
        List<BigInteger> round1 = new ArrayList<>();
        boolean data = true;
        BigInteger id = BigInteger.ZERO;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (data) {
                for (int j = 0; j < Integer.parseInt(c + ""); j++) {
                    round1.add(id);
                }
                id = id.add(BigInteger.ONE);
                data = !data;
            } else {
                for (int j = 0; j < Integer.parseInt(c + ""); j++) {
                    round1.add(new BigInteger("-1"));
                }
                data = !data;
            }
        }

        int typecnt = 0;
        BigInteger type = new BigInteger("-2");
        for (int i = round1.size()-1; i >= 0; i--) {
            if (round1.get(i).equals(type)) {
                typecnt++;
            } else {
                if (!type.equals(new BigInteger("-2"))) {
                    int mincnt = 0;
                    int lim = i+1;
                    for (int j=0; j<lim; j++) {
                        if (round1.get(j).equals(new BigInteger("-1"))) {
                            mincnt++;
                        } else {
                            mincnt = 0;
                        }
                        if (mincnt == typecnt) {
                            for (int k=0; k<typecnt; k++) {
                                round1.set(j-k, type);
                                round1.set(i+k+1, new BigInteger("-1"));
                            }
                            break;
                        }
                    }
                }

                typecnt = 1;
                type = round1.get(i);

            }
        }

        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < round1.size(); i++) {
            if (round1.get(i).equals(new BigInteger("-1"))) {continue;}
            BigInteger prod = BigInteger.valueOf(i).multiply(round1.get(i));
            sum = sum.add(prod);
        }
        return sum;
    }

//    public static int tenA(String filename) {
//        char[][] list = readBlock(filename);
//        List<Coordinate>[] coords = new List[10];
//        coords[0] = new ArrayList<>();
//        for (int i = 0; i < list.length; i++) {
//            for (int j = 0; j < list[i].length; j++) {
//                if (list[i][j] == '0') {
//                    Coordinate c = new Coordinate(i, j);
//                    c.origin = c;
//                    coords[0].add(c);
//                }
//            }
//        }
////        System.out.println("origins "+coords[0].size());
//        int score = 1;
//        while (score < 10) {
//            coords[score] = new ArrayList<>();
//            for (Coordinate coord : coords[score-1]) {
//                if (coord.x > 0 && Integer.parseInt(list[coord.x-1][coord.y]+"") == score) {
//                    Coordinate c = new Coordinate(coord.x-1, coord.y);
//                    c.origin = coord.origin;
//                    if (score == 9) {
//                        c.destination = c;
//                    }
//                    coords[score].add(c);
//                }
//                if (coord.y > 0 && Integer.parseInt(list[coord.x][coord.y-1]+"") == score) {
//                    Coordinate c = new Coordinate(coord.x, coord.y-1);
//                    c.origin = coord.origin;
//                    if (score == 9) {
//                        c.destination = c;
//                    }
//                    coords[score].add(c);
//                }
//                if (coord.x < list.length-1 && Integer.parseInt(list[coord.x+1][coord.y]+"") == score) {
//                    Coordinate c = new Coordinate(coord.x+1, coord.y);
//                    c.origin = coord.origin;
//                    if (score == 9) {
//                        c.destination = c;
//                    }
//                    coords[score].add(c);
//                }
//                if (coord.y < list.length-1 && Integer.parseInt(list[coord.x][coord.y+1]+"") == score) {
//                    Coordinate c = new Coordinate(coord.x, coord.y+1);
//                    c.origin = coord.origin;
//                    if (score == 9) {
//                        c.destination = c;
//                    }
//                    coords[score].add(c);
//                }
//            }
////            System.out.println(coords[score].get(0).x + "," + coords[score].get(0).y);
//            score++;
//        }
//        Set<String> fin = new HashSet<>();
//        for (Coordinate coord : coords[9]) {
//            String id = coord.origin.x + "," + coord.origin.y + "," + coord.destination.x + "," + coord.destination.y;
////            System.out.println(id);
//            fin.add(id);
//        }
//        return fin.size();
//    }

//    public static int tenB(String filename) {
//        char[][] list = readBlock(filename);
//        List<Coordinate>[] coords = new List[10];
//        coords[0] = new ArrayList<>();
//        for (int i = 0; i < list.length; i++) {
//            for (int j = 0; j < list[i].length; j++) {
//                if (list[i][j] == '0') {
//                    Coordinate c = new Coordinate(i, j);
//                    c.origin = c;
//                    coords[0].add(c);
//                }
//            }
//        }
//        int score = 1;
//        while (score < 10) {
//            coords[score] = new ArrayList<>();
//            for (Coordinate coord : coords[score-1]) {
//                if (coord.x > 0 && Integer.parseInt(list[coord.x-1][coord.y]+"") == score) {
//                    Coordinate c = new Coordinate(coord.x-1, coord.y);
//                    c.origin = coord.origin;
//                    if (score == 9) {
//                        c.destination = c;
//                    }
//                    coords[score].add(c);
//                }
//                if (coord.y > 0 && Integer.parseInt(list[coord.x][coord.y-1]+"") == score) {
//                    Coordinate c = new Coordinate(coord.x, coord.y-1);
//                    c.origin = coord.origin;
//                    if (score == 9) {
//                        c.destination = c;
//                    }
//                    coords[score].add(c);
//                }
//                if (coord.x < list.length-1 && Integer.parseInt(list[coord.x+1][coord.y]+"") == score) {
//                    Coordinate c = new Coordinate(coord.x+1, coord.y);
//                    c.origin = coord.origin;
//                    if (score == 9) {
//                        c.destination = c;
//                    }
//                    coords[score].add(c);
//                }
//                if (coord.y < list.length-1 && Integer.parseInt(list[coord.x][coord.y+1]+"") == score) {
//                    Coordinate c = new Coordinate(coord.x, coord.y+1);
//                    c.origin = coord.origin;
//                    if (score == 9) {
//                        c.destination = c;
//                    }
//                    coords[score].add(c);
//                }
//            }
//            score++;
//        }
////        Set<String> fin = new HashSet<>();
////        for (Coordinate coord : coords[9]) {
////            String id = coord.origin.x + "," + coord.origin.y + "," + coord.destination.x + "," + coord.destination.y;
////            fin.add(id);
////        }
////        return fin.size();
//        return coords[9].size();
//    }

    public static int elevenA(String filename) {
        List<String> list = read(filename);

        List<String> nums = new ArrayList<>(Arrays.asList(list.get(0).split(" ")));

//        print(nums);

        int loops = 25;

        for(int i=0; i<loops; i++) {
            for (int index=0; index<nums.size(); index++) {
                if (nums.get(index).equals("0")) {
                    nums.remove(index);
                    nums.add(index, "1");
                } else if (nums.get(index).length()%2 == 0) {
                    BigInteger a = new BigInteger(nums.get(index).substring(0, nums.get(index).length()/2));
                    BigInteger b = new BigInteger(nums.get(index).substring(nums.get(index).length()/2));
                    nums.remove(index);
                    nums.add(index, b.toString());
                    nums.add(index, a.toString());
                    index++;
                } else {
                    BigInteger a = new BigInteger(nums.remove(index)).multiply(new BigInteger("2024"));
                    nums.add(index, a.toString());
                }
            }
//            print(nums);
        }

        return nums.size();
    }

    public static int elevenB(String filename) {
        List<String> list = read(filename);

        List<String> nums = new ArrayList<>(Arrays.asList(list.get(0).split(" ")));

//        print(nums);

        int loops = 75;

        for(int i=0; i<loops; i++) {
            System.out.println(i);
            for (int index=0; index<nums.size(); index++) {
                if (nums.get(index).equals("0")) {
                    nums.remove(index);
                    nums.add(index, "1");
                } else if (nums.get(index).length()%2 == 0) {
                    BigInteger a = new BigInteger(nums.get(index).substring(0, nums.get(index).length()/2));
                    BigInteger b = new BigInteger(nums.get(index).substring(nums.get(index).length()/2));
                    nums.remove(index);
                    nums.add(index, b.toString());
                    nums.add(index, a.toString());
                    index++;
                } else {
                    BigInteger a = new BigInteger(nums.remove(index)).multiply(new BigInteger("2024"));
                    nums.add(index, a.toString());
                }
            }
//            print(nums);
        }

        return nums.size();
    }

    public static BigInteger count(BigInteger  num, int loops) {
        String key = num.toString() + ":" + loops;
        if (memo.containsKey(key) && memo.get(key).containsKey(loops)) {
            return memo.get(key).get(loops);
        }

        if (loops == 0) {
            return BigInteger.ONE;
        }

        BigInteger result;
        if (num.equals(BigInteger.ZERO)) {
            result = count(BigInteger.ONE, loops-1);
        } else if (num.toString().length() %2 ==0) {
            String numstring = num.toString();
            BigInteger a = new BigInteger(numstring.substring(0, numstring.length()/2));
            BigInteger b = new BigInteger(numstring.substring(numstring.length()/2));
            result =  count(a, loops-1).add(count(b, loops-1));
        } else {
            result = count(num.multiply(new BigInteger("2024")), loops-1);
        }

        memo.computeIfAbsent(key, _ -> new HashMap<>()).put(loops, result);

        return result;
    }

    public static BigInteger elevenC(String filename) {
        List<String> list = read(filename);
        List<String> nums = Arrays.asList(list.get(0).split(" "));

        int loops = 75;
        BigInteger sum = BigInteger.ZERO;

        memo.clear();

        for (String num : nums) {
            sum = sum.add(count(new BigInteger(num), loops));
        }

        return sum;
    }

    public static void findRegion(char[][] list, int x, int xmax, int y, int ymax, char type, boolean [][] visited, List<Coordinate> region) {
        if (x < 0 || x > xmax-1 || y < 0 || y > ymax-1 || visited[x][y] || list[x][y] != type) {
            return;
        }
        visited[x][y] = true;
        region.add(new Coordinate(x, y));
        findRegion(list, x-1, xmax, y, ymax, type, visited, region);
        findRegion(list, x+1, xmax, y, ymax, type, visited, region);
        findRegion(list, x, xmax, y-1, ymax, type, visited, region);
        findRegion(list, x, xmax, y+1, ymax, type, visited, region);
    }

    public static int calcPerimiter(List<Coordinate> region, char[][]list) {
        int perimiter = 0;
        char rtype = list[region.getFirst().x][region.getFirst().y];
        for (Coordinate c : region) {
            if (c.x == 0 || list[c.x-1][c.y] != rtype) {
                perimiter++;
            }
            if (c.y == 0 || list[c.x][c.y-1] != rtype) {
                perimiter++;
            }
            if (c.x == list.length-1 || list[c.x+1][c.y] != rtype) {
                perimiter++;
            }
            if (c.y == list[0].length-1 || list[c.x][c.y+1] != rtype) {
                perimiter++;
            }
        }
        return perimiter;
    }

    public static int countSides(List<Coordinate> region, char[][]list) {
        List<List<Integer>> hor = new ArrayList<>();
        List<List<Integer>> ver = new ArrayList<>();

        for (Coordinate c : region) {
            int x = c.x, y = c.y;
            if (y==0 || list[x][y-1] != list[x][y]) {

            }
        }
        return hor.size()+ver.size();
    }

    public static int twelveA(String filename) {
        char [][] list = readBlock(filename);
        int rows = list.length;
        int cols = list[0].length;
        boolean[][] visited = new boolean[rows][cols];
        List<List<Coordinate>> regions = new ArrayList<>();

        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                if (!visited[i][j]) {
                    char type = list[i][j];
                    List<Coordinate> region = new ArrayList<>();
                    findRegion(list, i, rows, j, cols, type, visited, region);
                    regions.add(region);

//                    int area;
//                    int sides;
//                    sum +=
                }
            }
        }

        return 0;//sum;
    }

    public static int twelveB(String filename) {
        char [][] list = readBlock(filename);
        int rows = list.length;
        int cols = list[0].length;
        boolean[][] visited = new boolean[rows][cols];
        List<List<Coordinate>> regions = new ArrayList<>();

        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                if (!visited[i][j]) {
                    char type = list[i][j];
                    List<Coordinate> region = new ArrayList<>();
                    findRegion(list, i, rows, j, cols, type, visited, region);
                    regions.add(region);
                }
            }
        }

        int sum = 0;
        for (List<Coordinate> region : regions) {
            sum += calcPerimiter(region, list) * region.size();
        }
        return sum;
    }

    public static long getPrize(long ax, long ay, long bx, long by, long px, long py) {
        Queue<long[]> q = new PriorityQueue<>((a, b) -> Math.toIntExact(a[2] - b[2]));
        Set<String> v = new HashSet<>();

        q.offer(new long[]{0,0,0,0,0});
        v.add("0,0");

        while(!q.isEmpty()) {
            long[] cur = q.poll();
            long x = cur[0];
            long y = cur[1];
//            System.out.println("x: " + x + " y: " + y);
            long total = cur[2];
            long buta=cur[3], butb=cur[4];

            if (x==px && y==py) {
                return total;
            } else if (x > px || y > py) {
                continue;
            }
            long newAX = x+ax;
            long newAY = y+ay;
            String newPosA = newAX+","+newAY;
            long newTotA = total+3;
            buta++;

            if (!v.contains(newPosA)) {
                q.offer(new long[]{newAX,newAY,newTotA, buta, butb});
                v.add(newPosA);
            }

            long newBX = x+bx;
            long newBY = y+by;
            String newPosB = newBX+","+newBY;
            long newTotB = total+1;
            buta--;
            butb++;
            if (!v.contains(newPosB)) {
                q.offer(new long[]{newBX,newBY,newTotB, buta, butb});
                v.add(newPosB);
            }
        }

        return 0;
    }

//    public static long getPrizeB(long ax, long ay, long bx, long by, long px, long py) {
//        GCD gcd = gcd(ax, bx);
//        if (px % gcd.x != 0 || py % gcd.y != 0) {
//
//        }
//        return 0L;
//    }

    public static long thirtA(String filename) {
        List<String> list = read(filename);

        long ax=0L, ay=0L, bx=0L, by=0L, px, py, sum=0L;

        for (String l : list) {
            if (!l.isEmpty()) {
                String[] ll = l.split(": ");
                if (ll[0].equals("Button A")) {
                    String[] lll = ll[1].split(", ");
                    ax = Long.parseLong(lll[0].replace("X+", ""));
                    ay = Long.parseLong(lll[1].replace("Y+", ""));
                    continue;
                }
                if (ll[0].equals("Button B")) {
                    String[] lll = ll[1].split(", ");
                    bx = Long.parseLong(lll[0].replace("X+", ""));
                    by = Long.parseLong(lll[1].replace("Y+", ""));
                    continue;
                }
                if (ll[0].equals("Prize")) {
                    String[] lll = ll[1].split(", ");
                    px = Long.parseLong(lll[0].replace("X=", "")) + 10000000000000L;
                    py = Long.parseLong(lll[1].replace("Y=", "")) + 10000000000000L;
                    long p = getPrize(ax, ay, bx, by, px, py);
                    System.out.println(p);
                    sum+=p;
                }
            }
        }

        return sum;
    }

    public static long thirtB(String filename) {
        List<String> list = read(filename);

        long ax=0, ay=0, bx=0, by=0, px, py, sum=0;

        for (String l : list) {
            if (!l.isEmpty()) {
                String[] ll = l.split(": ");
                if (ll[0].equals("Button A")) {
                    String[] lll = ll[1].split(", ");
                    ax = Long.parseLong(lll[0].replace("X+", ""));
                    ay = Long.parseLong(lll[1].replace("Y+", ""));
                    continue;
                }
                if (ll[0].equals("Button B")) {
                    String[] lll = ll[1].split(", ");
                    bx = Long.parseLong(lll[0].replace("X+", ""));
                    by = Long.parseLong(lll[1].replace("Y+", ""));
                    continue;
                }
                if (ll[0].equals("Prize")) {
                    String[] lll = ll[1].split(", ");
                    px = Long.parseLong(lll[0].replace("X=", "")) + 10000000000000L;
                    py = Long.parseLong(lll[1].replace("Y=", "")) + 10000000000000L;
                    sum+= getPrize(ax, ay, bx, by, px, py);
                }
            }
        }

        return sum;
    }

    public static long fourTA(String filename) {
        List<String> list = read(filename);

        long[] qs = new long[4];
        Arrays.fill(qs, 0);

        long xsize = 101;
        long ysize = 103;

        long mul = 1;

        for (String l : list) {

            String[] parts = l.split(" ");
            String[] posCoords = parts[0].substring(2).split(",");
            long px = Long.parseLong(posCoords[0]);
            long py = Long.parseLong(posCoords[1]);
            String[] velCoords = parts[1].substring(2).split(",");
            long vx = Long.parseLong(velCoords[0]);
            long vy = Long.parseLong(velCoords[1]);

            long npx = ((100*vx) + px)%xsize;
            if (npx<0){
                npx+=xsize;
            }
            long npy = ((100*vy) + py)%ysize;
            if (npy<0){
                npy+=ysize;
            }
            if (npx <= (xsize-3)/2 && npy <= (ysize-3)/2) {
                qs[0]++;
            } else if (npx >= (xsize+1)/2 && npy <= (ysize-3)/2) {
                qs[1]++;
            } else if (npx <= (xsize-3)/2 && npy >= (ysize+1)/2) {
                qs[2]++;
            } else if (npx >= (xsize+1)/2 && npy >= (ysize+1)/2) {
                qs[3]++;
            }

        }

        for (long i:qs){
            mul*=i;
        }

        return mul;
    }

    public static String toString(char[][] c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                sb.append(c[i][j]);
            }
        }
        return sb.toString();
    }

    public static long fourTB(String filename) {
        List<String> list = read(filename);

        int xsize = 101;
        int ysize = 103;

        char[][] array = new char[xsize][ysize];
        for (char[] ar: array){
            Arrays.fill(ar, ' ');
        }

        long mul = 1;

        List<Coordinate> coords = new ArrayList<>();
        HashSet<String> seen = new HashSet<>();

        for (String l : list) {

            String[] parts = l.split(" ");
            String[] posCoords = parts[0].substring(2).split(",");
            int px = Integer.parseInt(posCoords[0]);
            int py = Integer.parseInt(posCoords[1]);
            String[] velCoords = parts[1].substring(2).split(",");
            int vx = Integer.parseInt(velCoords[0]);
            int vy = Integer.parseInt(velCoords[1]);
            Coordinate c = new Coordinate(px, py);
            c.dirx = vx;
            c.diry = vy;
            coords.add(c);
            array[px][py] = '#';
        }

//        System.out.println("num: "+coords.size());

        int max = Integer.MIN_VALUE;
        long it=0;
        long mit = 0;
        long mmit = 0;
        while (max < coords.size()) {
            for (Coordinate c : coords) {
                array[c.x][c.y] = ' ';
                c.x = (c.x+c.dirx)%xsize;
                if (c.x<0){
                    c.x+=xsize;
                }
                c.y = (c.y+c.diry)%ysize;
                if (c.y<0){
                    c.y+=ysize;
                }
                array[c.x][c.y] = '#';
            }
            if (!seen.contains(toString(array))) {
                seen.add(toString(array));
            } else {
                break;
            }
            it++;
            int mmax = Integer.MIN_VALUE;

            int count = 0;
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (array[i][j] != '#') {
                        if (mmax > count){
                            mmit = it;
                        }
                        mmax = Math.max(mmax, count);
                        count=0;
                    } else {
                        count++;
                    }
                }
            }
            if (mmax > max) {
                mit = mmit;
//                System.out.println(mmax + " @ " + mit);
            }
            max = Math.max(max, mmax);
        }

        return mit;
    }

    public static void move(char dir){
        int newrow=atrow, newcol=atcol;
        int drow=0,dcol=0;
        switch (dir){
            case '<': dcol=-1; break;
            case '>': dcol=1; break;
            case '^': drow=-1; break;
            case 'v': drow=1; break;
        }

        newrow=atrow+drow;
        newcol=atcol+dcol;

        if (grid[newrow][newcol] == '#') {
            return;
        }

        if (grid[newrow][newcol] == 'O') {
            int pushrow = newrow + drow;
            int pushcol = newcol + dcol;
            List<Coordinate> boxes = new ArrayList<>();
            boxes.add(new Coordinate(newrow, newcol));
            while (grid[pushrow][pushcol] == 'O') {
                boxes.add(new Coordinate(pushrow, pushcol));
                pushrow+=drow;
                pushcol+=dcol;
            }
            if (grid[pushrow][pushcol] != '.') {
                return;
            }

            for (int i=boxes.size()-1; i>=0; i-- ) {
                Coordinate c = boxes.get(i);
                int destr = c.x+drow;
                int destc = c.y+dcol;
                grid[destr][destc] = 'O';
                grid[c.x][c.y] = '.';
            }
        }
        grid[newrow][newcol] = '@';
        grid[atrow][atcol] = '.';
        atrow = newrow;
        atcol = newcol;
    }

    public static long fifTA(String filename) {
        List<String> list = read(filename);

        List<String> gridLines = new ArrayList<>();
        StringBuilder movementBuilder = new StringBuilder();

        for (String line : list) {
            if (line.matches("^[<>v^]+$")) {
                movementBuilder.append(line);
            } else {
                gridLines.add(line);
            }
        }
        grid = new char[gridLines.size()][gridLines.get(0).length()];
        for (int i = 0; i < gridLines.size(); i++) {
            grid[i] = gridLines.get(i).toCharArray();
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '@') {
                    atrow = i;
                    atcol = j;
                }
            }
        }
        String moves = movementBuilder.toString();

        for (int i = 0; i < moves.length(); i++) {
//            print(grid);
//            System.out.println("Move " + moves.charAt(i));
            move(moves.charAt(i));
        }

//        print(grid);
        long sum = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'O') {
                    sum += (100*i+j);
                }
            }
        }

        return sum;
    }

    public static long fifTB(String filename) {
        List<String> list = read(filename);

        List<String> gridLines = new ArrayList<>();
        StringBuilder movementBuilder = new StringBuilder();

        for (String line : list) {
            if (line.matches("^[<>v^]+$")) {
                movementBuilder.append(line);
            } else {
                gridLines.add(line);
            }
        }
        grid = new char[gridLines.size()][gridLines.get(0).length()*2];
        for (int i = 0; i < gridLines.size(); i++) {
            String line = gridLines.get(i);
            int col=0;
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                switch (c) {
                    case 'O':
                        grid[i][c++] = '[';
                        grid[i][c++] = ']';
                        break;
                    case '.':
                        grid[i][c++] = '.';
                        grid[i][c++] = ']';
                        break;
                    case '#':
                        grid[i][c++] = '#';
                        grid[i][c++] = '#';
                        break;
                    case '@':
                        grid[i][c++] = '@';
                        grid[i][c++] = '.';
                        atrow = i;
                        atcol = j;
                        break;
                }
            }
        }
        String moves = movementBuilder.toString();

        for (int i = 0; i < moves.length(); i++) {
//            print(grid);
//            System.out.println("Move " + moves.charAt(i));
            move(moves.charAt(i));
        }

//        print(grid);
        long sum = 0;
        for (int i=0; i<grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'O') {
                    sum += (100*i+j);
                }
            }
        }

        return sum;
    }

    public static long doMaze(Coordinate c) {
        Queue<Coordinate> q = new LinkedList<>();
        long[][]visited = new long[grid.length][grid[0].length];
        for (long[] v:visited) {
            Arrays.fill(v, Long.MAX_VALUE);
        }
        q.offer(c);
        visited[c.x][c.y] = c.score;
        long score = Long.MAX_VALUE;
        while (!q.isEmpty()) {
            Coordinate cur = q.poll();
            if (grid[cur.x][cur.y] == 'E' && cur.score < score){
                score = cur.score;
            }

            for (int[] dir:directions) {
                int newx = cur.x+dir[0];
                int newy = cur.y+dir[1];
                Coordinate n = new Coordinate(newx, newy);
                n.score = cur.score;
//                n.cnt = cur.cnt + 1;
                if (cur.dirx == dir[0] && cur.diry == dir[1]) {
                    n.score++;
                } else {
                    n.score+=1001;
                }
                if (visited[newx][newy]>n.score && grid[newx][newy] != '#') {
                    visited[newx][newy] = n.score;
                    n.dirx = dir[0];
                    n.diry = dir[1];
                    q.offer(n);
                }
            }
        }
        return score;
    }

    public static long doMazePath(Coordinate c) {
        Queue<Coordinate> q = new LinkedList<>();
        long[][]visited = new long[grid.length][grid[0].length];
        for (long[] v:visited) {
            Arrays.fill(v, Long.MAX_VALUE);
        }
        q.offer(c);
        visited[c.x][c.y] = c.score;
        long score = Long.MAX_VALUE;
//        Set<String> visitedSet = new HashSet<>();
        while (!q.isEmpty()) {
            Coordinate cur = q.poll();
            if (grid[cur.x][cur.y] == 'E' && cur.score <= score){
                score = cur.score;
//                for (Coordinate coord : cur.path) {
//                    visitedSet.add(coord.x+","+coord.y);
//                }
            }

            for (int[] dir:directions) {
                int newx = cur.x+dir[0];
                int newy = cur.y+dir[1];
                Coordinate n = new Coordinate(newx, newy);
                n.score = cur.score;
                if (cur.dirx == dir[0] && cur.diry == dir[1]) {
                    n.score++;
                } else {
                    // changed for problem 18 from 1001 to 1
                    n.score+=1;
                }
                if (newx >= 0 && newx < grid.length && newy >= 0 && newy < grid.length && grid[newx][newy] != '#' && visited[newx][newy]>=n.score) {
                    visited[newx][newy] = n.score;
//                    n.path.addAll(cur.path);
//                    n.path.add(n);
                    n.dirx = dir[0];
                    n.diry = dir[1];
                    q.offer(n);
                }
            }
        }
        // commented out for problem 18
//        for (String s : visitedSet) {
//            int x = Integer.parseInt(s.split(",")[0]);
//            int y = Integer.parseInt(s.split(",")[1]);
//            grid[x][y] = 'O';
//        }
//        print(grid);
//        return visitedSet.size();
        return score;
    }

//    public static long sixTA(String filename) {
//        grid = readBlock(filename);
//        int startr=0, startc=0;
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                if (grid[i][j]=='S') {
//                    startr=i;
//                    startc=j;
//                }
//            }
//        }
//        Coordinate c = new Coordinate(startr,startc);
//        c.dirx=0;
//        c.diry=1;
//        c.score=0L;
//        c.cnt=1;
//        return doMaze(c);
//    }

//    public static long sixTB(String filename) {
//        grid = readBlock(filename);
//        int startr=0, startc=0, endr=0, endc=0;
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                if (grid[i][j]=='S') {
//                    startr=i;
//                    startc=j;
//                }
//            }
//        }
//        Coordinate c = new Coordinate(startr,startc);
//        c.dirx=0;
//        c.diry=1;
//        c.score=0L;
//        c.path.add(c);
//        return doMazePath(c);
//    }

    public static long getOperand(int o) {
        switch (o) {
            case 0: return 0L;
            case 1: return 1L;
            case 2: return 2L;
            case 3: return 3L;
            case 4: return ra;
            case 5: return rb;
            case 6: return rc;
        }
        return 0L;
    }

    public static BigInteger getOperand2(int o) {
        switch (o) {
            case 0: return BigInteger.ZERO;
            case 1: return BigInteger.ONE;
            case 2: return BigInteger.TWO;
            case 3: return new BigInteger("3");
            case 4: return bra;
            case 5: return brb;
            case 6: return brc;
        }
        return BigInteger.ZERO;
    }

    public static List<Long> run() {
        int pointer = 0;
        List<Long> out = new ArrayList<>();
        while (pointer < program.length) {
            int op = program[pointer];
            int operand = program[pointer+1];
            switch (op) {
                case 0: ra = (long) (ra / Math.pow(2, getOperand(operand))); break;
                case 1: rb ^= operand; break;
                case 2: rb = getOperand(operand)%8; break;
                case 3: if (ra!=0) pointer=operand-2; break;
                case 4: rb ^= rc; break;
                case 5: out.add(getOperand(operand)%8); break;
                case 6: rb = (long) (ra / Math.pow(2, getOperand(operand))); break;
                case 7: rc = (long) (ra / Math.pow(2, getOperand(operand))); break;
            }
            pointer += 2;
        }
        return out;
    }

    public static boolean run2() {
        int pointer = 0;
        BigInteger eight = new BigInteger("8");
        List<Long> out = new ArrayList<>();
        while (pointer < program.length) {
            int op = program[pointer];
            int operand = program[pointer+1];
            switch (op) {
                case 0: bra = bra.divide(new BigInteger((int)Math.pow(2,getOperand2(operand).intValue())+"")); break;
                case 1: brb = brb.xor(new BigInteger(operand+"")); break;
                case 2: brb = getOperand2(operand).mod(eight); break;
                case 3: if (!bra.equals(BigInteger.ZERO)) pointer=operand-2; break;
                case 4: brb = brb.xor(brc); break;
                case 5: out.add(getOperand2(operand).mod(eight).longValue());
                if (out.size() > program.length || out.getLast() != program[out.size()-1]) return false;
                if (out.size()==1 && out.getFirst()==2L) System.out.println(raval.toString());
                break;
                case 6: brb = bra.divide(new BigInteger((int)Math.pow(2,getOperand2(operand).intValue())+"")); break;
                case 7: brc = bra.divide(new BigInteger((int)Math.pow(2,getOperand2(operand).intValue())+"")); break;
            }
            pointer += 2;
        }
        return out.size() == program.length;
    }

    public static String sevenTA(String filename) {
        List<String> list = read(filename);

        for (String line : list) {
            if (line.startsWith("Register A:")) {
                ra = Long.parseLong(line.split(": ")[1].trim());
            }
            if (line.startsWith("Register B:")) {
                rb = Long.parseLong(line.split(": ")[1].trim());
            }
            if (line.startsWith("Register C:")) {
                rc = Long.parseLong(line.split(": ")[1].trim());
            }
            if (line.startsWith("Program:")) {
                program = Arrays.stream(line.split(": ")[1].trim().split(",")).mapToInt(Integer::parseInt).toArray();
            }
        }

        List<Long> result = run();
        return result.toString().replaceAll("[\\[\\]]", "").replaceAll(" ", "");
    }

    public static String sevenTB(String filename) {
        List<String> list = read(filename);

        for (String line : list) {
            if (line.startsWith("Register A:")) {
                bra = new BigInteger(raval.toString());
            }
            if (line.startsWith("Register B:")) {
                brb = new BigInteger(line.split(": ")[1].trim());
            }
            if (line.startsWith("Register C:")) {
                brc = new BigInteger(line.split(": ")[1].trim());
            }
            if (line.startsWith("Program:")) {
                program = Arrays.stream(line.split(": ")[1].trim().split(",")).mapToInt(Integer::parseInt).toArray();
            }
        }

        while (!run2()){
//            if (raval.equals(new BigInteger("117439"))) {
//                System.out.println("pause");
//            }
            raval = raval.add(BigInteger.ONE);
            bra = new BigInteger(raval.toString());;
        }
        return raval.toString();
    }

    public static long eightTA(String filename) {
        List<String> list = read(filename);
        int size = 71;
        int cnt = 1024;
        grid = new char[size][size];
        for (char[] g : grid) {
            Arrays.fill(g, '.');
        }
        for (String line : list) {
            String[] s = line.split(",");
            grid[Integer.parseInt(s[0])][Integer.parseInt(s[1])] = '#';
            cnt--;
            if (cnt==0){
                break;
            }
        }

        int[][] igrid = new int[size][size];

        for (int y=0; y<grid.length; y++) {
            for (int x=0; x<grid.length; x++) {
                igrid[x][y] = Integer.MAX_VALUE;
                if (grid[x][y]=='#') igrid[x][y]=-1;
            }
        }

        igrid[0][0] = 0;

        for (int z=0; z<size*10; z++) {
            for (int y=0; y<grid.length; y++) {
                for (int x=0; x<grid.length; x++) {
                    if (igrid[x][y] > -1 && igrid[x][y] < Integer.MAX_VALUE) {
                        for (int[]dir:directions){
                            int newx = x + dir[0];
                            int newy = y + dir[1];
                            if (newx>=0 && newx<size && newy>=0 && newy<size && igrid[x][y]<igrid[newx][newy]) {
                                igrid[newx][newy]=igrid[x][y]+1;
                                grid[newx][newy]='O';
                            }
                        }
                    }
                }
            }
        }
        return igrid[size-1][size-1];
    }

    public static int[][] init(List<String> list, int cnt, int size) {
        int[][] igrid = new int[size][size];
        for (int[] g:igrid) {
            Arrays.fill(g, Integer.MAX_VALUE);
        }
        for (String line : list) {
            String[] s = line.split(",");
            igrid[Integer.parseInt(s[0])][Integer.parseInt(s[1])] = -1;
            cnt--;
            if (cnt==0){
                break;
            }
        }
        return igrid;
    }

    public static String eightTB(String filename) {
        List<String> list = read(filename);
        int cnt = 1024;
        int size = 71;
        int[][] igrid = null;

        int end = 0;
        while (end<Integer.MAX_VALUE) {
            cnt++;
            igrid = init(list, cnt, size);
            igrid[0][0] = 0;
            for (int z = 0; z < size * 10; z++) {
                for (int y = 0; y < size; y++) {
                    for (int x = 0; x < size; x++) {
                        if (igrid[x][y] > -1 && igrid[x][y] < Integer.MAX_VALUE) {
                            for (int[] dir : directions) {
                                int newx = x + dir[0];
                                int newy = y + dir[1];
                                if (newx >= 0 && newx < size && newy >= 0 && newy < size && igrid[x][y] < igrid[newx][newy]) {
                                    igrid[newx][newy] = igrid[x][y] + 1;
                                }
                            }
                        }
                    }
                }
            }
            end = igrid[size-1][size-1];
        }
        return list.get(cnt-1);
    }

//    public static int oneT(String filename) {
//        List<String> list = read(filename);
//
//        return 0;
//    }

    public static void main(String [] args) {
        Instant start = Instant.now();
//        System.out.println("1a: " + oneA("1a.txt"));
//        System.out.println("1b: " + oneB("1a.txt"));
//        System.out.println("2a: " + twoA("2.txt"));
//        System.out.println("1b: " + twoB("2.txt"));
//        System.out.println("3a: " + threeA("3.txt"));
//        System.out.println("3b: " + threeB("3.txt"));
//        System.out.println("4a: " + fourA("4.txt"));
//        System.out.println("4b: " + fourB("4.txt"));
//        System.out.println("5a: " + fiveA("5.txt"));
//        System.out.println("5b: " + fiveB("5.txt"));
//        System.out.println("6a: " + sixA("6test.txt")); //41, 4789
//        System.out.println("6B: " + sixB("6.txt")); //338 too low
//        System.out.println("7a: " + sevenA("7.txt")); //33003154123
//        System.out.println("7b: " + sevenB("7test.txt"));
//        System.out.println("8a: " + eightA("8.txt")); //291 too low 313 too high
//        System.out.println("8b: " + eightB("8.txt")); //1133, 1138 too low
//        System.out.println("9a: " + nineA("9.txt")); //90095094087
//        System.out.println("9b: " + nineB("9.txt")); //too high 6362723394113
//        System.out.println("10a: " + tenA("10.txt"));
//        System.out.println("10b: " + tenB("10.txt"));
//        System.out.println("11a: " + elevenA("11.txt"));
//        System.out.println("11b: " + elevenC("11.txt")); // ans 223767210249237 (incorrect 240575072090579)
//        System.out.println("12a: " + twelveA("12.txt"));
//        System.out.println("12b: " + twelveB("12test.txt"));
//        System.out.println("13a: " + thirtA("13.txt"));
//        System.out.println("13b: " + thirtB("13test.txt"));
//        System.out.println("14a: " + fourTA("14.txt")); //96888960 not right
//        System.out.println("14b: " + fourTB("14.txt"));
//        System.out.println("15a: " + fifTA("15.txt"));
//        System.out.println("16a: " + sixTA("16test.txt"));
//        System.out.println("16b: " + sixTB("16test.txt"));
//        System.out.println("17a: " + sevenTA("17.txt"));
//        System.out.println("17b: " + sevenTB("17.txt"));
//        System.out.println("18a: " + eightTA("18.txt"));
        System.out.println("18b: " + eightTB("18.txt"));

        System.out.println("-----");
        System.out.println(Duration.between(start, Instant.now()).toMillis() + "ms");
    }

    public static class GCD {
        long gcd;
        long x;
        long y;

        GCD(long gcd, long x, long y) {
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }
    }

    static GCD gcd(long a, long b) {
        if (b==0) {
            return new GCD(a, 1, 0);
        }
        GCD ans = gcd(b, a%b);
        long x = ans.y;
        long y = ans.x - (a/b) * ans.y;
        return new GCD(ans.gcd, x, y);
    }

    public static class Coordinate {
        private int x;
        private int y;
//        Coordinate origin;
//        Coordinate destination;
//        Coordinate next;
//        Coordinate prev;
        int dirx;
        int diry;
        long score;
//        long cnt;
//        List<Coordinate> path = new ArrayList<>();

        public Coordinate() {
            this(0, 0);
        }

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Necessary for using as a HashMap key: equals and hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
