package homeWork.threads;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: m4r10
 * Date: 4/11/13
 * Time: 12:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class AtomicInt implements Runnable
{
    private static AtomicInteger counter = new AtomicInteger(-1);
    private static final Object locker = new Object();


    @Override
    public void run() {


        while(true)
        {
            synchronized (locker)
            {
                if(counter.get() >= 50) return;
                System.out.println(Thread.currentThread().getName() + " | " + counter.incrementAndGet());
            }

            for(long i = 0; i<100000; i++){}
        }


    }

    public static void main(String[] args)
    {
        new Thread(new AtomicInt()).start();
        new Thread(new AtomicInt()).start();
        new Thread(new AtomicInt()).start();
    }
}
