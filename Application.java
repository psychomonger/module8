/***********************************************************************
 * George E. Mitchell
 * 202330 Software Development I CEN-3024C-32552
 * Module 8 | Concurrency Assignment
 * 
 * This application creates an array of random numbers which it sums first
 * with a single thread and then with two threads concurrently and outputs
 * the time it takes both runs to complete. 
***********************************************************************/
package package1;

/**
 * This application has a single class Application.
 * @author George E. Mitchell
 * @since 6/25/2023
 */
public class Application {

	// Declare and initialize properties.
	private static int idx = 0; // Array index.
	private static int sum = 0; // Sum of numbers in array.
	private static int min = 1; // Minimum value of random number in array.
    private static int max = 10;// Maximum value of random number in array.
    private static int n = 200000000; // Number of random numbers in array.
	private static int[]array = new int[n]; // Array of random numbers.
	private static boolean bool = true; // Summation complete indicator.
	private static long startTime = 0; // Start time.
	private static long endTime = 0; // End time.
	private static long runTime = 0; // Run time in milliseconds.

	
	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
			
		// Output welcome statement.
		System.out.println("This application sums an array of " + String.format("%,d", n) + " random numbers.");
		
		
		// Create array of random numbers.
		for(int i = 0; i < n; i++) {
            array[i] = (int) (Math.random()*(max-min)) + min;
        }
		
		
		// Declare threads.
		Thread t1 = new Thread (new Runnable() { public void run() { while(bool) { addNextNumber(); } } });
		Thread t2 = new Thread (new Runnable() { public void run() { while(bool) { addNextNumber(); } } });
		Thread t3 = new Thread (new Runnable() { public void run() { while(bool) { addNextNumber(); } }	});
	
		
		// Start thread 1 running alone.
		startTime = System.currentTimeMillis();
		t1.start();
		t1.join();
		endTime = System.currentTimeMillis();
		runTime = endTime - startTime;
		System.out.println("The total with 1 thread running is: " + String.format("%,d", sum) + " and took " + runTime + " milliseconds to complete.");
		
		
		// Reinitialize variables for next run.
		idx = 0;
		bool = true;
		sum = 0;
		
		
		// Start thread 2 and 3 running concurrently.
		startTime = System.currentTimeMillis();
		t2.start();
		t3.start();
		t2.join();
		t3.join();
		endTime = System.currentTimeMillis();
		runTime = endTime - startTime;
		System.out.println("The total with 2 threads running is: " + String.format("%,d", sum) + " and took " + runTime + " milliseconds to complete.");
		
	}
	
	/**
	 * Synchronized method for adding next number in the array.
	 */
	public static synchronized void addNextNumber() {
				
		if(idx >= n) { bool = false; } 
		else {
			sum = sum + array[idx];
			idx++;
		}
	
	}
	
}
