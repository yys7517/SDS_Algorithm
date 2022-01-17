package Days01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1759 {
    // 암호 만들기   ( 백트래킹, DFS )
    // DFS는 모든 경로를 탐색을 한다.

    /*
        -- 암호의 구성 --

        암호는 서로 다른 L개의 알파벳 소문자들로 구성.
        최소 한 개의 모음 (a,e,i,o,u)
        최소 두 개의 자음으로 구성되어 있다.

        정렬된 문자열 abc (O), bac(X)
        중복되는 문자는 없다.

        L : 암호의 길이
        C : 주어지는 문자의 개수.

        그래프 탐색을 이용하여 푼다.

     */

    /*
        -- 접근 방법 --

        1. 전체 flow

            오름차순 ?
            방문 ?
            모음 자음 개수 = 모음이 1개 미만인지 ? 자음이 2개 미만인지 ?

            1. 체크인
            2. 목적지 ? ==> 암호의 길이 L ? 모음 1개 이상 ? 자음 2개 이상 ?
            3. 연결 v 순회   ==> 가능성 있는 모든 암호를 찾아야 한다.
                 4. 갈 수 있는가 ?
                 5. 간다
            6. 체크아웃


        2. 입력된 알파벳들을 정렬
     */

    static int L;
    static int C;

    static char[] data;
    static boolean[] visited;

    static BufferedReader br;
    static StringBuilder sb;

    static ArrayList<String> result;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();
        result = new ArrayList<>();

        StringTokenizer st;

        st = new StringTokenizer( br.readLine(), " ");
        L = Integer.parseInt( st.nextToken() );
        C = Integer.parseInt( st.nextToken() );

        visited = new boolean[ C ];
        data = new char[ C ];

        st = new StringTokenizer( br.readLine(), " ");
        for (int i = 0; i < C; i++) {
            data[i] = st.nextToken().charAt(0);
        }

        Arrays.sort( data );

        // 1. 모든 노드 앞에 빈 공간이 하나 있다고 생각하면, 시작점을 1개로 볼 수 있다.
        // 배열의 초기 인덱스인 0보다 앞 인덱스 값 -1을 current로 주면
        // 시작점을 1개로 잡을 수 있다.
        DFS( 0, 0,0,-1,"");

        // 2. 시작점을 C개로 잡는 방법
        /*
        for (int i = 0; i < C; i++) {
            if( isVowel( data[i] ) )
                DFS( 1, 0,1,i,data[i]+"");
            else
                DFS( 1, 1,0,i,data[i]+"");
        }
         */


        for (String s : result) {
            System.out.println( s );
        }
    }

    private static void DFS( int length, int cont, int vowel, int current, String pwd ) {
        /*
            오름차순 ?
            방문 ?
            모음 자음 개수 = 모음이 1개 미만인지 ? 자음이 2개 미만인지 ?

            1. 체크인  - 생략 가능.
            2. 목적지 ? ==> 암호의 길이 L ? 모음 1개 이상 ? 자음 2개 이상 ?
            3. 연결 v 순회   ==> 나보다 높은 알파벳
                 4. 갈 수 있는가 ? - 생략 가능
                 5. 간다 - dfs(next) -> 자음, 모음
            6. 체크아웃 - 생략 가능
         */

        // 1. 체크인  - 생략 가능.

        //  2. 목적지 ? ==> 암호의 길이 L ? 모음 1개 이상 ? 자음 2개 이상 ?
        if( length == L ) {
            if ( cont >= 2 && vowel >= 1 ) {
                result.add( pwd );
            }
        }

        else {
            // 3. 연결 v 순회   ==> 나보다 높은 알파벳
            for (int nextIdx = current + 1; nextIdx < data.length; nextIdx++) {
                 char next = data[nextIdx];

                 // 4. 갈 수 있는가 ? - 생략 가능

                // 5. 간다 - dfs(nextdata) -> 모음
                 if( isVowel( next ) )
                     DFS( length+1, cont, vowel + 1,  nextIdx , pwd + next );
                 // 5. 간다 - dfs(nextdata) -> 자음
                 else
                     DFS( length+1, cont + 1, vowel,  nextIdx , pwd + next );
            }
        }
    }

    private static boolean isVowel( char c ) {
        if ( c == 'a' || c == 'e' || c == 'i' || c =='o' || c == 'u' )
            return true;
        else
            return false;
    }

}
