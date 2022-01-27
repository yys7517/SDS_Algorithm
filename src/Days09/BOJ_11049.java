package Days09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11049 {
    // 행렬 곱셈 순서

    // 행렬의 개수 N
    static int N;

    static int[] matrixR;
    static int[] matrixC;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));

        N = Integer.parseInt( br.readLine() );
        matrixR = new int[ N+1 ];     // i번째 행렬의 행의 크기
        matrixC = new int[ N+1 ];     // i번째 행렬의 열의 크기
        dp = new int[ N+1 ][ N+1 ];  // dp[i][j] = i번째 행렬부터 j번째 행렬까지의 곱셈 연산의 최소 횟수.

        StringTokenizer st;

        // N개의 행렬에 대한 행의 크기 r과 열의 크기 c가 주어진다.
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer( br.readLine(), " ");
            matrixR[i] = Integer.parseInt( st.nextToken() );
            matrixC[i] = Integer.parseInt( st.nextToken() );
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if( i == j )
                    dp[i][j] = 0;
                else
                    dp[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int gap = 1; gap < N ; gap++) {          // 간격
            for (int i = 1; i+gap <= N ; i++) {   // dp[1][2], dp[1][3] ... dp[2][3]
                solve( i, i+gap );                // i번 행렬부터 i+d번 행렬까지 곱셈연산 최소 횟수 구하기.
            }
        }

        // 1번 행렬부터 N번 행렬까지 곱셈 연산의 최소 횟수 출력. dp[1][N]
        System.out.println( dp[1][N] );

    }

    // 행렬 두개의 곱셈 연산 수
    // A의 행의수 * A의 열의 수 * B의 열의 수 를 곱하면 행렬 곱셈 횟수가 나오게 된다.
    // rA * cA * cB = rA * rB * cB


    // 여러 개 행렬의 곱셈 연산 횟수
    // s번째 행렬부터 e번째 행렬까지의 곱셈 연산 최소 횟수
    private static void solve( int start, int end ) {
        for (int k = start; k < end ; k++) {
            // dp[s][e] = Min( dp[s][e], dp[s][k] + dp[k+1][e] + ( rS * cK * cE ) )
            int cnt = dp[start][k] + dp[k+1][end] + ( matrixR[start] * matrixC[k] * matrixC[end] );
            dp[start][end] = Math.min( dp[start][end], cnt );
        }
    }
}
