const getters = {
  sidebar: state => state.app.sidebar,
  language: state => state.app.language,
  size: state => state.app.size,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  code: state => state.user.code,
  name: state => state.user.name,
  roles: state => state.user.roles,
  menus: state => state.user.menus,
  addRouters: state => state.permission.addRouters,
  ifAdminRole: state => state.user.ifAdminRole,
}
export default getters
