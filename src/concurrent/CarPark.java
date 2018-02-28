package concurrent;

import org.jcsp.lang.*;

public class CarPark implements CSProcess {

    One2OneChannelInt arriveTrigger;
    One2OneChannelInt departTrigger;

    //One2OneChannelInt controllerArriveIn = Channel.one2oneInt();
    //One2OneChannelInt controllerDepartIn = Channel.one2oneInt();

    One2OneChannelInt controllerArriveOut = Channel.one2oneInt();
    One2OneChannelInt controllerDepartOut = Channel.one2oneInt();

    //controler needs any to one channel?
    Any2OneChannelInt controllerIn = Channel.any2oneInt();



    public CarPark(One2OneChannelInt  arriveChannel, One2OneChannelInt  departChannel ){

        arriveTrigger = arriveChannel;
        departTrigger = departChannel;
    }

    public void startCarPark(){

        Parallel carParkParallel;

        carParkParallel = new Parallel( new CSProcess[]{
                new Arrival(arriveTrigger,controllerArriveOut, controllerIn),new Depart(departTrigger,controllerDepartOut,controllerIn),new Control(controllerIn,controllerArriveOut,controllerDepartOut)
        });

    }

    public void run() {

        startCarPark();

    }
}
