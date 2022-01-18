package Days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2003 {
    // 수들의 합 2

    // N개의 수로 된 수열 A[1] .... A[N]이 있다.
    // i번째부터 j번째의 수의 합이 M이 되는 경우의 수를 구하자.

    // 두 포인터 [ 슬라이딩 윈도우 ]

    static int N;
    static int M;

    static int[] nums;
    static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        String[] token = br.readLine().split(" ");
        N = Integer.parseInt( token[0] );
        M = Integer.parseInt( token[1] );

        nums = new int[ N+1];

        token = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt( token[i] );
        }
        // 두 포인터를 사용 A[L] ~ A[H]의 합
        int low = 0, high = 0, sum = nums[0], count = 0;

        while ( high < N ) {
            if ( sum == M ){
                count++;
                sum -= nums[low++];
            }
            else if ( sum > M ) {
                sum -= nums[low++];
            }
            else {
                sum += nums[++high];
            }

        }

        System.out.println( count );
    }

}
