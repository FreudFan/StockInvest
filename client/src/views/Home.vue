<template>
  <div>
    <el-container>
      <!--顶部导航栏-->
      <el-header id="app-header">
        <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
          <el-radio-button :label="false">展开</el-radio-button>
          <el-radio-button :label="true">收起</el-radio-button>
        </el-radio-group>
      </el-header>
      <el-container>
        <!--侧边栏-->
        <el-aside id="app-aside">
          <!--<el-menu-->
            <!--class="menu-aside"-->
            <!--@select="handleSelect"-->
            <!--:collapse="isCollapse"-->
            <!--background-color="#20698f"-->
            <!--text-color="#fff"-->
            <!--active-text-color="#ffd04b"-->
          <!--&gt;-->
          <el-menu style="background: #ececec;width: 180px;" unique-opened router>
            <template v-for="(item,index) in this.routes" v-if="!item.hidden">
              <template v-for="(item,index) in this.routes" v-if="!item.hidden">
                <el-submenu :key="index" :index="index+''">
                  <template slot="title">
                    <i :class="item.iconCls" style="color: #20a0ff;width: 14px;"></i>
                    <span slot="title">{{item.name}}</span>
                  </template>
                  <el-menu-item width="180px"
                                style="padding-left: 30px;padding-right:0px;margin-left: 0px;width: 170px;text-align: left"
                                v-for="child in item.children"
                                :index="child.path"
                                :key="child.path">{{child.name}}
                  </el-menu-item>
                </el-submenu>
              </template>
            </template>

            <!--
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span slot="title">导航一</span>
              </template>
              <el-menu-item-group>
                <span slot="title">分组一</span>
                <el-menu-item index="Register">选项1</el-menu-item>
                <el-menu-item index="1-2">选项2</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="分组2">
                <el-menu-item index="1-3">选项3</el-menu-item>
              </el-menu-item-group>
              <el-submenu index="1-4">
                <span slot="title">选项4</span>
                <el-menu-item index="1-4-1">选项1</el-menu-item>
              </el-submenu>
            </el-submenu>
            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </el-menu-item>
            <el-menu-item index="3" disabled>
              <i class="el-icon-document"></i>
              <span slot="title">导航三</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">导航四</span>
            </el-menu-item>
            -->
          </el-menu>
        </el-aside>
        <!--主视图-->
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>
          <router-view></router-view>
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
  #app-header {
    height: 50px;
  }
  #app-aside {
    width: 200px;
    background-color: #20698f;
  }
  .menu-aside {
    width: 200px;
  }
</style>
