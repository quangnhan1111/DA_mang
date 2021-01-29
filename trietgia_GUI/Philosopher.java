
package trietgia_GUI;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;




public class Philosopher implements Runnable{
	
	static GUI frame ;
	
	private int philNumber;
    private Monitor monitor;
    DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");


    Random rand;
    private int sleepLength;
    long totalTime;
    private int eatCount;
    private long eatTime;
    private int hungryCount;
    private int hungryTime;
    private int thinkCount;
    private int thinkTime;
    
    Thread t;
    
    
    
    public Philosopher(int philNumber, Monitor monitor){
        this.philNumber = philNumber;
        this.monitor = monitor;
        rand = new Random();
        //sleepLength = rand.nextInt(3) + 1;
        sleepLength = 1;
//        t la mot luong cua thread Philosopher
        t = new Thread(this);
        t.start();
        
    }

    @Override
    public void run(){
        // 2 minutes
        long end = System.currentTimeMillis() + 120000;
        // start time of the program
        long startTime = System.currentTimeMillis();
        // while its not 2 minutes
        while(System.currentTimeMillis() < end) {
            try{
                // increment thinktime
                thinkTime += thinking();
                // increment think count
                thinkCount++;
                // pick up the forks
                monitor.pickup_forks(philNumber);
                // increment eat time
                eatTime += eat();
                // increment eat count
                eatCount++;
                // return the forks
                monitor.return_forks(philNumber);
                // increment hungry time
                //hungryTime += hungry();
                // increment hungry count
                //hungryCount++;
            }catch(InterruptedException x){
                x.printStackTrace();
            }
        }
        // end time of the program
        long endTime = System.currentTimeMillis();
        // get the total time that the program ran for
        totalTime = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
        try {
            writeToFile(toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getTotalTime(){
        return totalTime;
    }

   
    private int eat() throws InterruptedException {
    	frame.changeColourToGreen(philNumber+1);
        System.out.println(LocalTime.now().format(tf) + " " + "Philosopher " + (philNumber + 1) + ": is eating");
        //int time = rand.nextInt(3) + 1;
        int time = sleepLength;
        TimeUnit.SECONDS.sleep(time);
        //frame.setColourToBlack(philNumber+1);
        return time;
    }

    private int hungry() throws InterruptedException {
    	//frame.changeColourToBlack(philNumber+1);
        System.out.println(LocalTime.now().format(tf) + " " + "Philosopher " + (philNumber + 1) + ": is hungry");
        //int time = rand.nextInt(3) + 1;
        int time = sleepLength;
        TimeUnit.SECONDS.sleep(time);
        //frame.setColourToBlack(philNumber);
        return time;
    }


    private int thinking() throws InterruptedException {
    	frame.changeColourToBlue(philNumber+1);
        System.out.println(LocalTime.now().format(tf) + " " + "Philosopher " + (philNumber + 1) + ": is thinking");
        //int time = rand.nextInt(3) + 1;
        int time = sleepLength;
        TimeUnit.SECONDS.sleep(time);
        
        return time;
    }

    private void writeToFile(String contents) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("D:\\DiningResults.txt", true));
        try{
            out.write(contents);
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // closes the file
            out.close();
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("philosopher: " + (philNumber + 1) + "\n");
        sb.append("total time: " + getTotalTime() + " seconds" + "\n");
        sb.append("eat count: " + eatCount + "\n");
        sb.append("eat time: " + eatTime + " seconds" + "\n");
        sb.append("hungry count: " + hungryCount + "\n");
        sb.append("hungry time: " + hungryTime + " seconds" + "\n");
        sb.append("think count: " + thinkCount + "\n");
        sb.append("think time: " + thinkTime + " seconds" + "\n");
        return sb.toString();
    }

    public static void main(String[] args){
    	
    	int numOfPhil=5;
        // creates a new monitor
        Monitor monitor = new Monitor(numOfPhil);
        // creates 5 philosophers
        Philosopher[] philosophers = new Philosopher[numOfPhil];

        
        frame = new GUI();
		frame.setVisible(true);
        
        
        // initializes the philosophers
        for(int i = 0; i < philosophers.length; i++){
            philosophers[i] = new Philosopher(i, monitor);
        }
//        Thread.join() là một method thông báo rằng hãy chờ thread này hoàn thành rồi thread cha mới được tiếp tục chạy.
//        ==>hoan thanh xong thread moi in ra displays the results la doan code ben duoi
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
