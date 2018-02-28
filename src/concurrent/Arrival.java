package concurrent;

import org.jcsp.lang.*;

public class Arrival implements CSProcess {

    private ChannelInputInt arriveTrigger;

    private ChannelOutputInt controllerOut;
    private ChannelInputInt controllerIn;

    public Arrival(One2OneChannelInt trigIn,One2OneChannelInt controlIn,Any2OneChannelInt controlOut){

        arriveTrigger = trigIn.in();
        controllerIn = controlIn.in();
        controllerOut = controlOut.out();
    }

    public void run(){

        while (true){

            int value = arriveTrigger.read();
            System.out.println("Arrival");
            // print space left?

            controllerOut.write(value);
        }

    }



}

