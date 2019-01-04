package frame;

import java.awt.datatransfer.DataFlavor;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;

/**
 * UIFactory is used as a way to detect when a new Component has been created.
 * UIFactory is only used for JTextComponents to register the necessary listener
 * to update the enabled state of the cut, copy and paste actions.
 */
public class UIFactory {
	private static final DataFlavor[] PLAIN_TEXT_FLAVORS;

	static {
		try {
			PLAIN_TEXT_FLAVORS = new DataFlavor[5];
			PLAIN_TEXT_FLAVORS[0] = new DataFlavor("text/plain;class=java.lang.String");
			PLAIN_TEXT_FLAVORS[1] = new DataFlavor("text/plain;class=java.io.Reader");
			PLAIN_TEXT_FLAVORS[2] = new DataFlavor("text/plain;charset=unicode;class=java.io.InputStream");
			PLAIN_TEXT_FLAVORS[3] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType+";class=java.lang.String");
			PLAIN_TEXT_FLAVORS[4] = DataFlavor.stringFlavor;
		} catch (ClassNotFoundException cle) {
			throw new RuntimeException("Error creating TextTransferable", cle);
		}
	}
}
