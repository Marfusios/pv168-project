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
    private static int counter = -1;
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {


        while(counter < 50)
        {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " | " + ++counter);
            lock.unlock();

            for(long i = 0; i<100000000; i++)
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
