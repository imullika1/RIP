package table.service;

import table.MemCacheTable;
import table.model.RoutingTable;

import java.util.Set;
import java.util.UUID;

public class TableService {

	private MemCacheTable cache = MemCacheTable.getInstance();

	public void dataHandler (String address, int metric){
		Set<UUID> list = cache.getAllKey();
		boolean idIsEquals = false;
		boolean create = true;

		for (UUID key: list) {
			RoutingTable table = cache.getTableByKey(key);
			if (table.getAddress().equalsIgnoreCase(address)){
				idIsEquals = true;
				create = false;
			}
			if ((table.getMetric() > metric) && idIsEquals){//TODO metric > metric.table - skip
				cache.updateTable(key, metric);
//				idIsEquals = false;
				System.out.println("#INFO [TableService] [dataHandler] table[key:"+key+"] was update!");
				break;
			}
		}
		if (create){
			cache.addTable(new RoutingTable(address, metric)); //TODO create table
			System.out.println("#INFO [TableService] [dataHandler] table was create!");
		}
	}
}
