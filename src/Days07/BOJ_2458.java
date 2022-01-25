package Days07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2458 {
    // 키 순서

    // 모든 학생 중에서 키가 가장 작은 학생부터 자신이 몇 번째인지 알 수 있는 학생도 있고,
    // 그렇지 못한 학생들도 있다.

    // 자신의 키가 몇 번째인지 알 수 있는 학생들이 모두 몇 명인지 계산하여 출력하는 프로그램

    // 학생들의 수 N
    // 키를 비교한 연산의 수 M

    // 자신을 제외한 학생 수 : N - 1 이라고 할 수 있다.
    // 이 때, 자신보다 키가 작은 학생 + 자신보다 키가 큰 학생 == N - 1 일때,
    // 그 학생은 자신이 키가 몇 번째인지 알 수 있다.
    static int N;
    static int M;

    static class Student {
        int less ;   // 더 작은 사람 수
        int more ;   // 더 큰 사람 수

        public Student() {
            less = 0;
            more = 0;
        }
    }

    static ArrayList<Integer> [] lists;     // 인접 리스트 - 인덱스 : 작은 학생 , list : 자신보다 큰 학생 번호들.
    static Student[] Students;              // 1 ~ N 번 학생들,
                                            // less : 자신보다 작은 학생 수
                                            // more : 자신보다 큰 학생 수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        StringTokenizer st = new StringTokenizer( br.readLine(), " ");
        N = Integer.parseInt( st.nextToken() );
        M = Integer.parseInt( st.nextToken() );

        // 인접 리스트 사용.
        lists = new ArrayList[ N+1 ];
        Students = new Student[ N+1 ];

        for (int i = 1 ; i <= N ; i++) {
            lists[i] = new ArrayList<>();
            Students[i] = new Student();
        }


        for (int i = 0; i < M; i++) {
            st = new StringTokenizer( br.readLine(), " ");
            int a = Integer.parseInt( st.nextToken() );
            int b = Integer.parseInt( st.nextToken() );

            lists[a].add( b );
        }

        // 1 ~ N 번 학생이 자신보다 큰 학생을 찾는 BFS  작은 정점 -> 큰 정점    ( 유향 그래프 )
        // 찾았을 때, num 학생의 more++
        // 찾았을 때, 더 큰 학생의 less++
        for (int num = 1; num <= N ; num++) {
            Queue<Integer> queue = new LinkedList<>();
            boolean[] checked = new boolean[ N+1 ];     // i번 학생과 다른 학생과 키를 비교했는지 ?

            queue.add( num );
            checked[ num ] = true;

            while ( !queue.isEmpty() ) {
                int now = queue.poll();

                // lists[now] ==> now번호 학생보다 큰 학생번호 리스트.
                for ( Integer taller : lists[now] ) {
                    // 비교를 했다면 skip
                    if( checked[taller] )
                        continue;

                    checked[taller] = true;
                    queue.add( taller );

                    Students[ taller ].less++;
                    Students[ num ].more++;
                }
            }
        }

        int count = 0;  // 자신의 키가 몇 번째인지 아는 학생의 수

        // 자신을 제외한 학생 수 : N - 1 이라고 할 수 있다.
        for (int i = 1; i <= N ; i++) {
            // 이 때, 자신보다 키가 작은 학생 + 자신보다 키가 큰 학생 == N - 1 일때, 그 학생은 자신이 키가 몇 번째인지 알 수 있다.
            if( Students[i].less + Students[i].more == N-1 )
                count++;
        }

        System.out.println( count );

    }
}
