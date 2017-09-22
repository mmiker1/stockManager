<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Personal Stock Manager</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <link href="<%=request.getContextPath()%>/css/bootstrap.css" rel="stylesheet">
	    <link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet">
        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
           <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
           <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->	    
	    <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
        <script src="<%=request.getContextPath()%>/js/main.js"></script>

</head>
<body>

<div class="container-fluid">
    
    <div class="row">
		<div class="col-md-8 col-md-offset-1" >
		<H3>Personal Portfolio Stock Manager</H3>
		</div>
	</div>
	<div class="row">
	    <div class="col-md-8 col-md-offset-1" >
	       <H5>(try symbols AA, POT, AABA, AIG and etc...). 10 symbols limit.</H5>
	    </div>
	</div>
	<div class="row">
		<div class="col-md-2 col-md-offset-1 minheight35" >
		   <div class="input-group">
		       <input class="form-control" placeholder="Symbol" id="inputSymbol">
		       <div class='input-group-addon'><span onClick="searchSymbol()" title="Symbol Search" alt="Symbol Search" class="cursorPointer glyphicon glyphicon-search"></span></div>
		   </div>
		</div>
		<div class="col-md-4 col-md-offset-1 minheight35 fontsize20" id="searchMessage">
		   
		</div>
	</div>
	
	<div class="row">
	    <div class="col-md-6 col-md-offset-1 minheight80 fontsize16" id="searchStockItem">
		   
		</div>
	</div>
	<div class="row minheight15"></div>
	<div class="row minheight35">
	    <div class="col-md-6 col-md-offset-1 fontsize16 " id="errorMessage">
		   
		</div>
	</div>
	<div class="row">
	    <div class="col-md-8 col-md-offset-1" >
	       <H5>Portfolio items prices are refreshing in a real time every 5 seconds. Source - Yahoo Finance.</H5>
	    </div>
	</div>
	<div class="row">
	    <div class="col-md-6 col-md-offset-1 minheight240 fontsize16 div-border" id="stockPortfolio">
		    
		</div>
	</div>
	
</div>





</body>
</html>