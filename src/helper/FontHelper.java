package helper;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author houyin.tian
 *
 */
public class FontHelper {

	private static String typeface = "新宋体";
	private static int style = Font.PLAIN;
	private static int size = 12;
	
	public static void setFont(TitledBorder border) {
		border.setTitleFont(new Font(typeface, style, size));
	}
	
	public static void setFont(Component comp) {
		setFont(comp,new Font(typeface, style, size));
	}
	
	public static void setFont(JButton button) {
		button.setFont(new Font(typeface, style, size));
	}
	
	public static void setFont(JLabel label) {
		label.setFont(new Font(typeface, style, size));
	}
	
	public static void setFont(JTextField textField) {
		textField.setFont(new Font(typeface, style, size));
	}
	
	public static void setFont(JTextPane textPane) {
		textPane.setFont(new Font(typeface, style, size));
	}
	
	private static void setFont(Component comp, Font font) {
		if (comp instanceof Container) {
			Container container = (Container) comp;
			int n = container.getComponentCount();
			System.out.println(n);
			for (int i = 0; i < n; i++) {
				setFont(container.getComponent(i));
			}
		}else{
			comp.setFont(font);
		}
	}

}
