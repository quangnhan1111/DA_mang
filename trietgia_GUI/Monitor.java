package trietgia_GUI;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


//Dễ dàng cho thấy rằng giải pháp này đảm bảo rằng không có hai người hàng xóm nào ăn đồng thời và không có bế tắc nào xảy ra. 
//Tuy nhiên, chúng tôi lưu ý rằng một triết gia có thể chết đói.

//Starvation is possible because signal() is not fair:
//it wakes up any of the waiting threads at random and might thus not wake up a certain thread for an arbitrary long time.


public class Monitor {
	DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");
	
    private final int numOfPhil;
    final Lock lock;
    public enum States{HUNGRY, THINKING, EATING};
    public States[] state;
    public Condition[] self;

    boolean[] leftHungry = new boolean[5];
	boolean[] rightHungry = new boolean[5];
	
    
    
    public Monitor(int numOfPhil) {
        this.numOfPhil = numOfPhil;
        // creates a new reentrant lock
        lock = new ReentrantLock();
        // sets the states to the number of philosophers
        state = new States[numOfPhil];
        // creates a new condition for the amount of philosophers
        self = new Condition[numOfPhil];
        // initializes the state of each philosopher
        // sets the conidion of each philosopher
        initialize();
    }

    // helper method that initializes the variables
    private void initialize(){
        for(int i = 0; i < numOfPhil; i++){
            // sets the state to thinking
            state[i] = States.THINKING;
            // locks the condition
            self[i] = lock.newCondition();
            
            
            leftHungry[i] = false;
	        rightHungry[i] = false;
        }
    }

    public void pickup_forks(int philosopher_number) throws InterruptedException {
        // initial state of the fork is locked
        lock.lock();
        try {
            // sets the state of the philosopher number to hungry
            state[philosopher_number] = States.HUNGRY;
            
         // set state to eating in test() 
            // only if my left and right neighbors  
            // are not eating 
            test(philosopher_number);
            
         // if unable to eat, wait to be signaled 
            if (state[philosopher_number] != States.EATING) {
            	//System.out.println((philosopher_number+1));
                self[philosopher_number].await();
                
                
                rightHungry[left(philosopher_number)] = false;
			    leftHungry[right(philosopher_number)] = false;
            }
        }finally {
            // finally is unlocked
            lock.unlock();
        }
    }

    // helper method
    private void test(int philosopher_number){
//        neu nguoi ben trai va nguoi ben phai ko an va nguoi do dang doi
        if ((state[(philosopher_number + 4) % 5] != States.EATING) &&
                (state[philosopher_number] == States.HUNGRY) &&
        (state[(philosopher_number + 1) % 5] != States.EATING)    && !leftHungry[philosopher_number] && !rightHungry[philosopher_number]){
        	
        	System.out.format(LocalTime.now().format(tf) + " " +"Philosopher %d picks up left chopstick\n", philosopher_number+1);
			System.out.format(LocalTime.now().format(tf) + " " +"Philosopher %d picks up right chopstick\n", philosopher_number+1);
        	
        	
            // set trang thai dang an
            state[philosopher_number] = States.EATING;
            // signal() has no effect during Pickup(), 
            // but is important to wake up waiting 
            // hungry philosophers during Putdown() 
            self[philosopher_number].signal();

        }
    }

    
    public void return_forks(int philosopher_number){
        // starts off locked
        lock.lock();
        try{
        	
        	System.out.format(LocalTime.now().format(tf) + " " +"Philosopher %d puts down right chopstick\n", philosopher_number+1);
			System.out.format(LocalTime.now().format(tf) + " " +"Philosopher %d puts down left chopstick\n", philosopher_number+1);
            // indicate that I’m thinking 
            state[philosopher_number] = States.THINKING;
           
         // if right neighbor R=(i+1)%5 is hungry and 
            // both of R’s neighbors are not eating, 
            // set R’s state to eating and wake it up by  
            // signaling R’s CV 
            test((philosopher_number + 4) % 5);
            if (state[left(philosopher_number)] == States.HUNGRY)
		         leftHungry[philosopher_number] = true;
            test((philosopher_number + 1) % 5);
            if (state[right(philosopher_number)] == States.HUNGRY)
		         rightHungry[philosopher_number] = true;
        }finally {
            // finally unlocks the fork
            lock.unlock();
        }
    }
    
    
    
    private int left(int i) {
	      return (i+1)%5;
	   }    

	   private int right(int i) {
	      return (i+4)%5;
	   } 
}
