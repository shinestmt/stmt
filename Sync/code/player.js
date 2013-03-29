/**  @version 1.4 2012.05.02 by LaoTan **/

if(navigator.platform =='Win32'){
    var ver=1;
    if(BdPlayer['download'].indexOf("BaiduPlayerNetSetup")>-1){
        ver=2;
		alert(1);
        var TN = BdPlayer['download'].replace(/http:\/\/dl.p2sp.baidu.com\/BaiduPlayer\/un2\/BaiduPlayerNetSetup_(.*).exe/ig,"$1");
    }else{
		alert(2);
	var TN = BdPlayer['download'].replace(/http:\/\/dl.client.baidu.com\/BaiduPlayer\/un\/BaiduPlayer_(.*).exe/ig,"$1");
    }
	document.write('<img src="http://php.player.baidu.com/player/tn.php?tn='+TN+'&ver='+ver+'" width="0" height="0"/>');
	document.write('<div id="BdInstall"></div><div id=obj_cont style="display: none;"></div><div id="BdPlayer"></div>');
	document.write('<script language="javascript" src="http://php.player.baidu.com/player/player.php?url='+encodeURIComponent(BdPlayer['url'])+'" charset="utf-8"><\/script>');
}


function baidu_check(){
	alert(BdPlayer['download']);
	if(BdPlayer==null){ return true } else { return false }
}

alert(baidu_check());