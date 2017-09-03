package sudoku;

public class Pair {

    public int x;
    public int y;

    public Pair(int xx, int yy) {
        x = xx;
        y = yy;
    }

    public static Pair P(int xx, int yy) {
        return new Pair(xx, yy);
    }

    @Override
    public String toString() {
        return "X = " + x + "; Y = " + y;
    }
    
    /*public static Integer[][] toArray(Pair[] a) {
        Integer[][] b = new Integer[a.length][2];
        int i = 0;
        for (Pair p : a) {
            b[i][0]=p.x;
            b[i][1]=p.y;
    i+++
        }
    }*/

}
