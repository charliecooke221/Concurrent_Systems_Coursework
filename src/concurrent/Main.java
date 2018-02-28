package concurrent;

import org.jcsp.lang.Channel;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Main {



    public static void main(String[] args) throws Exception{

        One2OneChannelInt arrive = Channel.one2oneInt();
        One2OneChannelInt depart = Channel.one2oneInt();

        CarPark carpark = new CarPark(arrive,depart);
        carpark.run();

    }
}
