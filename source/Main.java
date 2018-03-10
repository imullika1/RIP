import core.Publisher;
import core.Receiver;
import service.PropertiesService;
import table.MemCacheTable;
import table.model.RoutingTable;

import java.io.IOException;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class Main {
    public static void main (String[] args) {

        PropertiesService properties = new PropertiesService();
        String ipOne = properties.getIpOne();
        String ipTwo = properties.getIpTwo();
        String groupOne = properties.getGroupOne();
        String groupTwo = properties.getGroupTwo();

        MemCacheTable hashMap = MemCacheTable.getInstance();

        if (!ipOne.equalsIgnoreCase("null") && !groupOne.equalsIgnoreCase("null")) {
            hashMap.addTable(new RoutingTable(ipOne, 0));
            createThread(groupOne);
        }

        if (!ipTwo.equalsIgnoreCase("null") && !groupTwo.equalsIgnoreCase("null")) {
            hashMap.addTable(new RoutingTable(ipTwo, 0));
            createThread(groupTwo);
        }

        new Thread (() -> {
            while (true) {
                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                    for (UUID uuid: hashMap.getAllKey()) {
                        RoutingTable table = hashMap.getTableByKey(uuid);
                        System.out.println(table);
                    }

                    sleep(5000);
                } catch (InterruptedException exception) {
                    System.err.println("Не удалось приостановить выполнение потока:\n" + exception.getMessage());
                    break;
                } catch (IOException exception) {
                    System.err.println("Не удалось очистить поток вывода:\n" + exception.getMessage());
                }
            }
        }).start();
    }

    private static void createThread (String address){
        new Thread (new Receiver(address)).start();
        new Thread (new Publisher(address)).start();
        System.out.println("Созданые потоки Publisher и Receiver: " + address);
    }
}
