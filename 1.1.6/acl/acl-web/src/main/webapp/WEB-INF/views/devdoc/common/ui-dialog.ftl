<#include "/layout.ftl"/>
<@body>

<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">成功</h3>
  </div>
  <div class="panel-body">
  	<p>弹出使用artDialog,这是一个比bootstrap自带的弹窗强大、灵活很多的一个弹窗插件。</p>
  </div>
</div>

<div class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">主要样式</h3>
  </div>
  <div class="panel-body">
    
    <button class="btn btn-primary" id="dialog">模态弹窗</button>
    
  </div>
</div>

<script type="text/javascript">
	
	$('#dialog').on('click', function () {
		var d = dialog({
			title: '角色成员添加',
			url: '${base}/devdoc/common/welcome.ftl',
			onclose: function(){
				this.remove();
			}
		});
		d.height(450)
		d.width(750)
		d.showModal();
	});

</script>

</@body>
