package concurrent;

public class MailEvent {

    public String bookingReference;
    public String message;
    public String mailType;

    public MailEvent(String ref, String msg)
    {

        bookingReference = ref;
        message = msg;
    }

}
