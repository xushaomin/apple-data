package com.appleframework.data.hbase.util;

import org.apache.hadoop.hbase.client.Scan;

import com.appleframework.data.hbase.client.RowKey;

public class ScanUtil {

	public static Scan getScan(RowKey startRow, RowKey stopRow) {
		Scan scan = new Scan();
		scan.setStartRow(startRow.toBytes());
		scan.setStopRow(stopRow.toBytes());
		return scan;
	}
}
