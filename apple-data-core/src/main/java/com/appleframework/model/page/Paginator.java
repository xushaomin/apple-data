package com.appleframework.model.page;

import java.util.List;

/**
 * 列表分页。包含list属性。
 * 
 * @author xusm
 * 
 */
public class Paginator<T> extends SimplePage implements java.io.Serializable, Paginable {

	private static final long serialVersionUID = 1385145241579184848L;
	
	public static <T> Paginator<T> create() {
		return new Paginator<T>();
	}
	
	public static <T> Paginator<T> create(long pageNo, long pageSize) {
		return new Paginator<T>(pageNo, pageSize);
	}
	
	public static <T> Paginator<T> create(long pageNo, long pageSize, long totalCount) {
		return new Paginator<T>(pageNo, pageSize, totalCount);
	}
	
	public static <T> Paginator<T> create(long pageNo, long pageSize, long totalCount, List<T> list) {
		return new Paginator<T>(pageNo, pageSize, totalCount, list);
	}

	public Paginator() {
	}
	
	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 */
	public Paginator(long pageNo, long pageSize) {
		super(pageNo, pageSize);
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public Paginator(long pageNo, long pageSize, long totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public Paginator(long pageNo, long pageSize, long totalCount, List<T> list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	/**
	 * 当前页的数据
	 */
	private List<T> list;
	
	/**
	 * 获得分页内容
	 * 
	 * @return
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 设置分页内容
	 * 
	 * @param list
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	
	/**
	 * 当前页几条数据
	 */
	public long getPageCount() {
		if(null != list)
			return list.size();
		else
			return 0;
	}
	
}
