package frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class UiApplication {
	// Controller in terms of MVC.
	public static JFrame frame;
	public static Hashtable<String, String>  uiautoData;
	public static boolean isPadding;
	public JFrame getFrame() {
		return frame;
	}
	public static int w = 900;
	public static int h = 800;
	protected void init() {

		frame = new JFrame();
		frame.setTitle("Android ROM/RAM shell填充工具...");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension fmDimension =new Dimension(w,h);
		frame.setPreferredSize(fmDimension);

		frame.setLocation((int)((screenSize.width-frame.getWidth())*0.15),(int)((screenSize.height-frame.getHeight())* 0.1));
		//frame.setLocation((screenSize.width-frame.getWidth())/2,(screenSize.height-frame.getHeight())/2);

		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				if (isPadding) {
					JOptionPane.showMessageDialog(new JFrame(), "正在填充ROM/RAM,禁止退出！", "提示",JOptionPane.INFORMATION_MESSAGE);
				}else {
					if(JOptionPane.showConfirmDialog(new JFrame(),"确定退出?", 
							"Warning",JOptionPane.YES_NO_OPTION) == 0){
						System.exit(0);
					}
				}
			}
		});
		String lnf = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lnf);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new Controller(frame);
	}
	public static void main(String[] args) {

		new UiApplication().init();
	}
}

