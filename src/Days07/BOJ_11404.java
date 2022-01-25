package Days07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11404 {
    // 플로이드 - 플로이드 워샬 알고리즘

    // n개의 도시가 있다.
    // m개의 버스
    // 모든 도시의 쌍 (A,B)에 대해서 도시 A에서 B로 가는데 필요한 비용의 최소값을 구하라.

    static int n;
    static int m;

    static int[][] Distance;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        n = Integer.parseInt( br.readLine() );
        m = Integer.parseInt( br.readLine() );

        Distance = new int[ n+1 ][ n+1 ];
        for (int i = 1; i <= n ; i++) {
            for (int j = 1; j <= n ; j++) {
                if( i == j )
                    Distance[i][j] = 0;
                else
                    Distance[i][j] = INF;
            }
        }
        StringTokenizer st;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer( br.readLine() , " ");
            int A = Integer.parseInt( st.nextToken() );
            int B = Integer.parseInt( st.nextToken() );
            int dist = Integer.parseInt( st.nextToken() );
            if( Distance[A][B] > dist )
                Distance[A][B] = dist;
        }

        for (int k = 1; k <= n ; k++) {
            for (int i = 1 ; i <= n ; i++) {
                for (int j = 1; j <= n ; j++) {
                    // 값이 INF 이면, 거리를 알 수 없는 것.
                    if( Distance[i][k] == INF || Distance[k][j] == INF  )
                        continue;

                    // i-k 와 k-j 값을 알 때, 경유해서 가는 것이 그냥 i-j 로 가는 값보다 최단거리이면 ? 값을 갱신해준다.
                    if( Distance[i][k] + Distance[k][j] < Distance[i][j] )
                        Distance[i][j] = Distance[i][k] + Distance[k][j];
                }
            }
        }

        for (int i = 1; i <= n ; i++) {
            for (int j = 1; j <= n ; j++) {
                int print = Distance[i][j];
                if( print == INF )  // 만약, i에서 j로 갈 수 없는 경우에는 그 자리에 0을 출력한다.
                    print = 0;
                System.out.printf("%d ", print );
            }
            System.out.println();
        }
    }
}
