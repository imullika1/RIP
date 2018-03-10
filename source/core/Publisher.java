package core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import table.MemCacheTable;
import table.model.RoutingTable;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Publisher implements Runnable {

	private byte[] buffer;
	private InetAddress group;
	private DatagramSocket socket;
	private MemCacheTable hashMap = MemCacheTable.getInstance();
	private JSONObject object;
    private JSONArray jsonArray = new JSONArray();

    public Publisher (String group) {
		try {
			this.socket = new DatagramSocket();
			this.group = Inet4Address.getByName(group);
		} catch (UnknownHostException | SocketException exception) {
			System.err.println("Ошбка запуска Publisher: " + exception.getMessage());
		}
	}

	@Override
	public void run() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				for (UUID uuid: hashMap.getAllKey()) {
					RoutingTable table = hashMap.getTableByKey(uuid);
					object = new JSONObject();
					object.put("address",table.getAddress());
					int metric = table.getMetric() + 1;
					object.put("metric",metric);
					jsonArray.add(object);
				}

				buffer = jsonArray.toJSONString().getBytes();
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 320);

				try {
					socket.send(packet);
				} catch (IOException exception) {
					System.err.println("Ошибка отправки пакета из Publisher: " + exception.getMessage());
				}
				jsonArray.clear();
			}
		};
		new Timer().schedule(task, 30000, 30000);
	}
}

