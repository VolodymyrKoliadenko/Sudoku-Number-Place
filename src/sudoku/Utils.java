package sudoku;

public class Utils {

    public static Sudoku revers(int[][] sud) {
        Sudoku retS = new Sudoku();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                retS.sud[i][j] = sud[j][i];
            }
        }

        return retS;
    }

}
