package Days03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1202 {
    // 보석 도둑

    // 보석 N개
    // 가방 K개
    // 가방에는 최대 한 개의 보석만 담을 수 있다.

    // 훔칠 수 있는 보석의 최대 가격을 구하라.
    static int N;
    static int K;

    static long[] bags;
    static Jewerl[] jewerls;

    static BufferedReader br;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st;
        st = new StringTokenizer( br.readLine(), " ");
        N = Integer.parseInt( st.nextToken() );
        K = Integer.parseInt( st.nextToken() );

        jewerls = new Jewerl[ N ];
        bags = new long[ K ];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer( br.readLine(), " ");
            int weight = Integer.parseInt( st.nextToken() );
            int price = Integer.parseInt( st.nextToken() );
            jewerls[i] = new Jewerl( weight,price );
        }

        for (int i = 0; i < K; i++) {
            bags[i] = Long.parseLong( br.readLine() );
        }

        Arrays.sort( bags );     // 가방 무게 오름차순 정렬.
        Arrays.sort( jewerls, Comparator.comparingInt( Jewerl::getWeight ));    // 보석 무게 오름차순 정렬
        // 보석은 무게 오름차순으로 정렬하여야 한다. 그래야 가방에 넣을 수 있는 보석을 빨리 넣을 수 있다.

        // 보석 가격 높은 값 기준 힙
        PriorityQueue<Jewerl> pq = new PriorityQueue( Comparator.comparingInt(Jewerl::getPrice).reversed() );
        // 우선 순위 큐 ( 힙 )에 넣을 때만, 가격 내림차순으로 하여 보석을 집어 넣는다.
        // 가격이 높은 보석이 우선 순위를 높게 가진다.

        int jIndex = 0;
        long result = 0;

        // 1. 남은 가방 중 제일 작은 가방을 선택. <- 정렬
        for (int i = 0; i < bags.length; i++) {
            long bag = bags[i];

            // 2. 선택된 가방에 넣을 수 있는 남은 보석 중 가장 비싼 보석을 선택. <- 힙을 사용.
            while ( jIndex < N && jewerls[jIndex].weight <= bag ) {
                pq.add( jewerls[jIndex++] );
            }
            // 가방에 넣을 수 있는 보석이 존재하면
            if( !pq.isEmpty() ) {
                result += pq.poll().price;  // 가장 우선순위가 높은 값을 뽑는다. ( 가격이 가장 비싼 보석을 선택해야 함. )
            }
        }

        System.out.println( result );

    }
}
class Jewerl {
    int weight;
    int price;

    public Jewerl(int weight, int price) {
        this.weight = weight;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }
}
