package concurrent;

import org.jcsp.lang.*;

public class Arrival implements CSProcess {

    private ChannelInputInt arriveTrigger;

    private ChannelOutputInt controllerOut;
    private ChannelInputInt controllerIn;
    private Control control;

    public Arrival(One2OneChannelInt trigIn,One2OneChannelInt controlIn,Any2OneChannelInt controlOut,Control cont){

        controllerIn = controlIn.in();
        controllerOut = controlOut.out();
        control = cont;
    }

    public void run(){

        while (true){

            int value = arriveTrigger.read();

            System.out.println("CARPARK ARRIVE - ARRIVAL");

            control.decrement();
            System.out.println("Atomic Spaces value = " + control.getValue());

            //controllerOut.write(value);
        }
    }

}

