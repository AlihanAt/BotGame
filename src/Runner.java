import lenz.htw.coshnost.Server;

public class Runner {

    public static void main(String[] args) {

//        TestThread testThread = new TestThread();
//        testThread.start();

        Client c1 = new Client();
        Client c2 = new Client();
        Client c3 = new Client();

        Thread t1 = new Thread(c1);
        t1.start();
        Thread t2 = new Thread(c2);
        t2.start();
        Thread t3 = new Thread(c3);
        t3.start();
    }
}

class TestThread extends Thread {
    public void run() {
        Server.runOnceAndReturnTheWinner(5);
    }
}
