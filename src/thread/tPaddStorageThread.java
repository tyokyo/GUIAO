package thread;

import frame.MainFrame;
import helper.ServiceHelper;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import log.Log;


public class tPaddStorageThread implements Runnable{
	List<String> cmds;
	String random;
	int row;
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		
	}
	public tPaddStorageThread(List<String> cmds){
		this.cmds = cmds;
	}
	
	public void run() {
		
		Hashtable<String,Object> ret = ServiceHelper.RunCommand(cmds);
		Object object = ret.get("code");
		if((Integer)object==0){
			System.out.println("执行成功！");
			Log.warn("...执行成功！...");
			MainFrame.jProgressBar.setValue(100);
			MainFrame.exitFlag = true;
			JOptionPane.showMessageDialog(new JFrame(), "执行成功!","提示", JOptionPane.INFORMATION_MESSAGE);
		
			MainFrame.isPadding = false;
		}else{
			System.out.println("执行失败！");
			Log.err("...执行失败！");
			Log.err(ret.get("msg").toString());
			
			MainFrame.jProgressBar.setValue(0);
			MainFrame.exitFlag = true;
			JOptionPane.showMessageDialog(new JFrame(), "执行失败!","警告", JOptionPane.WARNING_MESSAGE);
			
			MainFrame.isPadding = false;
		}
	}
}
