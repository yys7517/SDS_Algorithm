package Days01;
/*
    -- 깊이 우선 탐색 --

    그래프 탐색 방법의 한가지

    한 경로로 탐색 하다 특정 상황에서 최대한 깊숙하게 들어가서 확인 후
    다시 돌아가 다른 경로로 탐색하는 방식.

    일반적으로 재귀함수를 이용하여 구현, Stack을 사용하여 구현하기도 함.( 구조 상 Stack Overflow에 유의. )

    그래프 알고리즘에서 기초가 되는 탐색 방식 ( DFS, BFS )

    DFS 활용 : 백 트래킹, 단절선 찾기, 단절점 찾기, 위상정렬, 사이클 찾기 등.

    - DFS 과정 -
    1. 체크인
    2. 목적지인가?
    3. 연결된 곳을 순회
        4. 갈 수 있는가 ?
        5. 간다
    6. 체크아웃

    - Pseudo 코드 -
    DFS 구현 ( WHITE : 방문 전, GRAY : 방문 중, BLACK : 방문 완료 )

    for each vertex u in G.V :          // 초기화
        u.color = WHITE
        u.parent = NIL

    for each vertex u in G.V :          // 시작.
        if u.color == WHITE :
            DFS(G,u)

    DFS(G,u) :
        u.color = GRAY              // 1. 체크인
        for each v in G(Adj[u]):    // 3. 연결된 곳을 순회
            if v.color == WHITE     // 4. 방문되지 않은 곳을 방문한다.
            v.parent = u
            DFS(G,v)            // 5. 재귀

        u.color = BLACK         // 6. 체크아웃
 */

/*
    -- 너비 우선 탐색 --

    그래프 탐색 방법의 한 가지

    시작 노드에서 시작하여 인접한 노드를 먼저 탐색하는 방식.

    일반적으로 Queue 자료구조를 이용하여 구현.

    그래프 알고리즘에서 기초가 되는 탐색 방식으로 반드시 숙지.

    BFS 활용 : 최단경로 찾기, 위상정렬 등

    - BFS 과정 -
    1. 큐에서 꺼내옴
    2. 목적지인가 ?
    3. 연결된 곳을 순회
        4. 갈 수 있는가 ?
            5. 체크인
            6. 큐에 넣음.
        7. 체크아웃

    BFS(G,s) :
        // 초기화
        for each vertex u in G.V
            u.color = WHITE
            u.parent = NIL

        // 시작
        s.color = GRAY      // 시작점 초기화
        s.parent = NIL

        Q = new Queue()
        ENQUEUE(Q,s)        // 시작점

        while Q is not empty :
            u = DEQUEUE(Q)                  // 1
            for each v in G.Adj[u] :        // 3
                if v.color == WHITE         // 4
                    v.color = GRAY          // 5
                    v.parent = u
                    ENQUEUE(Q,v)            // 6

                u.color = BLACK             // 7. 체크아웃
 */
public class Ex_01 {
}
