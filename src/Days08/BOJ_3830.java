package Days08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_3830 {
    // 교수님은 기다리지 않는다.

    // 샘플 개수 N
    // 한 일의 수 M

    // 2 ≤ N ≤ 100,000
    // 1 ≤ M ≤ 100,000

    /*
     * WeightDiff[i] = i 와 i의 Root의 무게 차이.
     * 사용 예) WeightDiff[2] = 3 이고 Parent[2] = 4
     * 2의 Root는 4이고, 2와 4의 무게차이가 3이라는 뜻.
     */

    static int N;
    static int M;
    static int[] Parent;
    static int[] WeightDiff;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        StringBuilder sb = new StringBuilder();

        while ( true ) {
            StringTokenizer st = new StringTokenizer( br.readLine() , " ");
            N = Integer.parseInt( st.nextToken() );
            M = Integer.parseInt( st.nextToken() );

            Parent = new int[ N+1 ];
            WeightDiff = new int[ N+1 ];

            for (int i = 1; i <= N; i++) {
                Parent[i] = i;
            }

            if( N == 0 && M == 0 )
                break;




            for (int i = 0; i < M; i++) {
                st = new StringTokenizer( br.readLine() , " ");
                String query = st.nextToken();
                int a = Integer.parseInt( st.nextToken() );
                int b = Integer.parseInt( st.nextToken() );

                if( query.equals("!") ) {   // 무게를 쟀다.
                    int diff = Integer.parseInt( st.nextToken() );
                    Union(a,b,diff);        // a와 b의 무게 차이는 diff이고, b가 a보다 무겁다.
                }
                else { // ?
                    if( find(a) == find(b) ) {
                        // a와 b의 루트 값이 같을 때 == 같은 경로에 있을 때 ?
                        int result = WeightDiff[b] - WeightDiff[a];
                        sb.append( result ).append('\n');
                    }
                    else
                        sb.append("UNKNOWN").append('\n');
                }
                // b가 a보다 얼마나 무거운가 ?
                // a와 b의 무게 차이를 계산할 수 있다면, b가 a보다 얼마나 무거운지를 출력한다
                // 무게차이를 계산할 수 없다면 "UNKNOWN"


            }
        }
        System.out.println( sb );
    }

    // ! a b diff
    private static void Union(int a, int b, int diff) {
        int rootA = find( a );
        int rootB = find( b );

        // 이미 같은 집합에 있는 정점이라면, Union을 실행하지 않고 종료.
        if( rootA == rootB )
            return;

        // ** b가 a보다 diff 만큼 크게 ** 하여, ( 같은 집합에 속하게 하라 ==> Union )
        WeightDiff[ rootB ] = WeightDiff[ a ] - WeightDiff[ b ] + diff;
        // 최종 루트노드를 rootA로 하게 해야하므로,
        // WeightDiff[ rootB ]의 값을 rootA로 가는 거리로 바꿔줘야 한다.

        // 1. rootB에서 b로 다시 돌아간다.
        // 2. b에서 a로 돌아간다.
        // 3. a에서 rootA로 간다.


        // 1. - WeightDiff[ b ] = rootB로 가는 경로를 취소(-)
        // 2.    + diff         = b와 a의 거리 (+)
        // 3. + WeightDiff[ a ] = a에서 rootA까지의 거리(+)

        // b가 a랑 같은 집합에 속하게 해야, 차이를 구할 수 있다. => 경로 압축.
        // 최종 루트노드를 rootA로 하게 해야한다.
        Parent[ rootB ] = rootA;
    }


    private static int find( int i ) {
        if( Parent[i] == i )
            return i;


        int parent = find( Parent[i] );

        // parent에는 제일 끝 노드의 루트 값이 담기겠지만, 재귀호출이 발생한다.
        // A <- B <- C <- D => Find(D) =>  Find(C) =>  Find(B) =>  Find(A)
        // WeightDiff 값이 계속해서 갱신이 된다.
        WeightDiff[ i ] += WeightDiff[ Parent[i] ]; // i에서 Parent[i]까지의 거리 + Parent[i] 에서 Parent[ Parent[i] ]까지의 거리.

        // 원래의 루트 값을 루트 노드의 루트 값으로 갱신시켜준다. => 경로 압축
        return Parent[i] = parent; // i의 부모를 Parent[i]의 부모 값으로 변경.
    }
}
