package table;

import table.model.RoutingTable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemCacheTable {

	// Параметры
	private static MemCacheTable instance = new MemCacheTable();
	private final Map<UUID, RoutingTable> mapRoutingTable = new ConcurrentHashMap<>();
	/**
	 * Сеттер
	 * @param table
	 * Тип: экземпляр класса RoutingTable
	 * Описание: добавление экземпляра класса RoutingTable в хешированную карту
	 */
	public void addTable(RoutingTable table) {
		mapRoutingTable.put(UUID.randomUUID(),  table);
	}
	public void updateTable(UUID key, int metric){
		RoutingTable table = getTableByKey(key);
		removeTable(key);
		table.setMetric(metric);
		addTable(table);
	}
	public void removeTable(UUID key){
		mapRoutingTable.remove(key);
	}
	/**
	 * Геттер
	 * @param key
	 * Тип: UUID
	 * Описание: Уникальный ключ записи в хешированной карты
	 * @return
	 * Тип: экземпляр класса RoutingTable
	 * Описание: возвращает экземпляр класса RoutingTable из хешированной карты по ключу
	 */
	public RoutingTable getTableByKey(UUID key){
		return mapRoutingTable.get(key);
	}
	/**
	 * Геттер
	 * @return
	 * Тип: Set<UUID>
	 * Описание: возвращает значение интерфейса Set<UUID>
	 */
	public Set<UUID> getAllKey(){
		return mapRoutingTable.keySet();
	}
	public void printTable(){
		//TODO print table
	}
	/**
	 * Геттер
	 * @return
	 * Тип: MemCacheTable
	 * Описание: возвращает экземпляр класса MemCacheTable
	 */
	public static MemCacheTable getInstance(){
		return instance;
	}
}
