package com.teamtech.define;

import net.sf.json.JSONObject;;

public class News {
	private String userid,
	code,
    text,
    title ,
    linking,
    method,
    xxlx,
    lxmc,
    isread,
    ydsj,
    cssj,
    sxsj,
    gqsj,
    csczid,
    by1,
    by2,
    by3;
	private JSONObject data;
	
	public String getData() {
		return data.toString().replace("\"",
		"'");
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @param rs.getString("userid"),
	 * @param rs.getString("xh"),
	 * @param rs.getString("text"),
	 * @param rs.getString("title"),
	 * @param rs.getString("linking"),
	 * @param rs.getString("method"),
	 * @param JSONObject.fromObject(rs.getString("data")),
	 * @param rs.getString("xxlx"),
	 * @param rs.getString("lxmc"),
	 * @param rs.getString("isread"),
	 * @param rs.getString("ydsj"),
	 * @param rs.getString("cssj"),
	 * @param rs.getString("sxsj"),
	 * @param rs.getString("gqsj"),
	 * @param rs.getString("csczid"),
	 * @param rs.getString("by1"),
	 * @param rs.getString("by2"),
	 * @param rs.getString("by3")
	 */
	public News(String userid, String code, String text, String title, String linking, String method, JSONObject data, String xxlx, String lxmc, String isread, String ydsj, String cssj, String sxsj, String gqsj, String csczid, String by1, String by2, String by3) {
		super();
		this.userid = userid;
		this.code=code;
		this.text = text;
		this.title = title;
		this.linking = linking;
		this.method=method;
		this.data=data;
		this.xxlx = xxlx;
		this.lxmc=lxmc;
		this.isread = isread;
		this.ydsj = ydsj;
		this.cssj = cssj;
		this.sxsj = sxsj;
		this.gqsj = gqsj;
		this.csczid = csczid;
		this.by1 = by1;
		this.by2 = by2;
		this.by3 = by3;
	}
	
	/**
	 * @param rs.getString("userid"),
	 * @param rs.getString("xh"),
	 * @param rs.getString("text"),
	 * @param rs.getString("title"),
	 * @param rs.getString("linking"),
	 * @param rs.getString("method"),
	 * @param JSONObject.fromObject(rs.getString("data")),
	 * @param rs.getString("xxlx"),
	 * @param rs.getString("lxmc"),
	 * @param rs.getString("isread"),
	 * @param rs.getString("ydsj"),
	 * @param rs.getString("cssj"),
	 * @param rs.getString("sxsj"),
	 * @param rs.getString("gqsj"),
	 * @param rs.getString("csczid"),
	 * @param rs.getString("by1"),
	 * @param rs.getString("by2"),
	 * @param rs.getString("by3")
	 */
	public News() {
		super();
	}

	public String getBy1() {
		return by1;
	}

	public void setBy1(String by1) {
		this.by1 = by1;
	}

	public String getBy2() {
		return by2;
	}

	public void setBy2(String by2) {
		this.by2 = by2;
	}

	public String getBy3() {
		return by3;
	}

	public void setBy3(String by3) {
		this.by3 = by3;
	}

	public String getCsczid() {
		return csczid;
	}

	public void setCsczid(String csczid) {
		this.csczid = csczid;
	}

	public String getCssj() {
		return cssj;
	}

	public void setCssj(String cssj) {
		this.cssj = cssj;
	}

	public String getGqsj() {
		return gqsj;
	}

	public void setGqsj(String gqsj) {
		this.gqsj = gqsj;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public String getLinking() {
		return linking;
	}

	public void setLinking(String linking) {
		this.linking = linking;
	}

	public String getSxsj() {
		return sxsj;
	}

	public void setSxsj(String sxsj) {
		this.sxsj = sxsj;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getYdsj() {
		return ydsj;
	}

	public void setYdsj(String ydsj) {
		this.ydsj = ydsj;
	}

	public String getXxlx() {
		return xxlx;
	}

	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLxmc() {
		return lxmc;
	}

	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}
	
}
