package Days06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ_1922 {
    // 네트워크 연결

    // 모든 컴퓨터는 연결되어 있어야 한다. 직접적으로 연결되어 있지 않아도 연결된 경로가 있으면 된다. -> 연결 그래프
    // 컴퓨터를 연결하는 비용을 최소로 한다. -> MST
    // 각 컴퓨터를 연결하는데 필요한 비용이 주어졌을 때 모든 컴퓨터를 연결하는데 필요한 최소비용을 출력

    // 정점 정점  비용(가중치) 모양으로 입력이 주어진다. -> 간선 리스트

    // 컴퓨터의 수 N -> 정점 수
    // 연결하는 선의 수 M -> 간선 수

    static int N;
    static int M;

    static class Info {
        int node1;
        int node2;
        int cost;

        public Info(int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "node1=" + node1 +
                    ", node2=" + node2 +
                    ", cost=" + cost +
                    '}';
        }
    }

    static ArrayList< Info > list; // 간선 리스트
    static int[] group;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        N = Integer.parseInt( br.readLine() );
        M = Integer.parseInt( br.readLine() );

        group = new int[ N+1 ];

        StringTokenizer st;
        list = new ArrayList<>();
        // 간선 리스트로 그래프를 입력 받는다.
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer( br.readLine() , " ");
            int n1 = Integer.parseInt( st.nextToken() );
            int n2 = Integer.parseInt( st.nextToken() );
            int cost = Integer.parseInt( st.nextToken() );
            list.add( new Info(n1,n2,cost) );
        }

        // 정점 연결 초기화, self-loop
        // 사이클을 확인하기 위해 정점들이 같은 집합에 속해있는지 확인하기 위함.
        for (int i = 1; i <= N; i++) {
            group[i] = i;
        }

        // 간선들을 모두 정렬한다. ( 가중치 오름차순 )
        Collections.sort(list, (o1, o2) -> Integer.compare( o1.cost, o2.cost ));
//        for ( Info info : list) {
//            System.out.println( info.toString() );
//        }

        int ConnectCount = 0;   // 연결된 간선 수

        int result = 0;         // 총 컴퓨터가 연결된 비용 값.

        // 간선 리스트를 확인하며, 가중치가 가장 작은 간선들부터 그래프를 구성.
        // list는 가중치를 기준으로 오름차순 정렬되어 있으므로 인덱스가 낮은 것 부터 반복하면 된다.
        for (int i = 0; i < M; i++) {
            // 총 N-1 개의 간선을 연결하면 종료.
            if( ConnectCount == N - 1 )
                break;

            Info Current = list.get( i );   // 간선 리스트에서 현재 참조하고 있는 부분.

            // Union을 할 때, 두 점을 Find를 통해 값을 비교하여 값이 같으면 같은 집합으로 간주 -> 사이클이 생기므로 다음 반복 continue
            if( Find( Current.node1 ) == Find( Current.node2 ) )
                continue;

            // 값이 다르면 Union ( 사이클을 없애기 위해 )
            Union( Current.node1, Current.node2 );
            ConnectCount++;
            result += Current.cost;
        }
        System.out.println( result );
    }

    // Union - Find 자료구조를 사용한다.

    private static void Union( int n1, int n2 ) {
        int aRoot = Find( n1 );
        int bRoot = Find( n2 );

        group[aRoot] = bRoot;
    }

    private static int Find( int n1 ) {
        if( group[n1] == n1 )
            return n1;
        else
            return group[n1] = Find( group[n1] );
    }
}
