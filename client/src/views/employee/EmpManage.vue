<template>
    <div>
      <el-container>
        <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
          <div style="display: inline; margin-left: 20px;">
            姓名:
            <el-autocomplete
              v-model="queryName"
              :fetch-suggestions="querySearchAsync"
              @select="selectQueryName"
              placeholder="请输入内容"
              prefix-icon="el-icon-search"
              size="mini"
              ></el-autocomplete>
            <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search"
                       @click="loadEmps">搜索
            </el-button>
          </div>
          <div style="margin-left: 20px;margin-right: 20px;display: inline">
            <el-button type="success" size="mini" @click="exportEmps">
              <i class="fa fa-lg fa-level-down" style="margin-right: 5px"></i>导出数据
            </el-button>
            <el-button type="primary" size="mini" icon="el-icon-plus"
                       @click="showAddEmpView">
              添加员工
            </el-button>
          </div>
        </el-header>
        <el-main style="padding-top: 0px;">
          <el-table
          :data="Employee"
          v-loading="tableLoading"
          border
          show-header
          style="width: 100%;">
            <el-table-column
              prop="userName"
              label="登录名"
              align="center"
              width="80">
            </el-table-column>
          <el-table-column
            fixed
            sortable
            prop="name"
            label="姓名"
            align="center"
            width="100">
          </el-table-column>
          <el-table-column
            prop="gender"
            label="性别"
            align="center"
            width="100">
          </el-table-column>
          <el-table-column
            prop="birthday"
            label="生日"
            align="center"
            width="100">
          </el-table-column>
          <el-table-column
            prop="position"
            label="岗位"
            align="center"
            width="120">
          </el-table-column>
          <el-table-column
            prop="email"
            label="邮箱"
            align="center"
            width="160">
          </el-table-column>
          <el-table-column
            prop="phone"
            label="电话号码"
            align="center"
            width="120">
          </el-table-column>
          <el-table-column label="地址" align="center">
            <el-table-column
              prop="province"
              label="省份"
              align="center"
              width="120">
            </el-table-column>
            <el-table-column
              prop="city"
              label="市"
              align="center"
              width="120">
            </el-table-column>
            <el-table-column
              prop="district"
              label="区/县"
              align="center"
              width="120">
            </el-table-column>
            <el-table-column
              prop="area"
              label="具体地址"
              align="center"
              min-width="150px"
            >
            </el-table-column>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
            width="140">
            <template slot-scope="scope">
              <el-button @click="showEditEmpView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                         size="small">编辑
              </el-button>
              <el-button @click="resetPassword(scope.row)" type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                         >重置密码
              </el-button>
            </template>
          </el-table-column>
        </el-table>
          <div style="display: flex; justify-content: space-between; margin: 2px; width: 100% ">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[1, 5, 10, 20, 50, 100]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalCount">
          </el-pagination>
        </div>
        </el-main>
      </el-container>

      <el-form :model="emp" :rules="rules" ref="addEmpForm" style="margin: 0px;padding: 0px;">
        <div>
          <el-dialog
            :title="dialogTitle"
            style="padding: 0px; text-align: left; min-width: 1280px;"
            :close-on-click-modal="false"
            :visible.sync="registerVisible"
            width="70%">
            <el-row>
              <el-col :span="8">
                <div>
                  <el-form-item label="用户名:" prop="userName">
                    <el-input prefix-icon="el-icon-edit" v-model="emp.userName" size="middle" style="width: 165px"
                              placeholder="请输入用户名"></el-input>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <el-form-item label="姓名:" prop="name">
                    <el-input prefix-icon="el-icon-edit" v-model="emp.name" size="middle" style="width: 150px"
                              placeholder="请输入员工姓名"></el-input>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <el-form-item label="性别:" prop="gender">
                    <el-select v-model="emp.gender" style="width: 100px" size="middle" placeholder="性别">
                      <el-option
                        v-for="item in gender"
                        :key="item.value"
                        :label="item.name"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <div>
                  <el-form-item label="生日:" prop="birthday">
                    <el-date-picker
                      v-model="emp.birthday"
                      size="middle"
                      value-format="yyyy-MM-dd"
                      style="width: 150px"
                      type="date"
                      placeholder="生日">
                    </el-date-picker>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <el-form-item label="邮箱:" prop="email">
                    <el-input prefix-icon="el-icon-message" v-model="emp.email" size="middle" style="width: 180px"
                              placeholder="邮箱地址..."></el-input>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <el-form-item label="住址" >
                    <el-cascader
                      size="middle"
                      prop="address"
                      expand-trigger="hover"
                      :options="areas"
                      v-model="emp.address"
                      style="width: 200px"
                    >
                    </el-cascader>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <div>
                  <el-form-item label="详细地址:" prop="area">
                    <el-input v-model="emp.area" size="middle" style="width: 180px"
                              placeholder="请输入详细地址"></el-input>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <el-form-item label="职位:" prop="posId">
                    <el-select v-model="emp.posId" style="width: 180px" size="middle" placeholder="请选择职位">
                      <el-option
                        v-for="item in positions"
                        :key="item.objid"
                        :label="item.name"
                        :value="item.objid">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="8">
                <div>
                  <el-form-item label="状态:" prop="enabled">
                    <el-select v-model="emp.enabled" style="width: 100px" size="middle" placeholder="状态">
                      <el-option
                        v-for="item in enableStatus"
                        :key="item.value"
                        :label="item.name"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <div>
                  <el-form-item label="电话号码:" prop="phone">
                    <el-input prefix-icon="el-icon-phone" v-model="emp.phone" size="middle" style="width: 180px"
                              placeholder="电话号码..."></el-input>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
              <el-button size="middle" @click="cancelEidt">取 消</el-button>
              <el-button size="middle" type="primary" @click="addEmp('addEmpForm')">确 定</el-button>
            </span>
          </el-dialog>
        </div>
      </el-form>
    </div>
