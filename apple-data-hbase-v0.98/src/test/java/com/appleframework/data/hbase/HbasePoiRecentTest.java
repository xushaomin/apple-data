package com.appleframework.data.hbase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

import allen.test.CachedFileSystemResource;

import com.appleframework.data.hbase.client.SimpleHbaseClient;
import com.appleframework.data.hbase.client.SimpleHbaseClientImpl;
import com.appleframework.data.hbase.config.HBaseDataSource;
import com.appleframework.data.hbase.config.HBaseTableConfig;

public class HbasePoiRecentTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    HBaseDataSource hbaseDataSource = new HBaseDataSource();

	    List<Resource> hbaseConfigResources = new ArrayList<Resource>();
	    //If run on hbase cluster, modify the following config files.
	    //If run on hbase stand alone mode, comment out the following config files.
	    hbaseConfigResources.add(new CachedFileSystemResource("/Users/Cruise/Documents/Workspace/workspace_glsx/simplehbase/demo/hbase_site"));
	    //hbaseConfigResources.add(new CachedFileSystemResource("/Users/Cruise/Documents/Workspace/workspace_glsx/simplehbase/demo/zk_conf"));
	    hbaseDataSource.setHbaseConfigResources(hbaseConfigResources);

	    hbaseDataSource.init();

	    HBaseTableConfig hbaseTableConfig = new HBaseTableConfig();
	    //simplehbase config file.
	    hbaseTableConfig.setConfigResource(new CachedFileSystemResource("/Users/Cruise/Documents/Workspace/workspace_glsx/simplehbase/demo/myRecord.xml"));

	    hbaseTableConfig.init();

	    SimpleHbaseClient simpleHbaseClient = new SimpleHbaseClientImpl();
	    simpleHbaseClient.setHbaseDataSource(hbaseDataSource);
	    simpleHbaseClient.setHbaseTableConfig(hbaseTableConfig);
	    
	    
		//scan.setStartRow(ByteUtils.getBytes("0221400000000000"));
		//scan.setStopRow(ByteUtils.getBytes("0221499999999999"));

	    //21409205813
	    //21501090281
	    //search by range.
	    List<Poi> resultList = simpleHbaseClient.findObjectList(
	            new PoiRowKey("02214092058130000020150324000000"), new PoiRowKey("02214092058130000020150325000000"), Poi.class);
	    //log.info(resultList);
	    
	    for (Poi poi : resultList) {
			System.out.println(poi.toString());
		}



	}

}
