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
        currAmount = 100;//S1
        p = 0.4;//S2
        q = 1 - p;//S3
    }
    void printCurrentAmount(){
        System.out.println("CurrentAmount: "+currAmount);//S4
    }
    int getAmount(){
        int temp = 0;//S5
        synchronized(this){//S6
            temp = currAmount;//S7
        }
        return temp;//S8
    }
    void incrementAmount(){
        int temp = getAmount();//S9
        temp = temp + 10;//S10
        synchronized(this){//S11
            currAmount = temp;//S12
        }
    }
    void decrementAmount(){
        int temp = getAmount();//S13
        temp = temp - 10;//S14
        synchronized(this){//S15
            currAmount = temp;//S16
        }
    }
}
class GameRunner implements Runnable{
    Game g;
    GameRunner(Game g){
        this.g = g;//S17
    }
    public void run(){
        if(Thread.currentThread().getName().equalsIgnoreCase("Decrementer")){
            g.decrementAmount();//S18
        }else{
            g.incrementAmount();//S19
        }
    }
}
public class Question1b {
    public static void main(String[] args)throws InterruptedException{
        System.out.println();//S20
        Game g = new Game();//S21
        double p;//S22
        int trueAmount = 100;//S23
        for(int i=0;i<10;i++){
            //Playing for 10 hours
            p = Math.random();//S24
            if(g.p < p){
                //He won
                trueAmount += 10;//S25
                System.out.println("Expected/True Output\nCurrent Amount:"+trueAmount);//S26
                new Thread(new GameRunner(g),"Incrementer").start();//S27
            }else{
                //He lost
                trueAmount -= 10;//S28
                System.out.println("Expected/True Output\nCurrent Amount:"+trueAmount);//S29
                new Thread(new GameRunner(g),"Decrementer").start();//S30
            }
            System.out.println("Thread Execution Output");//S31
            g.printCurrentAmount();//S32
            System.out.println();//S33
        }
    }    
}
