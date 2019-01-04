package frame;

import helper.DfBean;
import helper.ServiceHelper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import log.Log;
import panel.NumberJTextField;
import thread.paddStorageThread;

public class Controller {
	public static JTable table;
	public static Thread runThead =null;
	public static Thread resourceThead =null;;
	public static boolean threadstop = true;
	public static JLabel domLabel;
	public static JProgressBar bar;
	public static int index;
	private Timer timer;

	private JFrame host;

	private JPanel toolBarPanel;
	static Dimension dimension =new Dimension(150, 22);
	static Dimension dimension3 =new Dimension(100, 22);

	static Dimension cboxdimension =new Dimension(200, 22);
	static Dimension kbdimension =new Dimension(50, 20);
	static Dimension devdimension =new Dimension(100, 20);
	static Dimension btnDimension = new Dimension(200, 22);
	static Dimension lbDimension = new Dimension(110, 22);

	static Dimension dfDimension = new Dimension(110, 22);

	public static boolean exitFlag;
	private JButton addLoc;
	private JButton sizebtn;
	public static JComboBox<String> dfLbox;
	public static JRadioButton percradion;
	public static JRadioButton sizeradion;
	public static JSlider slider;
	public static NumberJTextField jtfld;
	public static JComboBox<String> prefixbox;
	public static String paddFolder;
	public static JLabel warnlbl_size;
	public static JProgressBar jProgressBar;

	private void createUI(final JFrame frame) {
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());

		//JScrollPane s_pan = new JScrollPane(table);

		//container.add(LogPanel(),BorderLayout.CENTER);
		container.add(getToolPanel(),BorderLayout.NORTH);
		container.add(new JPanel(),BorderLayout.SOUTH);
		container.add(new JPanel(),BorderLayout.CENTER);
		
		
		frame.pack();
		frame.setVisible(true);
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
	public static DfBean dfCommand(String dir) throws IOException{
		DfBean dfBean = new DfBean();
		String dfstr = ServiceHelper.AdbCmds(String.format("adb shell df %s", dir));
		String[] dfArray = dfstr.split("\n")[1].replace(dir, "").split(" ");
		int size = dfArray.length;
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			if (!dfArray[i].trim().equals("")) {
				ret.add(dfArray[i].trim());
			}
		}
		dfBean.setSize(transfer(ret.get(0)));
		dfBean.setUsed(transfer(ret.get(1)));
		dfBean.setFree(transfer(ret.get(2)));
		dfBean.toString();

		return dfBean;
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
	private JPanel getToolPanel() {
		toolBarPanel = new JPanel();
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

		
		JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		JLabel devlbl = new JLabel("测试设备：");
		JComboBox<String> devbox = new JComboBox<String>();
		devbox.setPreferredSize(devdimension);

		JLabel devwarlbl = new JLabel();
		rowPanel.add(devlbl);
		rowPanel.add(devbox);
		rowPanel.add(devwarlbl);
		toolBarPanel.add(rowPanel);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JTextPane textPane = new JTextPane() {
			private static final long serialVersionUID = 1050724550342188152L;

			public boolean getScrollableTracksViewportWidth() {
				return false;
			}
		};
		textPane.setPreferredSize(new Dimension(UiApplication.w-10, 300));

		TitledBorder tb_log = BorderFactory.createTitledBorder("日志");
		tb_log.setTitleFont(new Font("新宋体", Font.PLAIN, 12));

		textPane.setFont(new Font("新宋体", Font.PLAIN, 12));
		JScrollPane testNumJsp = new JScrollPane(textPane);

		testNumJsp.setBorder(tb_log);
		textPane.setEditable(false);
		Log.setLog(textPane);

		rowPanel.add(testNumJsp);
		toolBarPanel.add(rowPanel);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);


		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel title = new JLabel("选择填充位置：");
		final JLabel warlbl = new JLabel("");

		dfLbox = new JComboBox<String>();
		dfLbox.setPreferredSize(cboxdimension);

		dfLbox.addItem("/sdcard/");
		dfLbox.addItem("/data/");
		dfLbox.addItem("/system/");
		dfLbox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (dfLbox.getSelectedItem().toString().contains("sdcard")) {
					warlbl.setText("");
				}else {
					warlbl.setText("需要root权限");
				}
			}
		});
		addLoc= new JButton("添加存储位置");
		addLoc.setPreferredSize(new Dimension(btnDimension));
		addLoc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddFolderDialog(new JFrame());
			}
		});

		JButton dfButton= new JButton("df");
		dfButton.setPreferredSize(new Dimension(btnDimension));
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
		
		
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

		rowPanel.add(title);
		rowPanel.add(dfLbox);
		rowPanel.add(warlbl);
		rowPanel.add(addLoc);
		rowPanel.add(dfButton);
		toolBarPanel.add(rowPanel);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel sizelbl = new JLabel("文件大小:");
		jtfld = new NumberJTextField();
		jtfld.setNumberOnly(true);
		jtfld.setPreferredSize(cboxdimension);

		prefixbox = new JComboBox<String>();
		prefixbox.addItem("Kb");
		prefixbox.addItem("Mb");
		prefixbox.addItem("Gb");
		prefixbox.setPreferredSize(kbdimension);

		warnlbl_size = new JLabel("");
		warnlbl_size.setPreferredSize(dfDimension);

		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		rowPanel.add(sizelbl);
		rowPanel.add(jtfld);
		rowPanel.add(prefixbox);
		rowPanel.add(warnlbl_size);
		toolBarPanel.add(rowPanel);

		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		jProgressBar = new JProgressBar(0, 100);
		jProgressBar.setPreferredSize(new Dimension(400, 20));
		jProgressBar.setStringPainted(true); // 显示百分比字符
		jProgressBar.setIndeterminate(false); // 不确定的进度条

		sizebtn = new JButton("开始填充");
		sizebtn.setPreferredSize(btnDimension);
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
						if (!isRoot()) {
							JOptionPane.showMessageDialog(new JFrame(), "没有root权限!","提示", JOptionPane.INFORMATION_MESSAGE);
						}else {
							
							String expect_padd_size_str = jtfld.getText()+prefixbox.getSelectedItem().toString().trim();

							paddFolder = expect_padd_size_str+"_"+startDate+".file";

							String padddir= dfLbox.getSelectedItem().toString();
							String warmsg = String.format("%s\n%s\n%s", "确定填充?",padddir,expect_padd_size_str);
							long expect_padd_size = transfer(expect_padd_size_str);

							//adb shell dd if=/dev/zero bs=1024 count=102400 of=/system/100Mb.file
							if(JOptionPane.showConfirmDialog(new JFrame(),warmsg, 
									"Warning",JOptionPane.YES_NO_OPTION) == 0){
								
								UiApplication.isPadding = true;
								
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

								paddStorageThread rThread = new paddStorageThread(cmdlist);
								Thread runthread = new Thread(rThread);
								runthread.start();
							}
						}
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});

		rowPanel.add(jProgressBar);
		rowPanel.add(sizebtn);
		toolBarPanel.add(rowPanel);

		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

		timer = new Timer();
		timer.schedule(new log.Devices(devbox),1000, 3000);

		return toolBarPanel;
	}
	Controller(JFrame hosts) {
		host = hosts;
		createUI(host);
		host.pack();
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
}
