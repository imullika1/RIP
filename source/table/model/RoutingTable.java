package table.model;

public class RoutingTable {

	private String address;
	private int metric;

	public RoutingTable(String address, int metric) {
		this.address = address;
		this.metric = metric;
	}

	public String getAddress() {
		return address;
	}
	public int getMetric() {
		return metric;
	}

	public void setMetric(int metric) {
		this.metric = metric;
	}

	@Override
	public String toString() {
		return "[" + address + ", " + metric + "]";
	}
}
