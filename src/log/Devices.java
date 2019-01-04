package log;

import helper.ServiceHelper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TimerTask;
import javax.swing.JComboBox;

public class Devices extends TimerTask{
	JComboBox<String> jComboBox;
	public Devices(JComboBox<String> jComboBox){
		this.jComboBox = jComboBox;
	}
	private List<Hashtable<String, String>> tlist = new ArrayList<Hashtable<String, String>>();
	boolean deviceIsOk;
	@Override
	public void run() {
		// TODO Auto-generated method stub

		List<Hashtable<String, String>> devList = ServiceHelper.getDevice();
		if (tlist == null) {
			tlist = devList;
		} else if (devList == null) {
			tlist = devList;
			deviceIsOk = false;
		} else if (!tlist.equals(devList)) {
			jComboBox.removeAllItems();
			List<String> errmsg = new ArrayList<String>();
			for (int i = 0; i < devList.size(); i++) {
				if (devList.get(i).get("status").equals("device")) {
					//System.out.println(devList.get(i).get("name"));
					String devitem = devList.get(i).get("name");
					jComboBox.addItem(devitem);
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
}