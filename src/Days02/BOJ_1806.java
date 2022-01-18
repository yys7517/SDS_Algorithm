package Days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1806 {
    // 부분합
    // 수열에서 연속된 수들의 부분합 중에서 그 합이 S 이상이 되는 것 중,
    // 가장 짧은 것의 길이를 구하라

    static int N;
    static int S;

    static int[] nums;
    static int min = Integer.MAX_VALUE;

    static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));

        String[] token = br.readLine().split(" ");
        N = Integer.parseInt( token[0] );
        S = Integer.parseInt( token[1] );

        int temp = min;

        nums = new int[ N+1 ];
        token = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt( token[i] );
        }

        int low = 0;
        int high = 0;
        int sum = nums[0];

        while ( true ) {

            if( sum < S ){
                sum += nums[++high];
            }
            else{ // sum >= S
                int length = high - low + 1;
                min = Math.min( length, min );
                sum -= nums[low++];
            }
            if( high == N )
                break;
        }

        if( temp != min )
            System.out.println( min );
        else
            System.out.println( 0 );
    }

}
