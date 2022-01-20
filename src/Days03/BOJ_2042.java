package Days03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2042 {
    // 구간 합 구하기

    // Indexed tree
    // Top-down or Bottom-up

    // 수의 개수 N
    // 수의 변경이 일어나는 횟수 M
    // 구간의 합을 구하는 횟수 K

    static BufferedReader br;
    static StringBuilder sb;

    static int N;
    static int M;
    static int K;

    static int S;
    static long[] arr;
    static long[] tree;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer( br.readLine() , " ");
        N = Integer.parseInt( st.nextToken() );
        M = Integer.parseInt( st.nextToken() );
        K = Integer.parseInt( st.nextToken() );

        arr = new long[ N+1 ];
        for (int i = 1; i <= N; i++) {
            arr[i] = Long.parseLong( br.readLine() );
        }

        S = 1;
        while ( S < N ) {
            S *= 2;
        }// S = 8
        // System.out.println( S );
        tree = new long[ S * 2 ];

        init( 1, N , 1);

        // a의 값이 1이면 ? b번째 수를 c로 바꾸고
        // a의 값이 2이면 ? b번째 수부터 c번째 수까지의 합을 구하여 출력한다.
        for (int i = 0; i < M+K; i++) {
            st = new StringTokenizer( br.readLine(), " ");
            int a = Integer.parseInt( st.nextToken() );
            int b = Integer.parseInt( st.nextToken() );
            long c = Long.parseLong( st.nextToken() );

            if( a == 1 ) {
                long diff = c - arr[b];
                arr[b] = c;
                // Update(int left, int start, int node, int target, long diff)
                Update( 1, N,1, b, diff );
            }
            // a == 2
            else if( a == 2){
                // 루트노드부터 시작, b ~ c 합 구하기.
                //long sum( int left, int right, int node, int Queryleft, int Queryright )
                sb.append( sum( 1, N, 1 , b, (int)c ) ).append('\n');
            }
        }

        System.out.println( sb );

    }



    // left ~ right 구간 합 트리를 생성하는 메서드.
    // node  : 현재 인덱스
    private static long init(int left, int right, int node) {
        // left와 right값이 같을 때는 leaf 노드이다.
        // leaf 노드일 경우, left번째 데이터의 값과 같다. left : 1 , right : 1
        // 1 ~ 1 구간의 합 : 1번째 데이터.
        if( left == right ) {
            return tree[node] = arr[left] ; // == arr[right]
        }

        // leaf 노드가 아닌 내부 노드의 경우
        int mid = ( left + right ) / 2;

        // 두 개의 노드로 나눠진다.
        return tree[node] = init( left, mid, node * 2 ) + init( mid + 1, right, node * 2 + 1 );
        // 왼쪽 자식과 오른쪽 노드의 합이 자기 자신이 됨.
        // 왼쪽 자식은 left ~ mid, idx = node * 2
        // 오른쪽 자식은 mid + 1 ~ right, idx = node * 2 + 1
    }

    // 현재 노드는 left ~ right 의 구간 합을 가지고 있다.
    // 현재 노드 번호 : 현재 노드 번호
    // 구하고자 하는 구간 합 : Queryleft ~ Queryright
    private static long sum( int left, int right, int node, int Queryleft, int Queryright ) {
        // 범위 밖에 있는 경우
        // 구하고자 하는 범위의 시작점이 , 구간의 right 보다 큰 경우 : 구하고자 하는 구간보다 왼쪽에 있는 구간
        // 구하고자 하는 범위의 마지막점이, 구간의 left보다 작은 경우 : 구하고자 하는 구간보다 오른쪽에 있는 구간.
        if( Queryleft > right || Queryright < left ) {
            return 0;
        }
        // 범위 안에 있는 경우 ( 구하고자 하는 구간이 완전히 구간을 품은 경우 )
        if( Queryleft <= left && Queryright >= right ) {
            return tree[node];
        }
        // 그렇지 않다면 두 부분으로 나누어 합을 구하기
        int mid = ( left + right ) / 2;
        return sum( left, mid, node * 2, Queryleft, Queryright ) +
                sum( mid + 1, right, node * 2 + 1, Queryleft, Queryright );
    }

    // target : 구간 합을 수정하고자 하는 노드
    // diff : 수정할 값
    private static void Update(int left, int right, int node, int target, long diff) {
        // target이 범위 밖에 있을 경우.
        if( target < left || target > right ) {
            return;
        }

        // 범위 안에 있으면 내려가면서 다른 원소들도 갱신
        tree[node] += diff;
        if( left == right )
            return;

        int mid = ( left + right ) / 2;
        Update( left, mid, node * 2, target, diff);
        Update( mid + 1, right, node * 2 + 1, target, diff);

    }

}
