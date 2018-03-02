package concurrent;

import org.jcsp.lang.*;
import org.jcsp.util.ints.BufferInt;


public class Main {

    public static void main(String[] args) throws Exception{

        int numberOfCustomers = 2;

        One2OneChannelInt arrive = Channel.one2oneInt();  //bookerGUI to Arrive
        One2OneChannelInt depart = Channel.one2oneInt();  //bookerGUI to Depart
        Any2OneChannel[] bookingMail = Channel.any2oneArray(numberOfCustomers); // ButtonEventReciever to mailbag

        BufferInt spacesBuffer = new BufferInt(1);
        spacesBuffer.put(30);

        Booker[] bookers = new Booker[numberOfCustomers];
        MailTool[] mailTools = new MailTool[numberOfCustomers];

        for(int i = 0; i < numberOfCustomers; i++ ){  // create customer processes
            bookingMail[i] = Channel.any2one();
            bookers[i] = new Booker(arrive,depart,bookingMail[i],spacesBuffer);
            mailTools[i] = new MailTool(bookingMail[i]);
        }

        CarPark carpark = new CarPark(arrive,depart,spacesBuffer);

        Parallel components;
        components = new Parallel( new CSProcess[]{carpark});

        for(int i = 0; i <numberOfCustomers; i++){
            components.addProcess(bookers[i]);
            components.addProcess(mailTools[i]);
        }

        components.run();

    }

}
