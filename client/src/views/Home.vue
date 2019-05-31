<template>
  <div>
    <el-container>
      <!--顶部导航栏-->
      <el-header id="app-header">
        <img src="/static/pic/logo.png" style="z-index: 99; margin-top: -12px" height="80">
        <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
          <!--<el-radio-button :label="false">展开</el-radio-button>-->
          <!--<el-radio-button :label="true">收起</el-radio-button>-->
        </el-radio-group>
        <el-dropdown @command="handleCommand" style="float: right; padding-right: 30px">
          <span class="el-dropdown-link home_userinfo" style="display: flex;align-items: center">
            <p>{{user.name}}</p>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人中心</el-dropdown-item>
            <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container id="app-container">
        <!--侧边栏-->
        <el-aside id="app-aside" >
          <el-menu
          class="menu-aside"
          @select="handleSelect"
          :collapse="isCollapse"
          background-color="#f9f9f9"
          text-color="#666666"
          active-text-color="#666666"
          router
          style="width: 200px;"
          >
            <!--<template v-for="(item,index) in routes" v-if="!item.hidden">-->
              <template v-for="(item,index) in routes" v-if="!item.hidden">
                <el-submenu :key="index" :index="index+''">
                  <template slot="title">
                    <i :class="item.iconCls" style="color: #000; "></i>
                    <span slot="title" class="tree-title">{{item.name}}</span>
                  </template>
                  <el-menu-item
                    v-for="child in item.children"
                    :index="child.path"
                    :key="child.path"
                    class="tree-child">{{child.name}}
                  </el-menu-item>
                </el-submenu>
              </template>
            <!--</template>-->
          </el-menu>
        </el-aside>
        <!--主视图-->
        <el-main id="app-main">
          <el-breadcrumb class="main-container">
            <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-text="$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>
          <div id="app-view">
            <router-view></router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: "Home",
    data() {
      return {
        isCollapse: false
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(keyPath);
      },
      handleCommand(cmd){
        var _this = this;
        if (cmd == 'logout') {
          this.$confirm('注销登录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            _this.getRequest("/login/logout");
            _this.$store.commit('logout');
            _this.$router.replace('/');
          }).catch(() => {
            _this.$message({
              type: 'info',
              message: '取消'
            });
          });
        }
      }
    },
    mounted() {
      this.changeWinSize();
    },
    computed: {
      user() {
        return this.$store.state.user;
      },
      routes() {
        return this.$store.state.routes;
      }
    }
  }
</script>

<style scoped>
  #app-view {
    background-color: #fff;
  }
  #app-header {
    height: 50px;
    background-color: #7dcdd1;
  }
  #app-aside {
    /*width: 200px;*/
    /*background-color: #20698f;*/
  }
  #app-container {
    background-color: #f9f9f9;
  }
  .menu-aside {
    /*width: 64px;*/
  }
  #app-main {
    background-color: #f5f5f5;
  }
  .main-container {
    padding-bottom: 15px;
  }
  .tree-title {
    font-size: 14px;
    font-weight: 700;
  }
  .tree-child {
    font-size: 14px;
  }
  .home_userinfo {
    color: #fff;
    cursor: pointer;
  }
</style>
