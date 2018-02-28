package concurrent;

import org.jcsp.lang.*;

public class Depart implements CSProcess {

    private ChannelInputInt departTrigger;

    private ChannelOutputInt controllerOut;
    private ChannelInputInt controllerIn;


    public Depart(One2OneChannelInt trigIn,One2OneChannelInt controlIn,Any2OneChannelInt controlOut){
        departTrigger = trigIn.in();
        controllerIn = controlIn.in();
        controllerOut = controlOut.out();


}

    public void run(){

        while (true){

            int value = departTrigger.read();
            System.out.println("Depart");

            controllerOut.write(value);
        }

    }
}
