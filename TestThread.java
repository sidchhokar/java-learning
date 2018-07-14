public class TestThread {

	
	public synchronized static void main(String[] args) throws InterruptedException {
		
		Thread t = new Thread(){};
		t.start();
		System.out.println("1st print");
		
		Thread t2 = new Thread() {
			public void run() {
				synchronized(TestThread.class) {
					System.out.println("print inside Thread class");
					TestThread.class.notify();
				}
			}
		};		
		
		t2.start();
		TestThread.class.wait();
		// t.wait(); this method shows IllegalMonitorStateException because 't' thread 
		// has already completed, since the run method of thread class have empty 
		// definition.
		System.out.println("after display");			
		System.out.println("2nd print");		
	}
	
}
