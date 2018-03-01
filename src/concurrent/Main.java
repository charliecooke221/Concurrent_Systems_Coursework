package concurrent;

import org.jcsp.lang.*;
import org.jcsp.util.ints.BufferInt;
import org.jcsp.awt.*;
import java.awt.*;


import javax.naming.PartialResultException;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) throws Exception{

        One2OneChannelInt arrive = Channel.one2oneInt();
        One2OneChannelInt depart = Channel.one2oneInt();
        BufferInt spacesBuffer = new BufferInt(1);
        spacesBuffer.put(30);

        CarPark carpark = new CarPark(arrive,depart,spacesBuffer);

        TestChannels testChannels = new TestChannels(arrive,depart,spacesBuffer);

        Booker booker = new Booker(arrive,depart,spacesBuffer);



        Parallel components;
        components = new Parallel( new CSProcess[]{carpark, testChannels, booker});
        components.run();

    }

}
