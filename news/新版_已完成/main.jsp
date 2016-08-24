<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import ="java.util.Date" %>
<%@ page import ="java.text.SimpleDateFormat" %>
<%@ page import ="com.teamtech.define.PublicModel" %>
<% 
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragrma","no-cache"); 
	response.setDateHeader("Expires",-1); 
%>
<%
String userbz = (String)request.getSession().getAttribute("userbz");  
%>

<html>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="js/ajax_gradxk.js"></script>
  <head>
  <title><%=PublicModel.JW_TITLE %></title>
  <link href="css/css1.css" rel="stylesheet" type="text/css" />
  <style>
  .navpoint {color:ffffff;cursor:hand;font-family:webdings;font-size:8pt}
  </style>
  <script>
  function switchbar(){
	  if (switchpoint.innerHTML==3){
		  switchpoint.innerHTML=4;
		  document.getElementById("frmtitle").style.display="none";
	  }
	  else{
		  switchpoint.innerHTML=3;
		  document.getElementById("frmtitle").style.display="";
	  }
  }
  </script>
  
  <body bgcolor="#ffffff" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" align="center" ><!-- scroll=no style="margin:0px""-->
<%--<div style="position:absolute;center:0;width:100%;background:#C00;color:#FFF;text-align:center;top:0;font-weight:bold;font-size:12px;padding:2px;">
当前为公测阶段，所有操作及修改将在正式选课前复原
</div>
--%>
<form name="form1" id="form1" action="quit.do"></form>
<table height="10%" width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
  <td width="100%">
  <div align="center">
  <!--头部开始-->
  <div class="head">
      <%if(userbz!=null && userbz.trim().equals("p")) {%>
	  <div class="logo"></div><!--教师logo;学生logo2-->
	  <%} %>
	  <%if(userbz!=null && userbz.trim().equals("t")) {%>
	  <div class="logo"></div><!--教师logo;学生logo2-->
	  <%} %>
	  <%if(userbz!=null && userbz.trim().equals("s")) {%>
	  <div class="logo2"></div><!--教师logo;学生logo2-->
	  <%} %>
	  <div class="head_right">
	  <%
		  String userName = (String)request.getSession().getAttribute("userName");
		  String userCode = (String)request.getSession().getAttribute("userCode");
		  Date dd = new Date();
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
		  String strdate=sdf.format(dd);
	   %>
	  <p class="login_name">
	  <%if(userbz!=null && userbz.trim().equals("t")) {%>
	  	尊敬的<%=(userName==null||userName.equals("null")||userName.equals("")) ? "" : userName %>老师，欢迎您登录教务管理系统！
	  	
	  <%} %>
	  <%if(userbz!=null && userbz.trim().equals("s")) {%>
	  	<%=(userName==null||userName.equals("null")||userName.equals("")) ? "" : userName %>-<%=(userCode==null||userCode.equals("null")||userCode.equals("")) ? "" : userCode %>
	  <%} %>
	  [<a href="javascript:void(0)" onclick="quit()" class="white">退出</a>]</p><div class="clear"></div>
	  <div>
	    <h1><img src="images/images_15.gif" /></h1><p class="login_time"><%=strdate %>登录</p></div>
	  </div>
	</div>
</div>	
<!--头部结束-->
  
  </td>
  </tr>
</table>

<!-- tab相关Html -->
  	<div id='tabDiv' align="left" style='width:1150px;height:20px;position:absolute;top:100px;left:170px;' >
	</div>
<!-- tab相关Html -->

  <table height="4%" width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr><td><iframe src="bt_top.do" width="100%" height="22" frameborder="0" scrolling="no" align="center"></iframe></td></tr>
  </table>
  <table align="center" border=0 cellpadding=0 cellspacing=0 height="74%" width="99%" >
  <tbody>
    <td align=middle id=frmtitle nowrap valign=center name="frmtitle" width='6%'>
    <!--scrolling=no -->
  	<iframe frameborder=0 id=boardtitle name=boardmenu src="left.do" style="height:100%;visibility:inherit;width:150px;z-index:2;overflow-x:hidden;" align="center"></iframe>  
    <td bgcolor=#c0dde9 onclick=switchbar() style="width:8pt">
  <span class=navpoint id=switchpoint title=close/open_main>3</span>
  </td>
  <td style="width:100%"><!-- initkcsq.do -->
  <div id='mainWhere' >
  	   	<iframe frameborder=0 id=frmright name="main" src="" style="position:absolute;top:150px;left:170px;visibility:inherit;z-index:1"></iframe> 
  </div>
  </td>
  <!-- main_init.html<td bgcolor=#c0dde9 onclick=switchbar() style="width:8pt">
  <span class=navpoint id=switchpoint title=close/open_main >7</span>
  </td> -->
  </tbody>  
  </table>
  
  <table height="4%" width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr><td><iframe src="bottom.html" width="100%" height="20" frameborder="0" scrolling="no" align="center"></iframe><br></td></tr>
  </table>
  
  <!-- news相关Html -->
  	<div id='newsWindow'
  		style='width:150;height:30px;position:absolute;bottom:5px;right:5px;z-index:1000;overflow:auto;' >
  		<a href='#' type='button' id='news' onclick="newsView();" >
  			<span id='newsText' >正在获取消息中...</span>
  		</a>
	</div>
  <!-- news相关Html -->
  	
  </body>  
  </html>
  <!-- news相关 -->
	<script type="text/javascript" src="/js/jsPackage/jquery.js"></script>
  <!-- news相关 -->
  
  <script language="JavaScript">
	function winclose(){
		parent.frames['xdkciframe'].location="userinit.do";
	}

 function quit(){
 
  if(confirm("确定要退出系统吗？")){
    document.form1.submit();
    window.opener=null;
    window.open('','_self');
    window.close();
  }
  
 }

