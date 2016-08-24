<%@ page contentType="text/html;charset=GBK" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" >
<html>
  <head>
    
    <title>News</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    	
	<link href="css/css1.css" rel="stylesheet" type="text/css" />
  	<style type="text/css"> 
		.newsWindow { 
			position: fixed; 
			background-color: #FFFFFF;
			z-index:2000;		
		} 
	</style> 
		
	<script type="text/javascript" src="/js/jsPackage/jquery.js"></script>
	<script type="text/javascript">
		
		var getCount=true;
		
		var newsList;
		 
		function getNewsCount(){
			if (!getCount){return;}
			$.ajax({ url: 'news.do', dataType: 'text', type:'post', data:{choose:"count"}, success: function(d,s,r){
				var newsText=$('#newsText');
				d==null||d==''?newsText.text('获取失败..稍后重新获取'):newsText.text('有'+d+'条未读的消息');
				if(d>0){
					newsText.css('color','#FF0000');
				}
      		}});
      		callCount=setTimeout('getNewsCount()',60000);
		}
		
		function getNewsAndLxInJson(){
			var xxlx=$('#close').find('select').val();
		
			$.ajax({ url: 'news.do', dataType: 'json', type:'post', data:{choose:"NewsInJson",'xxlx':xxlx}, success: function(d,s,r){
				$('.news').remove();
				for(n in d){
					var $temp=$newsTemplet.clone(true);
					$temp.attr({'class':'news','num':n});
					$temp.attr('arg',JSON.stringify({'code':d[n].code,'url':d[n].linking,'method':d[n].method,'data':d[n].data}));
					$temp[0].onclick=newsClick;
					$temp.find('#title').text(d[n].title);
					$temp.find('#lxmc').text(d[n].lxmc);
					//$temp.find('#context').text(d[n].text);
					$('#newsView').append($temp);
				}
				newsList=d;
      		}});
      		
      		$.ajax({ url: 'news.do', dataType: 'json', type:'post', data:{choose:"News_LxInJson"}, success: function(d,s,r){
				$('#close').find('select').find('option').remove();
				var $tempA=$news_lxTemplet.clone(true);
					$tempA.attr('value','');	
					$tempA.text('全部');
					$('#close').find('select').append($tempA);
				for(n in d){
					var $temp=$news_lxTemplet.clone(true);
					$temp.attr('value',d[n].xxlx);	
					$temp.text(d[n].lxmc);
					$('#close').find('select').append($temp);
					d[n].xxlx==xxlx?$temp.attr('selected',true):'';
				}
      		}});
		}
		
		function newsClick(){
			var newsIframe=$('#frmright', top.document);
			newsIframe.attr('src','newsSub.jsp');
			var arg=JSON.parse($(this).attr('arg'));
			
			newsIframe.one('load',arg,function(data){newsIframe[0].contentWindow.sub(arg);});
		}
		
		function newsOpen(){
			getCount=false;
			$('#newsNum').hide();
			$('#close').show();
			var newsIframe=$('#newsIframe', top.document);
			newsIframe.width(300);
			newsIframe.height(200);
			newsIframe.css('border','6px ridge #DDDDDD');
			getNewsAndLxInJson();
		}
		function newsClose(){
			getCount=true;
			getNewsCount();
			$('.news').remove();
			$('#close').hide();
			$('#newsNum').show();
			var newsIframe=$('#newsIframe', top.document);
			newsIframe.width(150);
			newsIframe.height(30);
			newsIframe.css('border','');
		}
		
		function showDetails(curr){
			//alert(JSON.stringify(newsList[ $(this).attr('num')]));
			$('#details').text($(curr).attr('num'));	
		
		}
		
		window.onload=function(){
			$newsDetailsWindow=$('#newsDetailsWindow', top.document);
			var widthPx=$newsDetailsWindow.width();
			var heightPx=$newsDetailsWindow.height();
			//$newsDetailsWindow.css({'top':heightPx/2-100,'left':widthPx/2-200,'width':'400px','height':'200px'});
			$newsTemplet=$("#newsTemplet").clone(true);
			$news_lxTemplet=$("#close").find('select').find('option').clone(true);
			$('#newsTemplet').remove();
			getNewsCount();
		}
	</script>
  </head>
  
  <body>
  	
  	<div id='close' style='display:none' >
  		<button onclick="newsClose();" >关闭</button>
  		<button onclick="getNewsAndLxInJson();" >刷新</button>
  		<select onchange="getNewsAndLxInJson();" >
  			<option value='' >全部</option>
  		</select>
  	</div>
  		
	<div id='newsNum' class='newsWindow' style='bottom:0%;right:0%' >
		<a href='#' type='button' id='news' onclick="newsOpen();" >
			<span id='newsText' >有0条未读的消息</span>
		</a>
	</div>
	
	<ul id='newsView' >
		<li id='newsTemplet' style='margin:5px;border:1px solid #00FA9A;cursor:pointer;' onmouseover="showDetails(this);">
			<span id='lxmc' style='color:#ff9955;font-size:24px' ></span>&nbsp;
			<span id='title' style='font-size:20px' ></span>
		</li>
	</ul>
  		
  </body>
</html>
