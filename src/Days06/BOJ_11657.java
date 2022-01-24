package Days06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_11657 {
    // 타임머신

    // N개의 도시         - 정점 수
    // M개의 버스         - 간선 수
    // A는 시작 도시      - 출발 정점
    // B는 시작 도시      - 도착 정점
    // C는 걸리는 시간    - 비용(가중치)

    // 1번 도시에서 출발
    // 어떤 도시로 가는 과정에서 시간을 무한히 오래 전으로 되돌릴 수 있다면 첫째 줄에 -1을 출력
    // => 음의 사이클을 만나면 -1 출력.

    // 그렇지 않다면 N-1개 줄에 걸쳐 각 줄에 1번 도시에서 출발해 2번 도시, 3번 도시, ..., N번 도시로 가는 가장 빠른 시간을 순서대로 출력
    // 해당 도시로 가는 경로가 없다면 -1 출력.

    static int N;
    static int M;

    static class Info{
        int node1;
        int node2;
        int cost;

        public Info(int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }
    }

    static long[] Cost;              // 비용 그래프      int로 할 시 출력초과가 난다.
    static ArrayList<Info> list;    // 간선 리스트

    static final long INF = 987654321;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer( br.readLine() , " ");
        N = Integer.parseInt( st.nextToken() );
        M = Integer.parseInt( st.nextToken() );

        list = new ArrayList<>();   // 간선 리스트.

        // 비용 그래프.
        Cost = new long[ N+1 ];
        for (int i = 1; i <= N; i++) {
            Cost[i] = INF;
        }

        // 간선 정보 입력.
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer( br.readLine() , " ");
            int A = Integer.parseInt( st.nextToken() );
            int B = Integer.parseInt( st.nextToken() );
            int C = Integer.parseInt( st.nextToken() );
            list.add( new Info(A,B,C) );
        }

        // 1번 정점에서 출발..
        // 1번 정점 값을 0으로 설정하여 시작하게 한다.
        Cost[1] = 0;

        // N-1 번 동안 반복 => 최단거리 계산은 "최대" 정점의 개수 -1 = 간선의 개수 만큼 반복한다.
        // 최단 거리를 구하는 것이기 때문에, 최대의 경우보다 더 반복해서는 안된다.
        for (int i = 1; i < N ; i++) {
            // 간선 리스트에서 간선들을 참조하여 계산한다.
            for (int j = 0; j < M; j++) {
                Info Current = list.get( j );   // 현재 참조 중인 간선.
                int from = Current.node1;
                int to = Current.node2;
                int cost = Current.cost;

                // 출발점이 무한정이면 건너뛴다. -> 출발점에 대한 정보가 없다는 뜻. => 이 때문에, 1번 정점의 비용을 0으로 초기화 한 것.
                if( Cost[from] == INF ) continue;

                // 도착점으로 가는 비용보다, 새로운 출발점에서 가는 비용이 더 적다면, 값을 업데이트 한다.
                if( Cost[to] > Cost[from] + cost )
                    Cost[to] = Cost[from] + cost;
            }
        }

        // 음의 사이클을 가진다면 -1 값만 출력.
        if( isNegativeCycle() )  {
            sb.append(-1);
        }
        else {
            // 1번 정점에서 출발하여 이동하는 비용을 출력해주는 것이므로.
            // 2번 정점 ~ N번 정점의 비용을 순회한다.
            for (int i = 2; i <= N ; i++) {
                if( Cost[i] == INF )        // 비용을 구할 수 없다면 -1
                    sb.append(-1).append('\n');
                else                        // 비용을 구했다면 값 출력.
                    sb.append( Cost[i] ).append('\n');
            }
        }
        System.out.println( sb.toString().trim() );
    }

    // N-1 번의 반복이 끝났음에도 배열의 값이 업데이트 된다면 ? => 음의 사이클을 가진다.
    // 업데이트 되지 않는다면 음의 사이클을 가지지 않는다.

    private static boolean isNegativeCycle() {
        for (int j = 0; j < M; j++) {
            Info Current = list.get(j);   // 현재 참조 중인 간선.
            int from = Current.node1;
            int to = Current.node2;
            int cost = Current.cost;

            if (Cost[from] == INF) continue;

            // 더 적은 비용이 한 번 더 발생하여 값을 업데이트 한다면 ? => 음의 사이클을 가진다.
            // 앞에서 N-1번의 반복을 마쳤기 때문에.
            if (Cost[to] > Cost[from] + cost)
                return true;
        }
        return false;
    }
}
