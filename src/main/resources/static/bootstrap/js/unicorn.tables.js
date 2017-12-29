/**
 * Unicorn Admin Template
 * Diablo9983 -> diablo9983@gmail.com
**/
$(document).ready(function(){
	/*$('.data-table').dataTable({
	"oLanguage" : {
		"sLengthMenu" : "每页显示 _MENU_ 条记录",
		"sZeroRecords" : "抱歉， 没有找到",
		"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
		"sInfoEmpty" : "没有数据",
		"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
		"oPaginate" : {
			"sFirst" : "首页",
			"sPrevious" : "前一页",
			"sNext" : "后一页",
			"sLast" : "尾页"
		},
		"sZeroRecords" : "没有检索到数据",
		"sProcessing" : "<img src='./loading.gif' />"
	},
	"bJQueryUI" : true,
	"sPaginationType" : "full_numbers",
	"sDom" : '<""l>t<"F"fp>'*/
//		l - Length changing 每页显示多少条数据选项
//		f - Filtering input 搜索框
//		t - The Table 表格
//		i - Information 表格信息
//		p - Pagination 分页按钮
//		r - pRocessing 加载等待显示信息
//		< and > - div elements 一个div元素
//		<"#id" and > - div with an id 指定id的div元素
//		<"class" and > - div with a class 指定样式名的div元素
//		<"#id.class" and > - div with an id and class 指定id和样式的div元素
	
	/*});*/
	
	
	$.extend( true, $.fn.dataTable.defaults, {
	   "serverSide": true,
	   "processing": true,
	   "bJQueryUI" : true, //是否使用 jQury的UI theme  
       "sPaginationType" : "full_numbers", //详细分页组，可以支持直接跳转到某页  
       "sDom" : '<""l>t<"F"ifp>',
       lengthMenu : [ 10, 25, 50],
       "bScrollCollapse" : true, //是否开启DataTables的高度自适应，当数据条数不够分页数据条数的时候，插件高度是否随数据条数而改变  
//       "iDisplayLength" : 1, //默认显示的记录数 
       "oLanguage": { //国际化配置  
	        "sProcessing" : "正在获取数据，请稍后...",    
	        "sLengthMenu" : "显示 _MENU_ 条",    
	        "sEmptyTable" : "没有您要搜索的内容",    
	        "sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",    
	        "sInfoEmpty" : "记录数为0",    
	        "sInfoFiltered" : "(全部记录数 _MAX_ 条)",    
	        "sSearch" : "搜索:",    
	        "oPaginate": {    
	            "sFirst" : "首页",    
	            "sPrevious" : "上一页",    
	            "sNext" : "下一页",    
	            "sLast" : "尾页"    
	        }  
     	}
	} );
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$("span.icon input:checkbox, th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		});
	});	
});
