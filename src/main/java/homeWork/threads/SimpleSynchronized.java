package homeWork.threads;

/**
 * Created with IntelliJ IDEA.
 * User: m4r10
 * Date: 4/10/13
 * Time: 11:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleSynchronized implements Runnable
{
    private static int counter = -1;
    private static final Object locker = new Object();

    @Override
    public  void run() {


        while(true)
        {
            synchronized (locker)
            {
                if(counter >= 50) return;
                System.out.println(Thread.currentThread().getName() + " | " + ++counter);
            }

            for(long i = 0; i<100000; i++)
            {}
        }
    }

    public static void main(String[] args)
    {
        new Thread(new SimpleSynchronized()).start();
        new Thread(new SimpleSynchronized()).start();
        new Thread(new SimpleSynchronized()).start();
    }
}
