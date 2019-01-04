package thread;


import java.lang.reflect.InvocationTargetException;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import frame.Controller;

public class Bar extends Thread {
	private static int DELAY = 500;
	JProgressBar progressBar;    

	public Bar(JProgressBar bar,int freesize) {
		progressBar = bar;
	}

	public void run() {
		int minimum = progressBar.getMinimum();
		int maximum = progressBar.getMaximum();
		Runnable runner = new Runnable() {
			public void run() {
				int value = progressBar.getValue();
				progressBar.setValue(value+1);
			}
		};
		for (int i=minimum; i<maximum; i++) {
			try {
				SwingUtilities.invokeAndWait(runner);
				// Our task for each step is to just sleep
				Thread.sleep(DELAY);
			} catch (InterruptedException ignoredException) {
			} catch (InvocationTargetException ignoredException) {
			}
		}
	}
}