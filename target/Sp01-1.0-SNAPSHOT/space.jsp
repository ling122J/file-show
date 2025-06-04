<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个人空间</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/layui/css/layui.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/space.css">
</head>
<body>
<div class="navbar">
    <a href="#home">主页</a>
    <a href="#search">检索</a>
    <a href="javascript:;" class="layui-font-blue account" id="dropdowntext">账户</a>
</div>
<div class="sidebar">
    <button class="layui-btn layui-btn-primary" onclick="showUploadSection()">上传文件</button>
    <button class="layui-btn layui-btn-primary" onclick="showDownloadSection()">下载文件</button>
    <button class="layui-btn layui-btn-primary" onclick="showUploadHistorySection()">上传记录</button>
</div>
<div class="content">
    <div id="upload-section" class="upload-section" style="display:none;">
        <div class="layui-upload">
            <button type="button" class="layui-btn layui-btn-normal" id="fileInput" name="file">选择多文件</button>
            <div class="layui-upload-list">
                <table class="layui-table">
                    <colgroup>
                        <col style="min-width: 100px;">
                        <col width="150">
                        <col width="260">
                        <col width="150">
                    </colgroup>
                    <thead>
                    <th>文件名</th>
                    <th>大小</th>
                    <th>上传进度</th>
                    <th>操作</th>
                    </thead>
                    <tbody id="filelist"></tbody>
                </table>
            </div>
            <button type="button" class="layui-btn" id="action">开始上传</button>
        </div>
    </div>
    <div id="download-section" style="display:none;">
        <table class="layui-hide" id="fileTable" lay-filter="fileTable"></table>
    </div>
    <div id="upload-history-section" style="display:none;">
        <table id="uploadHistoryTable" lay-filter="uploadHistoryTable"></table>
    </div>
</div>
<script type="text/html" id="toolDemo">
    <div class="layui-clear-space" lay-filter="">
        <a class="layui-btn layui-btn-xs" lay-event="download">下载</a>
        <a class="layui-btn layui-btn-xs" lay-event="toggle-freeze">冻结/解冻</a>
    </div>
</script>
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
        <button class="layui-btn layui-btn-sm" lay-event="getData">获取当前页数据</button>
        <button class="layui-btn layui-btn-sm layui-bg-blue" id="reloadTest">
            重载测试
            <i class="layui-icon layui-icon-down layui-font-12"></i>
        </button>
        <button class="layui-btn layui-btn-sm layui-btn-primary" id="rowMode">
            <span>{{= d.lineStyle ? '多行' : '单行' }}模式</span>
            <i class="layui-icon layui-icon-down layui-font-12"></i>
        </button>
    </div>
</script>
<script src="<%=request.getContextPath()%>/layui/layui.js"></script>
<script src="<%=request.getContextPath()%>/js/space.js"></script>
</body>

</html> 