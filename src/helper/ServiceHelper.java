package helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import exec.Exec;

import log.Log;

import thread.StreamCaptureThread;
import thread.StreamWatchThread;


public class ServiceHelper {
	public static String getSWVersion(){
		//
		String version = "";
		Process p = null;
		try {
			String cmd = "adb shell getprop ro.mediatek.version.release";
			p = Runtime.getRuntime().exec(cmd);
			StreamCaptureThread oStream = new StreamCaptureThread(p.getInputStream());
			new Thread(oStream).start();
			p.waitFor();
			String oStr = oStream.output.toString();
			version = oStr; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.info("soft version is "+version);
		return version;
	}
	public static void writeFile(String filePathAndName, String fileContent) { 
		try { 
			File f = new File(filePathAndName); 
			if (!f.exists()) { 
				f.createNewFile(); 
			} 
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"UTF-8"); 
			BufferedWriter writer=new BufferedWriter(write);   
			writer.write(fileContent); 
			writer.close(); 
		} catch (Exception e) { 
			System.out.println("写文件内容操作出错"); 
			e.printStackTrace(); 
		} 
	} 
	public static void writeToFile(String FileName,String strContent, boolean isAppended) {
		try {
			File fe = new File(FileName);
			if (!fe.getParentFile().exists()) {
				String dir = fe.getParentFile().getAbsolutePath();
				new File(dir).mkdirs();
			}
			if (!fe.exists()) {
				fe.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(FileName,isAppended);
			strContent=strContent+"\r\n";
			byte[] initContent = strContent.getBytes("UTF-8");
			fileOutputStream.write(initContent);

			fileOutputStream.close();
			fileOutputStream.flush();
			//LOG.info(FileName);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	@SuppressWarnings("finally")
	public static Hashtable<String,Object> RunCommand(String mkpath,String pypath,String device,String videopath){
		Hashtable<String,Object> ret = new Hashtable<String,Object>();
		int retCode = -9;
		String retMsg = "程序错误";
		String command = String.format("\"%s\" \"%s\" \"%s\" \"%s\"",
				mkpath,pypath,device,videopath);

		if(command == null){
			retCode = -2;
			retMsg = "操作命令不能为空！";
		}
		//String cmd = "\"E:\\Program Files\\android-sdk\\tools\\monkeyrunner.bat\" \"E:\\AutoKPI\\scripts\\mk.py\" \"HIKe 828\" path=E:\\\\AutoKPI\\\\video\\\\test.avi";
		System.out.println("start run cmd:"+command);
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			// process error and output message
			StreamWatchThread errorWatch = new StreamWatchThread(p.getErrorStream(),"error",true);
			StreamWatchThread outputWatch = new StreamWatchThread(p.getInputStream(),"info",true);

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

			retMsg = error+output;
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
	public static Hashtable<String,Object> RunCommand(List<String> cmds){
		Hashtable<String,Object> ret = new Hashtable<String,Object>();
		int retCode = -9;
		String retMsg = "程序错误";
		StringBuffer cmdBuffer = new StringBuffer();
		for (String cmd : cmds) {
			cmdBuffer.append(cmd+" ");
		}
		String command = cmdBuffer.toString();
		Log.info(command);
		
		if(command == null){
			retCode = -2;
			retMsg = "操作命令不能为空！";
		}
		//String cmd = "\"E:\\Program Files\\android-sdk\\tools\\monkeyrunner.bat\" \"E:\\AutoKPI\\scripts\\mk.py\" \"HIKe 828\" path=E:\\\\AutoKPI\\\\video\\\\test.avi";
		System.out.println("start run cmd:"+command);
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			// process error and output message
			StreamWatchThread errorWatch = new StreamWatchThread(p.getErrorStream(),"error",true);
			StreamWatchThread outputWatch = new StreamWatchThread(p.getInputStream(),"info",true);

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

			retMsg = error+output;
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
	public static int record(String rcpath) {
		List<String> jar = new ArrayList<String>();
		jar.add("java");
		jar.add("-jar");
		jar.add(rcpath);
		Hashtable<String, Object> ret = Exec.exec(jar, false);
		return (Integer)ret.get("code");
	}

	public static List<Hashtable<String, String>> getDevice() {
		List<Hashtable<String, String>> ldevices = new ArrayList<Hashtable<String, String>>();
		List<String> com = new ArrayList<String>();
		com.add("adb");
		com.add("devices");
		Hashtable<String, Object> ret = Exec.exec(com, false);
		if ((Integer) ret.get("code") != 0) {
			return null;
		}
		String retmsg = ret.get("msg").toString();
		String devices[] = retmsg.split("\n");
		boolean flag = false;
		for (int i = 0; i < devices.length; i++) {
			if (flag) {
				Hashtable<String, String> hdevice = new Hashtable<String, String>();
				String device[] = devices[i].split("\t");
				hdevice.put("name", device[0]);
				hdevice.put("status", device[1]);
				ldevices.add(hdevice);
			} else {
				if (devices[i].indexOf("attached") != -1) {
					flag = true;
				}
			}

		}
		return ldevices;
	}

	public static void killAdb() {
		List<String> com = new ArrayList<String>();
		com.add("adb");
		com.add("kill-server");
		Exec.exec(com, false);

	}
	public static String execPsGrepUiAutomator(boolean AliBln){
		//
		String version = "";
		Process p = null;
		try {
			String cmd="";
			if (AliBln==true) {
				cmd = "acb shell ps | grep uiautomator ";
			}else {
				cmd = "adb shell ps | grep uiautomator ";
			}
			p = Runtime.getRuntime().exec(cmd);
			StreamCaptureThread oStream = new StreamCaptureThread(p.getInputStream());
			new Thread(oStream).start();
			p.waitFor();
			String oStr = oStream.output.toString();
			//Log.err(oStr);
			version = oStr; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
	public static String KillUiAutomatorPids(boolean AliBln){
		String uiAutostr = execPsGrepUiAutomator(AliBln);
		Log.info(uiAutostr);
		if (uiAutostr.replace("\n", "").trim().length()>=1) {
			String[] pids = uiAutostr.split("\n");
			System.out.println(pids.length);
			for (int i = 0; i < pids.length; i++) {
				pids[i] = pids[i].replace("shell", "");
				System.out.println(pids[i]);
				int start = 0;
				for (int j = 0; j < pids[i].length(); j++) {
					if (pids[i].charAt(j)!=' ') {
						start = j;
						break;
					}
				}
				int end = 0;
				for (int j = start; j < pids[i].length(); j++) {
					if (pids[i].charAt(j)==' ') {
						end = j;
						break;
					}
				}
				final String pid = pids[i].substring(start, end).trim();
				System.out.println("["+pid+"]");
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							AdbCmds("adb shell kill "+pid.trim());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
		return uiAutostr;

	}
	public static String AdbCmds(String cmds) throws IOException{
		String retStr = "";
		try {
			Process p = Runtime.getRuntime().exec(cmds);
			
			//StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
			//StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
			
			StreamWatchThread errorWatch = new StreamWatchThread(p.getErrorStream(),"error",true);
			StreamWatchThread outputWatch = new StreamWatchThread(p.getInputStream(),"info",true);
			
			new Thread(errorWatch).start();
			new Thread(outputWatch).start();
			p.waitFor();
			String outputString = outputWatch.getOutput();
			String errorString  = errorWatch.getOutput();
			String output  = outputString +"\n"+errorString;
			System.out.println(output);
			retStr = output;
		} catch (InterruptedException e) {

		}
		return retStr;
	}
	// public static ArrayList<String> currentDevice() {
	// ArrayList<String> crDeviceList = new ArrayList<String>();
	// Process p = null;
	// String adbstr = "";
	// try {
	// String cmd = "adb devices";
	// p = Runtime.getRuntime().exec(cmd);
	// StreamWatchThread oStream = new StreamWatchThread(p.getInputStream(),
	// "info", false);
	// new Thread(oStream).start();
	// p.waitFor();
	// String oStr = oStream.getOutput();
	// adbstr = oStr;
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// String[] devstrsp = adbstr.split("attached");
	// if (devstrsp.length < 2) {
	// return null;
	// }
	// String tmpdevstr = devstrsp[1];
	// String[] devList = tmpdevstr.split("device");
	// for (String dev : devList) {
	// if (!dev.trim().equals("")) {
	// String device = trimLR(dev.replace("\n", ""));
	// // LOG.info("["+device+"]");
	// crDeviceList.add(device);
	// }
	// }
	// return crDeviceList;
	// }


	//	public static String getProjectJarPath(){
	//		java.net.URL url = ServiceHelper.class.getProtectionDomain().getCodeSource().getLocation();
	//		String filePath = null;
	//		try {
	//			filePath = java.net.URLDecoder.decode(url.getPath(), "UTF-8");
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//		return filePath;
	//	}

	//	/**
	//	 * 通过jar路径返回jar名称
	//	 * @param jarPath
	//	 * @return
	//	 */
	//    public static String getJarName() {  
	//    	String path = System.getProperty("java.class.path");
	//        File file = new File(path);
	//        return file.getName();
	//    }  

	//	public static String getProjectPath() {
	//		String filePath = getProjectJarPath();
	//		
	//		if (filePath.endsWith(".jar")){
	//			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
	//		}
	//		java.io.File file = new java.io.File(filePath);
	//		filePath = file.getAbsolutePath();
	//		return filePath;
	//	}

	public static String trimLR(String str) {
		int frtspace = 0;
		int lstspace = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') {
				frtspace = i;
				break;
			}
		}
		for (int t = str.length() - 1; t >= 0; t--) {
			if (str.charAt(t) != ' ') {
				lstspace = t;
				break;
			}
		}
		return str.substring(frtspace, lstspace);
	}

	/**
	 * 获取系统环境变量值
	 * 
	 * @param key
	 * @return
	 */
	public static String getEnv(String key) {
		Map<String, String> env = System.getenv();
		return env.get(key);
	}
	/**
	 * 判断当前jar是否启动
	 * @return
	 */
	public static boolean checkJarInProcess() {
		String jarName = JarHelper.getJarName();
		List<String> command = new ArrayList<String>();
		command.add("jps");
		Hashtable<String, Object> ret = Exec.exec(command, false);
		if ((Integer) ret.get("code") == 0) {
			String retMsg = ret.get("msg").toString();
			if (getOccur(retMsg,jarName) >=2) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取字符串中子字符串个数
	 * @param src
	 * @param find
	 * @return
	 */
	public static int getOccur(String src, String find) {
		int o = 0;
		int index = -1;
		while ((index = src.indexOf(find, index)) > -1) {
			++index;
			++o;
		}
		return o;
	}
	public static void main(String args[]){
		//E:\AutoKPI\video\20140318135354\a1\002\12_0120.jpg 到 E:\AutoKPI\video\20140318135354\a1\002_result
	}
}
