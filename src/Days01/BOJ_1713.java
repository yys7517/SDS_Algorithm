package Days01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1713 {
    // 후보 추천하기

    // 추천하면 추천받은 학생의 사진이 사진틀에 게시된다.
    // 비어있는 사진틀이 없는 경우에는, 현재까지 추천받은 횟수가 가장 적은 학생을 삭제.
    // 추천 수가 가장 적은 학생이 두 명 이상일 경우에는 ? 게시된 지 가장 오래된 사진을 삭제한다.

    // 사진이 게시된 학생이 추천을 받은 경우는 추천 수만 올라간다.
    // 사진이 삭제되는 경우에는 추천 수가 0으로 초기화 된다.

    static int N;
    static int R;

    static class Person implements Comparable<Person>{
        int num;                // 학생 번호
        int count;              // 추천 수
        int timeStamp;          // 등록일자
        boolean isIn;           // 사진틀에 있는 지.

        public Person(int num, int count, int timeStamp, boolean isIn) {
            this.num = num;
            this.count = count;
            this.timeStamp = timeStamp;
            this.isIn = isIn;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "num=" + num +
                    ", count=" + count +
                    ", timeStamp=" + timeStamp +
                    ", isIn=" + isIn +
                    '}';
        }

        @Override
        public int compareTo(Person o) {
            int comp1 = Integer.compare( count, o.count );
            if( comp1 == 0 ) {  // 추천수가 동일하다면, timeStamp 오름차순.
                return Integer.compare( timeStamp, o.timeStamp );
            }
            else {  // 추천수 오름차순
                return comp1;
            }
        }

        public int getNum() {
            return num;
        }

        public int getCount() {
            return count;
        }

        public int getTimeStamp() {
            return timeStamp;
        }
    }

    static Person[] people;
    static int[] inputs;

    static BufferedReader br;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();

        N = Integer.parseInt( br.readLine() );      // 사진틀 칸 개수.
        R = Integer.parseInt( br.readLine() );      // 추천 수
        StringTokenizer st = new StringTokenizer( br.readLine(), " ");

        inputs = new int[ R ];  // 추천 수
        people =new Person[ 101 ];  // 학생의 번호는 1부터 100까지의 자연수이다.

        // Person[] A = new Person[ 4 ];
        // Person[] B = new Person[ 4 ];

        // 기본 자료형을 제외한 모든 객체는 레퍼런스 타입.
        // Person p = new Person(0,0,0);
        /*
        A[0] = p;   // p의 주소 값.
        B[0] = p;   // p의 주소 값.
        p.count = 3;
        A[0].count ==> ?? >> 3


        int [] C = new int[4];
        int [] D = new int[4];
        D[0] = C[0];
        C[0] = 4;

        D[0] ==> ?? >> 0
         */

        List<Person> list = new ArrayList<>();

        for (int k = 0; k < R; k++) {
            int num = Integer.parseInt( st.nextToken() );

            if( people[num] == null ){  // 학생 번호에 따른 객체가 없을 때 새로 생성.
                people[num] = new Person( num,0,0, false);
            }
            // 사진 틀에 존재하는 경우
            if( people[num].isIn == true ){
                people[num].count++;
            }
            // 사진 틀에 존재하지 않는 경우
            else {
                // 자리가 없는 경우, 하나 골라서 제거 후, 새 후보 추가.
                if( list.size() == N ) {
                    Collections.sort( list );   // 추천 수와 timeStamp 순으로 오름차순 정렬.
                    Person p = list.remove(0);  // 0 인덱스 부분에, 추천 수가 가장 적거나, 오래 게시된 학생 번호가 오게 된다.
                    p.isIn = false;
                    // p.count = 0; isIn을 사용함으로써, 안해줘도 된다.
                    //
                }
                // 사진 틀에 넣어줄 때, count를 1로 다시 설정.
                people[num].count = 1;
                people[num].isIn = true;
                people[num].timeStamp = k; // 사진 틀에 올릴 때 timeStamp를 설정.
                list.add( people[num] );    // list에 그냥 더해줘도 된다.
                // 제거를 하는 과정에서 정렬을 한 후, 제거를 하기 때문에.
            }
        }

        /*
                [2 1 4] <- 3
                [1 4 3] <- 5
                [4 3 5] <- 6
                [3 5 6] <- 2
                [5 6 2] <- 7
                [6 2 7] <- 2
                [6 2 7]
         */

        // list를 num 오름차순으로 정렬.
        // 익명의 new Comparator<Person>() 람다로 바꿀수 있습니다.
        /*
        -- 정렬 부분 꼭 공부해두기 --
        ( Person의 num을 기준으로 오름차순 정렬하는 방법. )

        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return Integer.compare( o1.num, o2.num );
            }
        });

        Collections.sort(list, (o1, o2) -> Integer.compare( o1.num, o2.num ));

        // *** Comparator.comparingInt( Person::getNum ) ***

        Collections.sort(list, Comparator.comparingInt(o -> o.num));

         */

        Collections.sort( list, Comparator.comparingInt( Person :: getNum ) );


        while ( !list.isEmpty() ) {
            sb.append( list.remove(0).num ).append(" ");
        }

        System.out.println( sb );

    }

}
