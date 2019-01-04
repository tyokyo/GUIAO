package frame;
import helper.ServiceHelper;
import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import log.Log;
import org.jdesktop.application.SingleFrameApplication;
import panel.NumberJTextField;
import thread.tPaddStorageThread;

public class MainFrame extends SingleFrameApplication {
	private JPanel topPanel;
	private JComboBox<String> dfLbox,prefixbox,devbox;
	public static JProgressBar jProgressBar;
	public static boolean exitFlag;
	private NumberJTextField jtfld;
	private JButton sizebtn,addLoc;
	public static String paddFolder;
	public static JLabel warnlbl_size;
	public static boolean isPadding;
	@Override
	protected void startup() {
		topPanel = new JPanel();
		TableLayout topPanelLayout = new TableLayout(new double[][] {{TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}, {24.0, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL, TableLayout.FILL}});
		topPanelLayout.setHGap(10);
		topPanelLayout.setVGap(10);
		topPanel.setLayout(topPanelLayout);
		topPanel.setPreferredSize(new java.awt.Dimension(400, 300));
		{
			JLabel devlbl = new JLabel("测试设备：");
			topPanel.add(devlbl, "0, 0");
		}
		{
			devbox = new JComboBox<String>();
			topPanel.add(devbox, "1, 0");
		}
		{
			JLabel title = new JLabel("选择填充位置：");
			topPanel.add(title, "0, 1");
		}
		{
			dfLbox = new JComboBox<String>();

			dfLbox.addItem("/sdcard/");
			dfLbox.addItem("/data/");
			dfLbox.addItem("/system/");
			dfLbox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if (dfLbox.getSelectedItem().toString().contains("sdcard")) {
						//warlbl.setText("");
					}else {
						//warlbl.setText("需要root权限");
					}
				}
			});
			topPanel.add(dfLbox, "1, 1");
		}
		{
			addLoc= new JButton("添加存储位置");
			addLoc.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					AddFolderDialog(new JFrame());
				}
			});
			topPanel.add(addLoc, "2, 1");
		}
		{
			JButton dfButton= new JButton("df");
			dfButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Thread t1 = new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							ArrayList<String> cmdlist = new ArrayList<String>();
							cmdlist.add("adb shell ");
							cmdlist.add("df ");
							ServiceHelper.RunCommand(cmdlist);
						}
					});
					t1.start();
				}
			});
			topPanel.add(dfButton, "3, 1");
		}
		{
			JLabel sizelbl = new JLabel("文件大小:");
			topPanel.add(sizelbl, "0, 2");
		}
		{
			jtfld = new NumberJTextField();
			jtfld.setNumberOnly(true);
			topPanel.add(jtfld, "1, 2");
		}
		{
			prefixbox = new JComboBox<String>();
			prefixbox.addItem("Kb");
			prefixbox.addItem("Mb");
			prefixbox.addItem("Gb");
			topPanel.add(prefixbox, "2, 2");
		}
		{
			jProgressBar = new JProgressBar(0, 100);
			jProgressBar.setPreferredSize(new Dimension(400, 20));
			jProgressBar.setStringPainted(true); // 显示百分比字符
			jProgressBar.setIndeterminate(false); // 不确定的进度条

			topPanel.add(jProgressBar, "0, 3,2,2");
		}
		{
			sizebtn = new JButton("开始填充");
			sizebtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					exitFlag = false;
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");  
					String startDate=sdf.format(new java.util.Date());  
					if (jtfld.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(new JFrame(), "文件大小不能为空!","提示", JOptionPane.INFORMATION_MESSAGE);
					} else
						try {
							if (devbox!=null&&devbox.getItemCount()>=1) {
								String expect_padd_size_str = jtfld.getText()+prefixbox.getSelectedItem().toString().trim();
								paddFolder = expect_padd_size_str+"_"+startDate+".file";
								String padddir= dfLbox.getSelectedItem().toString();
								String warmsg = String.format("%s\n%s\n%s", "确定填充?",padddir,expect_padd_size_str);
								long expect_padd_size = transfer(expect_padd_size_str);

								//adb shell dd if=/dev/zero bs=1024 count=102400 of=/system/100Mb.file
								if(JOptionPane.showConfirmDialog(new JFrame(),warmsg, 
										"Warning",JOptionPane.YES_NO_OPTION) == 0){
									
									isPadding = true;
									
									jtfld.setEnabled(false);
									prefixbox.setEnabled(false);
									dfLbox.setEnabled(false);
									sizebtn.setEnabled(false);
									addLoc.setEnabled(false);
									
									ArrayList<String> cmdlist = new ArrayList<String>();
									cmdlist.add("adb ");
									cmdlist.add("shell ");
									cmdlist.add("dd ");
									cmdlist.add("if=/dev/zero ");
									cmdlist.add("bs=1024 ");
									cmdlist.add("count="+expect_padd_size+" ");
									String paddir = String.format("%s%s", dfLbox.getSelectedItem().toString(),paddFolder);
									System.out.println("pad dir="+paddir);
									cmdlist.add("of="+paddir);

									try {
										Thread barThread = new BarThread(jProgressBar);
										barThread.start();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									tPaddStorageThread rThread = new tPaddStorageThread(cmdlist);
									Thread runthread = new Thread(rThread);
									runthread.start();
								}
							}else {
								JOptionPane.showMessageDialog(new JFrame(), "设备异常!","提示", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			});
			topPanel.add(sizebtn, "3, 3");
		}
		/*{
			JButton stopbtn = new JButton("停止填充");
			stopbtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			topPanel.add(stopbtn, "3, 3");
		}*/
		{
			JTextPane textPane = new JTextPane() {
				private static final long serialVersionUID = 1050724550342188152L;

				public boolean getScrollableTracksViewportWidth() {
					return false;
				}
			};
			textPane.setPreferredSize(new Dimension(UiApplication.w-10, 300));
			textPane.setFont(new Font("新宋体", Font.PLAIN, 12));
			JScrollPane testNumJsp = new JScrollPane(textPane);

			textPane.setEditable(false);
			Log.setLog(textPane);
			
			topPanel.add(testNumJsp, "0,4,4,10");
		}
		{
			Timer timer = new Timer();
			timer.schedule(new log.Devices(devbox),1000, 3000);
		}
		show(topPanel);
	}
	public static int transfer(String size){
		int KMG = 0;
		if (size.toUpperCase().contains("G")) {
			KMG = Integer.parseInt(size.replace("G", "").replace("b", ""))*1024*1024;

		}
		if (size.toUpperCase().contains("M")) {
			KMG = Integer.parseInt(size.replace("M", "").replace("b", ""))*1024;
		}
		if (size.toUpperCase().contains("K")) {
			KMG = Integer.parseInt(size.replace("K", "").replace("b", ""));
		}
		return KMG;
	}
	public static void main(String[] args) {
		launch(MainFrame.class, args);
	}
	public class BarThread extends Thread {
		int DELAY = 500;
		JProgressBar progressBar;    
		Timer timer = new Timer();
		String dir;
		public BarThread(JProgressBar progressBar) throws IOException {
			this.progressBar = progressBar;
			dir = dfLbox.getSelectedItem().toString();
		}
		int ii = 1 ;
		public void run() {
			final Runnable runner = new Runnable() {
				public void run() {
					double actual_padd_size = 0;
					String expect_padd_size_str;
					double expect_padd_size = 0;
					expect_padd_size_str = jtfld.getText()+prefixbox.getSelectedItem().toString().trim();
					Log.info(expect_padd_size_str);
					expect_padd_size = transfer(expect_padd_size_str);
					Log.info("padd:"+(int)expect_padd_size+"Kb");
					try {
						//expect_padd_size_str+".file"
						actual_padd_size = lssCommand(dir,paddFolder);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double perctange = actual_padd_size/expect_padd_size;
					int value =(int)(perctange*100);
					progressBar.setValue(value);
					System.out.println("expect padd size:"+expect_padd_size+"|actual padd size:"+actual_padd_size+"|%比:"+value+"%"+"|setValue:"+value);

					Log.info("padd:"+perctange*100+"%");
					if (perctange==1||exitFlag) {
						jtfld.setEnabled(true);
						prefixbox.setEnabled(true);
						dfLbox.setEnabled(true);
						sizebtn.setEnabled(true);
						addLoc.setEnabled(true);
						
						timer.cancel();
						//timer.purge();
					}
				}
			};
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					runner.run();
				}
			}, 1000,1000);
		}
	}
	public static long lssCommand(String dir,String filename) throws IOException{
		String cmd = String.format("adb shell ls -s %s%s", dir,filename);
		Log.info(cmd);
		String dfstr = ServiceHelper.AdbCmds(cmd);
		long size;
		if (dfstr.contains("No such file or directory")) {
			size = 0;
		}else if (dfstr.contains("device not found")) {
			size = 0;
		}else if(dfstr.contains("ADB server didn't ACK")) {
			size = 0;
		}else if(dfstr.toUpperCase().contains("OFFLINE")) {
			size = 0;
		}else {
			String[] dfArray = dfstr.split(" ")[0].replace(dir, "").split(" ");
			size =Long.parseLong(dfArray[0]);
		}
		return size;
	}
	private boolean isRoot() throws IOException{
		boolean root = false;
		if (dfLbox.getSelectedIndex()!=0) {
			ArrayList<String> cmdlist = new ArrayList<String>();
			cmdlist.add("adb root");
			Hashtable<String,Object> rets= ServiceHelper.RunCommand(cmdlist);
			String rootmsg = rets.get("msg").toString();
			if (rootmsg.contains("running as root")) {
				root = true;
			}else {
				root = false;
			}
			Log.err(rootmsg);
		}else {
			root = true;
		}
		return root;
	}
	private JDialog AddFolderDialog(final JFrame frm){
		final JDialog AddDialog = new JDialog(frm, "添加存储位置", true);
		JLabel uLabel = new JLabel("存储位置:");
		final JTextField pathTF = new JTextField(40);
		JButton setButton = new JButton("确定");
		AddDialog.setSize(400, 80);
		AddDialog.getContentPane().setLayout(new BorderLayout());
		JPanel jPanel = new JPanel();
		jPanel.add(uLabel);
		jPanel.add(pathTF);
		jPanel.add(setButton);
		setButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				String loc = pathTF.getText();
				if (!loc.endsWith("/")) {
					JOptionPane.showMessageDialog(new JFrame(), "存储位置错误\n如:/systel/app/","提示", JOptionPane.INFORMATION_MESSAGE);
				}else {
					if(JOptionPane.showConfirmDialog(new JFrame(),"确定添加", 
							"Warning",JOptionPane.YES_NO_OPTION) == 0){
						dfLbox.addItem(loc.trim());
						AddDialog.dispose();
					}
				}
			}
		});
		AddDialog.getContentPane().add(jPanel,BorderLayout.CENTER);
		AddDialog.setLocationRelativeTo(frm);
		AddDialog.setResizable(true);
		AddDialog.setVisible(true);
		return AddDialog;
	}
}
