package concurrent;
import org.jcsp.lang.*;

import org.jcsp.lang.CSProcess;
import org.jcsp.util.ints.BufferInt;

public class Control implements CSProcess {

    // channels recive space updates from arrive and depart
    private ChannelInputInt channelIn;
    private ChannelOutputInt arriveChannelOut;
    private ChannelOutputInt departChannelOut;
    private BufferInt buffer;

    public Control(Any2OneChannelInt input, One2OneChannelInt arriveOut, One2OneChannelInt departOut, BufferInt  buff){

        channelIn = input.in();
        arriveChannelOut = arriveOut.out();
        departChannelOut = departOut.out();
        buffer = buff;
    }

    public void run(){

        while (true){


            int value = channelIn.read();

            if(value > 0){
                System.out.println("new arrival");
            }

            else if (value < 0){
                System.out.println("new departure");
            }

            int spaces = buffer.get();
            System.out.println("old avaliable spaces:" + spaces);

            spaces = spaces + value;
            buffer.put(spaces);

            System.out.println("new avaliable spaces:" + spaces);
        }

    }
}
