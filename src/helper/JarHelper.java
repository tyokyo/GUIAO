package helper;

import java.io.File;
import java.util.ArrayList;


public class JarHelper {
	private static int TAG = 2;
	public static ArrayList<String> getVideos(String path){
		ArrayList<String> videos= new ArrayList<String>();
		File file = new File(path);
		File[] videoFiles = file.listFiles();
		if(videoFiles!=null){
			for (File videofile : videoFiles) {
				String ppath = videofile.getPath();
				if(ppath.endsWith(".avi")){
					videos.add(videofile.getName());
				}
			}
		}
		return videos;
	}
	/**
	 * 获取当前jar项目路径
	 * @return
	 */
	public static String getProjectPath() {  
		String path = System.getProperty("user.dir");
		File file = new File(path);
		String ppath = file.getPath();
		String jarpath = "";
		if(ppath.endsWith(".jar")){
			if (TAG==1) {
				jarpath =  file.getParent()+File.separator;
			}else {
				jarpath= "E:\\ATT\\";
			}
		}else{
			if (TAG==1) {
				jarpath =  file.getPath()+File.separator;
			}else {
				jarpath= "E:\\ATT\\";
			}
			//return file.getPath()+File.separator;
			//return "E:\\AutoKPI\\";
		}
		return jarpath;
	}
	/**
	 * 通过jar路径返回jar名称
	 * @param jarPath
	 * @return
	 */
	public static String getJarName() {  
		String path = System.getProperty("java.class.path");
		File file = new File(path);
		return file.getName();
	}

	/**
	 * 获取当前jar项目路径
	 * @return
	 */
	public static String getJarProjectPath() {  
		String path = System.getProperty("java.class.path");
		File file = new File(path);
		String ppath = file.getPath();
		String jarpath="";
		if(ppath.endsWith(".jar")){
			if (TAG==1) {
				jarpath =  file.getParent()+File.separator;
			}else {
				jarpath= "E:\\ATT\\";
			}
			
			//return file.getParent()+File.separator;
			//return "E:\\AutoKPI\\";
		}else{
			if (TAG==1) {
				jarpath =  file.getPath()+File.separator;
			}else {
				jarpath= "E:\\ATT\\";
			}
			//return file.getPath()+File.separator;
			//return "E:\\AutoKPI\\";
		}
		return jarpath;
	}

	/**
	 * 获取jar路径
	 * @return
	 */
	public static String getJarPath() {  
		String path = System.getProperty("java.class.path");
		return path;
	}

	public static void main(String args[]){
		System.out.println(getJarName());
		System.out.println(getJarProjectPath());
		System.out.println(getJarPath());
	}
}
