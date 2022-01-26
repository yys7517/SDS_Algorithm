package Days08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_5719 {
    // 거의 최단경로

    /* 접근법 - 최단 경로를 구하는데, Real 최단 경로가 아니라 거의 최단 경로를 구해야 한다.
        거의 최단 경로란 ? 최단 경로에 포함되지 않는 경로로만 이루어진 경로 중 가장 짧은 것을 말한다.

        다익스트라 알고리즘

        다익스트라 알고리즘을 2회 수행한다 !

        첫 수행 시, Real 최단 경로가 나올 것이다.

        Real 최단 경로에 나온 경로들을 제외 후, 다익스트라 알고리즘을 두 번째 수행하면 ?
        ==> 우리는 거의 최단 경로를 구할 수 있다.

        * 1. 먼저 다익스트라 알고리즘을 사용해서, 출발지에서 갈수 있는 모든 노드들에 대해서 최단 경로를 구한다.

        * 2. 최단거리를 구할때 사용하는 모든 간선들을 체크한다.
               도착점에서부터 거꾸로 되돌아오면서 체크. => BackTracking ( DFS의 일종 )

        * 3. 2에서 사용하지 않은 간선들만을 가지고 다시 최단거리를 구한다.
     */

    static int N;   // 장소의 수 = 정점의 수
    static int M;   // 도로의 수 = 간선의 수

    static int Start;   // 시작점
    static int Destination;   // 도착점

    static class Info implements Comparable<Info>{
        int v;
        int cost;

        public Info(int v, int cost ) {
            this.v = v;
            this.cost = cost;
        }

        @Override
        public int compareTo(Info o) {
            return Integer.compare( cost, o.cost );
        }
    }
    static ArrayList<Info> [] graph;
    static int[] Distance;
    static boolean[][] isShortest;      // isShortest[a][b] = true, a -> b로 가는 간선이 최단 경로를 지나는 간선이다.
    static ArrayList<Integer> [] BackTracking;  // 최단 경로의 정점을 Tracking 하는 list.
                                            // 사용 예) Tracking[next].add(now) =>

    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        while ( true ) {
            st = new StringTokenizer( br.readLine(), " ");
            N = Integer.parseInt( st.nextToken() );
            M = Integer.parseInt( st.nextToken() );

            if( N == 0 && M == 0 )
                break;

            st = new StringTokenizer( br.readLine(), " ");
            Start = Integer.parseInt( st.nextToken() );
            Destination = Integer.parseInt( st.nextToken() );


            graph = new ArrayList[ N ];
            for (int i = 0; i < N ; i++) {
                graph[i] = new ArrayList<>();
            }

            isShortest = new boolean[ N ][ N ];

            Distance = new int[ N ];


            BackTracking = new ArrayList[ N ];

            // 그래프 정보 입력.
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer( br.readLine(), " ");

                int u = Integer.parseInt( st.nextToken() );
                int v = Integer.parseInt( st.nextToken() );
                int cost = Integer.parseInt( st.nextToken() );

                graph[u].add( new Info( v, cost ) );
            }

            // 다익스트라 알고리즘 첫 번 째 수행.
            findShortestPath( Start );

            // 각 테스트 케이스에 대해서, 거의 최단 경로의 길이를 출력한다.
            // 만약, 거의 최단 경로가 없는 경우에는 -1을 출력한다.
            if( Distance[ Destination ] == INF ) {
                sb.append(-1).append('\n');
                continue;
            }

            // 도착지에서 거꾸로 가며 체크.
            findShortestEdge( Destination , Start ); // 최단 경로 간선을 제외 후
            findShortestPath( Start );    // 두 번째 다익스트라 알고리즘 실행.

            // 각 테스트 케이스에 대해서, 거의 최단 경로의 길이를 출력한다.
            // 만약, 거의 최단 경로가 없는 경우에는 -1을 출력한다.
            if( Distance[ Destination ] == INF ) {
                sb.append(-1).append('\n');
                continue;
            }
            else
                sb.append( Distance[Destination] ).append('\n');
        }
        System.out.println( sb );
    }

    private static void findShortestEdge(int now, int end) {
        if( now == end )    // BackTracking이 시작점에 도착하였으면 재귀호출 종료. 초기 호출 시 now = Destination, end = Start
            return;

        for ( int next : BackTracking[now] ) {
            if( isShortest[next][now] == false ) {
                isShortest[next][now] = true;
                findShortestEdge( next, end );  // 부모를 시작으로 하여, 다음 정점을 찾아 BackTracking( DFS )
            }
        }
    }

    // 다익스트라 알고리즘 수행
    private static void findShortestPath( int start ) {
        // Tracking 리스트를 매 번 초기화 해줘야 한다.
        // 다익스트라를 2번 수행할 수 도 있음.
        // 수행마다 Tracking 리스트와, Distance 거리 배열을 초기화 시켜준다.
        for (int i = 0; i < N ; i++) {
            BackTracking[i] = new ArrayList<>();
        }
        Arrays.fill( Distance, INF );

        PriorityQueue<Info> pq = new PriorityQueue<>();
        Distance[ start ] = 0;
        pq.add( new Info( start, 0 ) );

        while ( !pq.isEmpty() )  {
            Info now = pq.poll();

            if( now.cost > Distance[now.v] )
                continue;

            for ( Info next : graph[now.v] ) {
                if( isShortest[now.v][next.v] == true ) // 최단 경로의 간선이면 skip
                    continue;

                // 다익스트라 알고리즘 - 더 짧은 경로이므로 거리 값 갱신.
                if( Distance[next.v] > Distance[now.v] + next.cost ) {
                    // 더 짧은 경로를 찾았으므로, 새로운 부모를 발견한 것.
                    // BackTracking 목록 지우고, 새로운 부모 추가하기.
                    BackTracking[next.v].clear();
                    BackTracking[next.v].add(now.v);
                    Distance[next.v] = Distance[now.v] + next.cost;
                    pq.add( new Info( next.v , Distance[next.v]) );
                }

                // now -> next 가 최단 경로의 대상일 경우, 거꾸로 탐색한다.
                // BackTracking 대상에 추가 ( next -> now )
                if( Distance[next.v] == Distance[now.v] + next.cost )
                    BackTracking[next.v].add(now.v);
            }
        }


    }
}
