<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import ="com.teamtech.define.PublicModel" %>
<%@ page import="com.teamtech.domain.XtMkdm" %>
<%@ page import="java.util.List" %>
<%
List list = (List)request.getAttribute("cdlist");
String userbz = (String)request.getSession().getAttribute("userbz");
String userType = (request.getSession().getAttribute("userType")!=null ? (String)request.getSession().getAttribute("userType") : "");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=PublicModel.JW_TITLE %></title>
<script src="js/prototype.lite.js" type="text/javascript"></script>
<script src="js/moo.fx.js" type="text/javascript"></script>
<script src="js/moo.fx.pack.js" type="text/javascript"></script>
<link href="css/css1.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%" height="530" border="0" cellpadding="0" cellspacing="0" bgcolor="#ffffff" align="center" >
  <tr>
    <td width="150" valign="top" style="background:url(images/left_bottom.jpg) no-repeat right bottom;">
  
     <%if(userbz!=null && userbz.trim().equals("t")){%>
     <%
       for(int i=0;i<list.size();i++){
       XtMkdm xtm=(XtMkdm)list.get(i);
       if("0".equals(xtm.getIsshow().trim())){continue;}
       String mkid=xtm.getMkid().trim();
       String sjdm=xtm.getSjdm().trim();
       String mkmc=xtm.getMkmc().trim();
       String mkxz=xtm.getMkxz().trim();
      // String isshow=xtm.getIsshow().trim();
       if(mkxz.equals("5")){
      // if(isshow.equals("1")){
       if(sjdm.equals("0000000")){  
    %>     
   <div id="container" style="margin-top:3px">
     <h1 class="type"><a href="javascript:void(0)"><%=mkmc%></a></h1>       
    <div class="content">
        <p><img src="images/menu_topline.gif" width="130" height="5" /></p> 
     <%
      for(int j=0;j<list.size();j++){
      XtMkdm xtm2=(XtMkdm)list.get(j);
      if("0".equals(xtm2.getIsshow().trim())){continue;}
      String mkid2 = xtm2.getMkid().trim();
      String sjdm2 = xtm2.getSjdm().trim();
      if(sjdm2.equals(mkid)&&(!mkid2.equals(sjdm2))){
     %>
        <ul class="MM">
        <li><a href='#' url="<%=((XtMkdm)list.get(j)).getMklink()%>" ><%=((XtMkdm)list.get(j)).getMkmc()%></a></li>
        </ul>
         <%}%> 
        <%}%> 
     </div>
     <%}%> 
    <%}%> 
   <%}%>
   <%}%> 
  <%if(userbz!=null && userbz.trim().equals("s")){%>
  <%
       for(int i=0; i<list.size(); i++){
       XtMkdm xtm=(XtMkdm)list.get(i);
       if("0".equals(xtm.getIsshow().trim())){continue;}
       String mkid=xtm.getMkid().trim();
       String sjdm=xtm.getSjdm().trim();
       String mkmc=xtm.getMkmc().trim();
       String mkxz=xtm.getMkxz().trim();
      // String isshow=xtm.getIsshow().trim();
       if(mkxz.equals("4")){
      // if(isshow.equals("1")){
       if(sjdm.equals("0000000")){  
    %>     
   <div id="container" style="margin-top:3px">
     <h1 class="type"><a href="javascript:void(0)"><%=mkmc%></a></h1>       
    <div class="content">
        <p><img src="images/menu_topline.gif" width="130" height="5" /></p> 
     <%
      for(int j=0;j<list.size();j++){
      XtMkdm xtm2= (XtMkdm)list.get(j);
      if("0".equals(xtm2.getIsshow().trim())){continue;}
      String mkid2 = xtm2.getMkid().trim();
      String sjdm2 = xtm2.getSjdm().trim();
      if(sjdm2.equals(mkid)&&(!mkid2.equals(sjdm2))){
     %>
        <ul class="MM">
        <li><a href='#' url="<%=((XtMkdm)list.get(j)).getMklink()%>" ><%=((XtMkdm)list.get(j)).getMkmc()%></a></li>
        </ul>
         <%}%> 
        <%}%> 
     </div>
     <%}%> 
    <%}%> 
   <%}%>
   <%}%> 
   
