//初始化员工表格
$("#list-table").DataTable(
		{
			"processing" : true,
			"serverSide" : true,
			"ordering" : false,
			"bPaginate" : true, // Pagination True
			"aLengthMenu" : false,
			"pageLength" : 10,
			"searching" : true,

			"language" : {
				"lengthMenu" : "",
				"paginate" : {
					"previous" : "前一页",
					"next" : "下一页"
				},
				"zeroRecords" : "",
				"sEmptyTable" : "",
				"info" : "显示_START_到_END_, 共有_TOTAL_条数据"
			},
			"ajax" : {
				"url" : "/xcloud/staff/get-by-dept.json",
				"type" : "GET",
				"data" : function(d) {
					var info = $('#list-table').DataTable().page.info();
					d.page = info.page + 1;

				}
			},

			"columns" : [
					{
						"data" : "user_id",
						"render" : function(data, type, full, meta) {
							return '<input type="checkbox" onclick="setSelectTable(' + data
									+ ')" id="user_id" name="user_id" value="' + data + '"\>';
						}
					}, {
						"data" : "job_number"
					}, {
						"data" : "name"
					}, {
						"data" : "mobile"
					}, {
						"data" : "dept_name"
					}, {
						"data" : "job_name"
					}, {
						"data" : "staff_type_name"
					},

			]
		});

// 初始化选择后的效果
$('#selected_users').tagsinput({
	itemValue : 'id',
	itemText : 'label',
	allowDuplicates : false,
	trimValue : true,
	maxChars : 8
});

// 表格中checkbox 选择触发事件
function setSelectTable(userId) {
	var table = $("#list-table").DataTable();

	var name = "";

	table.$('input[type="checkbox"]').each(function() {

		var $row = this.closest('tr');
		var data = table.row($row).data();

		if (data.user_id == userId) {

			name = data.name;
			if (this.checked) {
				addSelectUser(userId, name);
				$('#selected_users').tagsinput('add', {
					id : userId,
					label : name
				});
			} else {
				removeSelectUser(userId, name);
				$('#selected_users').tagsinput('remove', {
					id : userId,
					label : name
				});
			}

			return false;
		}
	});

	$('#selected_users').tagsinput('refresh');

}

// 表格中 checkBox 未选中的事件处理
function removeSelectTable(userId) {
	var table = $("#list-table").DataTable();

	var name = "";

	table.$('input[type="checkbox"]').each(function() {

		var $row = this.closest('tr');
		var data = table.row($row).data();

		if (data.user_id == userId) {
			this.checked = false;

			return false;
		}
	});

	// $('#selected_users').tagsinput('refresh');

}

// hidden selectUserIds 和 selectUserNames 处理添加的情况
function addSelectUser(userId, name) {

	var selectUserIds = $("#selectUserIds").val();
	var selectUserNames = $("#selectUserNames").html();

	var hasExist = false;
	if (selectUserIds != "") {
		var selectUserAry = selectUserIds.split(',');
		for (var i = 0; i < selectUserAry.length; i++) {
			if (selectUserAry[i] == userId) {
				hasExist = true;
				break;
			}
		}
	}

	if (hasExist == false) {
		selectUserIds = selectUserIds + userId + ",";
		selectUserNames = selectUserNames + name + ",";
		$("#selectUserIds").val(selectUserIds);
		$("#selectUserNames").html(selectUserNames);
	}
};

// hidden selectUserIds 和 selectUserNames 处理移除的情况
function removeSelectUser(userId, name) {
	var selectUserIds = $("#selectUserIds").val();
	var selectUserNames = $("#selectUserNames").html();

	var hasExist = false;
	var newSelectUserIds = "";
	var newSelectNames = "";
	if (selectUserIds != "") {
		var selectUserAry = selectUserIds.split(',');
		var selectNameAry = selectUserNames.split(',');
		for (var i = 0; i < selectUserAry.length; i++) {
			if (selectUserAry[i] != userId && selectUserAry[i] != "") {
				newSelectUserIds += selectUserAry[i] + ",";
				newSelectNames += selectNameAry[i] + ",";
			}
		}
	}

	$("#selectUserIds").val(newSelectUserIds);
	$("#selectUserNames").html(newSelectNames);

};

