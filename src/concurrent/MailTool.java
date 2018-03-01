package concurrent;

import org.jcsp.lang.*;
import org.jcsp.util.Buffer;

import javax.naming.PartialResultException;

public class MailTool implements CSProcess {

    Any2OneChannel bookingMade;

    Buffer mailsBuffer;


    public MailTool(Any2OneChannel bookingChan){

        bookingMade = bookingChan;
    }


    public void startMailTool(){

        mailsBuffer = new Buffer(10);

        Parallel mailToolParallel;

        mailToolParallel = new Parallel(new CSProcess[]{
                new MailBag(bookingMade,mailsBuffer),new Uint(mailsBuffer)
        });

        mailToolParallel.run();

    }

    public void run() {
        startMailTool();
    }
}
