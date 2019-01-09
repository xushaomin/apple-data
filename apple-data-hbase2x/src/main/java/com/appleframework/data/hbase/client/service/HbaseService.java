package com.appleframework.data.hbase.client.service;

import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Table;

/**
 * HbaseService
 * 
 * @author xinzhi.zhang
 * */
public interface HbaseService {

    /**
     * Get a reference to the specified table.
     * 
     * @param tableName table name
     * @return a reference to the specified table
     */
	public Table getTable(String tableName);

    /**
     * Get HBaseAdmin.
     * 
     * @return HBaseAdmin
     * */
    public Admin getHBaseAdmin();
}
