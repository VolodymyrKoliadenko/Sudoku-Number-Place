package sudoku;

import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import static sudoku.Shuffle.shufV;
import static sudoku.Utils.revers;

/**
 *
 * @author Администратор
 */
public class Sudoku {

    static final int ERROR = -57;

    int[][] sud = {
        {0, 9, 8,  2, 0, 5,  7, 0, 0},
        {0, 3, 0,  0, 0, 0,  2, 0, 4},
        {0, 0, 0,  0, 0, 0,  0, 3, 0},
            
        {0, 5, 0,  0, 0, 3,  4, 0, 0},
        {0, 0, 9,  0, 1, 0,  0, 2, 7},
        {0, 0, 0,  0, 7, 0,  0, 0, 0},
            
        {0, 0, 3,  0, 0, 0,  9, 0, 0},
        {0, 0, 0,  1, 0, 7,  0, 0, 5},
        {1, 0, 0,  0, 0, 0,  0, 0, 8} /*
        {0, 0, 5, 3, 0, 0, 0, 0, 0},  // 6.8 sec!!!! == 0,5 с транспонированием
        {8, 0, 0, 0, 0, 0, 0, 2, 0},
        {0, 7, 0, 0, 1, 0, 5, 0, 0},
        {4, 0, 0, 0, 0, 5, 3, 0, 0},
        {0, 1, 0, 0, 7, 0, 0, 0, 6},
        {0, 0, 3, 2, 0, 0, 0, 8, 0},
        {0, 6, 0, 5, 0, 0, 0, 0, 9},
        {0, 0, 4, 0, 0, 0, 0, 3, 0},
        {0, 0, 0, 0, 0, 9, 7, 0, 0}
            
            HARD: откуд это????
        {4, 0, 0,  2, 0, 0,  6, 0, 0},
        {6, 3, 0,  0, 0, 8,  9, 0, 0},
        {7, 0, 0,  0, 3, 1,  0, 0, 0},
            
        {0, 0, 4,  0, 6, 0,  0, 0, 0},
        {8, 0, 0,  0, 0, 2,  0, 0, 4},
        {0, 0, 0,  3, 5, 0,  0, 9, 0},
            
        {0, 9, 0,  0, 8, 0,  0, 0, 0},
        {5, 0, 0,  4, 0, 0,  0, 0, 7},
        {0, 0, 6,  0, 0, 0,  5, 0, 0}
            
        ____________    
        {0, 0, 0,  0, 0, 0,  0, 0, 0},
        {0, 0, 0,  0, 0, 0,  0, 0, 0},
        {0, 0, 0,  0, 0, 0,  0, 0, 0},
            
        {0, 0, 0,  0, 0, 0,  0, 0, 0},
        {0, 0, 0,  0, 0, 0,  0, 0, 0},
        {0, 0, 0,  0, 0, 0,  0, 0, 0},
            
        {0, 0, 0,  0, 0, 0,  0, 0, 0},
        {0, 0, 0,  0, 0, 0,  0, 0, 0},
        {0, 0, 0,  0, 0, 0,  0, 0, 0}
     */}; //http://igraemsami.ru/images/logika/reshenie-sudoku-1.jpg

    static long t;
    static long tempCounter = 0;

    Sudoku() {
    }

    Sudoku(int[][] s) {
        sud = new int[9][9];

        //copy BECOUSE pointers :(((
        for (int i = 0; i < 9; i++) {
            sud[i] = Arrays.copyOf(s[i], 9);
        }

    }

    public static void main(String[] args) {
        // только по срочкам - ПОКА так

        Sudoku s1 = new Sudoku();
        t = System.nanoTime();
        s1.test();
    }

    void test() {

        Pair p = findMaxRow();
        if (p == null) {
            return;
        }

        //System.out.println("first etap " + p.y);
        if (p.y == 9) {
            //complete9 dont need;
            finalStageStep();
        } else {
            int i = p.x;
            List<List<Integer>> vozvrat = new ArrayList<>();
            shufV(vozvrat, null, freeValue(i));

            List<Integer> list = freeValue(i);
            int N = list.size();

            Integer[] X = new Integer[N];
            X = freeX(i).toArray(X);

            outer:
            for (List<Integer> v : vozvrat) { // mega cicl po factorial
                Sudoku s = new Sudoku(sud);
                for (int j = 0; j < N; j++) {
                    int x = X[j];
                    s.sud[i][x] = v.get(j);

                    if (!s.checkColumn(x)) {
                        continue outer;
                    }
                    if (!s.checkSquare(i, x)) {
                        continue outer;
                    }
                }
                tempCounter++;
                s.finalStageStep();
            }
        }
        //System.out.println(Arrays.deepToString(sud));
    }

