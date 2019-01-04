package log;

import java.awt.Color;
import java.util.Date;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Log {
	
	private static JTextPane textPane = null;
	
	public static void setLog(JTextPane tp){
		textPane = tp;
	}
	
	private static JTextPane getLog()throws NullLogException{
		if(textPane == null){
			throw new NullLogException("日志模块未初始化！");
		}else{
			return textPane;
		}
	}
	/**
	 * 普通信息
	 * @param log
	 * @param msg
	 */
	public static void info(String msg) {
		try {
			setDocs(getLog(),"INFO",msg,Color.BLACK,false,12);
		} catch (NullLogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 错误信息
	 * @param log
	 * @param msg
	 */
	public static void err(String msg) {
		try {
			setDocs(getLog(),"ERR ",msg,Color.RED,false,12);
		} catch (NullLogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 警告信息
	 * @param log
	 * @param msg
	 */
	public static void warn(String msg) {
		try {
			setDocs(getLog(),"WARN",msg,Color.ORANGE,false,12);
		} catch (NullLogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param log
	 * @param str
	 * @param attrSet
	 */
	private static void insert(JTextPane log, String level ,String str, AttributeSet attrSet) {
		Document doc = log.getDocument();
		str =level+"\t"+getDateTime()+"\t"+str+"\n";
		try {
			doc.insertString(doc.getLength(), str, attrSet);
			log.setCaretPosition(doc.getLength());
		} catch (BadLocationException e) {
			System.out.println("BadLocationException:   " + e);
		}
	}
	/**
	 * 
	 * @param log
	 * @param str
	 * @param col
	 * @param bold
	 * @param fontSize
	 */
	private static void setDocs(JTextPane log,String level,String str, Color col, boolean bold, int fontSize) {
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, col);
		// 颜色
		if (bold == true) {
			StyleConstants.setBold(attrSet, true);
		}
		// 字体大小
		StyleConstants.setFontSize(attrSet, fontSize);
		// 字体类型
		// StyleConstants.setFontFamily(attrSet, "黑体 ");
		// 设置字体
		insert(log,level,str, attrSet);
	}
	
	private static String getDateTime(){
		
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
	}
}
