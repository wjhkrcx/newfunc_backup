package com.teamtech.action.xt;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.teamtech.common.BaseAction;
import com.teamtech.define.News;
import com.teamtech.define.News_Lx;
import com.teamtech.faced.XtFac;
import com.teamtech.spring.Container;

public class NewsAction extends BaseAction{

	@Override
	public ActionForward myExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=GBK");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		
		String userid = (String)session.getAttribute("userCode");
		userid= userid==null||"".equals(userid)?(String)session.getAttribute("userId"):userid;
		
		String choose=request.getParameter("choose");
		if (choose==null) {
			out.close();
			return null;
		}
		
		XtFac xt= (XtFac) Container.getContext().getBean("xtFac");
		
		if ("count".equals(choose)) {
			out.print(xt.getNewsCount(userid));
			out.close();
			return null;
		}
		if ("GetNews".equals(choose)) {
			String xxlx=request.getParameter("xxlx");
			String isread=request.getParameter("isRead");
			List<News> newsList=xt.getNews(userid,xxlx,isread);
			List<News_Lx>news_lxList=xt.getNews_Lx();
			request.setAttribute("newsList", newsList);
			request.setAttribute("news_lxList", news_lxList);
			request.setAttribute("xxlx", xxlx==null||"".equals(xxlx)?news_lxList.get(0).getxxlx():xxlx);
			request.setAttribute("isRead", isread);
			return mapping.findForward("success");
		}
		if ("isRead".equals(choose)) {
			String code=request.getParameter("code");
			List<String>codeList=new ArrayList<String>();
			codeList.add(code);
			out.print(xt.setStatusOfRead(codeList, 1));
		}
		out.close();
		return null;
	}

}
