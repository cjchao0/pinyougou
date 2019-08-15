var app = new Vue({
    el: "#app",
    data: {
        //列表数据
        entityList: [],
        //总记录数
        total: 0,
        //页大小
        pageSize: 10,
        //当前页
        pageNum: 1,
        //实体
        entity: {},
        //选择的id数组
        ids: [],
        //搜索数据
        searchEntity: {}
    },
    methods: {
        /*checkAll: function () {
            if (this.ids.length < 1) {
                this.entityList.forEach(function (entity, index) {
                    app.ids[index] = entity.id;
                });
                // app.ids = true;
                /!*for(var i = 0;i < this.entityList.length;i++){
                    this.ids[i] = this.entityList[i].id;
                }*!/
            } else {
                this.ids = [];
            }
            console.log("ids: " + this.ids);
        },*/
        // 实现全选和反选
        checkAll:function(){
            // 清空上一次的操作，所以要重新初始化
            this.ids = [];
            this.entityList.forEach(function(entity,index){
                entity.check = !entity.check;
                // 如果你已经选择，
                if(entity.check){
                    // 就把选择的ID放入数组中。
                    app.ids.push(entity.id);
                }
            });
        },
        curCheck:function(){
            var that = this;
            //过滤check为true的 1 2 3 4 5 javascript  数组
            //filter 把数组中满足条件的留下，不满足条件过滤掉。返回一个新的数组
            var curTrueArray=this.ids.filter(function(item){
                return item.check==true;
            });

            that.ids = [];
            // 遍历所有选中的数组
            curTrueArray.forEach(function(blog,index){
                that.ids.push(blog.id);
            });

            //判断选中的状态与总长度比较  当选中的项与总长度相等时 自动 勾选 “全选”
            // 不相等时 取消 全选
            that.checked = (curTrueArray.length == that.ids.length);
        },
        //查询方法
        searchList: function (curPage) {
            this.pageNum = curPage;
            axios.post("../brand/search.do?pageNum=" + this.pageNum + "&pageSize=" +
                this.pageSize, this.searchEntity).then(function (response) {
                //设置总记录数
                app.total = response.data.total;
                //设置列表
                app.entityList = response.data.list;
                app.ids = [];
            });
        },
        //保存 或 修改
        save: function () {
            var method = "add";
            if (this.entity.id != null) {
                //如果id存在说明是修改
                method = "update"
            }
            if (!this.entity.name) {
                alert("内容不能为空!");
                return;
            }
            if (!this.entity.firstChar) {
                alert("内容不能为空");
                return;
            }
            axios.post("../brand/" + method + ".do", this.entity).then(function (response) {
                if (response.data.success) {
                    //刷新列表
                    app.searchList(app.pageNum);
                } else {
                    alert(response.data.message);
                }
            });
        },
        //修改查询
        findOne: function (id) {
            axios.get("../brand/findOne/" + id + ".do").then(function (response) {
                app.entity = response.data;
            })
        },
        //删除
        deleteList: function () {
            if (this.ids.length < 1) {
                alert("请选择要删除的记录");
                return;
            }
            if (confirm("确定要删除选中的记录吗?")) {
                axios.get("../brand/delete.do?ids=" + this.ids).then(function (response) {
                    if (response.data.success) {
                        app.searchList(1);
                        app.ids = [];
                    } else {
                        alert(response.data.message);
                    }
                })
            }
        }
    },
    //页面加载
    created: function () {
        /*axios.get("../brand/findAll.do").then(function (response) {
            app.entityList = response.data;
        }).catch(function () {
            alert("获取数据失败!");
        });*/
        this.searchList(this.pageNum);
    }
});