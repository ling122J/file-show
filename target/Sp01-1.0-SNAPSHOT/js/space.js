function hideAllSections() {
    document.getElementById('upload-section').style.display = 'none';
    document.getElementById('download-section').style.display = 'none';
    document.getElementById('upload-history-section').style.display = 'none';
}

function showUploadSection() {
    hideAllSections();
    document.getElementById('upload-section').style.display = 'block';
}

function showDownloadSection() {
    hideAllSections();
    document.getElementById('download-section').style.display = 'block';
    loadFileTable();
}
function showUploadHistorySection() {
    hideAllSections();
    document.getElementById('upload-history-section').style.display = 'block';
    loadUploadHistoryTable();
}

layui.use(function () {
    var dropdown = layui.dropdown;
    // 绑定文字
    dropdown.render({
        elem: '#dropdowntext',
        data: [{
            type: '-' // 分割线
        }, {
            title: '退出登录',
            id: 104
        }],
        click: function (obj) {
            this.elem.val(obj.title);
            if (obj.id === 104) {
                window.location.href = 'logout'; // Replace 'logoutUrl' with the actual logout URL
            }
        }
    });
});

layui.use(function () {
    var upload = layui.upload;
    var element = layui.element;
    var $ = layui.$;
    var uploadListIns = upload.render({
        elem: '#fileInput',
        elemList: $('#filelist'), // 列表元素对象
        url: 'uploadFile',
        accept: 'file',
        multiple: true,
        number: 3,
        auto: false,
        bindAction: '#action',
        choose: function (obj) {
            var that = this;
            var files = this.files = obj.pushFile(); // 将每次选择的文件追加到文件队列
            // 读取本地文件
            obj.preview(function (index, file, result) {
                var tr = $(['<tr id="upload-' + index + '">',
                    '<td>' + file.name + '</td>',
                    '<td>' + (file.size / 1024).toFixed(1) + 'kb</td>',
                    '<td><div class="layui-progress" lay-filter="progress-demo-' + index + '"><div class="layui-progress-bar" lay-percent=""></div></div></td>',
                    '<td>',
                    '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>',
                    '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>',
                    '</td>',
                    '</tr>'].join(''));

                // 单个重传
                tr.find('.demo-reload').on('click', function () {
                    obj.upload(index, file);
                });

                // 删除
                tr.find('.demo-delete').on('click', function () {
                    delete files[index]; // 删除对应的文件
                    tr.remove(); // 删除表格行
                    // 清空 input file 值，以免删除后出现同名文件不可选
                    uploadListIns.config.elem.next()[0].value = '';
                });

                that.elemList.append(tr);
                element.render('progress'); // 渲染新加的进度条组件
            });
        },
        done: function (res, index, upload) { // 成功的回调
            var that = this;
            if (res.code == 0) { // 上传成功
                var tr = that.elemList.find('tr#upload-' + index)
                var tds = tr.children();
                tds.eq(3).html(''); // 清空操作
                delete this.files[index]; // 删除文件队列已经上传成功的文件
                return;
            }
            this.error(index, upload);
        },
        allDone: function (obj) { // 多文件上传完毕后的状态回调
            alert("文件上传成功")
        },
        error: function (index, upload) { // 错误回调
            var that = this;
            var tr = that.elemList.find('tr#upload-' + index);
            var tds = tr.children();
            // 显示重传
            tds.eq(3).find('.demo-reload').removeClass('layui-hide');
        },
        progress: function (n, elem, e, index) { // 注意：index 参数为 layui 2.6.6 新增
            element.progress('progress-demo-' + index, n + '%'); // 执行进度条。n 即为返回的进度百分比
        }
    });
})

