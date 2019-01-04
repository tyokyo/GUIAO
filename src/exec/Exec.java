package exec;

import java.util.*;

import thread.StreamWatchThread;


public class Exec {
	
	@SuppressWarnings("finally")
	public static Hashtable<String,Object> execpy(String cmd,boolean islog){
		System.out.println(cmd);
		int retCode = -9;
		Hashtable<String,Object> ret = new Hashtable<String,Object>();
		String retMsg = "程序错误";
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmd);
			StreamWatchThread errorWatch = new StreamWatchThread(p.getErrorStream(),"error",islog);
			StreamWatchThread outputWatch = new StreamWatchThread(p.getInputStream(),"info",islog);
			new Thread(errorWatch).start();
			new Thread(errorWatch).start();
			int exitVal = p.waitFor();
			
			String error = errorWatch.getOutput();
			String output = outputWatch.getOutput();
			retCode = exitVal;
			retMsg = output+error;
			p.destroy();

		}catch (Throwable t) {
			t.printStackTrace();
			retCode = -1;
			retMsg = "Throwable异常";
		}finally{
			ret.put("code", retCode);
			ret.put("msg", retMsg);
			return ret;
		}

	}
	/**
	 * 
	 * @param command
	 * @return 0执行成功,-1 Throwable异常，-2执行命令为空
	 */
	@SuppressWarnings("finally")
	public static Hashtable<String,Object> exec(List<String> command,boolean islog){
		Hashtable<String,Object> ret = new Hashtable<String,Object>();
		int retCode = -9;
		String retMsg = "程序错误";
		try {
			if(command == null){
				retCode = -2;
				retMsg = "操作命令不能为空！";
			}
			ProcessBuilder pb = null;
			Process p = null;
			pb = new ProcessBuilder(command);
			p = pb.start();
			
			// process error and output message
			StreamWatchThread errorWatch = new StreamWatchThread(p.getErrorStream(),"error",islog);
			StreamWatchThread outputWatch = new StreamWatchThread(p.getInputStream(),"info",islog);
			
			// start to watch
			errorWatch.start();
			outputWatch.start();
			// wait for exit
			int exitVal = p.waitFor();
			
			String error = errorWatch.getOutput();
			//System.out.println(error);
			String output = outputWatch.getOutput();
			//System.out.println(output);
			retCode = exitVal;
			retMsg = output+error;
			p.destroy();
			
		} catch (Throwable t) {
			t.printStackTrace();
			retCode = -1;
			retMsg = "Throwable异常";
		}finally{
			ret.put("code", retCode);
			ret.put("msg", retMsg);
			return ret;
		}
	}
}