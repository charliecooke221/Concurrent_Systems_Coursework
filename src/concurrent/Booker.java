package concurrent;

import org.jcsp.awt.ActiveButton;
import org.jcsp.awt.ActiveClosingFrame;
import org.jcsp.awt.ActiveFrame;
import org.jcsp.lang.*;
import org.jcsp.util.OverWriteOldestBuffer;
import org.jcsp.util.ints.BufferInt;

import javax.naming.PartialResultException;
import java.awt.*;

public class Booker implements CSProcess{


    private One2OneChannelInt arrivetrig;
    private One2OneChannelInt departtrig;
    private BufferInt spacesBuffer;

    Any2OneChannel buttonOutChannel = Channel.any2one(new OverWriteOldestBuffer(10));

    public Booker(One2OneChannelInt arrive, One2OneChannelInt depart, BufferInt buf){

        arrivetrig = arrive;
        departtrig = depart;
        spacesBuffer = buf;
    }


    public void startBooker(){

        Parallel bookerParallel;

        bookerParallel = new Parallel( new CSProcess[]{
                new BookerFrame(buttonOutChannel),new ButtonEventReciever(arrivetrig,departtrig,buttonOutChannel)
        });

        bookerParallel.run();
    }


    public void run(){
        startBooker();
    }
}
