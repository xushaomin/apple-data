package com.appleframework.data.hbase.client;

import org.apache.hadoop.hbase.client.TableDescriptor;

import com.appleframework.data.hbase.client.service.HBaseDataSourceAware;
import com.appleframework.data.hbase.config.HBaseTableSchema;


/**
 * SimpleHbaseAdminClient.
 * 
 * @author xinzhi
 * */
public interface SimpleHbaseAdminClient extends HBaseDataSourceAware {

    /**
     * Creates a new table. Synchronous operation.
     */
	public void createTable(TableDescriptor tableDescriptor);
    
    public void createTable(HBaseTableSchema hbaseTableSchema);

    /**
     * Deletes a table. Synchronous operation.
     */
    public void deleteTable(final String tableName);
    
    /**
     * Determine table does it exist.
     */
    public boolean tableExists(String tableName);
}