    void finalStageStep() {

        if (!checkFinal()) {
            return;
        }
        Pair p = findMaxRowFinal(); //(numb, max)
        if (p == null) {
            return;
        }
        int i = p.x;
        int n = p.y;
        if (n < 7) {
            Sudoku reversed = revers(sud);
            Pair pr = reversed.findMaxRowFinal(); //(numb, max)
            if (pr == null) {
                return;
            }
            if (pr.y > n) {
                sud = reversed.sud;
                i = pr.x;
            }

        }

        List<List<Integer>> vozvrat = new ArrayList<>();
        shufV(vozvrat, null, freeValue(i));

        List<Integer> list = freeValue(i);
        int N = list.size();
        //System.out.println("Final etap SIZE " + N + " !");// для оценки сложности. 

        Integer[] X = new Integer[N];
        X = freeX(i).toArray(X);

        outer:
        for (List<Integer> v : vozvrat) { // mega cicl po factorial
            Sudoku s = new Sudoku(sud);
            for (int j = 0; j < N; j++) {
                int x = X[j];
                s.sud[i][x] = v.get(j);

                if (!s.checkColumn(x)) {
                    continue outer;
                }
                if (!s.checkSquare(i, x)) {
                    continue outer;
                }
            }
            tempCounter++;
            s.finalStageStep();
        }

    }

    boolean checkSquare(int x, int y) {
        List<Integer> row = new ArrayList<>(9);
        x = (x / 3) * 3;
        y = (y / 3) * 3;
        for (int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++) {
                row.add(sud[i][j]);
            }
        }
        for (int i = 1; i <= 9; i++) {
            if (Collections.frequency(row, i) > 1) {
                //System.out.println("S"+i);
                return false;
            }
        }
        return true;
    }

    boolean checkRow(int n
    ) {
        List<Integer> row = Ints.asList(sud[n]);
        for (int i = 1; i <= 9; i++) {
            if (Collections.frequency(row, i) > 1) {
                return false;
            }
        }
        return true;
    }

    boolean checkColumn(int n
    ) {
        List<Integer> row = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            row.add(sud[i][n]);
        }
        //System.out.println(row);
        for (int i = 1; i <= 9; i++) {
            if (Collections.frequency(row, i) > 1) {
                //System.out.println(i);
                return false;
            }
        }
        long size = row.stream().filter(x -> x > 0).count();
        if (size == 8) {
            int temp = complete9column(n);
            return checkRow(temp) && checkSquare(temp, n);
        }
        return true;
    }

    List<Integer> freeX(int n
    ) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (sud[n][i] == 0) {
                ret.add(i);
            }
        }
        return ret;
    }

    List<Integer> freeValue(int n
    ) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            final int k = i;
            if (!IntStream.of(sud[n]).anyMatch(x -> x == k)) {
                ret.add(i);
            }
        }
        return ret;
    }

    Pair findMaxRow() {
        int numb = 0;
        int max = 0;

        for (int i = 0; i < 9; i++) {
            int m = sizeExist(i);
            if (m == ERROR) {
                return null;
            }
            if (m > max) {
                max = m;
                numb = i;
            }
        }
        //System.out.println(numb);
        return new Pair(numb, max);
    }

    Pair findMaxRowFinal() {
        int numb = -1;
        int max = 0;

        for (int i = 0; i < 9; i++) {
            int m = sizeExist(i);
            if (m == ERROR) {
                return null;
            }
            if (m > max && m < 8) {
                max = m;
                numb = i;
            }
        }
        return new Pair(numb, max);
    }

    int sizeExist(int a) {
        int s = 0;
        for (int i : sud[a]) {
            s = (i > 0) ? s + 1 : s;
        }
        //System.out.println("size " + s);
        if (s == 8) {
            if (!complete9(a)) {
                return ERROR;
            }
            return 9;
        }
        return s;
    }

    boolean complete9(int a) {
        int x = 45 - IntStream.of(sud[a]).sum();
        for (int j = 0; j < 9; j++) {
            if (sud[a][j] == 0) {
                sud[a][j] = x;
                return checkColumn(j) && checkSquare(a, j);
            }
        }
        return true;
    }

    int complete9column(int j) {

        int s = 0;
        int ret = 0;
        for (int i = 0; i < 9; i++) {
            if (sud[i][j] != 0) {
                s += sud[i][j];
            } else {
                ret = i;
            }
        }
        sud[ret][j] = 45 - s;
        return ret;
    }

    boolean checkFinal() {
        int min = 9;

        for (int i = 0; i < 9; i++) {
            int m = sizeExist(i);
            if (m == ERROR) {
                return false;
            }
            if (m < min) {
                return true; //если уж менбьше значит не конец
            }
        }
        System.out.println("ms: " + (System.nanoTime() - t) / 1e6);
        System.out.println("VARIANTS: " + tempCounter);
        System.out.println("END ");
        Printer.print(sud);
        System.out.println("OK " + checkAll());
        System.exit(0);
        return true;
    }

    boolean checkAll() {
        boolean check = true;

        for (int i = 0; i < 9; i++) {
            check = checkColumn(i) && check;
            if (!checkColumn(i)) {
                System.out.println("col FALSE " + i);
            }
            check = checkRow(i) && check;
            if (!checkRow(i)) {
                System.out.println("row FALSE " + i);
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                check = checkSquare(i, j) && check;
                if (!checkSquare(i, j)) {
                    System.out.println("col FALSE " + i + " " + j);
                }
            }
        }
        return check;
    }
}
