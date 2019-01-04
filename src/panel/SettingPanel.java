package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SettingPanel extends JPanel implements ActionListener{

	private JLabel lb_mk_path;
	//private JLabel lb_cyc_path;
	private JTextField tf_mk_path;
	//private JTextField tf_cyc_path;
	private JButton bt_select_mk;
	private JButton bt_mk_setting;
	//private JButton bt_cyc_setting;

	private JLabel ffmpeg_label;
	private JTextField ffmpeg_txtfld;

	private JLabel fps_label;
	private NumberJTextField fps_txtfld;
	private JButton fps_btn;

	private JButton ffmpeg_select_btn;
	private JButton ffmpeg_btn;

	private JLabel start_label;
	private NumberJTextField start_txtfld;
	private JButton start_btn;

	private JLabel end_label;
	private NumberJTextField end_txtfld;
	private JButton end_btn;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SettingPanel(){
		JPanel select_mk_panel = new JPanel();
		Dimension dimension = new Dimension(300, 20);
		Dimension seperatordim = new Dimension(100, 20);
		Dimension lbDimension = new Dimension(30, 20);

		select_mk_panel.setLayout(new BoxLayout(select_mk_panel, BoxLayout.PAGE_AXIS));
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);


		JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		JLabel e1Lbl= new JLabel("e1");
		e1Lbl.setPreferredSize(lbDimension);

		JComboBox jComboBox1 = new JComboBox();
		jComboBox1.addItem("文件数据");
		jComboBox1.addItem("范围");
		jComboBox1.addItem("数值");

		JTextField e1jtfd = new JTextField();
		e1jtfd.setPreferredSize(dimension);

		JLabel e1rectLbl= new JLabel("范围");
		JTextField e1_rect_jtfd_start = new JTextField();
		e1_rect_jtfd_start.setPreferredSize(seperatordim);

		JLabel e1_sep_lbl= new JLabel("~");
		JTextField e1_rect_jtfd_end = new JTextField();
		e1_rect_jtfd_end.setPreferredSize(seperatordim);

		JButton e1slt_btn = new JButton("选择");
		JButton e1set_btn = new JButton("设置");

		rowPanel.add(e1Lbl);
		rowPanel.add(jComboBox1);
		rowPanel.add(e1jtfd);
		rowPanel.add(e1rectLbl);
		rowPanel.add(e1_rect_jtfd_start);
		rowPanel.add(e1_sep_lbl);
		rowPanel.add(e1_rect_jtfd_end);
		rowPanel.add(e1slt_btn);
		rowPanel.add(e1set_btn);

		if (jComboBox1.getSelectedIndex()==0) {
			e1rectLbl.setEnabled(false);
			e1_rect_jtfd_start.setEnabled(false);
			e1_sep_lbl.setEnabled(false);
			e1_rect_jtfd_end.setEnabled(false);
		}

		select_mk_panel.add(rowPanel);
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		
		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		JLabel e2Lbl= new JLabel("e2");
		e2Lbl.setPreferredSize(lbDimension);

		JComboBox jComboBox2 = new JComboBox();
		jComboBox2.addItem("文件数据");
		jComboBox2.addItem("范围");
		jComboBox2.addItem("数值");

		JTextField e2jtfd = new JTextField();
		e2jtfd.setPreferredSize(dimension);

		JLabel e2rectLbl= new JLabel("范围");
		JTextField e2_rect_jtfd_start = new JTextField();
		e2_rect_jtfd_start.setPreferredSize(seperatordim);

		JLabel e2_sep_lbl= new JLabel("~");
		JTextField e2_rect_jtfd_end = new JTextField();
		e2_rect_jtfd_end.setPreferredSize(seperatordim);

		JButton e2slt_btn = new JButton("选择");
		JButton e2set_btn = new JButton("设置");

		rowPanel.add(e2Lbl);
		rowPanel.add(jComboBox2);
		rowPanel.add(e2jtfd);
		rowPanel.add(e2rectLbl);
		rowPanel.add(e2_rect_jtfd_start);
		rowPanel.add(e2_sep_lbl);
		rowPanel.add(e2_rect_jtfd_end);
		rowPanel.add(e2slt_btn);
		rowPanel.add(e2set_btn);

		if (jComboBox2.getSelectedIndex()==0) {
			e2rectLbl.setEnabled(false);
			e2_rect_jtfd_start.setEnabled(false);
			e2_sep_lbl.setEnabled(false);
			e2_rect_jtfd_end.setEnabled(false);
		}

		select_mk_panel.add(rowPanel);
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		
		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel e3Lbl= new JLabel("e2");
		e3Lbl.setPreferredSize(lbDimension);

		JComboBox jComboBox3 = new JComboBox();
		jComboBox3.addItem("文件数据");
		jComboBox3.addItem("范围");
		jComboBox3.addItem("数值");

		JTextField e3jtfd = new JTextField();
		e3jtfd.setPreferredSize(dimension);

		JLabel e3rectLbl= new JLabel("范围");
		JTextField e3_rect_jtfd_start = new JTextField();
		e3_rect_jtfd_start.setPreferredSize(seperatordim);

		JLabel e3_sep_lbl= new JLabel("~");
		JTextField e3_rect_jtfd_end = new JTextField();
		e3_rect_jtfd_end.setPreferredSize(seperatordim);

		JButton e3slt_btn = new JButton("选择");
		JButton e3set_btn = new JButton("设置");

		rowPanel.add(e3Lbl);
		rowPanel.add(jComboBox3);
		rowPanel.add(e3jtfd);
		rowPanel.add(e3rectLbl);
		rowPanel.add(e3_rect_jtfd_start);
		rowPanel.add(e3_sep_lbl);
		rowPanel.add(e3_rect_jtfd_end);
		rowPanel.add(e3slt_btn);
		rowPanel.add(e3set_btn);

		if (jComboBox3.getSelectedIndex()==0) {
			e3rectLbl.setEnabled(false);
			e3_rect_jtfd_start.setEnabled(false);
			e3_sep_lbl.setEnabled(false);
			e3_rect_jtfd_end.setEnabled(false);
		}
		select_mk_panel.add(rowPanel);

		
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

		//select_mk_panel.setPreferredSize(new Dimension(500, 600));
		this.add(select_mk_panel);


		/*int x = 0;
		int y = 0;
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0 };
		gbl_contentPane.rowHeights = new int[] { 0,0};
		gbl_contentPane.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_contentPane);


		//升级路径
		JPanel select_mk_panel = new JPanel();
		GridBagConstraints gbc_select_mk_panel = new GridBagConstraints();
		gbc_select_mk_panel.insets = new Insets(0, 0, 0, 0);
		gbc_select_mk_panel.fill = GridBagConstraints.BOTH;
		gbc_select_mk_panel.gridx = x;
		gbc_select_mk_panel.gridy = y;
		this.add(select_mk_panel, gbc_select_mk_panel);

		GridBagLayout gbl_select_mk_panel = new GridBagLayout();
		gbl_select_mk_panel.columnWidths = new int[] { 0, 0, 0,0 };
		gbl_select_mk_panel.rowHeights = new int[] { 0 };
		gbl_select_mk_panel.columnWeights = new double[] { 0.0,Double.MIN_VALUE, 0.0,0.0 };
		gbl_select_mk_panel.rowWeights = new double[] { Double.MIN_VALUE };
		select_mk_panel.setLayout(gbl_select_mk_panel);

		lb_mk_path = new JLabel("Monkeyrunner：");
		lb_mk_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lb_mk_path = new GridBagConstraints();
		gbc_lb_mk_path.insets = new Insets(0, 0, 0, 0);
		gbc_lb_mk_path.fill = GridBagConstraints.BOTH;
		gbc_lb_mk_path.gridx = 0;
		gbc_lb_mk_path.gridy = 0;
		select_mk_panel.add(lb_mk_path, gbc_lb_mk_path);

		tf_mk_path = new JTextField();
		tf_mk_path.setSize(100, 10);
		tf_mk_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_tf_mk_path = new GridBagConstraints();
		gbc_tf_mk_path.insets = new Insets(0, 0, 0, 0);
		gbc_tf_mk_path.fill = GridBagConstraints.BOTH;
		gbc_tf_mk_path.gridx = 1;
		gbc_tf_mk_path.gridy = 0;
		select_mk_panel.add(tf_mk_path, gbc_tf_mk_path);

		bt_select_mk = new JButton("选择");
		bt_select_mk.setActionCommand("bt_select_mk");
		bt_select_mk.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_bt_select_mk = new GridBagConstraints();
		gbc_bt_select_mk.insets = new Insets(0, 0, 0, 0);
		gbc_bt_select_mk.gridx = 2;
		gbc_bt_select_mk.gridy = 0;
		bt_select_mk.addActionListener(this);
		select_mk_panel.add(bt_select_mk, gbc_bt_select_mk);

		bt_mk_setting = new JButton("设置");
		bt_mk_setting.setActionCommand("bt_mk_setting");
		bt_mk_setting.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_bt_mk_setting = new GridBagConstraints();
		gbc_bt_mk_setting.insets = new Insets(0, 0, 0, 0);
		gbc_bt_mk_setting.gridx = 3;
		gbc_bt_mk_setting.gridy = 0;
		bt_mk_setting.addActionListener(this);
		select_mk_panel.add(bt_mk_setting, gbc_bt_mk_setting);

		//cycles
		lb_cyc_path = new JLabel("Cycles：");
		lb_cyc_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lb_cyc_path = new GridBagConstraints();
		gbc_lb_cyc_path.insets = new Insets(0, 0, 0, 0);
		gbc_lb_cyc_path.fill = GridBagConstraints.BOTH;
		gbc_lb_cyc_path.gridx = 0;
		gbc_lb_cyc_path.gridy = 1;
		select_mk_panel.add(lb_cyc_path, gbc_lb_cyc_path);

		tf_cyc_path = new JTextField();
		tf_cyc_path.setSize(100, 10);
		tf_cyc_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_tf_cyc_path = new GridBagConstraints();
		gbc_tf_cyc_path.insets = new Insets(0, 0, 0, 0);
		gbc_tf_cyc_path.fill = GridBagConstraints.BOTH;
		gbc_tf_cyc_path.gridx = 1;
		gbc_tf_cyc_path.gridy = 1;
		select_mk_panel.add(tf_cyc_path, gbc_tf_cyc_path);

//		bt_select_cyc = new JButton("选择");
//		bt_select_cyc.setFont(new Font("新宋体", Font.PLAIN, 12));
//		GridBagConstraints gbc_bt_select_cyc = new GridBagConstraints();
//		gbc_bt_select_cyc.insets = new Insets(0, 0, 0, 0);
//		gbc_bt_select_cyc.gridx = 2;
//		gbc_bt_select_cyc.gridy = 1;
//		bt_select_cyc.addActionListener(this);
//		select_mk_panel.add(bt_select_cyc, gbc_bt_select_cyc);

		bt_cyc_setting = new JButton("设置");
		bt_cyc_setting.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_bt_cyc_setting = new GridBagConstraints();
		gbc_bt_cyc_setting.insets = new Insets(0, 0, 0, 0);
		gbc_bt_cyc_setting.gridx = 3;
		gbc_bt_cyc_setting.gridy = 1;
//		gbc_bt_cyc_setting.gridwidth = 2;
		bt_cyc_setting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String cyc = tf_cyc_path.getText();
				if(!cyc.equals("")&&cyc!=null){
					if(!PatternHelper.testPartternInt(cyc)){
						JOptionPane.showMessageDialog(null, "你输入的循环次数不合法,请重新输入!", "ERROR",JOptionPane.ERROR_MESSAGE);
						return;
					}

					boolean bool = ConfigHelper.setCycles(cyc);
					if(bool){
						JOptionPane.showMessageDialog(null, "循环次数设置成功！", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "循环次数不能为空！", "WARNING",JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		select_mk_panel.add(bt_cyc_setting, gbc_bt_cyc_setting);*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
