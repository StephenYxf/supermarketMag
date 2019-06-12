function pageClick(k,url) {
	$(k).parent().find("div").removeClass("active");
	$(k).addClass("active");
	$(".content").text($(k).text());
    sendRequest(url);
}
function sendRequest(url) {
    var ajaxobj=new AJAXRequest;    // 创建AJAX对象,类在刚刚那个文件里了
    ajaxobj.method="GET";   // 设置请求方式为GET
    ajaxobj.url=url  // 响应的URL,以后可以改为一些动态处理页,会用Ajax的都知道，这在页里可以有目的返回不同的数据
    // 设置回调函数，输出响应内容,因为是静态页（这是我的需求嘛）所以所有内容都过来了
    ajaxobj.callback=function(xmlobj) {
        document.getElementById('content').innerHTML = xmlobj.responseText;//可要看好这句话哦
    }
    ajaxobj.send();    // 发送请求
}