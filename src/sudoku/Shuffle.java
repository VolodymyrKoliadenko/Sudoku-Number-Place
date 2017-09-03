package sudoku;

import java.util.ArrayList;
import java.util.List;
import static sudoku.Pair.P;

public class Shuffle {

    /*
        Задача - фаткориальная расстановка всех вариантов:
        2-2вар
        3-6вар
        9-363 000
        2-3 можно ручками, остальное рекурсией или циклами (8функций)
     */
    
    //вроде мне верхний код не нужен, только внутренний шуфл
    //вначале возврашаем Х, потом значение (У)
    static List<Integer[][]> shuf(List<Integer> V, List<Integer> X) {
        
        int N = X.size();
        
        List<Integer[][]> list = new ArrayList<>();

        List<List<Integer>> vozvrat = new ArrayList<>();
        shufV(vozvrat, null, V);
        for (List<Integer> i : vozvrat) {
            Integer[][] temp = new Integer[2][N];
            temp[0]=X.toArray(temp[0]);
            temp[1]=i.toArray(temp[1]);
            list.add(temp);
        }

        return list;
    }

    // эта функция таки делает фаткориальную перестановку листа из чисел
    static void shufV(List<List<Integer>> vozvrat,
            List<Integer> obr, List<Integer> neob) {
        /*https://stackoverflow.com/questions/18681165/shuffle-an-array-as-many-as-possible
         */
        if (obr == null) {
            obr = new ArrayList<>();
        }
        if (neob.size() > 0) {
            for (Integer i : neob) {
                List<Integer> temp = new ArrayList<>(neob);
                List<Integer> tempOb = new ArrayList<>(obr);
                temp.remove(i);
                tempOb.add(i);
                shufV(vozvrat, tempOb, temp);
            }
        } else {//super add HERE
            vozvrat.add(obr);
        }
        //return vozvrat;
    }

    /*static List<Pair[]> shuf2(List<Integer> V, List<Integer> X) {
        List<Pair[]> list = new ArrayList<>(2);
        list.add(new Pair[]{P(X.get(0), V.get(0)), P(X.get(1), V.get(1))});
        list.add(new Pair[]{P(X.get(0), V.get(1)), P(X.get(1), V.get(0))});
        return list;
    }

    static List<Pair[]> shuf3(List< Integer> V, List< Integer> X) {
        List<Pair[]> list = new ArrayList<>(6);
        list.add(new Pair[]{P(X.get(0),
            V.get(0)), P(X.get(1),
            V.get(1)), P(X.get(2),
            V.get(2))});
        list.add(new Pair[]{P(X.get(0),
            V.get(0)), P(X.get(1),
            V.get(2)), P(X.get(2),
            V.get(1))});

        list.add(new Pair[]{P(X.get(0),
            V.get(1)), P(X.get(1),
            V.get(2)), P(X.get(2),
            V.get(0))});
        list.add(new Pair[]{P(X.get(0),
            V.get(1)), P(X.get(1),
            V.get(0)), P(X.get(2),
            V.get(2))});

        list.add(new Pair[]{P(X.get(0),
            V.get(2)), P(X.get(1),
            V.get(1)), P(X.get(2),
            V.get(0))});
        list.add(new Pair[]{P(X.get(0),
            V.get(2)), P(X.get(1),
            V.get(0)), P(X.get(2),
            V.get(1))});
        return list;
    }*/
}