<!-- <h1 class="type" style="margin-top:3px;"><a href="T_mkglAction.do" target="main">个人菜单定制</a></h1> -->   
<div id="container" style="margin-top:3px">
<h1 class="type" style="margin-top:3px;"><a href="javascript:void(0)">联系方式</a></h1>
	<div class="content">
		<p><img src="images/menu_topline.gif" width="130" height="5" /></p>
		<ul class="MM"> 
			<li><a href='#' url="lx.html" >联系方式</a></li>
		</ul>
		<%	if("0".equals(userType.trim())){%>
		<ul class="MM" > 
			<li ><a href='#' url="contactTeachers.do" >联系教师</a></li>
		</ul>
		<%}%>	
	</div>
</div>

<h1 class="type" style="margin-top:3px;"><a href="docload.jsp" target="main">重要规定查询</a></h1>
     
 <%
  if( (userbz!=null) && ("t".equals(userbz)) && (!userType.equals("2")) ){
 %>
 <p> </br><a href="/download.do" target="_blank"><strong style="font:20px;color:BLUE;"><font size="3">客户端下载</font></strong></a></br>  </p>
<%} %>
    
    <script type="text/javascript">
		var contents = document.getElementsByClassName('content');
		var toggles = document.getElementsByClassName('type');
	
		var myAccordion = new fx.Accordion(
			toggles, contents, {opacity: true, duration: 400}
		);
		myAccordion.showThisHideOpen(contents[0]);
	</script>
        </td>
  </tr>
</table>
</body>
</html>
  
<script language="JavaScript">
function xkts(){
	a="\n\n                                              补退选操作指南\n\n一、补退选阶段采取先到先得规则，选中人数未达到限选人数的课程可补选。\n\n二、补退选阶段，在《我想选的课》中查看抽签结果。状态为《未抽中》及《待确认》状态的课程要退选后才释放时间及课程的限制。\n\n三、《基础体育选项》及《英语拓展》课程分为2个补退选阶段。\n\n阶段一：2月16日9:00-2月22日24:00\n\n此类课程的开课对象可以补选，课程类别选择《体育选项》或《英语拓展》。在阶段二仍可继续通过此方式选课。\n\n阶段二：2月23日9:00-3月8日22:00\n\n1、《基础体育选项》课程，只面向体育未修满4学分的学生，课程类别选择《体育选项》。课程不再限定年级。\n\n2、《英语拓展》课程，面向全校学生开放，课程类别选择《英语拓展》。非开课对象的学生，课程类别系统自动标注为《自由选修》。"
	//a=a.fontcolor("#FF0033") 
    alert(a);
//    return false;
    }

<!-- tab相关方法 -->
function getElementsByClassName(node,classname) {//自定义class选择器
  if (node.getElementsByClassName) {
    return node.getElementsByClassName(classname);
  } else {
    return (function getElementsByClass(searchClass,node) {
        if ( node == null )
          node = document;
        var classElements = [],
            els = node.getElementsByTagName("*"),
            elsLen = els.length,
            pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)"), i, j;

        for (i = 0, j = 0; i < elsLen; i++) {
          if ( pattern.test(els[i].className) ) {
              classElements[j] = els[i];
              j++;
          }
        }
        return classElements;
    })(classname, node);
  }
}
function mainTab(className){
	function topTab(){
		window.top.tab(this);
	}
	var classArry = getElementsByClassName(document, className);
	for(var n in classArry){
		classArry[n].onclick=topTab;
	}
}    
window.onload=function(){
	mainTab('MM')
}
<!-- tab相关方法 -->

</script>