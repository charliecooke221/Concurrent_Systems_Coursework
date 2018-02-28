package concurrent;

import org.jcsp.lang.*;
import org.jcsp.util.ints.BufferInt;

import javax.naming.PartialResultException;
import java.util.concurrent.TimeUnit;

public class Main {



    public static void main(String[] args) throws Exception{

        One2OneChannelInt arrive = Channel.one2oneInt();
        One2OneChannelInt depart = Channel.one2oneInt();
        BufferInt spacesBuffer = new BufferInt(1);

        spacesBuffer.put(30);

        CarPark carpark = new CarPark(arrive,depart,spacesBuffer);

        TestChannels testChannels = new TestChannels(arrive,depart);




        //ChannelOutputInt arrivetrig = arrive.out();

        //arrivetrig.write(1);

//        System.out.println("main1");
//        ChannelOutputInt deptrig = depart.out();
//        deptrig.write(1);
//        System.out.println("main2");

        Parallel components;
        components = new Parallel( new CSProcess[]{carpark, testChannels});
        components.run();

    }

}
