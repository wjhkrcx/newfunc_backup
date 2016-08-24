package com.teamtech.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import net.sf.json.JSONObject;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import pub.NewsManage;

import com.teamtech.define.KcTeachers;
import com.teamtech.define.News;
import com.teamtech.define.News_Lx;
import com.teamtech.define.TMkdm;
import com.teamtech.domain.JhStuKcxx;
import com.teamtech.domain.JtBsxtdm;
import com.teamtech.domain.LogOperate;
import com.teamtech.domain.PubForm;
import com.teamtech.domain.TAttachinfo;
import com.teamtech.domain.TLogin;
import com.teamtech.domain.TSlogin;
import com.teamtech.domain.TYhDwdm;
import com.teamtech.domain.TYhxxb;
import com.teamtech.domain.VXKDDBJSJ;
import com.teamtech.domain.Xkkz;
import com.teamtech.domain.XkkzId;
import com.teamtech.domain.XtLog;
import com.teamtech.domain.XtWtfkxx;
import com.teamtech.domain.XtWtfkxxId;
import com.teamtech.service.XtSvr;
import com.teamtech.staticdata.ConfigItem;
import com.teamtech.spi.BaseDaoHibernate;

public class XtSvrI extends HibernateDaoSupport implements XtSvr {
	private static Log log = LogFactory.getLog(XtSvrI.class);
	public List getStatic(ConfigItem conf) {
		// TODO Auto-generated method stub
		if (conf.getClassname() == null || conf.getClassname().trim().length()==0){
			String hql = "SELECT " + conf.getKey().trim() + "," + conf.getValue().trim()
				+ " FROM " + conf.getTablename().trim();
			if ((conf.getWhere() != null)
						&&(conf.getWhere().trim().length() > 0)) {
				hql = hql + " WHERE "+ conf.getWhere().trim();
			}
			if (conf.getOrder().trim().length() > 0) {
				hql = hql + " " + conf.getOrder().trim();
			}
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			List lstresult = new ArrayList();
			System.out.println(hql);
			try {
				conn = getSession().connection();
				stmt = conn.createStatement();				
				rs = stmt.executeQuery(hql);				
				while (rs.next()) {
					Object[] obj=new Object[3];
					obj[0]=rs.getString(1);
					obj[1]=rs.getString(2);
					lstresult.add(obj);
				}
				rs.close();
				stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return lstresult;
		} else {
			String hql = "FROM " + conf.getClassname().trim();
			if ((conf.getWhere() != null && conf.getWhere().trim().length()>0)
					&& (conf.getWhere().trim().length() > 0)) {
				hql = hql + " WHERE " + conf.getWhere().trim();
			}
			if (conf.getOrder() !=null && conf.getOrder().trim().length() > 0) {
				hql = hql + " " + conf.getOrder().trim();
			}
			return getHibernateTemplate().find(hql);
		}
	}

	//
	public List getGgxxll(String userType){
		String fsdx = "";
		if(userType!=null&&!userType.trim().equals(""))
		{
			if(userType.trim().equals("s"))
				fsdx = "B1";
			if(userType.trim().equals("t"))
				fsdx = "B2";
		}
		String hql = "FROM Xt_Ggxx WHERE fsdx='"+fsdx.trim()+"' and (yxq is null or yxq>sysdate) and (sysdate-fssj)<31  ORDER BY yxj DESC,fssj DESC";
		System.out.println(hql);
		
		List list = (List)getHibernateTemplate().find(hql);
		return list;
	}

	public List getGgxxxx(String ggid){
		String fsdx = "";
		List list = new ArrayList();
		String hql = "FROM Xt_Ggxx WHERE xxid='"+ggid+"'";
		list = (List)getHibernateTemplate().find(hql);
		return list;
	}

	public List getBsxtData(String lxdm, String jddm, String xnxq, String beiy1) {
		// TODO Auto-generated method stub
		String hql = "SELECT A.id.lxdm,A.id.xnxq,A.id.xkjddm,A.kssj,A.jssj FROM Xkkz A " +
						" WHERE A.kfzt='Y' AND A.id.kcbjh='999' ";//
		if(lxdm!=null && !"".equals(lxdm.trim()))hql+=" AND A.id.lxdm='"+lxdm.trim()+"' ";
		if(jddm!=null && !"".equals(jddm.trim()))hql+=" AND A.id.xkjddm='"+jddm.trim()+"' ";
		if(xnxq!=null && !"".equals(xnxq.trim()))hql+=" AND A.id.xnxq='"+xnxq.trim()+"' ";
		if(beiy1!=null && "1".equals(beiy1.trim()))hql+=" AND A.kssj<=SYSDATE AND A.jssj>SYSDATE ";
		
		hql+=" ORDER BY A.id.xnxq,A.id.lxdm,A.id.xkjddm";
		List list = new ArrayList();
		List xt_list = new ArrayList();
		Query qq =  getSession().createQuery(hql);
		list = qq.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Xkkz kz = new Xkkz();
			XkkzId id = new XkkzId();
			Object[] obj = (Object[]) it.next();
			id.setLxdm(String.valueOf(obj[0]));
			id.setXnxq(String.valueOf(obj[1]));
			id.setXkjddm(String.valueOf(obj[2]));
			kz.setId(id);
			kz.setKssj((Date)obj[3]);
			kz.setJssj((Date)obj[4]);
			List bsxtdm = new ArrayList();
			if(!"".equals(beiy1.trim())){
				bsxtdm = getJtBsxtdm(String.valueOf(obj[0]),String.valueOf(obj[2]));
				JtBsxtdm dm = new JtBsxtdm();
				dm = (JtBsxtdm)bsxtdm.get(0);
				kz.setBeiy1(dm.getLxmc().trim());
				kz.setBeiy2(dm.getJdmc().trim());
			}
			xt_list.add(kz);
		}
		return xt_list;
	}
	public List getJtBsxtdm(String lxdm,String jddm){
		String hql = " FROM JtBsxtdm A WHERE A.id.lxdm='"+lxdm.trim()+"' AND A.id.jddm='"+jddm.trim()+"' ";
		List list = (List)getHibernateTemplate().find(hql);
		return list;
	}

