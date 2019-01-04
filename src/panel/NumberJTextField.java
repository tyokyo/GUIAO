package panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class NumberJTextField extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int limit = Integer.MAX_VALUE;
	private boolean numberOnly;

	public NumberJTextField() {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (getText().length() + 1 > limit) {
					deleteInputChar(e);
					return;
				}
				if (numberOnly) {
					char input = e.getKeyChar();
					if (!Character.isDigit(input)) {
						deleteInputChar(e);
					}
				}
			}

			private void deleteInputChar(KeyEvent source) {
				source.setKeyChar((char) KeyEvent.VK_CLEAR);
			}
		});

	}

	public void setMaxTextLength(int limit) {
		if (limit < 0) {
			return;
		}
		this.limit = limit;
	}

	public int getMaxTextLength() {
		return limit;
	}

	public void setNumberOnly(boolean numberOnly) {
		this.numberOnly = numberOnly;
	}

	public boolean isNumberOnly() {
		return this.numberOnly;
	}
}