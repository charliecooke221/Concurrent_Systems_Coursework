package concurrent;

import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.util.Buffer;

public class MailBag implements CSProcess{ //stores mail events in buffer

    ChannelInput bookingEventChannel;
    Buffer mailsBuffer;

    public MailBag(Any2OneChannel bookings,Buffer mails){

        bookingEventChannel = bookings.in();
        mailsBuffer = mails;
    }

    public void run(){

        while(true) {  //keeps mail buffer updated

            MailEvent mail = (MailEvent) bookingEventChannel.read();
            mailsBuffer.put(mail); // do check to see if buffer is full
//            System.out.println("MAILTOOL MAILBAG - MAILRECIEVED");

//            System.out.println("MAILTOOL MAILBAG - MAILBUFFER " + mail);


        }
    }

}
