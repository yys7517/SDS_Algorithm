package Days08.LCA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_11437 {
    // LCA - 최소 공통 조상 - 두 노드의 가장 깊은 공통된 조상 노드 == 두 노드의 공통된 조상 중에서 가장 가까운 조상노드.

    /*
        - 문제 -
        * N(2 ≤ N ≤ 50,000)개의 정점으로 이루어진 트리
        * 트리의 각 정점은 1번부터 N번까지 번호가 매겨져 있으며, 루트는 1번
        * 두 노드의 쌍 M(1 ≤ M ≤ 10,000)개가 주어졌을 때 두 노드의 LCA는 몇 번 정점인지 구하라.
     */

    /*
        - 접근법 및 풀이 -
        1. 모든 노드에 대한 깊이를 다 구한다.
        2. 최소 공통 조상을 찾은 두 노드를 확인.
            (1). 먼저 두 노드의 깊이가 동일해질 때까지 거슬러 올라간다.
            (2). 이후에 부모가 같아질 때까지 반복적으로 두 노드의 부모 방향으로 거슬러 올라간다.

        2번 과정을 통해 두 노드 a,b의 LCA를 구할 수 있다.
        M 번의 LCA(a,b) 연산에 대하여 2번 과정을 M번 반복하면 된다.
        ==> O( M * N ) == O ( 5만 * 1만 )
     */

    static int N;
    static int M;

    static boolean[] checked;
    static int[] depth;

    static int[] parent;

    static ArrayList<Integer> [] Tree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt( br.readLine() );

        Tree = new ArrayList[ N+1 ];
        for (int i = 1; i <= N; i++) {
            Tree[i] = new ArrayList<>();
        }


        // 트리의 정점이 N개이면, 간선은 N-1개이다.
        // 간선 정보 입력.
        for (int i = 0; i < N-1; i++) {
            st = new StringTokenizer( br.readLine() , " ");
            int a = Integer.parseInt( st.nextToken() );
            int b = Integer.parseInt( st.nextToken() );
            Tree[a].add( b );
            Tree[b].add( a );
        }
        checked = new boolean[ N+1 ];
        depth = new int[ N+1 ];
        parent = new int[ N+1 ];

        // 연산횟수
        M = Integer.parseInt( br.readLine() );

        // 트리에서의 깊이 = 루트 노드에서의 특정 노드까지의 간선의 개수와 같다.....!!!
        // 1번 정점을 시작으로, 깊이는 0에서 부터 시작.
        dfs( 1 , 0 );

        for (int i = 0; i < M; i++) {
           st = new StringTokenizer( br.readLine() , " ");
           int a = Integer.parseInt( st.nextToken() );
           int b = Integer.parseInt( st.nextToken() );
           sb.append( getLCA(a,b) ).append('\n');
        }
        System.out.println( sb );
    }

    // 1. 모든 노드에 대한 깊이를 다 구한다. => DFS
    private static void dfs( int v, int d ) {
        checked[ v ] = true;
        depth[ v ] = d;

        // v의 인접 정점 next
        for ( int next : Tree[v] ) {
            if( checked[next] == true ) // 이미 방문한 정점이면 skip
                continue;

            // 방문되지 않은 정점이라면,
            parent[ next ] = v;     // 그의 부모를 v로 설정, 재귀호출
            dfs( next, d+1 ); // next 정점을 시작점 으로하고, 깊이 + 1
        }
    }

    // 1. 모든 노드에 대한 깊이를 다 구한다. => BFS
    private static void bfs( int v ) {
        Queue<Integer> queue = new LinkedList<>();
        checked[ v ] = true;
        depth[ v ] = 0; // 루트 노드의 깊이는 0이다.

        while ( !queue.isEmpty() ) {
            int now = queue.poll();

            for ( int next : Tree[now] ) {
                if( checked[ next ] == true )
                    continue;

                parent[next] = now;
                depth[next] = depth[now] + 1;   // 깊이 1 증가.
                queue.add( next );
            }
        }
    }

    // 2. 최소 공통 조상을 찾은 두 노드를 확인.
    //            (1). 먼저 두 노드의 깊이가 동일해질 때까지 거슬러 올라간다.
    //            (2). 이후에 부모가 같아질 때까지 반복적으로 두 노드의 부모 방향으로 거슬러 올라간다.


    private static int getLCA( int a, int b ) { // ==> O(N)의 시간 복잡도.
        int result = 0;

        // (1). 먼저 두 노드의 깊이가 동일해질 때까지 거슬러 올라간다.
        while ( depth[a] != depth[b] ) {
            // 거슬러 올라간다.. ?
            if( depth[a] > depth[b] ) {
                // a가 b보다 더 깊다면 a를 올리자.
                a = parent[ a ];
            }
            else {
                // b가 a보다 더 깊다면 b를 올리자.
                b = parent[ b ];
            }
        }

        // (2). 이후에 부모가 같아질 때까지 반복적으로 두 노드의 부모 방향으로 거슬러 올라간다.
        while ( a != b ) {
            // a와 b가 같아질 때, 그 값이 LCA !!
            a = parent[ a ];
            b = parent[ b ];
        }
        // while문이 종료될 때 a와 b는 LCA가 된다.
        result = a;

        return result;
    }
}
