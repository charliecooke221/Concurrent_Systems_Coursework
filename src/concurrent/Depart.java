package concurrent;

import org.jcsp.lang.*;

public class Depart implements CSProcess {

    private ChannelInputInt departTrigger;

    private ChannelOutputInt controllerOut;
    private ChannelInputInt controllerIn;
    private Control control;


    public Depart(One2OneChannelInt trigIn,One2OneChannelInt controlIn,Any2OneChannelInt controlOut,Control cont){
        departTrigger = trigIn.in();
        controllerIn = controlIn.in();
        controllerOut = controlOut.out();
        control = cont;

}

    public void run(){

        while (true){

            int value = departTrigger.read();

            System.out.println("CARPARK DEPART - DEPART");

            control.increment();
            System.out.println("Atomic Spaces value = " + control.getValue());
        }

    }
}
