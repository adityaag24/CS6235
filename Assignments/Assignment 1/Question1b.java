/*
 * Let us say we have a game where randomly with given probability p, a thread would reward a person Rs.10 and simultaneously
 * another thread would withdraw money by Rs.10 from a person with probability q (1-p). The person who starts the game starts initially with
 * Rs.100. Let us say the game draws its outcome every hour. What is the outcome of the game after 10 hours? Or, what is the amount of money that the
 * person would end up with after 10 hours? 
 */
class Game{
    int currAmount;
    double p,q;
    Game(){
        currAmount = 100;
        p = 0.4;
        q = 1 - p;
    }
    void printCurrentAmount(){
        System.out.println("CurrentAmount: "+currAmount);
    }
    int getAmount(){
        int temp = 0;//S1
        synchronized(this){//S2
            temp = currAmount;//S3
        }
        return temp;//S4
    }
    void incrementAmount(){
        int temp = getAmount();//S5
        temp = temp + 10;//S6
        synchronized(this){//S7
            currAmount = temp;//S8
        }
    }
    void decrementAmount(){
        int temp = getAmount();//S10
        temp = temp - 10;
        synchronized(this){//S11
            currAmount = temp;//S17
        }
    }
}
class GameRunner implements Runnable{
    Game g;
    GameRunner(Game g){
        this.g = g;
    }
    public void run(){
        if(Thread.currentThread().getName().equalsIgnoreCase("Decrementer")){
            g.decrementAmount();
        }else{
            g.incrementAmount();
        }
    }
}
public class Question1b {
    public static void main(String[] args)throws InterruptedException{
        System.out.println();
        System.out.println("*********Welcome to the Game of Chance*********\n");
        Game g = new Game();
        double p;
        int trueAmount = 100;
        for(int i=0;i<10;i++){
            //Playing for 10 hours
            p = Math.random();
            if(g.p < p){
                //He won
                trueAmount += 10;
                System.out.println("He won the current draw");
                System.out.println("Expected/True Output\nCurrent Amount:"+trueAmount);
                new Thread(new GameRunner(g),"Incrementer").start();
                System.out.println("Thread Execution Output");
            }else{
                //He lost
                trueAmount -= 10;
                System.out.println("He lost the current draw");
                System.out.println("Expected/True Output\nCurrent Amount:"+trueAmount);
                new Thread(new GameRunner(g),"Decrementer").start();
                System.out.println("Thread Execution Output");
            }
            g.printCurrentAmount();
            System.out.println();
            Thread.sleep(2000);
        }
        System.out.println("Final Expected Output: "+trueAmount);
        System.out.println("Thread Execution Output: "+g.getAmount());
    }    
}