	public List getTLogin(String yhdm) {//teacher
		// TODO Auto-generated method stub
		String hql = "FROM TLogin A WHERE A.yhdm=?";
		return getHibernateTemplate().find(hql, new Object[] { yhdm });
	}
	
	public boolean insertTlogin(String yhdm,String xm,String ip){

		XtLog xtlog=new XtLog();
		xtlog.setMkid("0000000");
		xtlog.setMkmc("教师登录");
		xtlog.setGjz(yhdm);
		xtlog.setCzlx("0");
		xtlog.setCznr("登录");
		xtlog.setCzip(ip);
		xtlog.setCzms("用户编号为"+yhdm+"的"+xm+"老师登录综合教务系统");
		this.getHibernateTemplate().save(xtlog);
		return true;
	}

	public List getSLogin(String sno) {//student
		// TODO Auto-generated method stub
		String hql = "FROM TSlogin A WHERE A.yhdm=?";
		return getHibernateTemplate().find(hql, new Object[] { sno });
	}
	
	public boolean insertSlogin(String sno,String xm,String ip){
		XtLog xtlog=new XtLog();
		xtlog.setMkid("0000000");
		xtlog.setMkmc("学生登录");
		xtlog.setGjz(sno);
		xtlog.setCzlx("0");
		xtlog.setCznr("登录");
		xtlog.setCzip(ip);
		xtlog.setCzms("学号为"+sno+"的"+xm+"同学登录综合教务系统");
		this.getHibernateTemplate().save(xtlog);
		return true;
	}

	public List getStuqx(String sno) {
		// TODO Auto-generated method stub
		String hql = "SELECT A.mkid FROM T_Role_Qx A WHERE A.roleid=?"
					+" UNION"
					+" SELECT B.mkid FROM Xt_Mkdm B WHERE B.mkxz=?";
		//return getHibernateTemplate().find(hql, new Object[] { "100", "4" });
		return this.getSession().createSQLQuery(hql).addScalar("mkid", Hibernate.STRING)
				.setParameter(0, 100).setParameter(1, "4").list();
	}

	public List getYhqx(String dwdm, Long yhid) {
		// TODO Auto-generated method stub
		if ((dwdm==null) || (dwdm.equals("999"))) {
			String hql = "SELECT A.mkid FROM XtMkdm A WHERE A.mkxz IN (?,?)";
			return getHibernateTemplate().find(hql,new Object[] { "2", "5" });
		} else {
			String hql = "SELECT A.id.mkdm AS mkid FROM TYhMkdm A WHERE A.id.dwdm=? AND A.id.yhid=?"
				+" UNION"
				+" SELECT B.id.mkid FROM TYhRole X,TRoleQx B WHERE X.id.roleid=B.id.roleid"
					+" AND X.id.dwdm=? AND X.id.yhid=?"
				+" UNION"
				+" SELECT C.mkid FROM XtMkdm C WHERE C.mkxz=?";
			//return getHibernateTemplate().find(hql, 
			//		new Object[] { dwdm, yhid, dwdm, yhid, "5" });
			
			return this.getSession().createSQLQuery(hql).addScalar("mkid", Hibernate.STRING)
							.setParameter(0, dwdm).setParameter(1, yhid)
							.setParameter(2, dwdm).setParameter(3, yhid)
							.setParameter(4, "5").list();
		}		
	}

	public String getDbNow(String strparam) {
		// TODO Auto-generated method stub
		String sql = "SELECT TO_CHAR(SYSDATE,'yyyy-mm-dd hh24:mi:ss') FROM DUAL";
		if ((strparam!=null) && (strparam.trim().length() > 0)) {
			sql = "SELECT TO_CHAR(SYSDATE,'"+strparam+"') FROM DUAL";	
		}		
		Session session = this.getSession();		
		try {
			List lst = session.createSQLQuery(sql).list();
			if ((lst==null) || (lst.size() != 1)) {
				return null;
			} else {
				return (String) lst.get(0);
			}
		} catch (HibernateException e) {			
			return null;
		} finally {
			releaseSession(session);
		}
	}

	public List getStuInfo(String sno) {
		// TODO Auto-generated method stub
		String hql = "FROM XjXsxxb A WHERE A.sno=?";
		return getHibernateTemplate().find(hql,new Object[] { sno });
	}

