package Days08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11660 {
    // 구간 합 구하기 5

    // N x N 크기의 배열이 있다.
    // (x1,y1) 부터 (x2,y2)까지의 합을 구하는 프로그램.
    // x는 행, y는 열을 의미한다.

    // 시간복잡도 O(N*N + M)

    static int N;
    static int M;

    static int[][] arr;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st = new StringTokenizer( br.readLine(), " ");
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt( st.nextToken() );
        M = Integer.parseInt( st.nextToken() );

        arr = new int[ N+1 ][ N+1 ];
        dp = new int[ N+1 ][ N+1 ];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer( br.readLine(), " ");
            for (int j = 1; j <= N; j++) {
                arr[i][j] = Integer.parseInt( st.nextToken() );
            }
        }

        /*
            DP[ i ][ j ] 누적합을 만드는 점화식

           (위에↑ 값) + (왼쪽← 값) - (↖중복되는 대각선 값) + (인풋값)
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1] + map[i][j];

         */
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1] + arr[i][j];
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer( br.readLine() , " ");
            int x1,y1,x2,y2;
            x1 = Integer.parseInt( st.nextToken() );
            y1 = Integer.parseInt( st.nextToken() );
            x2 = Integer.parseInt( st.nextToken() );
            y2 = Integer.parseInt( st.nextToken() );

            int result = dp[x2][y2] - dp[x1-1][y2] - dp[x2][y1-1] + dp[x1-1][y1-1];
            sb.append( result ).append('\n');
        }
        System.out.println( sb );
    }
}
