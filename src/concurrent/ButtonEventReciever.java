package concurrent;

import org.jcsp.lang.*;

public class ButtonEventReciever implements CSProcess{

    private ChannelOutputInt arrivetrig;
    private ChannelOutputInt departtrig;

    private ChannelInput buttonChannel;


    public ButtonEventReciever(One2OneChannelInt arrive, One2OneChannelInt depart, Any2OneChannel buttChan){

        arrivetrig = arrive.out();
        departtrig = depart.out();
        buttonChannel = buttChan.in();
    }

    public void run() {

        while (true){

            String event = (String) buttonChannel.read();

            if(event.equals("Arrive")){
                System.out.println("arrive button pressed");
                arrivetrig.write(-1);

            }
            if(event.equals("Depart")){
                System.out.println("depart button pressed");
                departtrig.write(1);
            }


        }
    }
}
