<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>GoodLife by FCT</title>
<!-- <link href="style/default.css" rel="stylesheet" type="text/css" media="all" /> -->

<style type="text/css">
/* Header */

#header-wrapper {
	overflow: hidden;
	height: 157px;
	background: #DEE3E5;
}

#header {
	width: 1000px;
	height: 20px;
	margin: 0 auto;
	padding: 0px 0px;
}

/* Logo */

#logo {
	float: left;
	width: 300px;
	height: 80px;
	margin: 0px;
	padding: 0px;
}

#logo h1 {
	padding: 30px 0px 0px 0px;
	text-transform: lowercase;
	letter-spacing: -2px;
	font-size: 3.6em;
}

#logo h1 a {
	text-decoration: none;
	color: #1B2024;
}

/* Menu */

#menu {
	float: right;
	width: 650px;
	height: 80px;
}

#menu ul {
	float: right;
	margin: 0;
	padding: 0px 0px 0px 0px;
	list-style: none;
	line-height: normal;
}

#menu li {
	float: left;
}

#menu a {
	display: block;
	margin-left: 1px;
	padding: 5px 20px 5px 20px;
	text-decoration: none;
	text-align: center;
	text-transform: lowercase;
	font-family: 'Oswald', sans-serif;
	font-size: 18px;
	font-weight: 300;
	color: #10202F;
}

#menu a:hover, #menu .current_page_item a {
	text-decoration: none;
	background: #1B2024;
	border-radius: 5px;
	color: #FFFFFF;
}

div {
	border-color: black;
	border-width: 1px;
}
</style>

</head>
<body>
	<div id="header-wrapper">
		<div id="header">
			<div id="logo">
				<h1>
					<a href="#">BLOG</a>
				</h1>
			</div>
			<div id="menu">
				<ul>
					<li class="current_page_item"><a href="#" accesskey="1"
						title="">/HOME</a></li>
					<li><a href="#" accesskey="2" title="">/blog</a></li>
					<li><a href="#" accesskey="3" title="">/about</a></li>
					<li><a href="#" accesskey="4" title="">/links</a></li>
					<li><a href="#" accesskey="5" title="">/weibo</a></li>
				</ul>
			</div>
		</div>
	</div>
	
</body>
</html>
