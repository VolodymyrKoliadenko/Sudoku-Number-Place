package sudoku;

public class Printer {

    static void print(int[][] sud) {
        for (int i = 0; i < 9; i++) {
            //System.out.print("|");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("|");
                }
                System.out.print(sud[i][j]+" ");
            }
            System.out.println("|");
            if (i % 3 == 2) {
                System.out.println("----------------------");
            }
        }
    }
}
