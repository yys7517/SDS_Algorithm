package Days03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BOJ_11279 {
    // 최대 힙

    static int N;
    static BufferedReader br;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();

        N = Integer.parseInt( br.readLine() );

        // Heap을 사용한 풀이
        /*
        MaxHeap maxHeap = new MaxHeap();

        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt( br.readLine() );
            if( x == 0 ) {
                sb.append( maxHeap.delete() ).append('\n');
            }
            else
                maxHeap.insert( x );
        }
         */

        // 우선순위 큐를 사용한 풀이
        // 내림차순 정렬. 수가 큰 값이 우선순위 높아짐. <-> 오름차순 정렬 Integer.compare( o1,o2 )
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare( o2,o1 ));
        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt( br.readLine() );
            if( x == 0 ) {
                if( !pq.isEmpty())
                    sb.append( pq.poll() ).append('\n');
                else
                    sb.append( 0 ).append('\n');
            }

            else
                pq.add( x );
        }

        System.out.println( sb );
    }
}

class MaxHeap {
    List<Integer> list;

    // 배열이 비어있는 경우 0 값을 출력해야하기 때문
    public MaxHeap() {
        list = new ArrayList<>();
        list.add( 0 );
    }

    public void insert( int val ) {
        // 1. leaf 마지막에 삽입.
        // list.size() - 1 = lastIdx
        list.add( val );

        int currentIdx = list.size() - 1;
        int parentIdx = currentIdx / 2;

        while ( true ) {
            // 본 노드가 루트노드이거나, 힙 조건을 만족하는 경우 ( MaxHeap )
            if( parentIdx == 0 || list.get( parentIdx ) >= list.get( currentIdx ) ) {
                break;
            }
            else {
                // 부모노드의 값이 더 작은 경우
                int temp = list.get( parentIdx );
                list.set( parentIdx , list.get( currentIdx ));
                list.set( currentIdx, temp );

                // 현재 노드가 부모가 되고,
                currentIdx = parentIdx;
                // 그 부모의 인덱스를 다시 구해준다.
                parentIdx = currentIdx / 2;
            }
            // 힙 조건을 만족하지 못하는 경우

        }
    }
    public int delete() {
        // 리스트의 크기가 1일 때, 빈 값으로 설정해두었던 0 값을 return 해준다.
        if( list.size() == 1 ) {
            return list.get( 0 );
        }
        else {
            // 루트 노드 값을 top에 담는다.
            int top = list.get( 1 );    // remove를 사용하지 않는다. remove하면, 루트의 왼쪽 자식이 루트 노드 위치에 오게 된다.
            // 루트 노드를 삭제하고, 마지막 leaf 노드를 루트 노드 위치에
            // 루트 노드에 위치하고, 다시 Heap을 정렬한다.
            list.set( 1, list.get( list.size() - 1 ));
            list.remove( list.size() - 1);

            int currentPos = 1; // 현재 위치
            while ( true ) {
                // 언제까지 반복?
                int leftPos = currentPos * 2;       // 왼쪽 자식
                int rightPos = currentPos * 2 + 1;  // 오른쪽 자식.

                // 왼쪽 자식의 인덱스가 list의 마지막 인덱스 를 넘어섰을 경우 == 자식이 없다는 뜻.
                // leaf에 도착했거나 ?
                if( leftPos > list.size() - 1  ) {
                    break;
                }

                // 왼쪽 자식부터 확인.
                int maxValue = list.get( leftPos );
                int maxPos = leftPos;

                // 오른쪽 자식의 인덱스가 리스트의 크기보다 작다 == Heap안에 나의 오른쪽 자식이 존재한다.
                // 왼쪽 자식과 비교, 자식들 중 큰 값을 선정.
                if( rightPos < list.size() && list.get( rightPos ) > maxValue ) {
                    maxValue = list.get( rightPos );
                    maxPos = rightPos;
                }

                // 자식들 중 큰 값이 부모의 값 보다 클 경우 ?
                // swap
                if( maxValue > list.get( currentPos ) ) {
                    int temp = list.get( currentPos );
                    list.set( currentPos, maxValue );
                    list.set( maxPos, temp );

                    currentPos = maxPos;
                }
                // 힙 조건을 만족할 경우
                else
                    break;
            }

            return top;
        }
    }

}
