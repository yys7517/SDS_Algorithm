package Days01;

/*
    재귀 함수

    자기 자신을 호출하는 함수
 */
public class Ex_02 {

    public static void main(String[] args) {
        System.out.println( fib(7) );
    }

    private static int fib(int n) {
        // 디버깅은 함수의 시작점을 breakpoint로 지정하여 시작한다.
        // 재귀함수의 경우 디버깅을 하여 stack 부분을 참고하면 좋다.
        if( n == 1 || n == 2 )
            return 1;
        else
            return fib(n-1) + fib( n-2 );
    }

    // main -> fib(7) -> fib(6) -> fib(5) -> fib(4) -> fib(3) -> fib(2)
}
