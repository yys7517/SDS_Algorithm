package Days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1920 {
    // 수 찾기

    static int N;
    static int M;

    static int[] arr;
    static int[] find;

    static BufferedReader br;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();

        StringTokenizer st;

        N = Integer.parseInt( br.readLine() );
        st = new StringTokenizer( br.readLine(), " ");

        arr = new int[ N ];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt( st.nextToken() );
        }

        M = Integer.parseInt( br.readLine() );
        st = new StringTokenizer( br.readLine(), " ");

        find = new int[ M ];
        for (int i = 0; i < M; i++) {
            find[i] = Integer.parseInt( st.nextToken() );
        }

        Arrays.sort( arr );

        for (int i = 0; i < M; i++) {
            int key = find[i];
            if( BinarySearch(arr,key) )
                sb.append( 1 ).append("\n");
            else
                sb.append( 0 ).append("\n");
        }

        System.out.println( sb );
    }

    private static boolean BinarySearch(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;

        while ( low <= high ) {
            int mid = ( low + high ) / 2;

            if( key == arr[mid] )
                return true;
            else {
                if( arr[mid] > key ) {
                    high = mid - 1;
                }
                else
                    low = mid + 1;
            }
        }
        return false;
    }
}
