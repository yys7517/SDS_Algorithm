package Days04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2243 {
    // 사탕상자

    // index : 맛
    // value : 개수

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
        // index번 째로 맛있는 사탕이
        // 왼쪽 자식 노드의 개수( 왼쪽 서브 트리의 사탕 개수 합 )
        // 보다 작을 때, 우리가 찾는 index번 째 사탕은 왼쪽에 있다.
        if ( tree[ node * 2 ] >= index ) {
            return Query( left, mid, node * 2, index ); // 왼쪽으로 ㄱㄱ
        }

        // 3. 왼쪽 < index
        else {
            // 왼쪽을 포함해서 index 번째 이므로, 오른쪽 서브트리로 가서 사탕을 찾을 때는,
            // index - tree[node * 2] 의 개수 만큼 뺀 순위의 사탕을 찾는다.
            return Query( mid + 1, right, node * 2 + 1, index - tree[ node * 2 ] );
        }
    }


    private static void update(int left, int right, int node, int target, int diff) {
        // 1. 범위 내의 사탕인가 ?
        if( target < left || target > right ) {
            return;
        }

        // 2. 개수 수정.
        tree[node] += diff;

        // 3. leaf 노드까지 재귀하여 수정한다.
        if( left == right )
            return;

        // 4. leaf 노드가 아니라면, 둘로 나뉘어져서 수정한다.
        int mid = ( left + right ) / 2;
        update( left, mid , node * 2 , target, diff );
        update( mid + 1, right , node * 2 + 1, target, diff );
    }

}
