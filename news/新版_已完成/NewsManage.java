package pub;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

public final class NewsManage {
	/**
	 * 用于增加消息
	 * 
	 * @param st
	 *            操作数据库所需要的对象，用完本方法不负责关闭
	 * @param userList
	 *            会接受到此条消息的用户id 不能为空
	 * @param text
	 *            消息的内容
	 * @param title
	 *            消息的标题
	 * @param linking
	 *            点击后跳转的链接，为空则没有任何动作
	 * @param method
	 *            提交参数的方式
	 * @param dataMap
	 *            将要提交的参数，提交后可用request获取，按键值（userid）与前面的userList（userid）对应
	 * @param xxlx
	 *            信息的类型
	 * @param sxsj
	 *            生效的时间
	 * @param gqsj
	 *            过期的的时间
	 * @param csczid
	 *            产生此条信息的操作人 不能为空 系统产生的为"system"
	 * @param by1,by2,by3
	 * 			  备用字段，无要求
	 * @return	userList , csczid为空，或数据库写入失败返回false
	 */
	public static boolean add(Statement st, List<String> userList,
			String text, String title, String linking, String method,
			HashMap<String,HashMap<String, String>> dataMap, String xxlx, Date sxsj, Date gqsj,
			String csczid, String by1, String by2, String by3) {	
		
		if (userList == null || userList.size() == 0 || csczid == null
				|| csczid == "") {
			return false;
		}

		String sqlBase = " insert into news (USERID,TEXT,TITLE,LINKING,METHOD,DATA,XXLX,SXSJ,GQSJ,CSCZID,BY1,BY2,BY3) " 
					+" values('?USERID','"+text+"','"+title+"','"+linking+"','"+method+"','?DATA','"+xxlx+"','"+to_date(sxsj)+"','"+to_date(gqsj)+"','"+csczid+"','"+by1+"','"+by2+"','"+by3+"') ";
		
		List<String> sqlList=new ArrayList<String>();
		for (String s : userList) {
			String data = JSONObject.fromObject(dataMap.get(s)).toString().replace("\"",
			"''");
			sqlList.add(sqlBase.replace("?USERID", s).replace("?DATA", data));
		}

		boolean flag=false;
		try {
			for (String sql : sqlList) {
				st.addBatch(sql);
			}
			st.executeBatch();
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				st.getConnection().rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 删除信息
	 * 
	 * @param st
	 * 			操作数据库所需要的对象，用完本方法不负责关闭
	 * @param codeList 要删除的信息的code集合
	 * @return 删除的行数,-1为数据库写入异常,-2为异常后回滚失败
	 */
	public static int delete(Statement st,List<String> codeList){
		if (codeList==null||codeList.size()==0) {
			return 0;
		}
		String sql="delete news where code in ( ";
		for (String s : codeList) {
			sql=sql+"'"+s+"' ,";
		}
		sql=sql.substring(0, sql.length()-1)+" ) ";
		int num;
		try {
			System.out.println(sql);
			num=st.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			num=-1;
			try {
				st.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				num=-2;
				e1.printStackTrace();
			}
		}
		return num;
	}
	
	/**
	 * 设置news的已读状态
	 * 
	 * @param st	操作数据库所需要的对象，用完本方法不负责关闭
	 * @param codeList	要修改的信息的code集合
	 * @param status	阅读状态 1 已看，0状态未看
	 * @return	修改的行数,-1为数据库写入异常,-2为异常后回滚失败
	 */
	public static int setStatusOfRead(Statement st,List<String> codeList,int status){
		if (codeList==null||codeList.size()==0||!(status==0||status==1)) {
			return 0;
		}
		String sql=" update news set isread='"+status+"' , ydsj=?ydsj where code in ( ";
		sql=sql.replace("?ydsj", status==1?"sysdate":"null");
		for (String s : codeList) {
			sql=sql+"'"+s+"' ,";
		}
		sql=sql.substring(0, sql.length()-1)+" ) ";
		int num;
		try {
			System.out.println(sql);
			num=st.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			num=-1;
			try {
				st.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				num=-2;
				e1.printStackTrace();
			}
		}
		return num;
	}
	
	/**
	 * 用于修改消息
	 * 
	 * @param st
	 *            操作数据库所需要的对象，用完本方法不负责关闭
	 * @param userList
	 *            会接受到此条消息的用户id 不能为空
	 * @param text
	 *            消息的内容
	 * @param title
	 *            消息的标题
	 * @param linking
	 *            点击后跳转的链接，为空则没有任何动作
	 * @param method
	 *            提交参数的方式
	 * @param dataMap
	 *            将要提交的参数，提交后可用request获取，按键值（userid）与前面的userList（userid）对应
	 * @param xxlx
	 *            信息的类型
	 * @param sxsj
	 *            生效的时间
	 * @param gqsj
	 *            过期的的时间
	 * @param csczid
	 *            修改此条信息的操作人 不能为空 系统产生的为"system"
	 * @param by1,by2,by3
	 * 			  备用字段，无要求
	 * @return	修改的行数,-1为数据库写入异常,-2为异常后回滚失败
	 */
	public static int update(Statement st, String userid, String code,
			String text, String title, String linking, String method,
			HashMap<String, String> dataMap, String xxlx, Date sxsj, Date gqsj,
			String csczid, String by1, String by2, String by3) {
		if (code == null || csczid == null || csczid == "" || userid == null) {
			return 0;
		}

		String data = JSONObject.fromObject(dataMap).toString().replace("\"",
		"''");
		
		String sql = " update news set "
				+ "USERID='"+userid+"',TEXT='"+text+"',TITLE='"+title+"',LINKING='"+linking+"'" 
				+ ",METHOD='"+method+"',DATA='"+data+"',XXLX='"+xxlx+"',SXSJ='"+to_date(sxsj)+"',GQSJ='"+to_date(gqsj)+"'" 
				+ ",CSCZID='"+csczid+"',cssj='sysdate',BY1='"+by1+"',BY2='"+by2+"',BY3='"+by3+"' ";
		
		int num;
		try {
			System.out.println(sql);
			num=st.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			num=-1;
			try {
				st.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				num=-2;
				e1.printStackTrace();
			}
		}
		return num;
	}
	
	/**
	 * 将日期对象转为sql语句中的to_date(...,'yyyy-mm-dd HH24:mi:ss')字符串
	 * @param date
	 * @return	转换后的字符串 
	 * <pre>例:to_date('2016-07-06 00:00:00','yyyy-mm-dd HH24:mi:ss')</pre>
	 */
	public static String to_date(Date date) {
		SimpleDateFormat sFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "to_date('"+sFormat.format(date)+"','yyyy-mm-dd HH24:mi:ss')";	
	}

}
