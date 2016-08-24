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
	 * ����������Ϣ
	 * 
	 * @param st
	 *            �������ݿ�����Ҫ�Ķ������걾����������ر�
	 * @param userList
	 *            ����ܵ�������Ϣ���û�id ����Ϊ��
	 * @param text
	 *            ��Ϣ������
	 * @param title
	 *            ��Ϣ�ı���
	 * @param linking
	 *            �������ת�����ӣ�Ϊ����û���κζ���
	 * @param method
	 *            �ύ�����ķ�ʽ
	 * @param dataMap
	 *            ��Ҫ�ύ�Ĳ������ύ�����request��ȡ������ֵ��userid����ǰ���userList��userid����Ӧ
	 * @param xxlx
	 *            ��Ϣ������
	 * @param sxsj
	 *            ��Ч��ʱ��
	 * @param gqsj
	 *            ���ڵĵ�ʱ��
	 * @param csczid
	 *            ����������Ϣ�Ĳ����� ����Ϊ�� ϵͳ������Ϊ"system"
	 * @param by1,by2,by3
	 * 			  �����ֶΣ���Ҫ��
	 * @return	userList , csczidΪ�գ������ݿ�д��ʧ�ܷ���false
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
	 * ɾ����Ϣ
	 * 
	 * @param st
	 * 			�������ݿ�����Ҫ�Ķ������걾����������ر�
	 * @param codeList Ҫɾ������Ϣ��code����
	 * @return ɾ��������,-1Ϊ���ݿ�д���쳣,-2Ϊ�쳣��ع�ʧ��
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
	 * ����news���Ѷ�״̬
	 * 
	 * @param st	�������ݿ�����Ҫ�Ķ������걾����������ر�
	 * @param codeList	Ҫ�޸ĵ���Ϣ��code����
	 * @param status	�Ķ�״̬ 1 �ѿ���0״̬δ��
	 * @return	�޸ĵ�����,-1Ϊ���ݿ�д���쳣,-2Ϊ�쳣��ع�ʧ��
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
	 * �����޸���Ϣ
	 * 
	 * @param st
	 *            �������ݿ�����Ҫ�Ķ������걾����������ر�
	 * @param userList
	 *            ����ܵ�������Ϣ���û�id ����Ϊ��
	 * @param text
	 *            ��Ϣ������
	 * @param title
	 *            ��Ϣ�ı���
	 * @param linking
	 *            �������ת�����ӣ�Ϊ����û���κζ���
	 * @param method
	 *            �ύ�����ķ�ʽ
	 * @param dataMap
	 *            ��Ҫ�ύ�Ĳ������ύ�����request��ȡ������ֵ��userid����ǰ���userList��userid����Ӧ
	 * @param xxlx
	 *            ��Ϣ������
	 * @param sxsj
	 *            ��Ч��ʱ��
	 * @param gqsj
	 *            ���ڵĵ�ʱ��
	 * @param csczid
	 *            �޸Ĵ�����Ϣ�Ĳ����� ����Ϊ�� ϵͳ������Ϊ"system"
	 * @param by1,by2,by3
	 * 			  �����ֶΣ���Ҫ��
	 * @return	�޸ĵ�����,-1Ϊ���ݿ�д���쳣,-2Ϊ�쳣��ع�ʧ��
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
	 * �����ڶ���תΪsql����е�to_date(...,'yyyy-mm-dd HH24:mi:ss')�ַ���
	 * @param date
	 * @return	ת������ַ��� 
	 * <pre>��:to_date('2016-07-06 00:00:00','yyyy-mm-dd HH24:mi:ss')</pre>
	 */
	public static String to_date(Date date) {
		SimpleDateFormat sFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "to_date('"+sFormat.format(date)+"','yyyy-mm-dd HH24:mi:ss')";	
	}

}
