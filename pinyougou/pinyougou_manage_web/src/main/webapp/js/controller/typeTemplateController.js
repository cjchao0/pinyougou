//注册下拉框组件
Vue.component('v-select', VueSelect.VueSelect);
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
        entity:{customAttributeItems:[]},
        //选择的id数组
        ids: [],
        //定一个空的搜索条件对象
        searchEntity:{},
        //品牌下拉列表数据
        brandList: [],
        //规格下拉列表数据
        specificationList: [],
        brandIds: [],
        // 全选
        selectedFlag:false
    },
    methods: {
        //将数组中的对象的属性拼接为字符串并返回
        json2Str: function(jsonArrayStr, key){
            var str = "";
            var arrayObj = JSON.parse(jsonArrayStr);
            for (const obj of arrayObj) {
                if (str.length > 0) {
                    str += "," + obj[key];
                } else {
                    str = obj[key];
                }
            }

            return str;
        },
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
        //获取格式化的品牌列表；格式为：[{id:'1',text:'联想'},{id:'2',text:'华为'}]
        findBrandList: function(){
            axios.get("../brand/selectOptionList.do").then(function (response) {
                app.brandList = response.data;
            });
        },
        //获取格式化的规格列表；格式为：[{id:'1',text:'屏幕尺寸'},{id:'2',text:'机身大小'}]
        findSpecificationList: function(){
            axios.get("../specification/selectOptionList.do").then(function (response) {
                app.specificationList = response.data;
            });
        },
        //删除属性
        deleteTableRow: function(index){
            //删除的元素索引号，要删除的元素个数
            this.entity.customAttributeItems.splice(index, 1);
        },
        //添加属性
        addTableRow: function(){
            this.entity.customAttributeItems.push({});
        },
        searchList: function (curPage) {
            this.pageNum = curPage;
            axios.post("../typeTemplate/search.do?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize, this.searchEntity).then(function (response) {
                //this表示axios；所以使用app设置entityList的数据
                app.entityList = response.data.list;
                /*app.entityList.forEach(function (value, index) {
                    app.entityList[index].brandIds = JSON.parse(value.brandIds);
                    app.entityList[index].specIds = JSON.parse(value.specIds);
                    app.entityList[index].customAttributeItems = JSON.parse(value.customAttributeItems);
                });*/
                app.total = response.data.total;
                app.ids = [];
                app.selectedFlag = false;
            });
        },
        //保存数据
        save: function () {
            var method = "add";
            if (this.entity.id != null) {
                //如果id存在则说明是 修改
                method = "update"
            }

            axios.post("../typeTemplate/"+method+".do", this.entity).then(function (response) {
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
            axios.get("../typeTemplate/findOne/" + id + ".do").then(function (response) {
                app.entity = response.data;
                //字符串类型内容转换为json对象，vue才能对对象进行处理
                app.entity.brandIds = JSON.parse(app.entity.brandIds);
                app.entity.specIds = JSON.parse(app.entity.specIds);
                app.entity.customAttributeItems = JSON.parse(app.entity.customAttributeItems);
            });
        },
        //删除；方法名不能直接使用vue关键字delete
        deleteList: function () {
            if (this.ids.length < 1) {
                alert("请选择要删除的记录");
                return;
            }
            if(confirm("确定要删除选中的记录吗？")){
                axios.get("../typeTemplate/delete.do?ids=" + this.ids).then(function (response) {
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

        //加载品牌列表；格式为：[{id:'1',text:'联想'},{id:'2',text:'华为'}]
        this.findBrandList();
        //加载规格列表；格式为：[{id:'1',text:'屏幕尺寸'},{id:'2',text:'机身大小'}]
        this.findSpecificationList();

        /*app.entityList.forEach(function (value, index) {
            app.brandIds[index] = JSON.parse(value.brandIds);
        });*/
        /*for (var i = 0;i < brandIds.length;i++){
            if(i == brandIds.length - 1){

            }
        }*/
    }
});