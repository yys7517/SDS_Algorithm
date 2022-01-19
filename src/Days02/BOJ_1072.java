package Days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_1072 {
    // 게임

    // 형택이는 앞으로 모든 게임에서 지지 않는다.

    // 게임 횟수 : X
    // 이긴 게임 : Y
    // 승률 : Z %

    // X와 Y가 주어질 때, 형택이가 게임을 최소 몇 번 해야 Z 가 변하는지 구하는 프로그램

    // 게임을 할 때 마다 X와 Y가 둘 다 1씩 증가. -- 유의

    static long X;
    static long Y;

    static int Z;
    // Z = ( Y / X )* 100

    //  Z = ( Y+1 / X+1 )* 100
    //  Z = ( Y+2 / X+2 )* 100
    //  Z = ( Y+3 / X+3 )* 100
    // ...

    static BufferedReader br;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st = new StringTokenizer( br.readLine(), " ");

        X = Long.parseLong( st.nextToken() );
        Y = Long.parseLong( st.nextToken() );

        Z = getPercent( X, Y );
        // System.out.println( Z );

        System.out.println( BinarySearch() );
        // System.out.println( Z );
    }

    // Z 값을 바꾸게 하는 최소 플레이 회수 ..
    // 1% 가 변하려면 ?
    private static long BinarySearch() {
        long start = 0;
        long end = 1000000000;
        long ans = -1;  // ans 값은 while문에서 Z를 변하게 하는
        // mid값을 찾지 못하면 -1로 유지되고 -1을 반환한다.

        while ( start <= end ) {
            long mid = ( start + end ) / 2;

            // Z값이 변하면 그 때의 mid 값을 ans에 담는다.
            if( getPercent( X + mid , Y + mid ) != Z ) {
                ans = mid;
                end = mid - 1;
                // return을 바로 하지 않는다. 최소의 mid 값을 찾아야 하므로, mid값을 더 낮춰서 반복해서 확인한다.
            }
            // Z값이 계속 유지되면, mid값이 더 적은 것, 게임을 더 많이 플레이 해야 함.
            else
                start = mid + 1;
        }

        return ans;
    }

    private static int getPercent( long x, long y ) {
        return (int) ( y * 100 / x );
        // return ( int ) ( ( y / x ) * 100 );  --> 부동소수점 오류.
        // 예를 들어 double 변수에 0.58을 저장하고 *100을 한다면 58이 나온다고 생각할 수 있지만
        // 컴퓨터는 이를 저장하지 못하고 0.5799999로 저장하기 때문에 실제로는 57이 반환된다.
    }
}
