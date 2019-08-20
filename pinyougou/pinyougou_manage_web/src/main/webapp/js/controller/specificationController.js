var app = new Vue({
    el: "#app",
    data: {
        //列表数据
        entityList:[],
        //总记录数
        total: 0,
        //页大小
        pageSize: 10,
        //当前页号
        pageNum: 1,
        //实体
        entity:{specification:{},specificationOptionList:[]},
        //选择的id数组
        ids: [],
        //定一个空的搜索条件对象
        searchEntity:{},
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
        //删除规格选项
        deleteTableRow: function(index){
            //删除的元素索引号，要删除的元素个数
            this.entity.specificationOptionList.splice(index, 1);
        },
        //添加规格选项
        addTableRow: function(){
            this.entity.specificationOptionList.push({});
        },
        searchList: function (curPage) {
            this.pageNum = curPage;
            axios.post("../specification/search.do?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize, this.searchEntity).then(function (response) {
                //this表示axios；所以使用app设置entityList的数据
                app.entityList = response.data.list;
                app.total = response.data.total;
                app.ids = [];
                app.selectedFlag = false;
            });
        },
        //保存数据
        save: function () {
            var method = "add";
            if (this.entity.specification.id != null) {
                //如果id存在则说明是 修改
                method = "update"
            }

            axios.post("../specification/"+method+".do", this.entity).then(function (response) {
                if (response.data.success) {
                    //刷新列表
                    app.searchList(app.pageNum);
                } else {
                    alert(response.data.message);
                }
            });
        },
        //根据主键查询
        findOne: function (id) {
            axios.get("../specification/findOne/" + id + ".do").then(function (response) {
                app.entity = response.data;
            });
        },
        //删除；方法名不能直接使用vue关键字delete
        deleteList: function () {
            if (this.ids.length < 1) {
                alert("请选择要删除的记录");
                return;
            }
            if(confirm("确定要删除选中的记录吗？")){
                axios.get("../specification/delete.do?ids=" + this.ids).then(function (response) {
                    if(response.data.success){
                        app.searchList(1);
                        app.ids = [];
                    } else {
                        alert(response.data.message);
                    }
                });
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
    created: function () {
        this.searchList(this.pageNum);
    }
});