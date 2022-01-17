package Days01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1713 {
    // 후보 추천하기

    // 추천하면 추천받은 학생의 사진이 사진틀에 게시된다.
    // 비어있는 사진틀이 없는 경우에는, 현재까지 추천받은 횟수가 가장 적은 학생을 삭제.
    // 추천 수가 가장 적은 학생이 두 명 이상일 경우에는 ? 게시된 지 가장 오래된 사진을 삭제한다.

    // 사진이 게시된 학생이 추천을 받은 경우는 추천 수만 올라간다.
    // 사진이 삭제되는 경우에는 추천 수가 0으로 초기화 된다.

    static int N;
    static int R;

    static int[] count; // N명의 학생이 받은 추천 수
    static int[] time;
    static ArrayList<Integer> Recommends;

    static BufferedReader br;
    static StringBuilder sb;

    static ArrayList<Integer> Photos;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader( new InputStreamReader( System.in ));
        sb = new StringBuilder();

        N = Integer.parseInt( br.readLine() );      // 사진틀 칸 개수.
        R = Integer.parseInt( br.readLine() );      // 추천 수
        StringTokenizer st = new StringTokenizer( br.readLine(), " ");

        Photos = new ArrayList<>();
        Recommends = new ArrayList<>();

        for (int i = 0; i < R; i++) {
            Recommends.add( Integer.parseInt( st.nextToken() ) );
        }

        // 1 ~ max 번 학생의 추천 수..
        // index 번에 따른 추천 수 count[index]
        count = new int[ 101 ];
        time = new int[ 101 ];

        Iterator< Integer > ir = Recommends.iterator();
        while ( ir.hasNext() ) {
            int number = ir.next(); // 추천된 학생 번호

            for ( int num : Photos ) {
                time[num]++;
            }
            // System.out.println( Arrays.toString( time ));

            // 사진 틀의 크기가 N보다 작을 때
            if( Photos.size() < N ) {
                // 이미 사진 틀에 걸려있는 학생의 번호가 추천 번호라면 ?
                if( Photos.contains( number ) ) {
                    count[ number ]++;      // 추천 수만 증가한다.
                }
                // 새롭게 추천된 학생의 번호라면 ?
                else {
                    Photos.add( number );
                    count[ number ]++;
                }

                // System.out.println( Photos );
            }
            // 추천 받은 학생의 수가 사진 틀의 크기를 초과했다.
            else {
                // 사진 틀에 있는 학생인 경우, 추천수만 증가.
                if( Photos.contains(number) ) {
                    count[number]++;
                }
                // 사진 틀에 없는 학생인 경우, 사진 틀에서 누군가를 삭제해야 한다.
                else {
                    int mincount = Integer.MAX_VALUE;   // 가장 작은 추천 수
                    int stcount = 0;                    // 추천 수가 가장 적은 학생의 수

                    // num : 사진 틀에 걸려있는 학생 번호.
                    for ( int num : Photos )  {
                        mincount = Math.min( count[num] , mincount );
                    }
                    // System.out.println("mincount:"+mincount);

                    // 가장 작은 추천 수를 가진 학생의 수를 구한다. stcount
                    for (int i = 1; i < count.length; i++) {
                        if( mincount == count[i] && Photos.contains(i) )
                            stcount++;
                    }
                    // System.out.println("stcount:"+stcount);

                    // 추천 수가 가장 적은 학생의 수가 2보다 작을 때
                    if( stcount < 2 ) {
                        for (int i = 1; i < count.length; i++) {
                            if( mincount == count[i] && Photos.indexOf(i) != -1  ) {
                                Photos.remove( Photos.indexOf(i) );     // 추천 수가 가장 작은 학생을 지운다.
                                count[ i ] = 0;         // 추천 수 또한 0으로 초기화 한다.
                                time[ i ] = 0;
                                break;
                            }

                        }
                    }

                    // 추천 수가 가장 적은 학생의 수가 2명 이상일 때
                    else {
                        int oldtime = Integer.MIN_VALUE;

                        for (int i = 0; i < count.length; i++) {
                            if( count[i] == mincount ) {
                                // 이 때의 i 값 여러개가 삭제되어야 할 학생 번호.
                                oldtime = Math.max( time[i], oldtime );        // 그 번호들 중 가장 오래된 시간.
                            }
                        }

                        // 이 때 구한 oldtime 은 가장 오래된 학생의 시간.
                        int oldnum = 0;

                        for (int i = 0; i < time.length; i++) {
                            if( time[i] == oldtime ) {
                                // 이 때 i번 학생이 삭제 되어야 한다.
                                oldnum = i;
                            }
                        }

                        // 사진 틀을 삭제할 때 사용하는 인덱스
                        int delidx = Photos.indexOf( oldnum );

                        // System.out.println( "delidx:"+delidx );

                        Photos.remove( delidx );
                        count[ oldnum ] = 0;
                        time[ oldnum ] = 0;


                        Photos.add( delidx, number );
                        count[ number ]++;



                        // System.out.println( Photos );
                    }
                }
            }



        }

        // 큐를 오름차순으로 정렬.
        Collections.sort( Photos );

        while ( !Photos.isEmpty() ) {
            sb.append( Photos.remove(0) ).append(" ");
        }

        System.out.println( sb );

    }

}
