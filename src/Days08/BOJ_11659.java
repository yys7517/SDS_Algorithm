package Days08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11659 {
    // 구간 합 구하기 4
    // 수 N개가 주어졌을 때, i번째 수부터 j번째 수까지 합을 구하는 프로그램을 작성.
    // 합을 구해야 하는 횟수 M
    /*
    1 ≤ N ≤ 100,000
    1 ≤ M ≤ 100,000
    1 ≤ i ≤ j ≤ N
     */
    static int N;
    static int M;
    static int[] arr;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st = new StringTokenizer( br.readLine(), " ");
        N = Integer.parseInt( st.nextToken() );
        M = Integer.parseInt( st.nextToken() );

        arr = new int[ N+1 ];
        dp = new int[ N+1 ];

        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer( br.readLine(), " " );
        int sum = 0;
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt( st.nextToken() );
            sum += arr[i];
            dp[i] = sum;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer( br.readLine() , " ");
            int from = Integer.parseInt( st.nextToken() );
            int to = Integer.parseInt( st.nextToken() );

            sb.append( dp[to] - dp[from-1] ).append('\n');
        }
        System.out.println( sb );
    }
}