</template>

<script>
    export default {
      name: "EmpManage",
      methods: {
        showAddEmpView() {
          this.dialogTitle = "添加员工";
          this.emptyEmpData();
          this.registerVisible = true;
        },
        showEditEmpView(row) {
          console.log(row);
          let _this = this;
          this.dialogTitle = "编辑员工";
          let empUserName = row.userName;
          let getParam = "userName=" + empUserName;
          this.getRequest("/employee/getEmpByUserName?" + getParam ).then(resp=> {
            if (resp && resp.status == 200) {
              let data = resp.data;
              _this.emp = data;
              this.registerVisible = true;
            }
          })
        },
        resetPassword(row) {
          let _this = this;
          let getParam = "userName=" + row.userName;
          this.getRequest("/employee/resetPassword?" + getParam ).then(resp=> {
            if (resp && resp.status == 200) {
              let data = resp.data;
              let msg = '默认登录密码：' + data.pass;
              this.$alert( msg, '警告' );
            }
          })
        },
        loadEmps(){
          let _this = this;
          this.tableLoading = true;
          let getParam = "currentPage=" + this.currentPage + "&currentSize=" + this.currentSize + "&queryName=" + this.queryName;
          this.getRequest("/employee/getEmp?" + getParam ).then(resp=> {
            this.tableLoading = false;
            if (resp && resp.status == 200) {
              // _this.emptyEmpData();
              let data = resp.data;
              _this.Employee = data.Employee;
              _this.totalCount = data.totalCount;
            }
          })
        },
        initData() {
          let _this = this;
          this.getRequest('/config/getAllArea').then((res) => {
            _this.areas = res.data;
          });
          this.getRequest('/config/getAllPosition').then((res) => {
            let data = res.data;
            _this.positions = data.positions;
          })
        },
        handleSizeChange(currentSize) {
          this.currentSize = currentSize;
          this.loadEmps();
        },
        handleCurrentChange(currentChange){
          this.currentPage = currentChange;
          this.loadEmps();
        },
        cancelEidt(){
          this.registerVisible = false;
          this.emptyEmpData();
        },
        emptyEmpData(){
          this.emp = {
            objid: '',
            name: '',
            userName: '',
            birthday: '',
            email: '',
            phone: '',
            gender: '',
            area: '',
            address: [],
            posId: '',
            enabled: ''
          }
        },
        addEmp(formName){
          let _this = this;
          this.$refs[formName].validate((valid) => {
            if (valid) {
              //新增或更新
              this.tableLoading = true;
              this.postRequest("/employee/register", this.emp).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  let data = resp.data;
                  if ( data == 'ok' ) {
                    if ( _this.emp.id ) {
                      _this.$elMessage('编辑成功', 'success');
                    } else {
                      _this.$elMessage('新增成功', 'success');
                    }
                    _this.cancelEidt();
                    _this.loadEmps();
                  } else if ( data == 'hasName') {
                    _this.$elMessage('有相同用户名，请重新输入', 'warning');
                  }
                }
              })
            } else {
              return false;
            }
          });
        },
        querySearchAsync(queryString, cb) {
          let _this = this;
          // this.queryName = queryString;
          this.postRequest("/employee/querySearchEmp", {"queryName": queryString} ).then(resp=> {
            if (resp && resp.status == 200) {
              let data = resp.data.emps;
              cb(data);
            }
          })
        },
        selectQueryName(item) {
          this.queryName = item.name;
        },
        exportEmps() {

        },
        fileUploadSuccess() {

        },
        fileUploadError() {

        },
        beforeFileUpload() {

        }
      },
      data() {
        return {
          queryName: '',
          Employee: [],
          showHeader: true,
          tableLoading: false,
          totalCount: -1,
          currentPage: 1,
          currentSize: 20,
          fileUploadBtnText: '导入数据',
          faangledoubleup: 'fa-angle-double-up',
          faangledoubledown: 'fa-angle-double-down',
          emp: {
            objid: '',
            name: '',
            userName: '',
            birthday: '',
            email: '',
            phone: '',
            gender: '',
            area: '',
            address: [],
            posId: '',
            enabled: 1,
            password: '1'
          },
          positions: [],
          areas: [],
          gender: [{"value":0,"name":"男"},{"value":1,"name":"女"}],
          enableStatus: [{"value":0,"name":"离职"},{"value":1,"name":"在职"}],
          registerVisible: false,
          dialogTitle: '',
          rules: {
            userName: [{required: true, message: '请输入用户名', trigger: 'blur'}],
            name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
            gender: [{required: true, message: '请选择性别', trigger: 'blur'}],
            birthday: [{required: true, message: '请输入生日', trigger: 'blur'}],
            password: [{required: true, message: '请输入密码', trigger: 'blur'}],
            email: [{required: true, message: '请输入邮箱', trigger: 'blur'}],
            phone: [{required: true, message: '请输入电话号码', trigger: 'blur'}],
            address: [{type:'array', required: true, message: '请选择地址', trigger: 'blur'}],
            area: [{required: true, message: '请选输入详细地址', trigger: 'blur'}],
          }
        }
      },
      mounted() {
        this.loadEmps();
        this.initData();
      }
    }
</script>

<style scoped>

</style>
