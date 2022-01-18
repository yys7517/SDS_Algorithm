package Days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class BOJ_2143 {
    // 두 배열의 합

    // *** 접근법 ***
    // A와 B의 부 배열은 A와 B의 구간 합들의 집합으로 구할 수 있다.

    // 합이 T가 되는 부 배열의 쌍의 개수를 구하라.
    // 두 포인터를 사용하여 A와 B의 부 배열의 쌍의 합이 T를 만족하는지 구하자.
    static int T;

    static int N;
    static int M;

    static int[] A;
    static int[] B;
    
    static ArrayList<Integer> SubA;
    static ArrayList<Integer> SubB;

    static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st;

        T = Integer.parseInt( br.readLine() );

        N = Integer.parseInt( br.readLine() );
        st = new StringTokenizer( br.readLine(), " ");

        A = new int[ N ];
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt( st.nextToken() );
        }
        SubA = new ArrayList<>();

        M = Integer.parseInt( br.readLine() );
        st = new StringTokenizer( br.readLine(), " ");

        B = new int[ M ];
        for (int i = 0; i < M; i++) {B[i] = Integer.parseInt( st.nextToken() );

        }
        SubB = new ArrayList<>();

        // 누적합 구하기
        for (int i = 0; i < N; i++) {
            int sum = 0;
            for (int j = i; j < N; j++) {
                sum += A[j];
                SubA.add( sum );
            }
        }
        // A = [ 1, 3, 1, 2 ]
        // 1 4 5 7 3 4 6 1 3 2

        for (int i = 0; i < M; i++) {
            int sum = 0;
            for (int j = i; j < M; j++) {
                sum += B[j];
                SubB.add( sum );
            }
        }
        // B = [ 1, 3, 2 ]
        // 1 4 6 3 5 2

        Collections.sort( SubA );
        Collections.sort( SubB , Comparator.reverseOrder() );  // B의 부배열을 가리키는 포인터는 큰 수부터 가리키기 때문에
        // 역순으로 정렬을 하면 0부터 시작할 수 있다.

        //System.out.println( SubA );
        //System.out.println( SubB );
        // A의 1번 인덱스부터 시작.
        int ptA = 0;
        int ptB = 0;

        //long count = 0;
        long result = 0;
        // long sum = SubA.get( ptA ) + SubB.get( ptB );

        /*
        while ( ptA < SubA.size() && ptB < SubB.size() ) {
            long currentA = SubA.get( ptA );
            long target = T - currentA; // SubB에서 찾고 싶은 값.

            // long currentB = SubB.get( ptB );
            // currentB == target   -> SubA, SubB 같은 수 개수 체크 -> 답 구하기. ptA + countA , ptB + countB
            // currentB > target    -> ptB++
            // currentB < target    -> ptA++

            if( SubB.get(ptB) == target ) {
                long countA = 0;
                long countB = 0;

                while( ptA < SubA.size()  && SubA.get(ptA) == currentA ) {
                    countA++;
                    ptA++;
                }

                while( ptB < SubB.size()  && SubB.get(ptB) == target ) {
                    countB++;
                    ptB++;
                }

                result += countA * countB ;

            }
            else if( SubB.get(ptB) > target ) {
                ptB++;
            }
            else {// SubB.get(ptB) < target
                ptA++;
            }
        }
         */

        while ( ptA < SubA.size() && ptB < SubB.size() ) {
            long currentA = SubA.get( ptA );
            long target = T - currentA;

            if( SubB.get( ptB ) == target ) {
                long countA = 0;
                long countB = 0;

                while( ptA < SubA.size() && SubA.get(ptA) == currentA )  {
                    countA++;
                    ptA++;
                }

                while( ptB < SubB.size() && SubB.get(ptB) == target )  {
                    countB++;
                    ptB++;
                }
                result += countA * countB ;
            }
            else if ( SubB.get(ptB) > target ) {    // 현재 B의 값이 target보다 클 때
                ptB++;  // 내림차순 정렬이라, 현재 가리키는 SubB의 값이 포인터가 증가할 수록 작아진다.
            }
            else {  // SubB.get(ptB) < target   // 찾는 값 target이 더 클 때,
                ptA++;
            }
        }

        System.out.println( result );
    }
}
