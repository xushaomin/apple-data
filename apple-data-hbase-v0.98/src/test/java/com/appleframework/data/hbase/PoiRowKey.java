package com.appleframework.data.hbase;

import org.apache.hadoop.hbase.util.Bytes;

import com.appleframework.data.hbase.client.RowKey;

public class PoiRowKey implements RowKey {

    private String row;

    public PoiRowKey(String row) {
        this.row = row;
    }

    @Override
    public byte[] toBytes() {
        return Bytes.toBytes(row);
    }
}