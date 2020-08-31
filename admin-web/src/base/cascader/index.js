import Component from './src/cascader'
Component.install = Vue => {
    Vue.Component(Component.name,Component)
}
export default Component
