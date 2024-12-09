import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {

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
        for (char[] s : a) {
            for (char c : s) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void print(List<BigInteger> a) {
        for (BigInteger b : a) {
            System.out.print(b);
        }
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

    public static int tenA(String filename) {
        List<String> list = read(filename);

        return 0;
    }

    public static int tenB(String filename) {
        List<String> list = read(filename);

        return 0;
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
        System.out.println("10a: " + tenA("10test.txt"));
//        System.out.println("10b: " + tenB("10test.txt"));


        System.out.println("-----");
        System.out.println(Duration.between(start, Instant.now()).toMillis() + "ms");
    }

    public static class Coordinate {
        private final int x;
        private final int y;

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