<!-- news相关方法 -->
	function getNewsCount(){
		$.ajax({ url: 'news.do', dataType: 'text', type:'post', data:{choose:"count"}, success: function(d,s,r){
			var newsText=$('#newsText');
			if(d==null||d==''){
				newsText.text('获取失败..稍后重新获取');
				setTimeout('getNewsCount()',60000);
			}else if(d>0){
				newsText.text('有'+d+'条未读的消息')
				newsText.css('color','#FF0000');
			}else{
				newsText.text('有'+d+'条未读的消息')
			}
      	}});
	}
	function newsView(){
			$('#newsText').text('点击查看消息');
			$('#newsText').css('color','#000000');
			if($('#frmright').length>0){
				$('#frmright').attr('src','news.do?choose=GetNews&isRead=0');
			}else{
				var	$a=$('<ul></ul>');
				var $b=$('<li></li>');
				var $c=$('<a></a>');
				$a.attr('class','MM');
				$c.attr({'href':'#','url':'news.do?choose=GetNews&isRead=0'});
				$c.text('查看消息');
				$b.append($c);
				$a.append($b);
				tab($a);
			}	
		}
<!-- news相关方法 -->

<!-- tab相关方法 -->
	var tablist=[];
	var iframeSelector;
	var $iframeBackup;
	function tab(curr){
		allHide(tablist);
		var url=$(curr).find('a').attr('url');
		var name=$(curr).find('a').text();
		var $iframeMain=$iframeBackup.clone(true);
		$('#mainWhere').append($iframeMain);
		var nameL=tablist.length;
		$iframeMain.attr({'src':url,'name':nameL,'id':nameL});
		tablist.push($iframeMain);
		
		var $divC=$('<button></button>');
		$divC.bind('click',showSelf);
		$divC.text(name);
		$divC.css('color','#FF0000');
		$divC.attr('class',nameL);
		$('#tabDiv').append($divC);
		$divC=$('<button></button>');
		$divC.bind('click',closeSelf);
		$divC.text('关闭');
		$divC.css('color','#FF0000');
		$divC.attr('class',nameL);
		$('#tabDiv').append($divC);
	}
	
	function showSelf(){
		var indexN=$(this).attr('class');
		allHide(tablist);
		tablist[indexN].show();
	}
	function closeSelf(){
		var indexN=$(this).attr('class');
		tablist[indexN].remove();
		tablist[indexN]='nothing';
		$('.'+indexN).remove();
		
		for(var n=0;n<tablist.length;n++){
			if(tablist[n]=='nothing'||tablist[n].is(":hidden")){
				continue;
			}
			return false;
		}
		var showNum=indexN;
		for(var n=indexN;n<tablist.length;n++){
			if(tablist[n]=='nothing'){
				continue;
			}
			showNum=n;
			break;
		}
		if(showNum==indexN){
			for(var n=indexN;n>-1;n--){
				if(tablist[n]=='nothing'){
					continue;
				}
				showNum=n;
				break;
			}
		}
		if(showNum!=indexN){
			tablist[showNum].show();
		}
	}
	
	function allHide(a){
		for(var n in a){
			$(a[n]).hide();
		}
	}
	
	function tabInit(iframeId){
		iframeSelector=iframeId;
		$('#tabDiv').css('width',$(window).width()-180+'px');
		$(iframeSelector).css('width',$(window).width()-180+'px');
		$(iframeSelector).css('height',$(window).height()-160+'px');
		$iframeBackup=$(iframeSelector).clone(true);
		$(iframeSelector).attr({'id':'','name':''});
		$(iframeSelector).remove();
	}
<!-- tab相关方法 -->
	window.onload=function(){ 
		getNewsCount();
		tabInit('#frmright');
	}
</script> 
