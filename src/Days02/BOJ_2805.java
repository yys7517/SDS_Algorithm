package Days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2805 {
    // 나무 자르기

    static int N;
    static int M;

    static int[] trees;
    static int max = Integer.MIN_VALUE;

    static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st;

        st = new StringTokenizer( br.readLine(), " ");
        N = Integer.parseInt( st.nextToken() );
        trees = new int[ N ];

        M = Integer.parseInt( st.nextToken() );

        st = new StringTokenizer( br.readLine() , " ");
        for (int i = 0; i < N; i++) {
            trees[i] = Integer.parseInt( st.nextToken() );
            max = Math.max( trees[i], max );
        }

        long result = ParamaterSearch();

        System.out.println( result );
    }

    private static long ParamaterSearch() {
        long start = 0;
        long end = max;
        long result = 0;    // 결과 값.

        while ( start <= end ) {
            long mid = ( start + end ) / 2;
            long sum = 0;
            for (int i = 0; i < N; i++) {
                if( trees[i] >= mid )
                    sum += trees[i] - mid;
            }
            // sum == M -> 정답, 탈출
            // sum < M -> mid -> end
            // sum > M -> mid -> s , 정답 후보
            if( sum == M ) {
                result = mid;
                break;
            }
            else if( sum < M ) {
                // 톱을 낮춰야 한다.
                end = mid - 1;
            }
            else {  // sum > M
                // 톱을 높여야 한다.
                start = mid + 1;
                result = mid;
            }
        }
        // start가 upperbound가 되었거나, 마지노선인 end값이 되었다는 것.
        // return end;
        // return start - 1 ;
        return result;
    }
}