	public List getStuStatus(String sno) {
		// TODO Auto-generated method stub
		String hql = "FROM XjXszkb A WHERE A.id.sno=? ORDER BY A.id.zylx";
		return getHibernateTemplate().find(hql,new Object[] { sno });
	}

	public List getYhxx(Long yhid) {
		// TODO Auto-generated method stub
		String hql = "FROM TYhxxb A WHERE A.yhid=?";		
		return getHibernateTemplate().find(hql,new Object[] { yhid });
	}

	public List getMkdm() {
		// TODO Auto-generated method stub
		String sql = "select a.mkid,a.mkmc,a.mkms,a.isshow,a.sjdm,a.mklink,"
									+"a.exefunction,level as curlevel,a.iconfile,a.issplit"
                  +" from xt_mkdm a"
                  //+" where a.yxx='1' and a.mkxz in ('1','4','5')"
                  +" where a.yxx='1'"
                  +" start with a.sjdm='0000000'"
                  +" connect by prior a.mkid=a.sjdm"
                  +" order SIBLINGS by a.xssx";	
		Session session = this.getSession();		
		try {
			return this.getSession().createSQLQuery(sql).addScalar("mkid", Hibernate.STRING)
							.addScalar("mkmc", Hibernate.STRING).addScalar("mkms", Hibernate.STRING)
							.addScalar("isshow", Hibernate.STRING).addScalar("sjdm", Hibernate.STRING)
							.addScalar("mklink", Hibernate.STRING).addScalar("exefunction", Hibernate.STRING)
							.addScalar("curlevel", Hibernate.STRING).addScalar("iconfile", Hibernate.STRING)
							.addScalar("issplit", Hibernate.STRING).
							setResultTransformer(Transformers.aliasToBean(TMkdm.class)).list();
		} catch (HibernateException e) {			
			return null;
		} finally {
			releaseSession(session);
		}
	}

