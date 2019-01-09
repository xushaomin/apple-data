package com.appleframework.data.hbase.client;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.log4j.Logger;

import com.appleframework.data.hbase.config.HBaseDataSource;
import com.appleframework.data.hbase.config.HBaseTableSchema;
import com.appleframework.data.hbase.exception.SimpleHBaseException;
import com.appleframework.data.hbase.util.Util;

/**
 * SimpleHbaseAdminClient's implementation.
 * 
 * @author xinzhi
 * */
@SuppressWarnings("deprecation")
public class SimpleHbaseAdminClientImpl implements SimpleHbaseAdminClient {

    private static Logger log = Logger.getLogger(SimpleHbaseAdminClientImpl.class);
    
    private HBaseDataSource hbaseDataSource;

    @Override
    public void createTable(TableDescriptor tableDescriptor) {
        Util.checkNull(tableDescriptor);
        try {
            Admin hbaseAdmin = hbaseDataSource.getHBaseAdmin();
            NamespaceDescriptor[] namespaceDescriptors = hbaseAdmin.listNamespaceDescriptors();
            String namespace = tableDescriptor.getTableName().getNamespaceAsString();
            boolean isExist = false;
            for (NamespaceDescriptor nd : namespaceDescriptors) {
                if (nd.getName().equals(namespace)) {
                    isExist = true;
                    break;
                }
            }
            log.info("namespace " + namespace + " isExist " + isExist);
            if (!isExist) {
                hbaseAdmin.createNamespace(NamespaceDescriptor.create(namespace).build());
            }
            hbaseAdmin.createTable(tableDescriptor);
            HTableDescriptor newTableDescriptor = hbaseAdmin.getTableDescriptor(tableDescriptor.getTableName());
            log.info("create table " + newTableDescriptor);
        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }
    
    @Override
    public void createTable(HBaseTableSchema hbaseTableSchema) {
    	Util.checkNull(hbaseTableSchema);
    	HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(hbaseTableSchema.getTableName()));
        try {
        	Admin hbaseAdmin = hbaseDataSource.getHBaseAdmin();
            NamespaceDescriptor[] namespaceDescriptors = hbaseAdmin.listNamespaceDescriptors();
            String namespace = tableDescriptor.getTableName().getNamespaceAsString();
            boolean isExist = false;
            for (NamespaceDescriptor nd : namespaceDescriptors) {
                if (nd.getName().equals(namespace)) {
                    isExist = true;
                    break;
                }
            }
            log.info("namespace " + namespace + " isExist " + isExist);
            if (!isExist) {
                hbaseAdmin.createNamespace(NamespaceDescriptor.create(namespace).build());
            }
            if(null != hbaseTableSchema.getDefaultFamily())
            	tableDescriptor.addFamily(new HColumnDescriptor(hbaseTableSchema.getDefaultFamily()));
            hbaseAdmin.createTable(tableDescriptor);
            HTableDescriptor newTableDescriptor = hbaseAdmin.getTableDescriptor(tableDescriptor.getTableName());
            log.info("create table " + newTableDescriptor);
        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }

    @Override
    public void deleteTable(String tableName) {
        Util.checkEmptyString(tableName);
        try {
        	Admin hbaseAdmin = hbaseDataSource.getHBaseAdmin();
            // delete table if table exist.
            TableName tableN = TableName.valueOf(tableName);
            if (hbaseAdmin.tableExists(tableN)) {
                // disable table before delete it.
                if (!hbaseAdmin.isTableDisabled(tableN)) {
                    hbaseAdmin.disableTable(tableN);
                }
                hbaseAdmin.deleteTable(tableN);
            }
        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }
    
    @Override
    public boolean tableExists(String tableName) {
        Util.checkEmptyString(tableName);
        try {
        	Admin hbaseAdmin = hbaseDataSource.getHBaseAdmin();
            TableName tableN = TableName.valueOf(tableName);
            return hbaseAdmin.tableExists(tableN);
        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }

    @Override
    public HBaseDataSource getHbaseDataSource() {
        return this.hbaseDataSource;
    }

    @Override
    public void setHbaseDataSource(HBaseDataSource hbaseDataSource) {
        this.hbaseDataSource = hbaseDataSource;
    }

}
