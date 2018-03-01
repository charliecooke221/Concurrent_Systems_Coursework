package concurrent;

import org.jcsp.lang.*;
import org.jcsp.util.ints.BufferInt;

import java.util.concurrent.TimeUnit;

public class TestChannels implements CSProcess{

    private ChannelOutputInt arrivetrig;
    private ChannelOutputInt departtrig;
    private BufferInt spacesBuffer;

    public TestChannels(One2OneChannelInt arrive, One2OneChannelInt depart, BufferInt buf){

        arrivetrig = arrive.out();
        departtrig = depart.out();
        spacesBuffer = buf;
    }

    public void run(){


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arrivetrig.write(1);
        //System.out.println("testClass");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        departtrig.write(-5);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(spacesBuffer.startGet());

    }

}
