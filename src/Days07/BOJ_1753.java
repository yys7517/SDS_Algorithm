package Days07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1753 {
    // 최단경로

    // 입력
    // 정점의 개수 V, 간선의 개수 E
    // 시작정점
    // 간선의 개수 만큼 간선 정보 입력

    // 출력
    // 시작정점에서 각 정점까지의 거리.

    static int V;
    static int E;

    static class Info {
        int node;
        int dist;

        public Info(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }

    }

    static ArrayList<Info>[] Map;
    static int[] Distance;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st = new StringTokenizer( br.readLine(), " ");

        V = Integer.parseInt( st.nextToken() );
        E = Integer.parseInt( st.nextToken() );

        Distance = new int[ V+1 ];
        Map = new ArrayList[ V+1 ];
        for (int i = 1; i <= V; i++) {
            Map[i] = new ArrayList<>();
        }

        int start = Integer.parseInt( br.readLine() );

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer( br.readLine(), " ");
            int A = Integer.parseInt( st.nextToken() );
            int B = Integer.parseInt( st.nextToken() );
            int dist = Integer.parseInt( st.nextToken() );

            Map[A].add( new Info( B, dist ));
        }

        Arrays.fill( Distance, INF );

        findShortestPath( start );

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= V ; i++) {
            if( Distance[i] == INF )
                sb.append("INF").append('\n');
            else
                sb.append( Distance[i] ).append('\n');
        }
        System.out.println( sb );
    }

    private static void findShortestPath(int start) {
        PriorityQueue< Info > pq = new PriorityQueue<>(new Comparator<Info>() {
            @Override
            public int compare(Info o1, Info o2) {
                return Integer.compare( o1.dist, o2.dist );
            }
        });

        Distance[ start ] = 0;
        pq.add( new Info( start,0 ) );

        while ( !pq.isEmpty() ) {
            Info now = pq.poll();

            // 큐에서 뽑은 정점까지의 거리가 지금까지 구한 거리 보다 크다면 skip
            if( now.dist > Distance[ now.node ] )
                continue;

            // 현재 정점 now.node의 { 인접 정점, 인접 정점까지의 거리 } => next
            for ( Info next : Map[now.node] ) {
                // 거리를 갱신한다.
                // next -> { 인접 정점, 인접 정점까지의 거리 }
                // now.node -> 현재 정점

                // 다음 정점까지의 거리가, 현재 정점에서 이동하는 거리보다 크다면 ?
                if( Distance[next.node] > Distance[now.node] + next.dist ) {
                    Distance[next.node] = Distance[now.node] + next.dist; //  더 작은 값으로 갱신한다.
                    pq.add( new Info( next.node, Distance[next.node ] ) );  // 새로 갱신된 거리로 큐에 삽입한다.
                }
            }
        }
    }
}