// tagsInput 删除元素完成后的触发事件
$('#selected_users').on('itemRemoved', function(event) {
	// event.item: contains the item

	var item = event.item;
	var userId = item.id;
	var name = item.label;

	removeSelectUser(userId, name);

	removeSelectTable(userId);

});

var curFormDateTime = moment().add('m', 5).format('YYYY-MM-DD HH:mm');
$('.form_datetime').datetimepicker({
	language : 'zh-CN',
	format : 'yyyy-mm-dd hh:ii',
	autoclose : true,
	todayBtn : true,
	startDate : curFormDateTime,
	minuteStep : 1
});

$('#setNowSend').not('[data-switch-no-init]').bootstrapSwitch();

// 提交验证
$("#btn-card-submit").on('click', function(e) {

	var fv = formValidation();


	if (fv == false) return false;

	// 组建提交卡片接口数据
	var params = {}
	
	params.card_id = $("#cardId").val();
	params.card_type = $("#cardType").val();
	params.title = "";
	var userId = $("#userId").val();
	params.create_user_id = userId;
	params.user_id = userId;
	
	var serviceTime = $("#serviceTime").val();
	params.service_time = moment(serviceTime, "YYYY-MM-DD HH:mm:00")/1000;
	var serviceAddr = $("#serviceAddr").val();
	if (serviceAddr == undefined) serviceAddr =  "";
	params.service_addr = serviceAddr;
	
	params.service_content = $("#serviceContent").val();
	params.set_remind = $("#setRemind").val();
	
	var setNowSend = 0;
	var setNowSendSwitch = $("#setNowSend").bootstrapSwitch("state", $(this).data('switch-value'));
	if (setNowSendSwitch == true) setNowSend = 1;
	
	params.set_now_send = setNowSend;
	
	params.set_sec_do = 0;
	params.set_sec_remarks = "";
	params.card_extra = "";
	params.status = 1;
	
	console.log(params);
	
	$.ajax({
		type : "POST",
		url : appRootUrl + "/card/post_card.json", // 发送给服务器的url
		data : params,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.status == "999") {
				alert(data.msg);
				return false;
			}

			if (data.status == 0) {
				location.href = "/xcloud/schedule/list";
			}
		}
	})
	
});

function formValidation() {
	var selectUserIds = $("#selectUserIds").val();

	var selectFlag = true;
	if (selectUserIds == "") {
		selectFlag = false;
		alert("请选择人员!");
	}

	var form = $('#card-form');

	var formValidity = $('#card-form').validator().data('amui.validator').validateForm().valid

	if (formValidity && selectFlag) {
		// done, submit form
		console.log("ok");
		// 判断提醒时间点可以提醒.
		// 1. 获取提醒时间
		var serviceTime = $("#serviceTime").val();
		var serviceTimeLong = moment(serviceTime, "YYYY-MM-DD HH:mm:00");
		var nowTimeLong = moment();
		
		// 2. 获取提醒设置
		var setRemind = $("#setRemind").val();
		
		var setRemindMin = getRemindMin(setRemind);
		
		var lastRemindTime = serviceTimeLong - setRemindMin * 60 * 1000;
		
		if (lastRemindTime <= nowTimeLong) {
			alert("时间选择必须大于当前时间.");
			return false;
		}
		
		return true;
		// form.submit();
	} else {
		// fail
		console.log("fail");
		return false;
	}
	;
}

// 根据提醒设置返回提前提醒的分钟数
function getRemindMin(setRemind) {
	var remindMin = 0;
	switch (setRemind) {
		case "0":
			remindMin = 0;
			break;
		case "1":
			remindMin = 0;
			break;
		case "2":
			remindMin = 5;
			break;
		case "3":
			remindMin = 15;
			break;
		case "4":
			remindMin = 30;
			break;
		case "5":
			remindMin = 60;
			break;
		case "6":
			remindMin = 2 * 60;
			break;
		case "7":
			remindMin = 6 * 60;
			break;
		case "8":
			remindMin = 24 * 60;
			break;
		case "9":
			remindMin = 48 * 60;
			break;
		default:
			remindMin = 0;
	}

	return remindMin;
}