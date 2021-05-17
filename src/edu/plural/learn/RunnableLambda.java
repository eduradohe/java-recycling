package edu.plural.learn;

public class RunnableLambda {

    private static Runnable createAnonymousRunnable() {
        return new Runnable() {

            @Override
            public void run() {

                for ( int i = 0; i < 3; i++ ) {
                    System.out.println(
                            "Anonymous Hello World from thread [" +
                                    Thread.currentThread().getName() +
                                    "]"
                    );
                }
            }
        };
    }
    private static Runnable createLambdaRunnable() {
        return () -> {
            for ( int i = 0; i < 3; i++ ) {
                System.out.println(
                        "Lambda Hello World from thread [" +
                                Thread.currentThread().getName() +
                                "]"
                );
            }
        };
    }

    private static void run( final Runnable runnable ) throws InterruptedException {
        final Thread t = new Thread(runnable);
        t.start();
        t.join();
    }

    public static void main(String[] args) throws InterruptedException {

        final Runnable anonymousRunnable = createAnonymousRunnable();
        final Runnable lambdaRunnable = createLambdaRunnable();

        run(anonymousRunnable);
        run(lambdaRunnable);
    }
}
