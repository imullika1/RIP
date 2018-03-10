package core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.JsonHandler;
import table.service.TableService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver implements Runnable{

	private byte[] buffer = new byte[1024];//TODO dynamic size
	private InetAddress group;
	private MulticastSocket socket;

	public Receiver(String group) {
		try {
			this.socket = new MulticastSocket(320);
			this.group = Inet4Address.getByName(group);
		} catch (IOException exception) {
			System.err.println("Ошибка запуска Receiver: " + exception.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			socket.joinGroup(group);

			while (!socket.isClosed()) {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				buffer = packet.getData();

				if (!Inet4Address.getLocalHost().getHostAddress().equals(packet.getAddress().getHostAddress())) {
					String source = new String(buffer).trim();

					JsonHandler handler = new JsonHandler();
                    JSONArray array = handler.getDataFromJson(source);
                    TableService service = new TableService();
                    for (int index = 0; index < array.size(); index++ ){
                        JSONObject object = (JSONObject) array.get(index);
                        String address = (String) object.get("address");
                        int metric = Integer.parseInt(object.get("metric").toString());
                        service.dataHandler(address, metric);
                    }
				}
			}
		} catch (IOException exception) {
			System.err.println("Ошибка Receiver: " + exception.getMessage());
		}
	}
}