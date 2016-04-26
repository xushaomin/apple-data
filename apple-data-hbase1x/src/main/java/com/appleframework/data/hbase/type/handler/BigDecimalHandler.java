package com.appleframework.data.hbase.type.handler;

import java.math.BigDecimal;

import org.apache.hadoop.hbase.util.Bytes;

import com.appleframework.data.hbase.type.AbstractTypeHandler;

public class BigDecimalHandler extends AbstractTypeHandler {

	@Override
	protected boolean aboutToHandle(Class<?> type) {
		return type == BigDecimal.class;
	}

	@Override
	protected byte[] innerToBytes(Class<?> type, Object value) {
		BigDecimal bigDec = (BigDecimal) value;
		return Bytes.toBytes(bigDec);
	}

	@Override
	protected Object innerToObject(Class<?> type, byte[] bytes) {
		return Bytes.toBigDecimal(bytes);
	}

}
