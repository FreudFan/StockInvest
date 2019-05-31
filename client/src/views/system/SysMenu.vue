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
            :data="menuTree"
            :props="defaultProps"
            @node-click="handleMenuNode"
            :filter-node-method="menuFilterNode"
            v-loading="menuTreeLoading"
            default-expand-all
            ref="menuTree"
          >
              <span slot-scope="{ node, data }">
                <i :class="data.iconCls"></i>
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
                         @click="addChild">新增
              </el-button>
            </div>
          </el-header>
          <el-main style="padding-top: 0px;">
            <el-table
              :data="menuTable"
              v-loading="tableLoading"
              style="width: 100%;">
              <el-table-column
                prop="url"
                label="url"
                align="center"
                width="80">
              </el-table-column>
              <el-table-column
                prop="path"
                label="path"
                align="center"
                min-width="150">
              </el-table-column>
              <el-table-column
                prop="component"
                label="component"
                align="center"
                min-width="150">
              </el-table-column>
              <el-table-column
                prop="name"
                label="name"
                align="center"
                width="150">
              </el-table-column>
              <el-table-column
                prop="iconCls"
                label="iconCls"
                align="center"
                min-width="150">
              </el-table-column>
              <el-table-column
                label="操作"
                align="center"
                width="130">
                <template slot-scope="scope">
                  <el-button @click="showEditMenu(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                             size="small">编辑
                  </el-button>
                  <el-button @click="deleteMenu(scope.row)" type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
        </el-container>

      </el-main>
    </el-container>
  </div>
</template>

<script>
    export default {
      name: "SysMenu",
      watch: {
        menuFilterText(val) {
          this.$refs.menuTree.filter(val);
        }
      },
      methods: {
        initData() {
          this.getMenuTree();
          this.getMenuChild();
        },
        getMenuTree() {
          let _this = this;
          this.menuTreeLoading = true;
          this.postRequest('/org/getMenuByOrg', {}).then((res) => {
            _this.menuTreeLoading = false;
            let data = res.data;
            _this.menuTree = data;
          });
        },
        menuFilterNode(value, data) {
          if (!value) return true;
          return data.name.indexOf(value) !== -1;
        },
        handleMenuNode(data) {
          this.menuId = data.objid;
          this.getMenuChild();
        },
        getMenuChild() {
          let _this = this;
          this.tableLoading = true;
          this.postRequest('/org/getChildMenu', this.menuId).then((res) => {
            _this.menuTable = res.data;
            _this.tableLoading = false;
          });
        },
        addChild() {

        },
        showEditMenu(row) {

        },
        deleteMenu(row) {

        },
      },
      mounted() {
        this.initData();
      },
      data() {
        return {
          menuId: 1,
          menuFilterText: '',
          menuTreeLoading: false,
          menuTree: [],
          defaultProps: {
            children: 'children',
            label: 'name'
          },
          tableLoading: false,
          menuTable: []
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
    color: #657390;
    font-weight: 400;
  }
  .tree-row {
    padding-left: 4px;
    font-size: 18px;
  }
</style>
