package Days06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1717 {
    // 집합의 표현

    static int n;   // 정점 개수
    static int m;   // 연산 횟수

    static int[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st = new StringTokenizer( br.readLine(), " ");

        n = Integer.parseInt( st.nextToken() );
        graph = new int[ n+1 ];

        // 1. 초기화
        for (int i = 1; i <= n; i++) {
            graph[i] = i;
        }

        m = Integer.parseInt( st.nextToken() );
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer( br.readLine() , " ");
            // 연산  정점a  정점b 형태로 입력이 주어진다.
            // 합집합은 0 a b의 형태
            // 두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산은 1 a b

            int op = Integer.parseInt( st.nextToken() );
            int a = Integer.parseInt( st.nextToken() );
            int b = Integer.parseInt( st.nextToken() );

            if( op == 0 ) {
                Union(a,b);
//                System.out.println(Arrays.toString( graph ));
            }

            else {
                boolean bl = Same(a,b);
                if( bl )
                    System.out.println("YES");
                else
                    System.out.println("NO");
            }
        }
    }

    // 연산 1
    private static boolean Same(int a, int b) {
        if ( Find(a) == Find(b) )
            return true;
        else
            return false;
    }

    // 연산 0
    private static void Union(int a, int b) {
        int aRoot = Find( a );
        int bRoot = Find( b );

        graph[aRoot] = bRoot;
    }


    private static int Find( int V ) {
        if( graph[V] == V )
            return V;
        else
            return graph[V] = Find( graph[V] );
    }
}
