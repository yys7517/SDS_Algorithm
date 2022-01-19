package Days03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class BOJ_1927 {
    // 최소 힙

    static int N;
    static BufferedReader br;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();
        N = Integer.parseInt( br.readLine() );
        MinHeap minHeap = new MinHeap();

        for (int i = 0; i < N; i++) {
            int input = Integer.parseInt( br.readLine() );
            if( input == 0 ) {
                sb.append( minHeap.delete() ).append('\n');
            }
            else
                minHeap.insert( input );
        }
        System.out.println( sb );

        /*
        int min = Integer.MAX_VALUE;
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt( br.readLine() );
            if( x == 0 ) {
                // 배열이 비어있을 때.
                if( queue.isEmpty() )
                    sb.append("0").append('\n');
                else {
                    sb.append(queue.poll()).append("\n");
                }
            }
            else {
                queue.add( x );
            }
        }

        System.out.println( sb );
         */
    }
}

class MinHeap {
    List<Integer> list;

    public MinHeap() {
        list = new ArrayList<>();
        list.add(0);
    }

    public void insert(int val) {
        // 1. leaf 마지막에 삽입.
        // list.size() - 1 = lastIdx
        list.add( val );
        int current = list.size() - 1;
        int parent = current / 2;
        // 2. 부모와 비교 후 조건에 맞지 않으면 Swap
        // 3. 조건이 만족되거나 root 까지 반복
        while ( true ) {
            // minheap 조건 만족.
            if( parent == 0 || list.get( parent ) <= list.get( current ) ) {
                break;
            }

            int temp = list.get( parent );

            list.set( parent , list.get(current) );
            list.set( current , temp );

            current = parent;
            parent = current / 2;
        }
    }

    public int delete() {
        if( list.size() == 1 ) {
            return 0;
        }
        int top = list.get(1);
        // 1. Root에 leaf 마지막 데이터 가져옴.
        list.set(1, list.get( list.size() -1 ));
        list.remove( list.size() - 1 );

        // 2. 자식과 비교 후 조건이 맞지 않으면 swap
        // 3. 조건이 만족되거나 leaf 까지 반복
        int currentPos = 1;

        while ( true ) {
            int leftPos = currentPos * 2;
            int rightPos = currentPos * 2 + 1;

            // 왼쪽 자식의 인덱스가 lastIdx 를 넘어섰을 경우 == 자식이 없다는 뜻.
            // leaf에 도착했거나 ?
            if( leftPos > list.size() - 1  ) {
                break;
            }

            // 왼쪽 먼저 확인
            int minValue = list.get(leftPos);
            int minPos = leftPos;

            // 오른쪽 자식이 리스트 안에 있다. == 오른쪽 자식이 존재한다.
            if( rightPos <= list.size() -1 && list.get(rightPos) < minValue ) {
                minValue = list.get( rightPos );
                minPos = rightPos;
            }

            // 루트 값이 minvalue보다 크면 swap해줘야 함.
            if( list.get(currentPos) > minValue ) {
                // swap
                int temp = list.get( currentPos );
                list.set( currentPos, minValue );
                list.set( minPos, temp );

                currentPos = minPos;
            }
            // heap조건에 만족했거나 ? ( minheap )
            else {
                break;
            }
        }

        return top;
    }
}
