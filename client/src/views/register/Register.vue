<template>
  <el-container>
    <el-header>欢迎注册投研系统</el-header>
    <el-main>
    <div id="Register">
      <el-form ref="form" :model="Employee" :rules="rules" label-width="80px">
        <el-form-item label="姓名:" prop="name">
          <el-input v-model="Employee.name"></el-input>
        </el-form-item>

        <el-form-item label="密码:" prop="pass">
          <el-input type="password" v-model="Employee.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码:" prop="checkPass">
          <el-input type="password"  autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="生日:">
          <el-col>
            <el-date-picker type="date" placeholder="选择日期" v-model="Employee.birthday" ></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="电话号码:">
          <el-input v-model="Employee.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱:">
          <el-input v-model="Employee.email"></el-input>
        </el-form-item>

        <el-form-item label="性别">
          <el-select v-model="Employee.region" placeholder="请选择性别">
            <el-option label="男" value="0"></el-option>
            <el-option label="女" value="1"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <el-form ref="form" :model="Address" :rules="rules" label-width="80px">
        <el-form-item label="住址">
          <el-cascader
            expand-trigger="hover"
            :options="areas"
            v-model="Address.address"
            @change="handleChange">
          </el-cascader>
        </el-form-item>

        <el-form-item label="详细地址:">
          <el-input v-model="Address.area"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">注册</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    </el-main>
  </el-container>
</template>

<script>

  export default {
    name: "Register",
    data() {
      return {
        areas: [],
        Employee: {
          name: '',
          birthday: '',
          region: '',
          date: ''
        },
        Address: {
          address: [],
          area: ''
        },
        rules: {
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 1, max: 20, message: '长度在 1 到 20 个字符以内', trigger: 'blur' }
          ],
          region: [
            { required: true, message: '请选择活动区域', trigger: 'change' }
          ],
          date1: [
            { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
          ],
          date2: [
            { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
          ],
          desc: [
            { required: true, message: '请填写活动形式', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      handleChange(value) {
        console.log(value);
      }
    },
    created() {
      this.$axios.post('/getAllArea')
        .then((res) => {
          this.areas = res.data;
        });
    }
  }

</script>

<style scoped>
#Register {
  width:300px;
}
</style>
