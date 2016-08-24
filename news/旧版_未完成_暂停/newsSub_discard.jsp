<%@ page contentType="text/html;charset=GBK" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" >
<html>
  <head>
    
    <title>NewsSub</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	
	<!--[if !IE]><!--><script type="text/javascript" src="/js/jsPackage/jquery-3.1.0.min.js"></script><!--<![endif]-->
	<!--[if IE]><script type="text/javascript" src="/js/jsPackage/jquery.js"></script><![endif]-->
	
	<script type="text/javascript">
		
		function sub (arg){
			if(arg.url=='') return;
			var $formS=$('<form></form>');
			$('body').append($formS);
			$formS.hide();
			$formS.attr({'action':arg.url,'method':arg.method});
			$formS.append($('<input></input>').attr({'name':'code','value':arg.code}));
			for(var d in arg.data){
				$formS.append($('<input></input>').attr({'name':d,'value':arg.data[d]}));
			}
			$formS.submit();
		}
		
	</script>
  </head>
  
  <body>
  
  </body>
  
</html>
