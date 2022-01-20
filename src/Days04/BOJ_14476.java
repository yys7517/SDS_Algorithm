package Days04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14476 {
    // 최대공약수 하나 빼기
    static int N;

    static BufferedReader br;
    static StringBuilder sb;

    static int[] nums;
    static int[] gcdLtoR;
    static int[] gcdRtoL;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();

        N = Integer.parseInt( br.readLine() );

        nums = new int[ N ];
        gcdLtoR = new int[ N ];
        gcdRtoL = new int[ N ];

        StringTokenizer st = new StringTokenizer( br.readLine() , " ");
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt( st.nextToken() );
        }

        // 최대공약수를 누적합 방식으로 구한다.
        //      a                b                  c                      d                     e
        //   nums[0]         gcd(ab)             gcd(abc)                gcd(abcd)           gcd(abcde)
        //  gcd(abcde)      gcd(bcde)           gcd(cde)                 gcd(de)             nums[ N-1 ]


        gcdLtoR[0] = nums[0];           // LR 포인터
        gcdRtoL[0] = nums[ N - 1 ];     // RL 포인터

        for (int i = 1; i < N; i++) {
            gcdLtoR[i] = GCD( gcdLtoR[ i-1 ], nums[i] );
        }

        for (int i = N - 2 ; i >= 0 ; i-- ) {
            gcdRtoL[i] = GCD( gcdRtoL[i + 1], nums[i] );
        }

        int max = 0 ;
        int maxIndex = 0;

        // k값에 따라 변하는 최대공약수 값.
        //      a                b                  c                      d                     e
        //   nums[0]         gcd(ab)             gcd(abc)                gcd(abcd)           gcd(abcde)                 gcdLtoR
        //  gcd(abcde)      gcd(bcde)           gcd(cde)                 gcd(de)             nums[ N-1 ]                gcdRtoL
        //  K 는 a b c d e 중 특정 수를 빼는 것이다.
        for (int i = 0; i < N; i++) {
            int gcd = 0;
            if ( i == 0 ) {                 // 첫 번째 수를 뺀 나머지 최대공약수 gcd( bcde )
              gcd = gcdRtoL[1];
            } else if ( i == N - 1 ) {      // 마지막 수를 뺀 나머지 최대공약수 gcd( abcd )
                gcd = gcdLtoR[ N - 2 ];
            } else {                        // 나머지 수 : ex ) 중간 수를 뺀다면  gcd( abde ) => gcd( gcd(ab), gcd(de) )
                gcd = GCD( gcdLtoR[ i - 1 ], gcdRtoL[ i + 1 ] );
            }

            // nums[i] 값이 k가 된다.
            // k를 빼고 구한 gcd가 k에 나눠지지 않아야 하고, 최대의 값을 가져야 한다.
            if( nums[i] % gcd != 0 && gcd > max ) {
                max = gcd;              // 최대공약수 값.
                maxIndex = i;           // 최대의 최대공약수를 구하기 위한 K 값의 인덱스.
            }
        }

        // 최대공약수 값과 K 값을 출력
        // 최대공약수 값 = max, K 값 = nums[maxIndex]
        if( max == 0 )
            sb.append( max );
        else
            sb.append( max ).append(" ").append( nums[maxIndex] );

        System.out.println( sb );
    }
    // gcd(a,b) == gcd(b, a%b), stop when a % b == 0, b -> gcd
    private static int GCD( int a, int b ) {
        while ( b != 0  ) {
            int r = a % b;
            a = b;
            b = r;
            // r의 값이 0이되면, b의 값이 0이된다.
            // r의 값이 0이 될 때, b의 값이 최대공약수인데, b의 값은 a에 들어가있다.
        }
        return a;
    }
}
