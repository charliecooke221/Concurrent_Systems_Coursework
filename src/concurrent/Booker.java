package concurrent;

import org.jcsp.lang.*;
import org.jcsp.util.OverWriteOldestBuffer;
import org.jcsp.util.ints.BufferInt;

public class Booker implements CSProcess{


    private One2OneChannelInt arrivetrig;
    private One2OneChannelInt departtrig;
    private BufferInt spacesBuffer;
    private Any2OneChannel bookingMail;

    Any2OneChannel buttonOutChannel = Channel.any2one(new OverWriteOldestBuffer(10));

    public Booker(One2OneChannelInt arrive, One2OneChannelInt depart,Any2OneChannel bookMail , BufferInt buf){

        arrivetrig = arrive;
        departtrig = depart;
        bookingMail = bookMail;
        spacesBuffer = buf;
    }


    public void startBooker(){

        Parallel bookerParallel;

        bookerParallel = new Parallel( new CSProcess[]{
                new BookerGUI(buttonOutChannel,spacesBuffer),new ButtonEventReciever(arrivetrig,departtrig,buttonOutChannel,bookingMail)
        });

        bookerParallel.run();
    }


    public void run(){
        startBooker();
    }
}
