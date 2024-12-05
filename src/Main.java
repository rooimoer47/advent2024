import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public static int oneT(String filename) {
        List<String> list = read(filename);

        return 0;
    }

    public static void main(String [] args) {
        Instant start = Instant.now();
        System.out.println("1a: " + oneA("1a.txt"));
        System.out.println("1b: " + oneB("1a.txt"));
        System.out.println("2a: " + twoA("2.txt"));
        System.out.println("1b: " + twoB("2.txt"));
        System.out.println("3a: " + threeA("3.txt"));
        System.out.println("3b: " + threeB("3.txt"));
        System.out.println("4a: " + fourA("4.txt"));
        System.out.println("4b: " + fourB("4.txt"));
        System.out.println("5a: " + fiveA("5.txt"));
        System.out.println("5b: " + fiveB("5.txt"));

        System.out.println("-----");
        System.out.println(Duration.between(start, Instant.now()).toMillis() + "ms");
    }
}
