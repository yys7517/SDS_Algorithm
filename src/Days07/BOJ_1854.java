package Days07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_1854 {
    // K번째 최단경로 찾기

    // 도시의 개수 n    ==> 정점
    // 도로의 수 m      ==> 간선
    // 그리고 구하는 K 번째 최단경로 K

    // 그래프 정보가 주어지고, a b c
    // a는 출발
    // b는 도착
    // c는 소요시간

    // 출력 : 1번 도시에서 출발하여 1 ~ n 번 도시로 도착하는 K번째 최단 경로를 개행을 하며 출력한다.
    // K 번째 최단 경로는 0이 아닐 수 있음에 유의한다.
    // 각 도시마다 K번째 최단 경로가 없을 시 -1을 출력한다.

    static int N,M,K;
    static ArrayList< Info >[] Map;
    static PriorityQueue<Integer>[] Distance;

    static class Info {
        int node;
        int dist;

        public Info(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer( br.readLine() , " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        Map = new ArrayList[ N+1 ];
        Distance = new PriorityQueue[ N+1 ];

        for (int i = 1; i <= N ; i++) {
            Map[i] = new ArrayList<>();
            Distance[i] = new PriorityQueue<>((o1, o2) -> Integer.compare( o2, o1 ));
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer( br.readLine(), " ");
            int a = Integer.parseInt( st.nextToken() );
            int b = Integer.parseInt( st.nextToken() );
            int c = Integer.parseInt( st.nextToken() );

            // a -> b, c의 시간이 걸린다.
            Map[a].add( new Info( b,c ) );
        }

        // 1번 도시에서 출발하여 각 도시까지 가는 최단 경로를 모두 우선순위 큐에 담는다.
        // 이 우선순위 큐에서 K번째 최단경로를 뽑아내기 위함이다.
        findShortestPath( 1 );


        for (int i = 1; i <= N ; i++) {
            // i번 도시까지의 최단경로 모음의 크기가 K일 때 값을 뽑는다.( 내림차순 되어있으므로, 크기가 K일 때, 값을 뽑으면 K번째 최단 경로 값이 나옴. )
            if( Distance[i].size() == K )
                sb.append( Distance[i].peek() ).append('\n');
            else
                sb.append( -1 ).append('\n');
        }

        System.out.println( sb );
    }

    private static void findShortestPath( int start ) {
        PriorityQueue< Info > pq = new PriorityQueue<>((o1, o2) -> Integer.compare( o1.dist, o2.dist ));
        Distance[start].add( 0 );
        pq.add( new Info( start, 0 ));

        while ( !pq.isEmpty() ) {
            Info now = pq.poll();
            // 내림차순 정렬이 되어있기 때문에
            // Distance[ now.node ].peek() ==> 1번 도시에서 now.node번 도시까지 이동하는 최단경로 중, 가장 큰 값
            // now.dist => pq에서 꺼내온 now.node 도시까지 가는 거리 값이 더 크면 ?
            // 최단 경로를 구하는 다익스트라 알고리즘이기 때문에, 가장 큰 값보다 큰 거리 값이 들어오면 skip.
            if( now.dist > Distance[now.node].peek() )
                continue;

            // next => 인접 정점.
            // next.dist => 인접정점까지의 거리.
            for ( Info next: Map[now.node] ) {
                // 인접 정점까지의 거리 중 K 번째 거리를 구하기 위해서 K개 만큼의 최단 경로를 구해줘야 함.
                if( Distance[next.node].size() < K ) {  // K개보다 작으면.
                    Distance[next.node].add( now.dist + next.dist );
                    pq.add( new Info( next.node, now.dist + next.dist ));
                }
                // 인접 정점까지의 거리 중 가장 큰 값         ==> Distance[ next.node ].peek()
                // 현재 정점을 거쳐서 인접 정점까지 가는 거리 ==> now.dist + next.dist
                // 사이즈가 K와 같거나 K보다 클 때
                else if( Distance[next.node].peek() > now.dist + next.dist ) {
                    Distance[next.node].poll();                         // 큰 값을 버린다. 최단경로에 같은 정점이 여러 번 포함되어도 되기 때문에.
                    Distance[next.node].add( now.dist + next.dist );    // 작은 값, 최단 경로 값을 삽입.
                    pq.add( new Info( next.node, now.dist + next.dist));
                }
            }

        }
    }
}
