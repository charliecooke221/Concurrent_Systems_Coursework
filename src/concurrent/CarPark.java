package concurrent;

import org.jcsp.lang.*;
import org.jcsp.util.Buffer;
import org.jcsp.util.ints.BufferInt;

public class CarPark implements CSProcess {

    One2OneChannelInt arriveTrigger;
    One2OneChannelInt departTrigger;

    //One2OneChannelInt controllerArriveIn = Channel.one2oneInt();
    //One2OneChannelInt controllerDepartIn = Channel.one2oneInt();

    One2OneChannelInt controllerArriveOut = Channel.one2oneInt();
    One2OneChannelInt controllerDepartOut = Channel.one2oneInt();

    //controler needs any to one channel?
    Any2OneChannelInt controllerIn = Channel.any2oneInt();

    BufferInt spacesBuffer;



    public CarPark(One2OneChannelInt  arriveChannel, One2OneChannelInt  departChannel, BufferInt buffer){

        arriveTrigger = arriveChannel;
        departTrigger = departChannel;
        spacesBuffer = buffer;
    }

    public void startCarPark(){


        Parallel carParkParallel;

        carParkParallel = new Parallel( new CSProcess[]{
                new Arrival(arriveTrigger,controllerArriveOut, controllerIn),new Depart(departTrigger,controllerDepartOut,controllerIn),new Control(controllerIn,controllerArriveOut,controllerDepartOut,spacesBuffer)
        });

        System.out.println("beforerun");
        carParkParallel.run();
        System.out.println("afterrun");

    }

    public void run(){
        startCarPark();
    }


}
