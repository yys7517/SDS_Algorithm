package Days01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_3055 {
    // 탈출

    // R행 C열 지도

    // 비어있는 곳은 '.'
    // 물이 차있는 곳은 '*'
    // 돌은 'X' 이동 불가
    // 비버의 굴은 'D'
    // 고슴도치의 위치는 'S'

    // 인접한 왼쪽 오른쪽 위 아래로 고슴도치는 이동할 수 있다.
    // 하지만, 물이 있는 인접한 빈 공간으로는 이동할 수 없다.
    // 물도 매 분마다 인접한 칸으로 확장되기 때문이다.

    // 비버의 굴(목적지)로 이동할 수 있는 가장 빠른 시간을 출력
    // 이동할 수 없다면 "KAKTUS"를 출력한다.

    static int R;
    static int C;

    static char[][] map;
    static int[][] dp;

    static BufferedReader br;

    static class Dot {
        int i;
        int j;
        char type;

        public Dot( int i, int j, char type ) {
            this.i = i;
            this.j = j;
            this.type = type;
        }
    }
    static int[] di = { 1,-1,0,0 };
    static int[] dj = { 0,0,-1,1 };

    static Queue<Dot> queue;

    static boolean foundAnswer;


    /*
        1. 큐에서 꺼내옴
        2. 목적지인가 ?          'D'
        3. 연결된 곳을 순회    상하좌우
            4. 갈 수 있는가 ?
                1. 고슴도치
                    1. 맵 영역
                    2. 빈 공간( *인접 X ) -- 생략 가능 ( 물을 먼저 번지게 한다. )
                    3. 방문 X
                2. 물
                    1. 맵 영역
                    2. 빈 공간
                    3. 비버 소굴 X
     */

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        
        StringTokenizer st;
        st = new StringTokenizer( br.readLine(), " ");
        
        R = Integer.parseInt( st.nextToken() );
        C = Integer.parseInt( st.nextToken() );
        
        map = new char[ R ][ C ];
        dp = new int[ R ][ C ];

        Dot start = null;
        queue = new LinkedList<>();

        for (int i = 0; i < R; i++) {
            String[] token = br.readLine().split("");
            for (int j = 0; j < C; j++) {
                map[i][j] = token[j].charAt(0);
                if (map[i][j] == 'S')
                    start = new Dot(i, j, 'S');     // 고슴도치는 선언
                else if (map[i][j] == '*')
                    queue.add(new Dot(i, j, '*'));  // 물을 먼저 queue에 삽입.
            }
        }

        // 고슴도치를 나중에 삽입. why ? 물을 먼저 이동시키기 위함.
        queue.add( start );

        while ( !queue.isEmpty() ) {
            // 1. 큐에서 꺼내옴 -> 'S', '.', 'D', '*'
            Dot dot = queue.poll();

            int i = dot.i;
            int j = dot.j;
            char type = dot.type;

            // 2. 목적지인가 ? -> 'D'
            if( type == 'D' ) {
                System.out.println( dp[i][j] );
                foundAnswer = true;
                break;
            }
            // 3. 연결된 곳을 순회 -> 상 하 좌 우
            for (int k = 0; k < 4; k++) {
                int ni = i + di[k];
                int nj = j + dj[k];

                // 4. 갈수 있는가? ( 고슴도치 )
                // 맵을 벗어나지 않고, '.' or 'D', 방문하지 않은 곳
                // 4. 갈 수 있는가 ? ( 물 )
                // 맵을 벗어나지 않고, '.'
                if( ni >= 0 && nj >= 0 && ni < R && nj < C ) {
                    if( type == '.' || type =='S' ) {
                        if( (map[ni][nj] == '.' || map[ni][nj] == 'D') && dp[ni][nj] == 0 ) {
                            // 5. 체크인 -> dp에 현재 +1을 기록.
                            // 다음 도착지 = 현재 도착지 시간 + 1
                            dp[ni][nj] = dp[i][j] + 1;
                            // 6. 큐에 넣음
                            queue.add( new Dot( ni, nj, map[ni][nj] ));
                        }
                    }

                    else if ( type == '*' ) {
                        if( map[ni][nj] == '.' || map[ni][nj] == 'S') {
                            // 5. 체크인 -> 지도에 *표기
                            map[ni][nj] = '*';
                            // 6. 큐에 넣음.
                            queue.add( new Dot( ni,nj,'*') );
                            // '.'이 아니라 '*'을 넣는 이유 : '.'을 넣으면
                            // 고슴도치가 다음에 이동하면서 들어갈 '.'과 다음 물이 들어갈 '.'을 구분할 수 없음.
                        }

                    }

                }
            }
        }

        if( !foundAnswer )
            System.out.println("KAKTUS");
        else
            return;
    }

}
