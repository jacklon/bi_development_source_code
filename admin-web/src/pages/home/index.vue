<template>
  <div class="page-home">
    <mheader @collapsed="onCollapsed" :collapsed="isCollapsed"></mheader>
    <div style="display:flex;height: 100%">
      <div class="home-side-bar">
        <menu-list :collapsed="isCollapsed"></menu-list>
      </div>
      <div class="clearfix my-page-list">

        <router-view class="sub-page"/>
      </div>
    </div>

  </div>
</template>

<script>
  import Menu from '../menu/menu'
  import mheader from '../m-header/m-header'
	export default {
    data(){
      return {
        isCollapsed: false
      }
    },
    components:{
      MenuList: Menu,
      mheader: mheader
    },
    methods:{
      onCollapsed(){
        this.isCollapsed = !this.isCollapsed
      }
    }
	}
</script>

<style lang="scss" scoped>
  .page-home {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    background: rgb(240, 243, 244);
    .home-side-bar{
      overflow-y:auto;
      background-color:rgb(0,0,43);
      border-right: 1px solid rgb(0,0,43);
      z-index: 2;
      height: 100%;
    }
    .my-page-list{
      flex: 1;
      height: 100%;
      overflow: hidden;
      .sub-page{
        height: 100%;
      }
    }
  }

</style>
