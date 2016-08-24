package com.teamtech.define;

public class News_Lx {
	private String xxlx,
    lxmc;
	
	/**
	 * @param rs.getString("xxlx"),
	 * @param rs.getString("lxmc")
	 */
	public News_Lx(String xxlx, String lxmc) {
		super();
		this.xxlx = xxlx;
		this.lxmc = lxmc;
	}
	
	/**
	 * @param rs.getString("xxlx"),
	 * @param rs.getString("lxmc")
	 */
	public News_Lx() {
		super();
	}

	public String getlxmc() {
		return lxmc;
	}

	public void setlxmc(String lxmc) {
		this.lxmc = lxmc;
	}

	public String getxxlx() {
		return xxlx;
	}

	public void setxxlx(String xxlx) {
		this.xxlx = xxlx;
	}
	
}
