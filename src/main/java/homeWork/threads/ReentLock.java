package homeWork.threads;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: xkotas3
 * Date: 11.4.13
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class ReentLock implements Runnable
{
    private static volatile int counter = -1;
    private static final ReentrantLock LOCK = new ReentrantLock();

    @Override
    public void run() {


        while(true)
        {
            try
            {
                LOCK.lock();

                if(counter >= 50) return;
                System.out.println(Thread.currentThread().getName() + " | " + ++counter);
            }
            finally {
                LOCK.unlock();
            }

            for(long i = 0; i<10000000; i++)
            {}
        }


    }

    public static void main(String[] args)
    {
        new Thread(new ReentLock()).start();
        new Thread(new ReentLock()).start();
        new Thread(new ReentLock()).start();
    }
}
