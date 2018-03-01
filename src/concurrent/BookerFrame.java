package concurrent;

import org.jcsp.awt.*;
import org.jcsp.lang.*;
import org.jcsp.util.ints.BufferInt;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class BookerFrame implements CSProcess {

    private static final int maxWidth = 1024;
    private static final int maxHeight = 768;
    private static final int width = 480;
    private static final int height = 270;

    private ChannelOutput buttonChannel;
    private BufferInt spacesBuffer;



    private ActiveLabel activeLabel;


    public BookerFrame(Any2OneChannel buttChann,BufferInt buf){

        buttonChannel = buttChann.out();
        spacesBuffer = buf;

    }

    public void buildFrame(){
        final Frame root = new Frame ("Booker");
        root.setSize (width, height);


        ActivePanel carParkPanel = new ActivePanel();

        ActiveButton arriveButton = new ActiveButton();
        arriveButton = new ActiveButton(null,buttonChannel,"Arrive");
        arriveButton.setBackground(Color.green);

        ActiveButton departButton = new ActiveButton();
        departButton = new ActiveButton(null,buttonChannel,"Depart");
        departButton.setBackground(Color.red);

        final int nLabels = 1;

        One2OneChannel[] activeLabelUpdater = Channel.one2oneArray(nLabels);



        //root.add(activeLabel);

        carParkPanel.setLayout (new GridLayout (2, 2));


        final ActiveLabel[] label = new ActiveLabel[1];
        for (int i = 0; i < label.length; i++) {
            label[i] = new ActiveLabel(activeLabelUpdater[i].in(),"Avaliable spaces: " + spacesBuffer.startGet());
            label[i].setAlignment (Label.CENTER);
        }
        for (int i = 0; i < nLabels; i++) {
            carParkPanel.add(label[i]);
        }

        carParkPanel.add(arriveButton);
        carParkPanel.add(departButton);

        carParkPanel.setVisible(true);

        new Parallel (
                new CSProcess[]{
                        new Parallel(label),
                        arriveButton,
                        departButton,
                        new CSProcess() {
                            public void run() {

                                while (true) {

                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    //System.out.println("updating labels");
                                    //System.out.println(spacesBuffer.startGet());
                                    String spacesCount = "Avaliable spaces: " + spacesBuffer.startGet();
                                    for (int i = 0; i < nLabels; i++) {
                                        activeLabelUpdater[i].out().write(spacesCount);
                                    }
                                }

                            }
                        }
                }).run();
    }


    public void run() {
        buildFrame();

    }
}
