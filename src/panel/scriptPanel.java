package panel;

import helper.JarHelper;
import helper.ServiceHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import log.Log;

public class scriptPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lb_file_path;
	private JLabel lb_device;
	private JLabel lb_device_warn;
	private JLabel lb_monkey_warn;

	public static JComboBox<String> cb_device;

	public static JTextField tf_file_path;

	private JButton bt_select_sc;
	private JButton bt_set_sc;
	private JButton bt_up;
	private JButton bt_down;

	private JTextPane textPane;
	private JScrollPane testNumJsp;

	private JLabel lb_video_name;
	public static  JTextField tf_video_name;

	private static JFrame parent;

	private Timer timer;

	private static boolean deviceIsOk = false;
	Dimension dimension =new Dimension(150, 22);
	private List<Hashtable<String, String>> tlist = new ArrayList<Hashtable<String, String>>();

	/**
	 * 
	 * Create the panel.
	 */
	public scriptPanel(JFrame parent) {
		super();
		this.parent = parent;
		int x = 0;
		int y = 0;

		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0,  0.0,Double.MIN_VALUE };
		this.setLayout(gbl_contentPane);

		JPanel device_panel = new JPanel();
		GridBagConstraints gbc_device_panel = new GridBagConstraints();
		gbc_device_panel.insets = new Insets(0, 0, 0, 0);
		gbc_device_panel.fill = GridBagConstraints.BOTH;
		gbc_device_panel.gridx = x;
		gbc_device_panel.gridy = y;
		this.add(device_panel, gbc_device_panel);

		GridBagLayout gbl_device_panel = new GridBagLayout();
		gbl_device_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_device_panel.rowHeights = new int[] { 0 };
		gbl_device_panel.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_device_panel.rowWeights = new double[] { Double.MIN_VALUE };
		device_panel.setLayout(gbl_device_panel);

		lb_device = new JLabel("测试设备：");
		lb_device.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lb_device = new GridBagConstraints();
		gbc_lb_device.insets = new Insets(0, 0, 0, 0);
		gbc_lb_device.fill = GridBagConstraints.BOTH;
		gbc_lb_device.gridx = 0;
		gbc_lb_device.gridy = 0;
		device_panel.add(lb_device, gbc_lb_device);

		cb_device = new JComboBox<String>();
		cb_device.setPreferredSize(dimension);
		cb_device.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_cb_device = new GridBagConstraints();
		gbc_cb_device.insets = new Insets(0, 0, 0, 0);
		gbc_cb_device.fill = GridBagConstraints.BOTH;
		gbc_cb_device.gridx = 1;
		gbc_cb_device.gridy = 0;
		device_panel.add(cb_device, gbc_cb_device);

		lb_device_warn = new JLabel();
		lb_device_warn.setFont(new Font("新宋体", Font.PLAIN, 12));
		lb_device_warn.setForeground(Color.RED);
		GridBagConstraints gbc_lb_device_warn = new GridBagConstraints();
		gbc_lb_device_warn.insets = new Insets(0, 0, 0, 0);
		gbc_lb_device_warn.fill = GridBagConstraints.BOTH;
		gbc_lb_device_warn.gridx = 2;
		gbc_lb_device_warn.gridy = 0;
		device_panel.add(lb_device_warn, gbc_lb_device_warn);

		lb_monkey_warn = new JLabel();
		lb_monkey_warn.setFont(new Font("新宋体", Font.PLAIN, 12));
		lb_monkey_warn.setForeground(Color.RED);
		GridBagConstraints gbc_lb_monkey_warn = new GridBagConstraints();
		gbc_lb_monkey_warn.insets = new Insets(0, 0, 0, 0);
		gbc_lb_monkey_warn.fill = GridBagConstraints.BOTH;
		gbc_lb_monkey_warn.gridx = 3;
		gbc_lb_monkey_warn.gridy = 0;
		device_panel.add(lb_monkey_warn, gbc_lb_monkey_warn);


		/*// 测试脚本选择
		JPanel select_file_panel = new JPanel();
		GridBagConstraints gbc_select_file_panel = new GridBagConstraints();
		gbc_select_file_panel.insets = new Insets(0, 0, 0, 0);
		gbc_select_file_panel.fill = GridBagConstraints.BOTH;
		gbc_select_file_panel.gridx = 0;
		gbc_select_file_panel.gridy = ++y;
		this.add(select_file_panel, gbc_select_file_panel);

		GridBagLayout gbl_select_file_panel = new GridBagLayout();
		gbl_select_file_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_select_file_panel.rowHeights = new int[] { 0 };
		gbl_select_file_panel.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE, 0.0 };
		gbl_select_file_panel.rowWeights = new double[] { Double.MIN_VALUE };
		select_file_panel.setLayout(gbl_select_file_panel);

		lb_file_path = new JLabel("Jar文件 ：");
		lb_file_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lb_file_path = new GridBagConstraints();
		gbc_lb_file_path.insets = new Insets(0, 0, 0, 0);
		gbc_lb_file_path.fill = GridBagConstraints.BOTH;
		gbc_lb_file_path.gridx = 0;
		gbc_lb_file_path.gridy = 0;
		select_file_panel.add(lb_file_path, gbc_lb_file_path);

		tf_file_path = new JTextField();
		tf_file_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_tf_file_path = new GridBagConstraints();
		gbc_tf_file_path.insets = new Insets(0, 0, 0, 0);
		gbc_tf_file_path.fill = GridBagConstraints.BOTH;
		gbc_tf_file_path.gridx = 1;
		gbc_tf_file_path.gridy = 0;
		select_file_panel.add(tf_file_path, gbc_tf_file_path);

		bt_select_sc = new JButton("选择");
		bt_select_sc.setPreferredSize(dimension);
		bt_select_sc.setActionCommand("bt_select_sc");
		bt_select_sc.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_bt_select = new GridBagConstraints();
		gbc_bt_select.insets = new Insets(0, 0, 0, 0);
		gbc_bt_select.gridx = 2;
		gbc_bt_select.gridy = 0;
		bt_select_sc.addActionListener(this);
		select_file_panel.add(bt_select_sc, gbc_bt_select);

		bt_set_sc = new JButton("设置");
		bt_set_sc.setPreferredSize(dimension);
		bt_set_sc.setActionCommand("bt_set_sc");
		bt_set_sc.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_bt_set = new GridBagConstraints();
		gbc_bt_set.insets = new Insets(0, 0, 0, 0);
		gbc_bt_set.gridx = 3;
		gbc_bt_set.gridy = 0;
		bt_set_sc.addActionListener(this);
		select_file_panel.add(bt_set_sc, gbc_bt_set);


		bt_up = new JButton("UP");
		bt_up.setPreferredSize(dimension);
		bt_up.setActionCommand("bt_up");
		bt_up.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_up = new GridBagConstraints();
		gbc_up.insets = new Insets(0, 0, 0, 0);
		gbc_up.gridx = 2;
		gbc_up.gridy = 1;
		bt_up.addActionListener(this);
		select_file_panel.add(bt_up, gbc_up);


		bt_down = new JButton("DOWN");
		bt_down.setPreferredSize(dimension);
		bt_down.setActionCommand("bt_down");
		bt_down.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_down = new GridBagConstraints();
		gbc_down.insets = new Insets(0, 0, 0, 0);
		gbc_down.gridx = 3;
		gbc_down.gridy = 1;
		bt_down.addActionListener(this);
		select_file_panel.add(bt_down, gbc_down);
*/
		//脚本模块
		// 日志模块
		JPanel log_panel = new JPanel();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 0, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = ++y;
		gbc_textArea.gridwidth = 2;
		this.add(log_panel, gbc_textArea);

		GridBagLayout gbl_log_panel = new GridBagLayout();
		gbl_log_panel.columnWidths = new int[] { 0 };
		gbl_log_panel.rowHeights = new int[] { 0 };
		gbl_log_panel.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_log_panel.rowWeights = new double[] { Double.MIN_VALUE };
		log_panel.setLayout(gbl_log_panel);

		textPane = new JTextPane() {
			private static final long serialVersionUID = 1050724550342188152L;

			public boolean getScrollableTracksViewportWidth() {
				return false;
			}

			public void setSize(Dimension d) {
				if (d.width < getParent().getSize().width) {
					d.width = getParent().getSize().width;
				}
				d.width += 100;
				super.setSize(d);
			}
		};

		TitledBorder tb_log = BorderFactory.createTitledBorder("日志");
		tb_log.setTitleFont(new Font("新宋体", Font.PLAIN, 12));
		log_panel.setBorder(tb_log);

		textPane.setFont(new Font("新宋体", Font.PLAIN, 12));
		testNumJsp = new JScrollPane(textPane);

		GridBagConstraints gbc_log_panel = new GridBagConstraints();
		gbc_log_panel.insets = new Insets(0, 0, 0, 0);
		gbc_log_panel.fill = GridBagConstraints.BOTH;
		gbc_log_panel.gridx = 0;
		gbc_log_panel.gridy = 0;
		gbc_log_panel.gridheight=200;
		log_panel.add(testNumJsp, gbc_log_panel);

		textPane.setEditable(false);
		Log.setLog(textPane);

		timer = new Timer();
		timer.schedule(new Devices(),1000, 3000);
	}


	/**
	 * 回放脚本
	 */
	public static void replay() {

	}

	/**
	 * 清空日志
	 */
	public void clear() {
		textPane.setText("");
	}
	/**
	 * 关闭timer
	 */
	public void closeTimer(){
		timer.cancel();
		timer.purge();
	}

	class Devices extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub

			String monkey = ServiceHelper.execPsGrepUiAutomator(false);
			if (monkey.contains("uiautomator")) {
				lb_monkey_warn.setText("uiautomator已连接");
			}else {
				lb_monkey_warn.setText("uiautomator已断开");
			}

			List<Hashtable<String, String>> devList = ServiceHelper.getDevice();
			if (tlist == null) {
				tlist = devList;
			} else if (devList == null) {
				tlist = devList;
				deviceIsOk = false;
			} else if (!tlist.equals(devList)) {
				cb_device.removeAllItems();
				List<String> errmsg = new ArrayList<String>();
				for (int i = 0; i < devList.size(); i++) {
					if (devList.get(i).get("status").equals("device")) {
						//System.out.println(devList.get(i).get("name"));
						String devitem = devList.get(i).get("name");
						cb_device.addItem(devitem);
						/*if (!MainFrame.devicesList.contains(devitem)) {
							MainFrame.devicesList.add(devitem);
						}*/
					} else {
						errmsg.add(devList.get(i).get("name") + "\t" + devList.get(i).get("status"));
						Log.warn(devList.get(i).get("name") + "\t" + devList.get(i).get("status"));
					}
				}
				tlist = devList;
				String msg = checkRepeat(tlist);
				if(msg!=null){
					errmsg.add(msg);
				}
				String err = "";
				if(errmsg.size()>0){
					err=err+"警告：";
					for(int i=0;i<errmsg.size();i++){
						err=err+(i+1)+"、"+errmsg.get(i)+";";
					}
					deviceIsOk = false;
				}else{
					deviceIsOk = true;
				}
				lb_device_warn.setText(err);
			}

		}
	}
	//判断devices是否存在重复
	public String checkRepeat(List<Hashtable<String, String>> list) {
		Hashtable<String, String> temp;
		boolean bool = false;
		String msg = null;
		for (int i = 0; i < list.size() - 1; i++) {
			if (bool) {
				break;
			}
			temp = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				if (temp.get("name").equals(list.get(j).get("name"))) {
					msg = "存在多个名称为“" + temp.get("name") + "”的设备！";
					bool = true;
					break;
				}
			}
		}
		return msg;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		System.out.println(cmd);
		if (cmd.equals("bt_set_sc")) {
			if(JOptionPane.showConfirmDialog(new JFrame(),"确定Push jar文件到手机端?", 
					"Warning",JOptionPane.YES_NO_OPTION) == 0){
				Thread t1 = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ArrayList<String> cmds = new ArrayList<String>();
						cmds.add("adb");
						cmds.add("push");
						cmds.add(tf_file_path.getText());
						cmds.add("/data/local/tmp/");
						Hashtable<String,Object> ret = ServiceHelper.RunCommand(cmds);
						String pop  = String.format("%s \n %s \n %s",cmds.toString(),ret.get("code"),ret.get("msg"));
						JOptionPane.showMessageDialog(new JFrame(), pop,"提示", JOptionPane.INFORMATION_MESSAGE);
					}

				});
				t1.start();
			}
		}
		if (cmd.equals("bt_select_sc")) {
			String path = JarHelper.getJarProjectPath() + "\\jar";
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			JFileChooser c = new JFileChooser(new File(JarHelper.getJarProjectPath() + "\\jar"));
			// 设置为智能选文件
			c.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// 去掉所有
			c.removeChoosableFileFilter(c.getAcceptAllFileFilter());
			c.setDialogTitle("请选择jar");
			FileNameExtensionFilter py = new FileNameExtensionFilter("jar(*.jar)", "jar");
			c.setFileFilter(py);
			int result = c.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = c.getSelectedFile();
				tf_file_path.setText(file.getPath());
			}
		}
	}
}
