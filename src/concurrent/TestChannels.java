package concurrent;

import org.jcsp.lang.*;

import java.util.concurrent.TimeUnit;

public class TestChannels implements CSProcess{

    private ChannelOutputInt arrivetrig;
    private ChannelOutputInt departtrig;

    public TestChannels(One2OneChannelInt arrive,One2OneChannelInt depart){

        arrivetrig = arrive.out();
        departtrig = depart.out();
    }

    public void run(){


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arrivetrig.write(1);
        System.out.println("testClass");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        departtrig.write(-1);

    }

}
