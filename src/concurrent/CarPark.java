package concurrent;

import org.jcsp.lang.*;

import org.jcsp.util.ints.BufferInt;

public class CarPark implements CSProcess {

    One2OneChannelInt arriveTrigger;
    One2OneChannelInt departTrigger;

    One2OneChannelInt controllerArriveOut = Channel.one2oneInt();
    One2OneChannelInt controllerDepartOut = Channel.one2oneInt();

    Any2OneChannelInt controllerIn = Channel.any2oneInt();

    BufferInt spacesBuffer;


    public CarPark(One2OneChannelInt  arriveChannel, One2OneChannelInt  departChannel, BufferInt buffer){

        arriveTrigger = arriveChannel;
        departTrigger = departChannel;
        spacesBuffer = buffer;
    }

    public void startCarPark(){

        Control control = new Control();

        Parallel carParkParallel;

        carParkParallel = new Parallel( new CSProcess[]{
                new Arrival(arriveTrigger,controllerArriveOut, controllerIn,control),new Depart(departTrigger,controllerDepartOut,controllerIn,control)
        });

        //System.out.println("beforerun");
        carParkParallel.run();
        //System.out.println("afterrun");
    }

    public void run(){
        startCarPark();
    }
}
