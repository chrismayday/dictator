webpackJsonp([6],{COTu:function(e,t,a){"use strict";t.a=function(){return Object(l.a)({url:"/group/list"})};var l=a("vLgD")},"V/Yq":function(e,t,a){"use strict";t.a=function(){return Object(l.a)({url:"/profile/list"})};var l=a("vLgD")},hlnp:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=a("V/Yq"),r=a("COTu"),o=a("vLgD");var i={name:"configTable",data:function(){return{listLoading:!0,tableData:[],listQuery:{pageNum:1,pageSize:10,configName:"",groupName:""},pageData:{currentPage:0,total:0},recoveryForm:{showForm:!1,configId:"",historyList:[]},profileList:[],groupList:[]}},methods:{onQueryFormSubmit:function(){this.fetchData()},onRecoverySubmit:function(){},onPageNumChange:function(e){this.listQuery.pageNum=e,this.fetchData()},onPageSizeChange:function(e){this.listQuery.pageSize=e,this.fetchData()},handleRecoveryBtnClick:function(e){var t,a=this;(t=e.id,Object(o.a)({url:"/configHistory/recovery",data:{id:t}})).then(function(){a.fetchData()}).catch(function(){})},fetchData:function(){var e,t=this;this.listLoading=!0,(e=this.listQuery,Object(o.a)({url:"/configHistory/list",data:e})).then(function(e){var a=e.data,l=a.list;t.tableData=l,t.listQuery.pageNum=a.pageNum,t.listQuery.pageSize=a.pageSize,t.listQuery.total=a.total,t.listLoading=!1}).catch(function(){t.listLoading=!1})},fetchProfile:function(){var e=this;Object(l.a)().then(function(t){var a=t.data;e.profileList=a})},fetchGroup:function(){var e=this;Object(r.a)().then(function(t){var a=t.data;e.groupList=a})}},created:function(){this.fetchGroup(),this.fetchProfile(),this.fetchData()}},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-header",[a("el-form",{attrs:{model:e.listQuery,inline:!0}},[a("el-form-item",{attrs:{label:"应用ID"}},[a("el-input",{model:{value:e.listQuery.appId,callback:function(t){e.$set(e.listQuery,"appId",t)},expression:"listQuery.appId"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"配置名"}},[a("el-input",{model:{value:e.listQuery.configName,callback:function(t){e.$set(e.listQuery,"configName",t)},expression:"listQuery.configName"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"部署ID"}},[a("el-input",{model:{value:e.listQuery.deploymentId,callback:function(t){e.$set(e.listQuery,"deploymentId",t)},expression:"listQuery.deploymentId"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"环境"}},[a("el-select",{model:{value:e.listQuery.profileId,callback:function(t){e.$set(e.listQuery,"profileId",t)},expression:"listQuery.profileId"}},e._l(e.profileList,function(e){return a("el-option",{key:e.profileName,attrs:{label:e.profileName,value:e.id}})}))],1),e._v(" "),a("el-form-item",{attrs:{label:"分组"}},[a("el-select",{model:{value:e.listQuery.groupId,callback:function(t){e.$set(e.listQuery,"groupId",t)},expression:"listQuery.groupId"}},e._l(e.groupList,function(e){return a("el-option",{key:e.groupName,attrs:{label:e.groupName,value:e.id}})}))],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.onQueryFormSubmit}},[e._v("查询")])],1)],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:e.listLoading,expression:"listLoading",modifiers:{body:!0}}],attrs:{data:e.tableData,border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{prop:"id",label:"id",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"appId",label:"应用ID",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"deploymentId",label:"部署ID",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"profileName",label:"环境",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"groupName",label:"分组",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"configName",label:"配置名",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"configValue",label:"属性值",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"version",label:"版本",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"createdTime",label:"创建时间",align:"center"}}),e._v(" "),a("el-table-column",{attrs:{prop:"updatedTime",label:"修改时间",align:"center"}}),e._v(" "),a("el-table-column",{scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{icon:"el-icon-upload2"},on:{click:function(a){e.handleRecoveryBtnClick(t.row)}}},[e._v("恢复")])]}}])})],1),e._v(" "),a("el-footer",[a("el-pagination",{attrs:{background:"",layout:"sizes, prev, pager, next",data:e.listQuery,"current-page":e.listQuery.pageNum,total:e.listQuery.total},on:{"current-change":e.onPageNumChange,"size-change":e.onPageSizeChange,"update:currentPage":function(t){e.$set(e.listQuery,"pageNum",t)}}})],1),e._v(" "),a("el-dialog",{attrs:{visible:e.recoveryForm.showForm,width:"500px"},on:{"update:visible":function(t){e.$set(e.recoveryForm,"showForm",t)}}},[a("el-form",{attrs:{"label-width":"80px"},model:{value:e.recoveryForm,callback:function(t){e.recoveryForm=t},expression:"recoveryForm"}},[a("el-form-item",{attrs:{label:"历史记录"}},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.recoveryForm.configId,callback:function(t){e.$set(e.recoveryForm,"configId",t)},expression:"recoveryForm.configId"}},e._l(e.recoveryForm.historyList,function(t){return a("el-option",{key:t.configValue,attrs:{label:t.configName,value:t.id}},[a("span",{staticStyle:{float:"left"}},[e._v(e._s(t.configName))]),e._v(" "),a("span",{staticStyle:{float:"right",color:"#8492a6","font-size":"13px"}},[e._v(e._s(t.configValue))])])}))],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.onRecoverySubmit}},[e._v("立即创建")]),e._v(" "),a("el-button",{on:{click:function(t){e.recoveryForm.showForm=!1}}},[e._v("取消")])],1)],1)],1)],1)},staticRenderFns:[]};var c=a("VU/8")(i,n,!1,function(e){a("jPsr")},"data-v-0154b5e5",null);t.default=c.exports},ikVc:function(e,t,a){(e.exports=a("FZ+f")(!1)).push([e.i,"\n.el-pagination[data-v-0154b5e5] {\n  margin-top: 10px;\n}\n.form[data-v-0154b5e5] {\n  width: 500px;\n}\n",""])},jPsr:function(e,t,a){var l=a("ikVc");"string"==typeof l&&(l=[[e.i,l,""]]),l.locals&&(e.exports=l.locals);a("rjj0")("79993f23",l,!0)}});