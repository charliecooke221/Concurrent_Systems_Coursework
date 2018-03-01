package concurrent;

import org.jcsp.lang.*;

public class ButtonEventReciever implements CSProcess{

    private ChannelOutputInt arrivetrig;
    private ChannelOutputInt departtrig;

    private ChannelInput buttonChannel;
    private ChannelOutput departMail;


    public ButtonEventReciever(One2OneChannelInt arrive, One2OneChannelInt depart, Any2OneChannel buttChan,Any2OneChannel bookmail){

        arrivetrig = arrive.out();
        departtrig = depart.out();
        buttonChannel = buttChan.in();

        departMail = bookmail.out();


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
                String reference = "test";
                MailEvent departMailEvent = new MailEvent(reference,"Thank you for using our car park!");
                departMail.write(departMailEvent);
                // sent ty email
            }


        }
    }
}
