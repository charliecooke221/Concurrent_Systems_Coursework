package concurrent;

import org.jcsp.awt.ActiveButton;
import org.jcsp.awt.ActiveClosingFrame;
import org.jcsp.awt.ActiveFrame;
import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.util.ints.BufferInt;

import java.awt.*;

public class BookerFrame implements CSProcess {

    private static final int maxWidth = 1024;
    private static final int maxHeight = 768;
    private static final int width = 480;
    private static final int height = 270;

    private ChannelOutput buttonChannel;
    private BufferInt spacesBuffer;



    public BookerFrame(Any2OneChannel buttChann){

        buttonChannel = buttChann.out();

    }

    public void buildFrame(){

        System.out.println("FRAME BUILDER");

        ActiveButton arriveButton = new ActiveButton();
        arriveButton = new ActiveButton(null,buttonChannel,"Arrive");
        arriveButton.setBackground(Color.green);

        ActiveButton departButton = new ActiveButton();
        departButton = new ActiveButton(null,buttonChannel,"Depart");
        departButton.setBackground(Color.red);

        final Frame root = new Frame ("ActiveButton Example");

        root.setSize (width, height);
        root.setLayout (new GridLayout (2, 2));

        root.add(arriveButton);
        root.add(departButton);

        root.setVisible(true);



//        final ActiveClosingFrame activeClosingframe = new ActiveClosingFrame ("Booker");
//        final ActiveFrame activeFrame = activeClosingframe.getActiveFrame ();
//        activeFrame.setSize (maxWidth, height);
//        activeFrame.pack ();
//        activeFrame.setLocation ((maxWidth - width)/2, (maxHeight - height)/2);
//        activeFrame.setVisible (true);
//        activeFrame.setLayout(new GridLayout());
//        activeFrame.toFront ();
    }


    public void run() {
        buildFrame();

    }
}
