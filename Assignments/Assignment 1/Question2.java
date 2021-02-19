/*
 * Let us say we have a game where randomly with given probability p, a thread would reward a person Rs.10 and simultaneously
 * another thread would withdraw money by Rs.10 from a person with probability q (1-p). The person who starts the game starts initially with
 * Rs.100. If the amount goes to 0, he is declared bankrupt.
 * Let us say the game draws its outcome every hour. What is the outcome of the game after 10 hours? Or, what is the amount of money that the
 * person would end up with after 10 hours? 
 */
class Game{
    int currAmount,currHour;
    double p,q;
    Game(){
        currAmount = 100;
        p = 0.4;
        currHour = 0;
        q = 1 - p;
    }
    void printCurrentAmountHour(){
        System.out.println("Hour #"+currHour+" CurrentAmount: "+currAmount);
    }
    double getProbabilitySuccess(){
        return p;
    }
    int getAmount(){
        int temp;
        synchronized(this){
            temp = currAmount;
        }
        return temp;
    }
    void incrementAmount(){
        int temp = getAmount();
        temp = temp + 10;
        synchronized(this){
            currAmount = temp;
            currHour++;
        }
    }
    void decrementAmount(){
        int temp = getAmount();
        synchronized(this){
            if(temp <= 10){
                temp = 0;
            }else{
                temp -= 10;
            }
            currHour++;
            currAmount = temp;
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
public class Question2 {
    public static void main(String[] args)throws InterruptedException{
        Game g = new Game();
        double p;
        int trueAmount = 100;
        for(int i=0;i<10;i++){
            //Playing for 10 hours
            p = Math.random();
            if(g.getProbabilitySuccess() < p){
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
            g.printCurrentAmountHour();
            System.out.println();
            Thread.sleep(5000);
        }
        System.out.println("Final Expected Output: "+trueAmount);
        System.out.println("Thread Execution Output: "+g.getAmount());
    }    
}