function loadFileTable() {
    layui.use(['table', 'dropdown', 'layer', 'jquery'], function () {
        var table = layui.table;
        var dropdown = layui.dropdown;
        var layer = layui.layer;
        var $ = layui.$;

        table.render({
            elem: '#fileTable',
            url: 'showDownload', // 从服务器端获取数据的 URL
            toolbar: '#toolbarDemo',
            defaultToolbar: ['filter', 'exports', 'print', {
                title: '提示',
                layEvent: 'LAYTABLE_TIPS',
                icon: 'layui-icon-tips',
                onClick: function (obj) {
                    layer.alert('自定义工具栏图标按钮');
                },
            }],
            height: 'full-35', // 最大高度减去其他容器已占有的高度差
            css: [ // 重设当前表格样式
                '.layui-table-tool-temp{padding-right: 145px;}'
            ].join(''),
            cellMinWidth: 80,
            totalRow: true, // 开启合计行
            page: true,
            limit: 10,  // 默认每页显示10条
            limits: [10, 20, 30, 50], // 可选每页条数
            request: { // 添加请求参数映射
                pageName: 'pageIndex', // 将Layui的默认page参数名改为pageIndex
                limitName: 'pageSize'   // 将Layui的默认limit参数名改为pageSize
            },
            method: 'post',
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'fileid', title: 'ID', width: 100},
                {field: 'uploadname', title: '上传者', width: 200, sort: true},
                {field: 'filename', title: '文件名', width: 200},
                {
                    field: 'timeUp', title: '上传时间', width: 200, sort: true, templet: function (d) {
                        var date = new Date(d.timeUp);
                        var year = date.getFullYear();
                        var month = ('0' + (date.getMonth() + 1)).slice(-2);
                        var day = ('0' + date.getDate()).slice(-2);
                        var hours = ('0' + date.getHours()).slice(-2);
                        var minutes = ('0' + date.getMinutes()).slice(-2);
                        var seconds = ('0' + date.getSeconds()).slice(-2);
                        return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
                    }
                },
                {field: 'downTimes', title: '下载次数', width: 200, sort: true},
                {field: 'status', title: '状态', width: 200},
                {fixed: 'right', title: '操作', width: 300, minWidth: 125, templet: '#toolDemo'}
            ]],
            done: function () {
                var id = this.id;
                // 重载测试
                dropdown.render({
                    elem: '#reloadTest', // 可绑定在任意元素中，此处以上述按钮为例
                    data: [{
                        id: 'reload',
                        title: '重载'
                    }, {
                        id: 'reloadData',
                        title: '仅重载数据'
                    }],
                    // 菜单被点击的事件
                    click: function (obj) {
                        switch (obj.id) {
                            case 'reload':
                                table.reload(id);
                                break;
                            case 'reloadData':
                                table.reload(id, {
                                    url: 'showDownload',
                                    page: {
                                        curr: 1
                                    }
                                });
                                break;
                        }
                    }
                });
                // 行模式
                dropdown.render({
                    elem: '#rowMode',
                    data: [{
                        id: 'default-row',
                        title: '单行模式（默认）'
                    }, {
                        id: 'multi-row',
                        title: '多行模式'
                    }],
                    // 菜单被点击的事件
                    click: function (obj) {
                        var checkStatus = table.checkStatus(id)
                        var data = checkStatus.data; // 获取选中的数据
                        switch (obj.id) {
                            case 'default-row':
                                table.reload('fileTable', {
                                    lineStyle: null // 恢复单行
                                });
                                layer.msg('已设为单行');
                                break;
                            case 'multi-row':
                                table.reload('fileTable', {
                                    // 设置行样式，此处以设置多行高度为例。若为单行，则没必要设置改参数 - 注：v2.7.0 新增
                                    lineStyle: 'height: 95px;'
                                });
                                layer.msg('即通过设置 lineStyle 参数可开启多行');
                                break;
                        }
                    }
                });
            }
        });

        // 触发单元格工具事件
        table.on('tool(fileTable)', function (obj) {
            var data = obj.data; // 获得当前行数据
            var layEvent = obj.event; // 获得 lay-event 属性值
            if (layEvent === 'download') {
                $.ajax({
                    url: 'CheckDownPermission/lingabc' + data.fileid,
                    type: 'POST',
                    success: function (result) {
                        if (result.success) {
                            window.location.href = 'Download/lingabc' + data.fileid;
                            alert(result.message);
                        } else {
                            alert(result.message);
                        }
                    },
                    error: function () {
                        layer.msg('操作失败，请稍后再试', {icon: 2});
                    }
                });
            } else if (layEvent === 'toggle-freeze') {
                $.ajax({
                    url: 'ToggleFreeze/lingabc' + data.fileid,
                    type: 'POST',
                    success: function (result) {
                        if (result.success) {
                            alert(result.message);
                            obj.update({
                                status: result.newStatus
                            });
                        } else {
                            alert(result.message);
                            if (result.disableButton) {
                                $(obj.tr).find('.toggle-freeze').prop('disabled', true);
                            }
                        }
                    },
                    error: function () {
                        layer.msg('操作失败，请稍后再试', {icon: 2});
                    }
                });
            } else if (layEvent === 'comments') {
                var date = new Date().toISOString();
                window.location.href = 'comment.jsp?fileid=' + data.fileid + '&date=' + date;
            }
        });

        // 工具栏事件
        table.on('toolbar(fileTable)', function (obj) {
            var id = obj.config.id;
            var checkStatus = table.checkStatus(id);
            var othis = lay(this);
            switch (obj.event) {
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(layui.util.escape(JSON.stringify(data)));
                    break;
                case 'getData':
                    var getData = table.getData(id);
                    console.log(getData);
                    layer.alert(layui.util.escape(JSON.stringify(getData)));
                    break;
            }
        });
        // table 滚动时移除内部弹出的元素
        var tableInst = table.getOptions('fileTable');
        tableInst.elem.next().find('.layui-table-main').on('scroll', function () {
            dropdown.close('dropdown-table-tool');
        });
        // 触发表格复选框选择
        table.on('checkbox(test)', function (obj) {
            console.log(obj)
        });

        // 触发表格单选框选择
        table.on('radio(test)', function (obj) {
            console.log(obj)
        });
    });
}

function loadUploadHistoryTable() {
    layui.use(['table', 'dropdown'], function () {
        var table = layui.table;
        var dropdown = layui.dropdown;
        table.render({
            elem: '#uploadHistoryTable',
            url: 'showRecord', // 从服务器端获取数据的 URL
            method: 'post',
            cols: [[
                {field: 'uploadname', title: '上传者', width: 100},
                {field: 'filename', title: '文件名', width: 200},
                {
                    field: 'timeUp', title: '上传时间', width: 200, sort: true, templet: function (d) {
                        var date = new Date(d.timeUp);
                        var year = date.getFullYear();
                        var month = ('0' + (date.getMonth() + 1)).slice(-2);
                        var day = ('0' + date.getDate()).slice(-2);
                        var hours = ('0' + date.getHours()).slice(-2);
                        var minutes = ('0' + date.getMinutes()).slice(-2);
                        var seconds = ('0' + date.getSeconds()).slice(-2);
                        return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
                    }
                },
                {field: 'downTimes', title: '下载次数', width: 200},
                {field: 'status', title: '状态', width: 200}
            ]]
        })
    })
}