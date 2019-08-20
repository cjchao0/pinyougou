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
        searchEntity: {},
        // 全选
        selectedFlag:false
    },
    methods: {
        // 实现全选
        checkAll:function(){
            // 清空上一次的操作，所以要重新初始化
            /*this.ids = [];
            this.entityList.forEach(function(entity,index){
                entity.check = !entity.check;
                // 如果你已经选择，
                if(entity.check){
                    // 就把选择的ID放入数组中。
                    app.ids.push(entity.id);
                }
            });*/
            if(!this.selectedFlag){
                //选中
                for (let i = 0; i < this.entityList.length; i++) {
                    const entity = this.entityList[i];
                    this.ids[i] = entity.id;
                }
            } else {
                this.ids = [];
            }
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
                app.selectedFlag = false;
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
    //监听数据属性的变化
    watch :{
        ids: function (newValue, oldValue) {
            if (this.ids.length != this.entityList.length) {
                this.selectedFlag = false;
            } else{
                this.selectedFlag = true;
            }
        }
    },
    //页面加载
    created() {
        /*axios.get("../brand/findAll.do").then(function (response) {
            app.entityList = response.data;
        }).catch(function () {
            alert("获取数据失败!");
        });*/
        this.searchList(this.pageNum);
    }
});