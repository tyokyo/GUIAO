package thread;

import frame.Controller;
import frame.UiApplication;
import helper.ServiceHelper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import log.Log;


public class paddStorageThread implements Runnable{
	List<String> cmds;
	String random;
	int row;
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		
	}
	public paddStorageThread(List<String> cmds){
		this.cmds = cmds;
	}
	
	public void run() {
		
		Hashtable<String,Object> ret = ServiceHelper.RunCommand(cmds);
		Object object = ret.get("code");
		if((Integer)object==0){
			System.out.println("执行成功！");
			Log.warn("...执行成功！...");
			Controller.jProgressBar.setValue(100);
			Controller.exitFlag = true;
			JOptionPane.showMessageDialog(new JFrame(), "执行成功!","提示", JOptionPane.INFORMATION_MESSAGE);
		
			UiApplication.isPadding = false;
		}else{
			System.out.println("执行失败！");
			Log.err("...执行失败！");
			Log.err(ret.get("msg").toString());
			
			Controller.jProgressBar.setValue(0);
			Controller.exitFlag = true;
			JOptionPane.showMessageDialog(new JFrame(), "执行失败!","警告", JOptionPane.WARNING_MESSAGE);
			
			UiApplication.isPadding = false;
		}
	}
}
