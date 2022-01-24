package Days06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2252 {
    // 줄 세우기

    // N명의 학생들을 키 순서대로 줄을 세우려고 한다.

    // 모든 학생들의 키를 비교해서 정렬시키면 좋지만, 그러지 못한다. 일부 학생들만 키를 비교하는 결과가 주어졌을 때,
    // 줄을 세우는 프로그램 작성.

    // 사람 간의 키를 비교한 결과를 키가 작은 사람에서 키가 큰 사람으로 연결하는 간선으로 생각하면,
    // 주어진 문제의 그래프는 싸이클이 없는 방향 그래프(DAG)가 되는 것이다 -> 위상정렬

    // 위상정렬
    // 1. 최초 진입차수가 0인 정점들을 처리한다.
    // 2. 정점을 처리하면서, 인접 정점들의 진입차수를 -1 해준다.
    // 3. 이와 같은 과정을 반복하면서, 진입 차수가 0이 된 인접정점을 기준으로 계속하여 처리해준다.

    // 진입차수가 0이되는 정점들이 여러 개인 시점이 많으므로, 위상정렬의 결과는 유일하지 않다.

    static int N;   // 학생 수
    static int M;   // 비교 횟수

    static Queue<Integer> queue;        // 진입차수가 0인 정점들을 저장할 큐
    static int[] indegree;
    // static int[][] graph;
    static ArrayList<Integer>[] map;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st = new StringTokenizer( br.readLine() , " ");
        sb = new StringBuilder();

        N = Integer.parseInt( st.nextToken() );

        // *** 인접 리스트를 사용 시 각 요소들을 초기화 시켜줘야 한다 ***
        map = new ArrayList[ N+1 ];     // 인접 리스트를 구현한다.
        for (int i = 1; i <= N ; i++) {
            map[i] = new ArrayList<>();
        }
        // *******

        // 리스트의 인덱스는 시작 정점이고, 리스트의 요소는 ArrayList로 인접 정점의 집합이다.
        indegree = new int[ N+1 ];

        // 0. 진입차수가 0인 정점들을 저장할 큐를 만든다.
        queue = new LinkedList<>();

        M = Integer.parseInt( st.nextToken() );
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer( br.readLine() , " ");
            // A B 학생의 번호가 순서대로 주어진다.
            // 이는 학생 A를 학생 B의 앞에 세워야 한다는 것이다.
            int A = Integer.parseInt( st.nextToken() );
            int B = Integer.parseInt( st.nextToken() );
            map[A].add( B );
            indegree[B]++;
        }

        // 1. 최초 진입차수가 0인 정점들을 큐에 넣는다.
        for (int i = 1; i <= N ; i++) {
            if( indegree[i] == 0 )
                queue.add( i );
        }

        // 2. 큐가 빌 때까지 반복한다.
        while ( !queue.isEmpty() ) {
            int poll = queue.poll();
            sb.append(poll).append(" ");
            // 큐에서 꺼낸 정점의 인접한 정점들의 진입 차수를 -1 한다.
            for ( int V : map[poll] ) {
                indegree[V]--;
                // 이 때 대상 정점의 진입차수가 0이 되면 해당 정점을 큐에 넣는다.
                if( indegree[V] == 0 )
                    queue.add( V );
            }
        }
        System.out.println( sb );
    }
}
