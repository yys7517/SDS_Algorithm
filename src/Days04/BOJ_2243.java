package Days04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2243 {
    // 사탕상자

    // index : 맛
    // value : 개수

    /*
        mid = (left+right) / 2 ;
        Q( 2 )
     */
    static BufferedReader br;
    static StringBuilder sb;

    static int N;

    static int A;
    static int B;
    static int C;

    static int[] tree;
    static int MAX = 1000000;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();

        N = Integer.parseInt( br.readLine() );

        int S = 1;

        // 사탕은 그 맛이 좋고 나쁨이 1부터 1,000,000까지의 정수로 구분된다.
        // index = 1 ~ 1,000,000
        while ( S < MAX )
            S *= 2;

        tree = new int[ 2 * S ];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer( br.readLine() , " ");
            A = Integer.parseInt( st.nextToken() );

            // A가 1인 경우, B 번째로 맛있는 사탕을 꺼낸다.
            if( A == 1 ) {
                B = Integer.parseInt( st.nextToken() );
                int index = Query( 1, S, 1 , B );
                update( 1, S , 1, index, -1); // 한 개의 사탕이 꺼내지게 된다.
                System.out.println( index );
            }
            // A가 2인 경우, 사탕을 넣는 경우. B는 넣을 사탕의 맛이고, C는 그러한 사탕의 개수이다.
            //                                                         C가 음수일 경우, 사탕을 뺀다.
            else if ( A == 2 ){
                B = Integer.parseInt( st.nextToken() );
                C = Integer.parseInt( st.nextToken() );
                update( 1, S ,1, B, C);
            }

        }
    }

    // index 번째 맛있는 사탕을 찾아라.
    // node 에서 시작.
    private static int Query( int left, int right, int node, int index ) {
        // 1. Leaf 노드에 도착했을 때, 사탕 번호 반환.
        if ( left == right ) {
            return left;
        }
        int mid = ( left + right ) / 2;
        // 2. 왼쪽 >= index -> 왼쪽으로 이동.
        if ( tree[ node * 2 ] >= index ) {
            return Query( left, mid, node * 2, index );
        }
        // 3. 왼쪽 < index
        else {
            return Query( mid + 1, right, node * 2 + 1, index - tree[ node * 2 ] );
        }
    }

    private static void update(int left, int right, int node, int target, int diff) {
        if( target < left || target > right ) {
            return;
        }

        tree[node] += diff;

        if( left == right )
            return;

        int mid = ( left + right ) / 2;
        update( left, mid , node * 2 , target, diff );
        update( mid + 1, right , node * 2 + 1, target, diff );
    }

}
