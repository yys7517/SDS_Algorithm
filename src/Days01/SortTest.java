package Days01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortTest {
    public static void main(String[] args) {
        Item item1 = new Item(1, 3, 1);
        Item item2 = new Item(2, 2, 3);
        Item item3 = new Item(3, 1, 2);

        List<Item> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);

        System.out.println(list);

        Collections.sort(list);

        System.out.println( list );

        // 위 Collection sort는 무시가 됨.
        // 새로운 Comparator로 사용한다.
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Integer.compare( o1.c, o2.c );
            }
        });

        System.out.println( list );

        // Collections.sort ( ComparingInt + getter )
        Collections.sort( list , Comparator.comparingInt( Item::getB ));

        System.out.println( list );
    }
}

class Item implements Comparable<Item>{
    int a;
    int b;
    int c;

    public Item(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Item{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }

    // 음수 : -
    // 0 : -
    // 양수 : swap
    @Override
    public int compareTo(Item o) {

        // return Integer.compare( a, o.a );   // a값 오름차순.
        // return Integer.compare( o.a, a );   // a값 내림차순
        /*
        if( a < o.a )
            return -1;
        else if( a == o.a )
            return 0;
        else
            return 1;
         */

        int comp1 = Integer.compare( a, o.a );
        if( comp1 == 0 ) // a와 o.a 두 값이 같다면, b를 기준으로 오름차순.
            return Integer.compare( b, o.b );
        else
            return comp1;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }
}