	public String getUserPassInfo(String userCode, String type) {
		// TODO Auto-generated method stub
		String s_hql = " SELECT yhmm FROM TSlogin WHERE yhdm='"+userCode.trim()+"' ";
		String t_hql = " SELECT yhmm FROM TLogin WHERE yhdm='"+userCode.trim()+"' ";
		String jmpass="";
		if(null!=type && "s".equals(type.trim()))
			jmpass = (String)getHibernateTemplate().iterate(s_hql).next();
		if(null!=type && "t".equals(type.trim()))
			jmpass = (String)getHibernateTemplate().iterate(t_hql).next();
		return jmpass;
	}
	public int getUserInfo(String userCode, String type) {
		// TODO Auto-generated method stub
		String s_hql = " SELECT COUNT(*) FROM TSlogin WHERE yhdm='"+userCode.trim()+"' ";
		String t_hql = " SELECT COUNT(*) FROM TLogin WHERE yhdm='"+userCode.trim()+"' ";
		int count=0;
		if(null!=type && "s".equals(type.trim()))
			count = ((Long) getHibernateTemplate().iterate(s_hql).next()).intValue();
		if(null!=type && "t".equals(type.trim()))
			count = ((Long) getHibernateTemplate().iterate(t_hql).next()).intValue();
		return count;
	}
	public String updateUserPassWord(TSlogin s_user, TLogin t_user, String type) {
		// TODO Auto-generated method stub
		String userCode = "";
		String password = "";
		String userId = "";
		String sql = "";
		String tag = "0";
		if(null!=type){
			if("s".equals(type.trim())){
				userCode = s_user.getYhdm().trim();
				password = s_user.getYhmm().trim();
				String s_sql = " update T_SLOGIN set yhmm='"+password+"' where yhdm='"+userCode+"' ";
				sql = s_sql;
			}
			if("t".equals(type.trim())){
				userCode = t_user.getYhdm().trim();
				password = t_user.getYhmm().trim();
				userId = Long.toString(t_user.getYhid());
				String t_sql = " update T_LOGIN set yhmm='"+password+"' where yhid='"+userId+"' ";
				sql = t_sql;
			}
		}
		log.info("sql-:"+sql);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			stmt.execute(sql);
			tag = "1";
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				stmt.close();
			} catch (SQLException ee) {
				ee.printStackTrace();
			}
		}
		return tag;
	}

	public List getStuKc(String sno, String rxnf) {
		// TODO Auto-generated method stub
		String sql = "SELECT A.* FROM Jh_Stu_Kcxx partition(P"+rxnf+") A WHERE A.sno=?";
		Session session = this.getSession();		
		try {
			List lst = this.getSession().createSQLQuery(sql).addEntity(JhStuKcxx.class)
												.setParameter(0, sno.trim()).list();
			return lst;
		} catch (HibernateException e) {			
			return null;
		} finally {
			releaseSession(session);
		}
	}

	public List getPlanKc(String rxnf,String zydm,String kcmc) {
		// TODO Auto-generated method stub		
		String hql = "FROM VZxjhKc A";
		String hqlwhere = "";
		List lst = new ArrayList();
		if (rxnf!=null && rxnf.trim().length()>0) {
			if (hqlwhere.trim().length()>0) {
				hqlwhere += " AND ";
			}
			lst.add(Long.parseLong(rxnf.trim()));
			hqlwhere += " A.rxnf=?";
		}
		if (zydm!=null && zydm.trim().length()>0) {
			if (hqlwhere.trim().length()>0) {
				hqlwhere += " AND ";
			}
			lst.add(Long.parseLong(zydm.trim()));
			hqlwhere += " A.zydm=?";
		}
		if (kcmc!=null && kcmc.trim().length()>0) {
			if (hqlwhere.trim().length()>0) {
				hqlwhere += " AND ";
			}
			lst.add("%"+kcmc.trim()+"%");
			hqlwhere += " A.kczw like ?";
		}
		if (hqlwhere.trim().length()>0) {
			return getHibernateTemplate().find(hql+" WHERE "+hqlwhere,lst.toArray());
		} else {
			return getHibernateTemplate().find(hql);
		}
		//return getHibernateTemplate().find(hql,new Object[] { Long.parseLong(planId) });		
	}

	public List getPlanKc(String planid) {
		// TODO Auto-generated method stub
		String hql = "FROM VZxjhKc A WHERE A.id.zxjhid=?";
		return getHibernateTemplate().find(hql, new Object[] { Long.parseLong(planid) });
	}

	public List getPublicKc(String kcmc) {
		// TODO Auto-generated method stub
		String hql = "FROM VKcPublic A";
		if (kcmc!=null && kcmc.trim().length()>0) {
			return getHibernateTemplate().find(hql+" WHERE A.kczw LIKE ?", new Object[] { "%"+kcmc.trim()+"%" });
		} else {
			return getHibernateTemplate().find(hql);
		}
	}

	public List getGrade() {
		// TODO Auto-generated method stub
		String sql = "SELECT MAX(A.NF) AS MAXNF FROM XT_XQJDB A WHERE A.jddm<100";
		List lstresult = new ArrayList();
		Session session = this.getSession();		
		try {
			Integer nowNF = (Integer) session.createSQLQuery(sql).addScalar("MAXNF",Hibernate.INTEGER).uniqueResult();
			for (int i=0;i<4;i++) {
				Integer inf = nowNF-i;
				lstresult.add(String.valueOf(inf));
			}
		} catch (HibernateException e) {			
			return null;
		} finally {
			releaseSession(session);
		}
		return lstresult;
	}

	public Boolean updateKc(List lstkcxx) {
		// TODO Auto-generated method stub
		String sql = "";
		for (int i=0;i<lstkcxx.size();i++) {
			JhStuKcxx stukc = (JhStuKcxx) lstkcxx.get(i);
			if (stukc.getKczt().trim().equals("A")) {
				sql += "INSERT INTO JH_STU_KCXX PARTITION(P"+stukc.getRxnf().trim()+")(SNO,KCID,KCLX,XNXQ,KCZT,RXNF,CZSJ,REMARKS,BEIY1,BEIY2,BEIY3,BZ) "
						+" VALUES('"+stukc.getId().getSno().trim()+"',"
						+stukc.getId().getKcid()+",'"+stukc.getId().getKclx().trim()+"',"
						+"'"+(stukc.getXnxq()==null?"":stukc.getXnxq().trim())+"',"
						+"'0','"+stukc.getRxnf().trim()+"',sysdate,'',0,'','','');";
			} else {
				sql +="INSERT INTO LOG_JH_STU_KCXX(SNO,KCID,KCLX,XNXQ,KCZT,RXNF,CZSJ,REMARKS,LOGCZR,LOGCZSJ,BEIY1,BEIY2,BEIY3,BZ)"
							+" SELECT SNO,KCID,KCLX,XNXQ,KCZT,RXNF,CZSJ,REMARKS,"
							+"'"+stukc.getId().getSno().trim()+"',sysdate,BEIY1,BEIY2,BEIY3,BZ"
							+" FROM JH_STU_KCXX PARTITION(P"+stukc.getRxnf().trim()+")"
							+" WHERE kcid="+stukc.getId().getKcid()+" AND kclx='"+stukc.getId().getKclx().trim()+"'"
							+" AND sno='"+stukc.getId().getSno().trim()+"';"
						+"UPDATE JH_STU_KCXX PARTITION(P"+stukc.getRxnf().trim()+") SET xnxq='"+(stukc.getXnxq()==null?"":stukc.getXnxq().trim())+"',"
						+"kczt='"+(stukc.getKczt()==null?"":stukc.getKczt().trim())+"',"
						+"remarks='"+(stukc.getRemarks()==null?"":stukc.getRemarks().trim())+"'"
						+" WHERE kcid="+stukc.getId().getKcid()+" AND kclx='"+stukc.getId().getKclx().trim()+"'"
						+" AND sno='"+stukc.getId().getSno().trim()+"';";
			}
		}
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			System.out.println(sql);
			stmt.execute("BEGIN "+sql+"END;");
			conn.commit();
			stmt.close();
			conn.close();			
		} catch (Exception e) {						
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}

	public String getXnxq(String strparam) {
		// TODO Auto-generated method stub
		String sql = "select nf,xq,jddm,dqxq,bz from xt_xqjdb where jddm<100 order by nf,xq";
		Session session = this.getSession();		
		try {
			List lst = session.createSQLQuery(sql).list();
			if ((lst==null) || (lst.size() == 0)) {
				return null;
			} else {
				Object[] obj = (Object[]) lst.get(0);
				if (strparam==null) {
					return obj[0].toString().trim()+obj[1].toString().trim();
				} else if (strparam.equals("xn")) {
					return obj[0].toString().trim();
				} else if (strparam.equals("xq")) {
					return obj[1].toString().trim();
				} else if (strparam.equals("xnxq")) {
					return obj[0].toString().trim()+obj[1].toString().trim();
				} else {
					return obj[0].toString().trim()+obj[1].toString().trim();
				}
			}
		} catch (HibernateException e) {			
			return null;
		} finally {
			releaseSession(session);
		}
	}

	public String getZpdz(String zplx) {
		// TODO Auto-generated method stub
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs=null;
        String zpdz ;
        zpdz="";
		String sql = "select csz from xt_xtcsb where yxx='1' and csdm='"+zplx+"' ";
		Session session = this.getSession();		
		try{
			conn = getSession().connection();
	        stmt = conn.createStatement();
	        rs=stmt.executeQuery(sql);
	        while(rs.next()){
	         zpdz=rs.getString(1);
	        }
			}catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
			}
		return zpdz;
	}
	
	public String getLwmc(String sno,String zplx,String xh) {
		// TODO Auto-generated method stub
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs=null;
        String zpdz ;
        zpdz="";
		String sql = "select fjmc from xt_scfjb where sno='"+sno+"' and fjlx='"+zplx+"' and xh='"+xh+"' ";
		Session session = this.getSession();		
		try{
			conn = getSession().connection();
	        stmt = conn.createStatement();
	        rs=stmt.executeQuery(sql);
	        while(rs.next()){
	         zpdz=rs.getString(1);
	        }
			}catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
			}
		return zpdz;
	}
	
	public String updateUserInfo(TSlogin s_user, TLogin t_user, String type) {
		// TODO Auto-generated method stub
		String userCode = "";
		String password = "";
		String userId = "";
		String sql = "";
		String tag = "0";
		if(null!=type){
			if("s".equals(type.trim())){
				userCode = s_user.getYhdm().trim();
				String s_sql = " update T_SLOGIN set yhdm='"+userCode+"' where yhdm='"+userCode+"' ";
				sql = s_sql;
			}
			if("t".equals(type.trim())){
				userCode = t_user.getYhdm().trim();
				userId = Long.toString(t_user.getYhid());
				log.info("--------userCode-----------"+userCode);
				String t_sql = " update T_LOGIN set yhdm='"+userCode+"' where yhid='"+userId+"' ";
				sql = t_sql;
			}
		}
		log.info("sql-:"+sql);
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			stmt.execute(sql);
			tag = "1";
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				stmt.close();
			} catch (SQLException ee) {
				ee.printStackTrace();
			}
		}
		return tag;
	}

	public List getWtfkByStu(String sno) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find(" FROM XtWtfkxx WHERE id.sno='"+sno.trim()+"' ORDER BY tjsj DESC ");
	}

	public String insertWtfk(XtWtfkxx wt) {
		// TODO Auto-generated method stub
		String sql = " SELECT decode(max(xh),null,0,max(xh))  FROM XT_WTFKXXB WHERE sno='"+wt.getId().getSno().trim()+"' ";
		
		//getHibernateTemplate().find(hql)
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Long xh = Long.parseLong("0");
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				xh = rs.getLong(1);
			}
			String sqlinst = " INSERT INTO XT_WTFKXXB ( SNO, WTLX, XNXQ,KCBJH1,KCBJH2,XH, WTMS, TJSJ, ZT, CLSJ, BZ, BEIY1, BEIY2 ) " +
							" VALUES ( '"+wt.getId().getSno()+"', '"+wt.getId().getWtlx()+"','"+wt.getXnxq()+"','"+wt.getKcbjh1()+"','"+wt.getKcbjh2()+"', '"+(xh+1)+"', '"+wt.getWtms()+"', SYSDATE, '1', NULL, NULL, NULL, NULL )  "; 
			rs.close();
			stmt.execute(sqlinst);
			stmt.close();
			// conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
				stmt.close();
				// conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return "1";
	}

	public List getCurrentXnxq() {
		// TODO Auto-generated method stub
		//SELECT * FROM XT_XQJDB WHERE JDDM>-1 AND JDDM<100 ORDER BY NF,XQ;
		return (List)getHibernateTemplate().find(" FROM XtXqjdb WHERE jddm>-1 AND jddm<100 ORDER BY nf,xq ");
	}
	public List getXkkfsj(){
		//String sql = " select distinct(xnxq) from V_XK_DDBJSJ where yx=1 or cq=1 or btx=1 or bx=1 or tx=1 ";
		String sql = " select max(xnxq) from XK_KZB where kfzt='Y' and lxdm='1'";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				VXKDDBJSJ xksj = new VXKDDBJSJ();
				xksj.setXnxq((String)rs.getString(1));
				list.add(xksj);
				log.info("*****************00000000000000000000******************");
			}
			rs.close();
			stmt.close();
			// conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
				stmt.close();
				// conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public List getTAttachinfoById(Long yhid) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find(" FROM TAttachinfo WHERE id.yhid='"+Long.toString(yhid)+"' ");
	}

	public List getTYhDwdmById(Long yhid) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find(" FROM TYhDwdm WHERE id.yhid='"+Long.toString(yhid)+"' order by id.xh");
	}

	public List getTYhxxbById(Long yhid) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find(" FROM TYhxxb WHERE yhid='"+Long.toString(yhid)+"' ");
	}

	public int insertTeachInfo(TYhxxb yh, List info, List gz) {
		// TODO Auto-generated method stub
		
		   int jg=0;
		   String sql="";
		   Session session = getHibernateTemplate().getSessionFactory()
		                      .openSession();
           Transaction tx;
           tx = session.beginTransaction();
		   try{
			     session.update(yh);
			     tx.commit();
		   }catch (Exception ex) {
			     jg=1;
			     tx.rollback();
			     ex.printStackTrace();
				 return jg;
		   }finally {
			     session.close();
		   }
			Connection CurConn;
			CurConn = getSession().connection();
			PreparedStatement ps;
		   try {
		    	  System.out.println("3");
		    	   sql="delete from t_attachinfo where yhid='"+yh.getYhid()+"'"; 
		    	   ps = CurConn.prepareStatement(sql);
				   ps.executeUpdate();
			 if (info.size()>0){
				  for (int i = 1; i <= info.size(); i++) {
					    TAttachinfo attinfo=new TAttachinfo();
					    attinfo=(TAttachinfo) info.get(i-1);
					    sql="insert into t_attachinfo (yhid,info_code,info_content) values(to_number("+attinfo.getId().getYhid()+")," +
					    		" '"+attinfo.getId().getInfoCode()+"','"+attinfo.getInfoContent()+"')";
					    System.out.println("sql==="+attinfo.getId().getYhid());
					    System.out.println("sql==="+sql);
					    ps = CurConn.prepareStatement(sql);
						ps.executeUpdate();
						
			      }
			 }
				  CurConn.commit();
				  ps.close();	
			  }catch (SQLException e2) {
					// TODO Auto-generated catch block
				    jg=2;
					try {
						CurConn.rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					e2.printStackTrace();
					return jg;
				}
		return jg;
	}

				/*CurConn = getSession().connection();
		     try {
		    	      sql="delete from t_yh_dwdm where yhid='"+yh.getYhid()+"'"; 
		    	      ps = CurConn.prepareStatement(sql);
				      ps.executeUpdate();
		    	 if (gz.size()>0){
				      for (int i = 1; i <= gz.size(); i++) {
				    	  TYhDwdm dwdm=new TYhDwdm();
				    	  dwdm=(TYhDwdm) gz.get(i-1);
				    	  sql="insert into t_yh_dwdm (yhid,xh,dwdm,zwdm) values(to_number("+dwdm.getId().getYhid()+")," +
				    		" '"+dwdm.getId().getXh()+"','"+dwdm.getDwdm()+"','"+dwdm.getZwdm()+"')";
				          System.out.println("sql==="+dwdm.getId().getYhid());
				          System.out.println("sql==="+sql);
				          ps = CurConn.prepareStatement(sql);
					      ps.executeUpdate();
				      }
		    	 }
		    	  CurConn.commit();
				  ps.close();	
			  }catch (SQLException e2) {
					// TODO Auto-generated catch block
				    jg=3;
					try {
						CurConn.rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					e2.printStackTrace();
				}
		   return jg;*/
	

	public void saveLog(LogOperate log) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(log);
	}

	public String getYhsf(TYhxxb yhxx) {
		// TODO Auto-generated method stub
		String strresult = "";
		//String hql = "select distinct a.zwlx from jt_zwdm a where exists(select 1 from t_yhxxb x,t_yh_dwdm y"
        //      +" where (x.GZZW=a.zwdm and x.yhid="+yhxx.getYhid()+" and x.dwdm="+yhxx.getDwdm()+")"
        //            +" or (y.ZWDM=a.zwdm and y.yhid="+yhxx.getYhid()+" and y.dwdm="+yhxx.getDwdm()
        //            +" and y.zwdm="+yhxx.getGzzw()+"))";
		String hql = "select distinct a.zwlx from jt_zwdm a where exists(select 1 from t_yhxxb x,t_yh_dwdm y"
	              +" where (x.GZZW=a.zwdm and x.yhid="+yhxx.getYhid()+")"
	                    +" or (y.ZWDM=a.zwdm and y.yhid="+yhxx.getYhid()+"))";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println(hql);
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(hql);
			while (rs.next()) {
				if (!strresult.equals("1") && !strresult.equals("3")) {
					strresult = rs.getString(1);
				}
			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strresult;
	}
	public boolean saveXtLog(XtLog xtLog){
		this.getHibernateTemplate().save(xtLog);
		return true;
	}	
	
	public String getYzupdatepaw(String yhdm, String email, String userbz){
	String zg="0";	
	String sql="";
	String t_sql="";
	String s_sql="";
		if(userbz!=null&&userbz.equals("s")){
		 s_sql="select 1 zg from xj_xsxxb where sno='"+yhdm.trim()+"' and email='"+email.trim()+"'";
		 sql=s_sql;
		}
		if(userbz!=null&&userbz.equals("t")){
		 t_sql="select 1 zg from t_yhxxb a,t_login b where a.yhid=b.yhid and  b.yhdm='"+yhdm.trim()+"' and a.email='"+email.trim()+"'";
		 sql=t_sql;
		}
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println(sql);
		 try{
		      conn = getSession().connection();
	          stmt = conn.createStatement();
	          rs=stmt.executeQuery(sql);
	           while(rs.next()){
	             zg="1";
	                }
	         
	          }
		    catch (SQLException e) {
	     // TODO Auto-generated catch block
	         e.printStackTrace();
		    }
		
		return zg;
	}
	
	public String getSavepaswsq(String yhdm, String userbz, String yzm){
			
		 String sql="insert into XT_PAWSQB (MYDM,yhdm,yhlx,sqsj,yxx) values ('"+yzm.trim()+"','"+yhdm.trim()+"','"+userbz.trim()+"',sysdate,'1')";
		
		 Connection conn = null;
			Statement stmt = null;
			try {
				conn = getSession().connection();
				stmt = conn.createStatement();
				System.out.println(sql);
				stmt.execute(sql);
				conn.commit();
				stmt.close();
				conn.close();			
			} catch (Exception e) {						
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return "0";
			}
			return "1";
	}
	
	public String getUpdatepawzg(String yhdm, String userbz, String yzm){
		String zg="0";
		String sql="select 1 zg from V_XT_PAWSQCX where mydm='"+yzm.trim()+"' and yhdm='"+yhdm.trim()+"' and yhlx='"+userbz.trim()+"' and yxx='1' and yxbz='1'";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		System.out.println(sql);
		 try{
		      conn = getSession().connection();
	          stmt = conn.createStatement();
	          rs=stmt.executeQuery(sql);
	           while(rs.next()){
	             zg="1";
	                }
	         
	          }
		    catch (SQLException e) {
	     // TODO Auto-generated catch block
	         e.printStackTrace();
		    }
		
		return zg;
	}
	
	public String getSavepaswsubmit(String yhdm, String userbz, String newpaw,String yzm){
		String sql = "";
		String tag = "0";
		if(null!=userbz){
			if("s".equals(userbz.trim())){
				String s_sql = " update T_SLOGIN set yhmm='"+newpaw.trim()+"' where yhdm='"+yhdm.trim()+"' ";
				sql = s_sql;
			}
			if("t".equals(userbz.trim())){
				String t_sql = " update T_LOGIN set yhmm='"+newpaw.trim()+"' where yhdm='"+yhdm.trim()+"' ";
				sql = t_sql;
			}
		}
	String sql1="update XT_PAWSQB set yxx='0' where mydm='"+yzm.trim()+"'";
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.execute(sql1);
			tag = "1";
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				stmt.close();
			} catch (SQLException ee) {
				ee.printStackTrace();
			}
		}
		return tag;
	}

	public PubForm getXnxqIn() {
	    String sql="select nf||xq xnxq,xsmc from xt_xqjdb where sysdate>xqqssj and sysdate<xqjzsj";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PubForm pf = new PubForm();
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pf.setP1(rs.getString(1));
				pf.setP2(rs.getString(2));
			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pf;

	}
	public List getKcTeachers(String nd,String xqdm,String kcbjh,String dwdm,String kczw,String xm,String kcblx) {
		if (nd==null&&xqdm==null&&kcbjh==null&&dwdm==null&&kczw==null&&xm==null) {
			return new ArrayList();
		}
	    String sql="select distinct a.ND,a.XQDM,a.KCBJH,a.BEIY2,e.DWMC,e.DWBH,b.KCZW,d.XM,d.MOBILE,d.EMAIL from pk_kkrw a,jh_kc_zk b,pk_kcbjs c,t_yhxxb d,jt_dwdm e where a.KCID=b.KCID(+) and a.KCBJH=c.KCBJH(+) and  a.ND=c.ND(+) and a.XQDM=c.XQDM(+) and c.JS=d.YHID(+) and a.BEIY2=e.DWDM(+) and a.nd>(to_char(sysdate, 'yyyy' )-5)";
	    if (nd!=null&&!"".equals(nd)) {
			sql=sql+" and a.nd= "+nd;
		}
	    if (xqdm!=null&&!"".equals(xqdm)) {
			sql=sql+" and a.xqdm= "+xqdm;
		}
	    if (kcbjh!=null&&!"".equals(kcbjh)) {
			sql=sql+" and a.kcbjh like '%"+kcbjh+"%' ";
		}
	    if (dwdm!=null&&!"".equals(dwdm)) {
			sql=sql+" and a.BEIY2= "+dwdm;
		}
	    if (kczw!=null&&!"".equals(kczw)) {
			sql=sql+" and b.KCZW like '%"+kczw+"%' ";
		}
	    if (xm!=null&&!"".equals(xm)) {
			sql=sql+" and d.XM like '%"+xm+"%' ";
		}
	    if (kcblx!=null&&"benke".equals(kcblx)) {
			sql=sql+" and REGEXP_REPLACE(a.KCBLX,'[[:alpha:]]','') is not null ";
		}
	    sql=sql+" order by a.nd desc,a.xqdm desc,a.kcbjh asc ";
	    Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List kcteasList=new ArrayList();
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			System.out.println("-----"+sql);
			rs = stmt.executeQuery(sql);	
			while (rs.next()) {
				KcTeachers kcTeachers=new KcTeachers();
				kcTeachers.setNd(rs.getString("nd"));
				kcTeachers.setXqdm(rs.getString("xqdm"));
				kcTeachers.setXqdmmc();
				kcTeachers.setKcbjh(rs.getString("kcbjh"));
				kcTeachers.setBeiy2(rs.getString("beiy2"));
				kcTeachers.setDwmc(rs.getString("dwmc"));
				kcTeachers.setKczw(rs.getString("kczw"));
				kcTeachers.setXm(rs.getString("xm"));
				kcTeachers.setMobile(rs.getString("mobile"));
				kcTeachers.setEmail(rs.getString("email"));
				kcTeachers.setDwbh(rs.getString("dwbh"));
				kcteasList.add(kcTeachers);
			}
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return kcteasList;
	}
	public List getKcTeachersConditionList() {
		List list =new ArrayList();
		String sql1="select distinct nd,xqdm from pk_kkrw where nd>(to_char(sysdate, 'yyyy' )-5) order by nd desc,xqdm desc";
	    String sql2="select distinct a.beiy2,b.dwmc,b.DWBH from pk_kkrw a,jt_dwdm b where a.BEIY2=b.DWDM(+) and a.nd>(to_char(sysdate, 'yyyy' )-5) order by b.DWBH";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1);
			System.out.println("-----"+sql1);
			List list1=new ArrayList();
			while (rs.next()) {
				KcTeachers kcTeachers=new KcTeachers();
				kcTeachers.setNd(rs.getString("nd"));
				kcTeachers.setXqdm(rs.getString("xqdm"));
				kcTeachers.setXqdmmc();
				list1.add(kcTeachers);
			}
			rs = stmt.executeQuery(sql2);
			System.out.println("-----"+sql2);
			List list2=new ArrayList();
			while (rs.next()) {
				KcTeachers kcTeachers=new KcTeachers();
				kcTeachers.setBeiy2(rs.getString("beiy2"));
				kcTeachers.setDwmc(rs.getString("dwmc"));
				kcTeachers.setDwbh(rs.getString("dwbh"));
				list2.add(kcTeachers);
			}
			list.add(list1);list.add(list2);
			rs.close();
			stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					conn.rollback();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
		return list;
	}
	
	public List<News_Lx> getNews_Lx(){
		String sql=" select * from news_lx order by xxlx desc ";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<News_Lx> list =new ArrayList<News_Lx>();
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			System.out.println("-----"+sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				News_Lx news_lx =new News_Lx(
						rs.getString("xxlx"),
						rs.getString("lxmc")
						);
				list.add(news_lx);
			}
			rs.close();
			stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		return list;
	}
	public List<News> getNews(String userid,String xxlx,String isread){
		if (userid==null||"".equals(userid.trim())) {
			return null;
		}
		String sql=" select a.*,b.lxmc from news a,news_lx b where a.xxlx=b.xxlx(+) and a.userid='"+userid.trim()+"'"
				+" and (a.sxsj<sysdate or sxsj is null) and (a.gqsj>(sysdate-1) or gqsj is null) ";
		if (xxlx!=null&&""!=xxlx) {
			sql+=" and a.xxlx='"+xxlx+"' ";
		}
		if (isread!=null&&""!=isread) {
			sql+=" and a.isread='"+isread+"' ";
		}
		sql+=" order by a.cssj desc,a.xxlx desc,a.isread ";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<News> list =new ArrayList<News>();
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			System.out.println("-----"+sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				News news =new News(
						rs.getString("userid"),
						rs.getString("code"),
						rs.getString("text"),
						rs.getString("title"),
						rs.getString("linking"),
						rs.getString("method"),
						JSONObject.fromObject(rs.getString("data")),
						rs.getString("xxlx"),
						rs.getString("lxmc"),
						rs.getString("isread"),
						rs.getString("ydsj"),
						rs.getString("cssj"),
						rs.getString("sxsj"),
						rs.getString("gqsj"),
						rs.getString("csczid"),
						rs.getString("by1"),
						rs.getString("by2"),
						rs.getString("by3")
						);
				list.add(news);
			}
			rs.close();
			stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		return list;
	}
	
	public Integer getNewsCount(String userid){
		if (userid==null||"".equals(userid.trim())) {
			return null;
		}
		String sql=" select count(*) from news where userid='"+userid.trim()+
				"' and (sxsj<sysdate or sxsj is null) and (gqsj>(sysdate-1) or gqsj is null) and isread='0' ";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getSession().connection();
			stmt = conn.createStatement();
			System.out.println("-----"+sql);
			rs = stmt.executeQuery(sql);	
			while (rs.next()) {
				return rs.getInt("count(*)");
			}
			rs.close();
			stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		return null;
	}
	public boolean setStatusOfRead(List<String> codeList,int status) {
		Connection conn = null;
		Statement stmt = null;
		conn = getSession().connection();
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		int i=NewsManage.setStatusOfRead(stmt, codeList, status);
		
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return i==codeList.size();
	}
}
