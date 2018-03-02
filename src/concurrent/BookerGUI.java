package concurrent;

import org.jcsp.awt.*;
import org.jcsp.lang.*;
import org.jcsp.util.OverWriteOldestBuffer;
import org.jcsp.util.ints.BufferInt;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class BookerGUI implements CSProcess {

    private static final int maxWidth = 1024;
    private static final int maxHeight = 768;
    private static final int width = 700;
    private static final int height = 400;

    private ChannelOutput buttonChannel;

    private BufferInt spacesBuffer;

    private String registrationString;



    private ActiveLabel activeLabel;


    public BookerGUI(Any2OneChannel buttChann, BufferInt buf){

        buttonChannel = buttChann.out();
        spacesBuffer = buf;

    }

    public void buildFrame(){
        final Frame root = new Frame ("Booker");
        root.setSize (width, height);


        ActivePanel bookingPanel = new ActivePanel(); // Booking panel
        bookingPanel.setLayout(new BorderLayout());
        int activeTextNum = 1;

        One2OneChannel registrationFieldChan = Channel.one2one();

        final ActiveTextField[] regActiveText = new ActiveTextField[activeTextNum];

        for (int i = 0; i < activeTextNum; i++) {
            regActiveText[i] = new ActiveTextField (null, registrationFieldChan.out (),"reg");
        }

        for (int i = 0; i < activeTextNum; i++) {
            bookingPanel.add(regActiveText[i],BorderLayout.CENTER);
        }

        Any2OneChannel bookingEnterChannel = Channel.any2one(new OverWriteOldestBuffer(10));

        ActiveButton enterBookingButton = new ActiveButton();
        enterBookingButton = new ActiveButton(null,bookingEnterChannel.out(),"Enter Booking and Proceed to Carpark page");
        enterBookingButton.setBackground(Color.green);
        enterBookingButton.setPreferredSize(new Dimension(200,100));

        Label welcomeLabel = new Label("Welcome To the Car Park Booking Page");
        welcomeLabel.setAlignment(Label.CENTER);
        Label enterCarRegLabel = new Label("Please enter your car registration here:");

        bookingPanel.add(welcomeLabel ,BorderLayout.PAGE_START);
        bookingPanel.add(enterCarRegLabel, BorderLayout.LINE_START);
        bookingPanel.add(enterBookingButton,BorderLayout.PAGE_END);
        bookingPanel.setVisible(true);


        ActivePanel carParkPanel = new ActivePanel();       // Car park panel

        One2OneChannel arriveButtConfigChan = Channel.one2one();
        ActiveButton arriveButton = new ActiveButton();
        arriveButton = new ActiveButton(arriveButtConfigChan.in(),buttonChannel,"Arrive");
        arriveButton.setBackground(Color.green);

        One2OneChannel departButtConfigChan = Channel.one2one();
        ActiveButton departButton = new ActiveButton();
        departButton = new ActiveButton(departButtConfigChan.in(),buttonChannel,"Depart");
        departButton.setBackground(Color.red);

        final int nLabels = 1;

        One2OneChannel[] activeLabelUpdater = Channel.one2oneArray(nLabels);
        //root.add(activeLabel);

        carParkPanel.setLayout (new GridLayout (2, 2));

        Label carparkWelcomeLabel = new Label("Please use this page for checking in your arrival and departure of the car park");
        carParkPanel.add(carparkWelcomeLabel);

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

        carParkPanel.setVisible(false);


        ActivePanel mainPanel = new ActivePanel();
        mainPanel.setLayout(new CardLayout());
        mainPanel.add(bookingPanel);
        mainPanel.add(carParkPanel);

        root.add(mainPanel);
        root.setVisible(true);

        new Parallel (
                new CSProcess[]{
                        new Parallel(label),
                        enterBookingButton,
                        arriveButton,
                        departButton,
                        new CSProcess() {
                            @Override
                            public void run() {
                                while (true){
                                        registrationString = (String) registrationFieldChan.in().read();

                                        String arriveButtString = "Arrive " + registrationString;
                                        String departButtSting = "Depart " + registrationString;
                                        //System.out.println(arriveButtString);

                                        arriveButtConfigChan.out().write(arriveButtString);
                                        departButtConfigChan.out().write(departButtSting);
                                    }
                                }
                            },
                        new CSProcess() {
                            @Override
                            public void run() {
                                while (true){
                                    String enterBookButton = (String) bookingEnterChannel.in().read();
                                    if(enterBookButton.equals("Enter Booking and Proceed to Carpark page"))
                                    {
                                        bookingPanel.setVisible(false);
                                        carParkPanel.setVisible(true);

                                        buttonChannel.write("Booking " + registrationString);
                                    }
                                }
                            }
                        },
                        new CSProcess() {
                            public void run() {

                                while (true) {      //spaces updater

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
