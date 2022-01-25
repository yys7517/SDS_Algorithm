package Days07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_14675 {
    // 단절점과 단절선

    /*
        * 단절점(cut vertex) : 해당 정점을 제거하였을 때, 그 정점이 포함된 그래프가 2개 이상으로 나뉘는 경우, 이 정점을 단절점이라 한다.
        * 단절선(bridge) : 해당 간선을 제거하였을 때, 그 간선이 포함된 그래프가 2개 이상으로 나뉘는 경우, 이 간선을 단절선이라 한다.
        *
        *
        * 트리의 정점 개수 N ( 트리의 정점은 1번부터 N번까지 존재 )  => 트리는 무방향 그래프.
        * 간선의 정보 a, b가 주어진다. 이는 a정점과 b정점이 연결되어 있다는 뜻
        * 다음 줄에는 질의의 개수 q
        *
        * 질의 t k가 주어진다
        * t가 1일 때는 k번 정점이 단절점인지 ?
        * t가 2일 때는 입력에서 주어지는 k번째 간선이 단절선인지 ?


        각각은 개행으로 구분하며, 질의가 맞다면 ‘yes’를, 질의가 틀리면 ‘no’를 출력
     */

    static int N;

    static ArrayList<Integer>[] Tree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));

        N = Integer.parseInt( br.readLine() );
        Tree = new ArrayList[ N+1 ];
        for (int i = 1; i <= N; i++) {
            Tree[i] = new ArrayList<>();
        }
        StringTokenizer st;

        // 트리 정보, a-b는 연결되어 있음을 뜻함.
        // 입력으로 주어지는 정보는 트리가 됨을 보장.
        // 트리의 특징 - N개의 정점, N-1개의 간선을 갖고, 사이클이 없으며, 모든 정점이 연결되어 있다.

        for (int i = 0; i < N-1; i++) {
            st = new StringTokenizer( br.readLine(), " ");
            int a = Integer.parseInt( st.nextToken() );
            int b = Integer.parseInt( st.nextToken() );

            Tree[a].add( b );
            Tree[b].add( a );
        }

        int q = Integer.parseInt( br.readLine() );  // 질의 수
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer( br.readLine(), " ");
            int t = Integer.parseInt( st.nextToken() );
            int k = Integer.parseInt( st.nextToken() );



            if( t == 1 ) {
                // k번 정점이 단절점인지 ?
                if( isCutVertex( k ) ) {
                    sb.append("yes").append('\n');
                }
                else
                    sb.append("no").append('\n');
            }
            // 트리에게 단절선을 찾는 방법
            // 트리에서는 어떤 간선을 하나라도 지울 경우, 2개의 트리로 나눠진다.
            else { // t == 2
                sb.append("yes").append('\n');
            }
        }

        System.out.println( sb );
    }



    // 단절점을 찾는 방법
    // 1. 자식이 2개 이상인가 ? => 연결된 정점이 2개 이상인가 ?

    //      루트 노드, 리프 노드를 지우는 경우

    //         1. 루트 노드의 경우, 자식이 2개 이상이 아니라면, 단절점이 아니다. => no
    //                            루트노드의 자식이 2개 이상이라면? 단절점이다 => yes

    //         2. 리프 노드의 경우는, 트리에서는 연결된 정점이 1개이다.          => no

    //    2. 루트 노드, 리프 노드가 아닌 노드를 지우는 경우
    //          연결된 정점이 2개 이상인지 확인한다.
    //          루트노드와 리프노드가 아닌 다른 노드들은 트리에서 연결된 정점이 모두 2개 이상이 된다. => 단절점.


    // 2. 단절점의 인접 정점이 k번 정점보다 앞선 DFS 방문 순서의 정점으로 가는 경로가 없는가 ?
    // 트리의 경우, 계층 관계를 가지므로, 자식의 개수와 상관 없이 앞서 방문된 노드로 갈 수 있는 경로가 없다.
    // ==> 따라서 트리에서 단절점을 찾을 경우, 연결된 정점의 개수만 확인하면 된다.

    // k번 정점이 단절점인지 확인하는 메서드
    private static boolean isCutVertex(int k) {
        // 트리의 경우는 k번째 정점을 지웠을 때, k번 째보다 앞서 방문된 정점으로는 이동할 수 없다.
        int count = 0;
        // k번 정점과 연결된 정점 val의 개수
        for ( Integer val : Tree[k] ) {
            count++;
        }

        if( count >= 2 )
            return true;

        return false;
    }
}
