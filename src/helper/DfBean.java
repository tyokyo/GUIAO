package helper;

public class DfBean {
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public int getFree() {
		return free;
	}
	public void setFree(int free) {
		this.free = free;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		System.out.println(this.size);
		System.out.println(this.used);
		System.out.println(this.free);
		return super.toString();
	}
	private int size;
	private int used;
	private int free;
}
