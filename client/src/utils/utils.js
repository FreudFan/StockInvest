import {getRequest} from "@/utils/api";

export const initMenu = (router, store)=> {
  if (store.state.routes.length > 0) {
    return;
  }
  getRequest("/config/getSysMenu").then( resp => {
    if (resp && resp.status == 200) {
      let fmtRoutes = formatRoutes(resp.data);
      router.addRoutes(fmtRoutes);
      store.commit('initMenu', fmtRoutes);
      // store.dispatch('connect');
    }
  })
}

export const formatRoutes = (routes) => {
  let fmRoutes = [];
  routes.forEach( router => {
    let {
      path,
      component,
      name,
      meta,
      iconCls,
      children
    } = router;
    if (children && children instanceof Array) {
      children = formatRoutes(children);
    }
    let fmRouter = {
      path: path,
      component(resolve){
        if (component.startsWith("Home")) { //首页
          require(['@/views/' + component + '.vue'], resolve)
        } else if (component.startsWith("Emp")) {   //用户管理
          require(['@/views/employee/' + component + '.vue'], resolve)
        } else if (component.startsWith("Res")) {   //注册
          require(['@/views/register/' + component + '.vue'], resolve)
        } else if (component.startsWith("Fun")) {   //基本面
          require(['@/views/fundamental/' + component + '.vue'], resolve)
        } else if (component.startsWith("Sys")) {   //系统管理
          require(['@/views/system/' + component + '.vue'], resolve)
        }
      },
      name: name,
      iconCls: iconCls,
      meta: meta,
      children: children
    };
    fmRoutes.push(fmRouter);
  })
  return fmRoutes;
}

