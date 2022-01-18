package Days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2748 {
    // 피보나치 수 2

    static int N;
    static long result;

    static long[] dp;
    static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));

        N = Integer.parseInt( br.readLine() );
        dp = new long[ N+1 ];

        dp[0] = 0;
        dp[1] = 1;
        // 동적계획법 - dp 배열의 초기값에 의미를 둬야 한다.

        // 초기값인 0으로 그대로 두기에는 0번째 피보나치 수와 겹치므로.
        // 아직 메모리에 할당이 되지 않았음을 -1을 채워넣음.
        for (int i = 2; i <= N ; i++) {
            dp[i] = -1;
        }
        result = Fib( N );
        System.out.println( result );
    }

    // dp의 n번째 값을 반환하는 함수.
    private static long Fib( int n ) {
        if( dp[n] == -1 ) { // 만약 N 번째 피보나치 수가 저장이 되어있지 않으면.
            dp[n] = Fib(n-1) + Fib(n-2) ;   // 구한다.
        }
        return dp[n];   // dp의 n번째 값을 반환.
    }
}
