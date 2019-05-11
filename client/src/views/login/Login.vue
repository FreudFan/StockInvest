<template>
  <div class="login_bg">
    <div class="Login">
      <h2>欢迎使用投研系统</h2>
      <el-form ref="form" :model="login" label-width="80px">
        <el-form-item label="用户名">
          <el-input placeholder="请输入用户名" v-model="login.userName" maxlength="20" ></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input placeholder="请输入密码" v-model="login.password" show-password maxlength="20" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onLogin">登录</el-button>
          <el-button @click="toRegister">注册</el-button>
        </el-form-item>
      </el-form>
      <!--<router-link to="/register">register</router-link>-->
      <!--<router-link :to="{name: 'Register'}">register</router-link>-->
    </div>

    <el-form :model="Employee" :rules="rules" ref="addEmpForm" style="margin: 0px; padding: 0px;">
      <div style="text-align: left;">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px; min-width: 1250px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="70%">
          <el-row>
            <el-col :span="8">
              <div>
                <el-form-item label="用户名:" prop="userName">
                  <el-input prefix-icon="el-icon-edit" v-model="Employee.userName" style="width: 180px"
                            placeholder="请输入用户名"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <el-form-item label="姓名:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="Employee.name" style="width: 180px"
                            placeholder="请输入用户姓名"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <el-form-item label="性别" prop="gender">
                  <el-select v-model="Employee.gender" placeholder="请选择性别" style="width: 180px">
                    <el-option label="男" value="0"></el-option>
                    <el-option label="女" value="1"></el-option>
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
                    v-model="Employee.birthday"
                    value-format="yyyy-MM-dd"
                    style="width: 180px"
                    type="date"
                    placeholder="生日">
                  </el-date-picker>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <el-form-item label="密码:" prop="password">
                <el-input type="password" v-model="Employee.password" autocomplete="off"
                          style="width: 180px" placeholder="请输入密码"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="确认密码:" prop="checkPass">
                <el-input type="password"  autocomplete="off" style="width: 180px"
                          placeholder="请再次输入密码"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <div>
                <el-form-item label="邮箱:" prop="email">
                  <el-input prefix-icon="el-icon-message" v-model="Employee.email" style="width: 180px"
                            placeholder="电子邮箱地址..."></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <el-form-item label="电话号码:" prop="phone">
                  <el-input prefix-icon="el-icon-phone" v-model="Employee.phone" style="width: 180px"
                            placeholder="请输入电话号码"></el-input>
                </el-form-item>
              </div>
            </el-col>

            <el-col :span="8">
              <div>
                <el-form-item label="住址" >
                  <el-cascader
                    prop="address"
                    expand-trigger="hover"
                    :options="areas"
                    v-model="Employee.address"
                    @change="handleChange"
                    style="width: 180px"
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
                  <el-input v-model="Employee.area" style="width: 180px"
                            placeholder="请输入详细地址"></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="8">
              <div>
                <el-form-item label="职位" >
                  <el-select v-model="Employee.posId" placeholder="请选择职位" style="width: 180px">
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
          </el-row>

          <span slot="footer" class="dialog-footer">
            <el-button @click="cancelEidt">取 消</el-button>
            <el-button type="primary" @click="onRegister('addEmpForm')">注 册</el-button>
          </span>
        </el-dialog>
      </div>
    </el-form>

  </div>

</template>

<script>

  export default {
    name: 'Login',
    data() {
      return {
        login: {
          userName: '',
          password: ''
        },
        dialogVisible: false,
        dialogTitle: '',
        areas: [],
        positions: [],
        Employee: {
          name: '',
          userName: '',
          birthday: '',
          email: '',
          phone: '',
          gender: '',
          password: '',
          area: '',
          address: [],
          posId: '',
          enabled: 1
        },
        Address: {
          address: [],
          area: ''
        },
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
          // posId: [{required: true, message: '请选择职位', trigger: 'change'}],
        }
      }
    },
    methods: {
      onLogin() { //登录
        let _this = this;
        this.postRequest( '/config/login', this.login )
          .then( res => {
            if ( res && res.status == 200 ) {
              let msg = res.data;
              if ( msg != "error" ) {
                _this.$elMessage('登录成功', 'success');
                _this.$store.commit('login', msg);
                let path = _this.$route.query.redirect;
                _this.$router.replace('/home');
                // _this.$router.push('/home')
              } else {
                _this.$elMessage('用户名或密码错误', 'warning');
                _this.login.name = '';
                _this.login.password = '';
              }
            }
          })
      },
      toRegister() {  //注册用户
        this.dialogTitle = "注册用户";
        this.dialogVisible = true;
        // this.getAddress();
        // this.$router.push('/register')
      },
      cancelEidt() { //取消注册，注册页面初始化
        this.dialogVisible = false;
        // Object.assign(this.$data, this.$options.data)
        this.emptyEmpData();
      },  //更改地址复选框选项
      handleChange(value) {
        console.log(value);
      },  //提交注册信息
      onRegister(formName) {
        let _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.postRequest('/employee/register', this.Employee).then((res) => {
              if ( res.data == 'ok' ) {
                // _this.$router.replace('/');
                _this.$elMessage('注册成功，请登录！', 'success');
                this.dialogVisible = false;
                this.emptyEmpData();
              } else {
                _this.$elMessage('有相同用户名，请重新输入', 'warning');
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      emptyEmpData() {
        this.Employee = {
          name: '',
          userName: '',
          birthday: '',
          email: '',
          phone: '',
          gender: '',
          password: '',
          area: '',
          address: [],
          posId: '',
          enabled: ''
        }
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
      }
    },
    created() {
      this.initData();
    }
  }
</script>

<style scoped>
  .Login {
    position:fixed;
    top:50%;
    left:50%;
    width:340px; /* 按需改变数值 */
    height:300px; /* 按需改变数值 */
    margin-top:-150px; /* height数值的一半 */
    margin-left:-200px; /* width数值的一半 */
    -webkit-box-shadow:0 0 3px rgba(0,0,0,.2);
    -moz-box-shadow:0 0 3px rgba(0,0,0,.2);
    box-shadow:0 0 3px rgba(0,0,0,.2);
    padding: 10px 40px 0 10px;
    border-radius:10px;
    background: rgba(255, 255, 255, 0.91);
  }

  .login_bg{
    height: 100%;
    background-image: url("img/index.gif");
  }

  h2 {
    text-align: center;
    padding-left: 40px;
    padding-bottom: 10px;
  }
</style>
