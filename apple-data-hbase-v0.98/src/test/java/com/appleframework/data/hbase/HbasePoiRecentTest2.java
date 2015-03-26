package com.appleframework.data.hbase;

import java.util.List;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.data.hbase.client.SimpleHbaseClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/apple/apple-data-hbase.xml" })
public class HbasePoiRecentTest2 {

	@Resource
	private SimpleHbaseClient simpleHbaseClient;

	@Test
	public void testAddOpinion1() {

		try {
			List<Poi> resultList = simpleHbaseClient.findObjectList(
		            new PoiRowKey("02214092058130000020150324000000"), new PoiRowKey("02214092058130000020150325000000"), Poi.class);
		    //log.info(resultList);
		    
		    for (Poi poi : resultList) {
				System.out.println(poi.toString());
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}

}
