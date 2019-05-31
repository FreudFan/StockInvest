<template>
    <div id="menuPage">
      <el-container>
        <el-aside id="pageAside">
          <div class="tree">
            <el-input
              placeholder="输入关键字进行过滤"
              size="mini"
              v-model="menuFilterText"
              style="width: 90%; padding: 10px 10px 15px;"
            >
            </el-input>
            <el-tree
              :data="org"
              :props="defaultProps"
              @node-click="handleOrgNode"
              :filter-node-method="orgFilterNode"
              v-loading="orgTreeLoading"
              default-expand-all
              ref="orgTree"
            >
              <span slot-scope="{ node, data }">
                <i :class="data.icon"></i>
                <span class="tree-row">{{node.label}}</span>
              </span>
            </el-tree>
          </div>
        </el-aside>
        <el-main id="pageMain">

          <el-container>
            <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
              <div style="display: inline; margin-left: 20px;">
                <el-button type="primary" size="mini" style="margin-left: 5px"
                           @click="save">保存
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 5px"
                           >继承权限（清空自身权限）
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 5px"
                           >继承权限（保留自身权限）
                </el-button>
              </div>
              <div style="margin-left: 20px; margin-right: 18px; display: inline">
                <el-input
                  placeholder="输入关键字进行过滤"
                  v-model="menuFilterText"
                  size="mini"
                  style="width: 100%"
                  default-expand-all
                >
                </el-input>
              </div>
            </el-header>
            <el-main style="padding-top: 0px;">
              <el-tree
                :data="menu"
                show-checkbox
                node-key="objid"
                :filter-node-method="menuFilterNode"
                ref="menuTree"
                default-expand-all
                :props="defaultProps"
                v-loading="orgMenuLoading"
                style="padding-top: 10px"
              >
                <span slot-scope="{ node, data }">
                  <i :class="data.iconCls"></i>
                  <span class="tree-row">{{node.label}}</span>
                </span>
              </el-tree>
            </el-main>
          </el-container>

        </el-main>
      </el-container>
    </div>
</template>

<script>
    export default {
      name: "SysMenuAuth",
      watch: {
        menuFilterText(val) {
          this.$refs.menuTree.filter(val);
        },
        orgFilterText(val) {
          this.$refs.orgTree.filter(val);
        }
      },
      methods: {
        // 保存菜单权限参数：type: {0:保存, 1:继承权限(清空), 2:继承权限(保留)}
        save() {
          let checked = this.$refs.menuTree.getCheckedKeys();
          this.selectNode.type = 0;
          this.selectNode.node = checked;
          this.selectNode.role = this.role;
          let _this = this;
          this.orgMenuLoading = true;
          this.postRequest('/org/setMenu', this.selectNode).then((res) => {
            _this.$refs.menuTree.setCheckedNodes(res.data);
            _this.orgMenuLoading = false;
          });
        },
        handleOrgNode(data) {
          this.role.objid = data.objid;
          this.role.name = data.name;
          this.role.clazzType = data.clazzType;
          this.setMenuTreeNode();
          console.log(data);
        },
        setMenuTreeNode() {
          let _this = this;
          this.orgMenuLoading = true;
          this.postRequest('/org/getMenuByOrg', this.role).then((res) => {
            _this.orgMenuLoading = false;
            _this.$refs.menuTree.setCheckedNodes(res.data);
          });
        },
        orgFilterNode(value, data) {
          if (!value) return true;
          return data.name.indexOf(value) !== -1;
        },
        menuFilterNode(value, data) {
          if (!value) return true;
          return data.name.indexOf(value) !== -1;
        },
        initData() {
          this.getOrgTree();
          this.getMenuTree();
        },
        getMenuTree() {
          let _this = this;
          this.orgMenuLoading = true;
          this.postRequest('/org/getMenuByOrg', {}).then((res) => {
            _this.orgMenuLoading = false;
            _this.menu = res.data;
          });
        },
        getOrgTree() {
          let _this = this;
          this.orgTreeLoading = true;
          this.postRequest('/org/getEmpByUserName').then((res) => {
            _this.orgTreeLoading = false;
            _this.org = res.data;
          });
        }
      },
      mounted() {
        this.changeWinSize();
        this.initData();
      },
      data() {
        return {
          org: [],
          menu: [],
          orgFilterText: '',
          menuFilterText: '',
          role: {
            objid: '',
            name: '',
            clazzType: '',
            checkMenu: [],
          },
          defaultProps: {
            children: 'children',
            label: 'name'
          },
          selectNode: {
            type: '',
            node: [],
            role: []
          },
          orgTreeLoading: false,
          orgMenuLoading: false
        }
      }
    }
</script>

<style scoped>
  #menuPage {
    background-color: #f5f5f5;
  }
  #pageAside {
    width: 254px;
    background-color: #fff;
  }
  #pageMain {
    margin-left: 20px;
    background-color: #fff;
  }
  .el-tree {
    min-width: 100%;
    display:inline-block !important;
  }
  .tree {
    padding: 10px;
  }
  .el-tree-node:focus > .el-tree-node__content {
    background-color: #3687ac !important;
    color: #3687ac;
    font-weight: 600;
  }
  .tree-row {
    padding-left: 4px;
    font-size: 18px;
  }
</style>
