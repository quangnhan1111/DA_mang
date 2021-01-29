
package trietgia_GUI;



public class DiningMonitor {
    public static void main(String[] args){
        // creates a new monitor
        Monitor monitor = new Monitor(5);
        // creates 5 philosophers
        Philosopher[] philosophers = new Philosopher[5];

        
//        frame = new Test_GUI();
//		frame.setVisible(true);
        
        
        // initializes the philosophers
        for(int i = 0; i < philosophers.length; i++){
            philosophers[i] = new Philosopher(i, monitor);
        }

        // joins the thread with each of the philosopher
        for(int i = 0; i < philosophers.length; i++) {
            try {
                philosophers[i].t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // displays the results
        for(int i = 0; i < philosophers.length; i++){
            System.out.println(philosophers[i].toString());
        }
    }
}
