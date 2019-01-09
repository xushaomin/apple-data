package com.appleframework.data.hbase.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import com.appleframework.data.hbase.exception.SimpleHBaseException;
import com.appleframework.data.hbase.util.ConfigUtil;
import com.appleframework.data.hbase.util.StringUtil;
import com.appleframework.data.hbase.util.Util;

/**
 * HbaseDataSource represent one hbase data source.
 * 
 * @author xinzhi
 * */
public class HBaseDataSource {

    /** log. */
    final private static Logger log = Logger.getLogger(HBaseDataSource.class);
    //----------config--------------
    /**
     * dataSource id.
     * */
    @ConfigAttr
    private String              id;
    /**
     * hbase's config resources, such as hbase zk config.
     * */
    @ConfigAttr
    private List<Resource>      hbaseConfigResources;

    //---------------------------runtime-------------------------
    /**
     * final hbase's config item.
     * */
    private Map<String, String> finalHbaseConfig = new HashMap<String, String>();
    
    /**
     * final hbase's config item.
     * */
    private Properties hbaseProperties = new Properties();

    /**
     * hbase Configuration.
     * */
    private Configuration hbaseConfiguration;
    
    private Connection hbaseConnection;
    
    /**
     * init dataSource.
     * */
	public void init() {
        try {

            System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
                    "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
            System.setProperty("javax.xml.parsers.SAXParserFactory",
                    "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");

            initHbaseConfiguration();
            
            hbaseConnection = ConnectionFactory.createConnection(hbaseConfiguration);

            log.info(this);

        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }

    /**
     * Get Table by table Name.
     * 
     * @param tableName tableName.
     * @return Table.
     * */
    public Table getTable(String tableName) {
        Util.checkEmptyString(tableName);
        try {
        	return hbaseConnection.getTable(TableName.valueOf(tableName));
        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }
    
    /**
     * Get one HBaseAdmin.
     * */
    public Admin getHBaseAdmin() {
        try {
            return hbaseConnection.getAdmin();
        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }

    /**
     * init HbaseConfiguration
     * */
    private void initHbaseConfiguration() {
        try {
            if (hbaseConfigResources != null) {
                for (Resource resource : hbaseConfigResources) {
                    finalHbaseConfig.putAll(ConfigUtil.loadConfigFile(resource.getInputStream()));
                }
            }
            
            if (hbaseProperties != null) {
                Enumeration<?> en = hbaseProperties.propertyNames();
                while (en.hasMoreElements()) {
                	String key = (String) en.nextElement();
                	String Property = hbaseProperties.getProperty (key);
                	finalHbaseConfig.put(key, Property);
                }
            }
            
            hbaseConfiguration = HBaseConfiguration.create();
            for (Map.Entry<String, String> entry : finalHbaseConfig.entrySet()) {
                hbaseConfiguration.set(entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            log.error("parseConfig error.", e);
            throw new SimpleHBaseException("parseConfig error.", e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Configuration getHbaseConfiguration() {
        return hbaseConfiguration;
    }

    public List<Resource> getHbaseConfigResources() {
        return hbaseConfigResources;
    }

    public void setHbaseConfigResources(List<Resource> hbaseConfigResources) {
        this.hbaseConfigResources = hbaseConfigResources;
    }

    public Properties getHbaseProperties() {
		return hbaseProperties;
	}

	public void setHbaseProperties(Properties hbaseProperties) {
		this.hbaseProperties = hbaseProperties;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------datasource--------------------------\n");
        StringUtil.append(sb, "#id#", id);
        StringUtil.append(sb, "#finalHbaseConfig#", finalHbaseConfig);
        sb.append("---------------datasource--------------------------\n");
        return sb.toString();
    }

}
