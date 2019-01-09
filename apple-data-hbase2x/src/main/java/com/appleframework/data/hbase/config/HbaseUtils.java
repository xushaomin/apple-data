package com.appleframework.data.hbase.config;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.util.StringUtils;

import com.appleframework.data.hbase.exception.SimpleHBaseException;

/**
* Helper class featuring methods for Hbase table handling and exception translation. 
* 
* @author Costin Leau
*/
public class HbaseUtils {

	/**
	 * Converts the given (Hbase) exception to an appropriate exception from <tt>org.springframework.dao</tt> hierarchy.
	 * 
	 * @param ex Hbase exception that occurred
	 * @return the corresponding DataAccessException instance
	 */
	public static SimpleHBaseException convertHbaseException(Exception ex) {
		return new SimpleHBaseException(ex);
	}

	/**
	 * Retrieves an Hbase table instance identified by its name.
	 * 
	 * @param configuration Hbase configuration object
	 * @param tableName table name
	 * @return table instance
	 */
	public static Table getTable(String tableName, Configuration configuration) {
		return getTable(tableName, configuration, getCharset(null));
	}

	/**
	 * Retrieves an Hbase table instance identified by its name and charset using the given table factory.
	 * 
	 * @param tableName table name
	 * @param configuration Hbase configuration object
	 * @param charset name charset (may be null)
	 * @param tableFactory table factory (may be null)
	 * @return table instance
	 */
	public static Table getTable(String tableName, Configuration configuration, Charset charset) {
		if (HbaseSynchronizationManager.hasResource(tableName)) {
			return (HTable) HbaseSynchronizationManager.getResource(tableName);
		}
		Table t = null;
		try {
			Connection connection = ConnectionFactory.createConnection(configuration);
			t = connection.getTable(TableName.valueOf(tableName));
			return t;

		} catch (Exception ex) {
			throw convertHbaseException(ex);
		}
	}

	static Charset getCharset(String encoding) {
		return (StringUtils.hasText(encoding) ? Charset.forName(encoding) : Charset.forName("UTF-8"));
	}

	/**
	 * Releases (or closes) the given table, created via the given configuration if it is not managed externally (or bound to the thread).
	 * 
	 * @param tableName table name
	 * @param table table
	 * @param tableFactory table factory
	 */
	public static void releaseTable(String tableName, Table table) {
		try {
			doReleaseTable(tableName, table);
		} catch (IOException ex) {
			throw HbaseUtils.convertHbaseException(ex);
		}
	}

	private static void doReleaseTable(String tableName, Table table)
			throws IOException {
		if (table == null) {
			return;
		}

		// close only if its unbound 
		if (!isBoundToThread(tableName)) {
			table.close();
		}
	}

	private static boolean isBoundToThread(String tableName) {
		return HbaseSynchronizationManager.hasResource(tableName);
	}
}
