package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class Control{


    private AtomicInteger spaces = new AtomicInteger(30);


    public void increment() {
        spaces.incrementAndGet();
    }

    public void decrement() {
        spaces.decrementAndGet();
    }

    public int getValue() {
        return spaces.get();
    }


//    public void run(){
//
//       while (true){









//            int value = channelIn.read();

//            if(value > 0){
//
//                //System.out.println("CARPARK CONTROL - DEPARTURE");
//            }
//
//            else if (value < 0){
//                //System.out.println("CARPARK CONTROL - ARRIVAL");
//            }

//            int spaces = buffer.get();
            //System.out.println("old avaliable spaces:" + spaces);

            //System.out.println("CARPARK CONTROL - SPACES "+ spaces +" "+ value );

//            spaces = spaces + value;
//            buffer.put(spaces);

            //System.out.println("new avaliable spaces:" + spaces);
//        }
//
//    }
}
