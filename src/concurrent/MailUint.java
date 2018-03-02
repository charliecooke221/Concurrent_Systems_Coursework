package concurrent;

import org.jcsp.awt.ActiveButton;
import org.jcsp.awt.ActiveLabel;
import org.jcsp.awt.ActivePanel;
import org.jcsp.lang.*;
import org.jcsp.util.Buffer;
import org.jcsp.util.OverWriteOldestBuffer;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class MailUint implements CSProcess{

    Buffer mailsBuffer;
    public Any2OneChannel buttonChannel;

    public MailUint(Buffer mailBuff){

        mailsBuffer = mailBuff;
    }


    public void buildFrame(){

        final Frame root = new Frame ("Mail");
        root.setSize(480,270);
        root.setLocation(480,270);

        ActivePanel mainPanel = new ActivePanel();
        mainPanel.setLayout(new CardLayout());

        ActivePanel introPanel = new ActivePanel();
        ActivePanel mailPanel = new ActivePanel();
        ActivePanel buttonPanel = new ActivePanel();
        introPanel.setLayout(new GridLayout(3,3));
        mailPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(1,3));

        mainPanel.add(introPanel);
        mainPanel.add(mailPanel);

        buttonChannel = Channel.any2one(new OverWriteOldestBuffer(10));

        ActiveButton openMailButton = new ActiveButton();
        openMailButton = new ActiveButton(null,buttonChannel.out(),"Enter Mail Tool");
        openMailButton.setBackground(Color.green);


        ActiveButton prevButton = new ActiveButton();
        prevButton = new ActiveButton(null,buttonChannel.out(),"Prev");
        prevButton.setBackground(Color.red);

        ActiveButton nextButton = new ActiveButton();
        nextButton = new ActiveButton(null,buttonChannel.out(),"Next");
        nextButton.setBackground(Color.red);

        ActiveButton deleteButton = new ActiveButton();
        deleteButton = new ActiveButton(null,buttonChannel.out(),"Delete");
        deleteButton.setBackground(Color.red);


        int nLabels = 1;


        One2OneChannel[] activeLabelUpdater = Channel.one2oneArray(nLabels);

        final ActiveLabel[] label = new ActiveLabel[1];
        for (int i = 0; i < label.length; i++) {
            label[i] = new ActiveLabel(activeLabelUpdater[i].in(),"Welcome to the mailing tool");
            label[i].setAlignment (Label.CENTER);

        }

        for (int i = 0; i < nLabels; i++) {
            mailPanel.add(label[i],BorderLayout.CENTER);
        }

        Label mailTitleLabel = new Label("Mail Box");

        introPanel.add(openMailButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(deleteButton);
        mailPanel.add(buttonPanel,BorderLayout.PAGE_END);
        mailPanel.add(mailTitleLabel, BorderLayout.PAGE_START);

        root.add(mainPanel);
        root.setVisible(true);

        new Parallel (
                new CSProcess[]{
                        new Parallel(label),
                        openMailButton,
                        prevButton,
                        nextButton,
                        deleteButton,
                        new CSProcess() {
                            public void run() {
                                //System.out.println("PANEL BUILDER");
                                int currentMail = -1;
                                boolean end = false;


                                introPanel.setVisible(true);

                                while (true) {

                                    String buttonValue = (String) buttonChannel.in().read();

                                    if(buttonValue.equals("Enter Mail Tool")){
                                        introPanel.setVisible(false);
                                        mailPanel.setVisible(true);
                                    }


                                    if(buttonValue.equals("Next")) {

                                        MailEvent[] mailObjects = new MailEvent[10];
                                        //System.out.println(((MailEvent) mailsBuffer.get()).message);
                                        if (mailsBuffer.getState() != 0 && !end) {  // if mail buffer has values
                                            currentMail = currentMail + 1;

                                            int mCount = 0;
                                            while (mailsBuffer.getState() != 0) { // move buffer to array
                                                //System.out.println(((MailEvent) mailsBuffer.get()).message);
                                                mailObjects[mCount] = (MailEvent) mailsBuffer.get();
                                                //System.out.println(((MailEvent) mailsBuffer.get()).message);
                                                mCount++;
                                            }
                                            //System.out.println("MAILTOOL MAILUINT - MAILBUFFER READ " + mailObjects[currentMail]);

                                            for (int i = 0; i < mailObjects.length; i++) { // move array back to buffer issue
                                                if(mailObjects[i] != null) {
                                                    mailsBuffer.put(mailObjects[i]);
                                                }
                                            }
                                            System.out.println(currentMail);

                                            try {
                                                String mailMessage = mailObjects[currentMail].message + " Booking Reference: " + mailObjects[currentMail].bookingReference;
                                                activeLabelUpdater[0].out().write(mailMessage); //write mail object message to label

                                            }
                                            catch (NullPointerException e){
                                                activeLabelUpdater[0].out().write("No More Mail"); // issue
                                                //currentMail = currentMail - 1;
                                                end = true;

                                            }

                                        } else {
                                            activeLabelUpdater[0].out().write("No More Mail");
                                        }
                                    }

                                    if(buttonValue.equals("Prev")){

                                        MailEvent[] mailObjects = new MailEvent[10];
                                        System.out.println("previous button press");
                                        System.out.println(currentMail);
                                        end = false;

                                        if (mailsBuffer.getState() != 0 && currentMail > 0) {

                                            currentMail = currentMail - 1;
                                            System.out.println("previousmeth");
                                            System.out.println(currentMail);


                                            int mCount = 0;
                                            while (mailsBuffer.getState() != 0) {
                                                System.out.println("loading from buffer");
                                                mailObjects[mCount] = (MailEvent) mailsBuffer.get();
                                                mCount++;
                                            }

                                            for (int i = 0; i < mailObjects.length; i++) {

                                                if(mailObjects[i] != null){
                                                    mailsBuffer.put(mailObjects[i]);
                                                }
                                            }

                                            System.out.println(mailObjects[currentMail].message);

                                            String mailMessage = mailObjects[currentMail].bookingReference + mailObjects[currentMail].message;
                                            activeLabelUpdater[0].out().write(mailMessage);


                                        } else {

                                            if(currentMail == 0){
                                                currentMail = -1;
                                            }
                                            activeLabelUpdater[0].out().write("No More Mail");
                                        }

                                    }
                                    }
                                }

                            }
                }).run();
    }



    public void run(){
        buildFrame();
    }

}
