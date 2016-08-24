<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" >
<html>
	<head>

		<title>NewsView</title>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		
		<style type="text/css">
			.l{
   				width: 1px;
   				height: 200px;
   				background: #000;
			 }
			 li{
   				cursor:pointer;
			 }
			 .divText{
			 	width:150px;
			 	height:50px;
			 	overflow:auto;
			 	white-space:normal;
			 	word-wrap : break-word ;
			 }
		</style>
		
		<script type="text/javascript" src="/js/jsPackage/jquery-1.4.2.js"></script>
		<script type="text/javascript">
			xxlxGobal='${xxlx}';
			isReadGobal='${isRead}';
			
			function sub (arg,cur){
				if(arg.xxlx!=''&&arg.xxlx!=null&&(typeof(arg.xxlx) != 'undefined')){xxlxGobal=arg.xxlx;}
				if(arg.url==''||arg.url==null) return;
				var $formS=$('<form></form>');
				$('body').append($formS);
				$formS.hide();
				$formS.attr({'action':arg.url,'method':arg.method});
				$formS.append($('<input></input>').attr({'name':'choose','value':arg.choose}));
				$formS.append($('<input></input>').attr({'name':'isRead','value':isReadGobal}));
				$formS.append($('<input></input>').attr({'name':'xxlx','value':xxlxGobal}));
				$formS.append($('<input></input>').attr({'name':'code','value':arg.code}));
				for(var d in arg.data){
					$formS.append($('<input></input>').attr({'name':d,'value':arg.data[d]}));
				}
				$formS.submit();
			}
			function setNewsIsread(code,cur){
				curr=cur;
				if (code==''||code==null){return;}
				$.ajax({ url: 'news.do', dataType: 'text', type:'post', data:{'choose':'isRead','code':code}, success: function(d,s,r){
					if(d=='true'){$(curr).find('span').text('已读');}	
      			}});
			}
			function selectVal(){
				isReadGobal=$('#isread').val();
				sub({'url':'news.do','method':'post','choose':'GetNews'});
			}
		</script>
	
	</head>

	<body>
		<select id='isread' onchange="selectVal();">
			<option value="" >全部</option>
			<option value='0' <%="0".equals(request.getAttribute("isRead"))? "selected='selected'":""%>>未读</option>
			<option value="1" <%="1".equals(request.getAttribute("isRead"))? "selected='selected'":""%>>已读</option>
		</select>
		<table>
		<tr valign='top'>
		<td>
			<ul>
				<c:forEach var='lx' items="${news_lxList}" varStatus="s" >
					<li onclick="sub({'url':'news.do','method':'post','xxlx':'${lx.xxlx }','choose':'GetNews'});">${lx.lxmc }</li>
				</c:forEach>
			</ul>
		</td>
		<td>
			<div class="l"></div>
		</td>
		<td>
			<ul>
				<c:forEach var='news' items="${newsList}" varStatus="s" >
					<c:if test="${news.xxlx==xxlx}">
					<li onclick="setNewsIsread('${news.code}',this);" >
						<span>${news.isread==0?"未读":"已读" }</span>
						${news.title }
						<div class="divText">${news.text }</div>
						<c:if test="${news.linking!=''&&news.linking!=null}">
						<span onclick="sub({'code':'${news.code}','url':'${news.linking}','method':'${news.method}','data':${news.data==null?'\'':''}${news.data}${news.data==null?'\'':''}});">
						请点击此处
						</span>
						</c:if>
						<br />
					</li>
					</c:if>
				</c:forEach>
			</ul>
		</td>
		</tr>
		</table>
	</body>

</html>
