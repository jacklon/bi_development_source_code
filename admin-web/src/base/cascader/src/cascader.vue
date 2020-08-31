<template>
    <div class="cascader" :style="{'height': height + 'px','width': width + 'px'}">
        <el-cascader clearable ref="belongClass" :props="defaultCascadeProps"
                     :options="options"
                     :value="value" expand-trigger="hover" @change="onChange"/>
    </div>


</template>

<script>
export default {
    name: 'YCascader',
    data(){
        return {
            defaultCascadeProps: {
                children: 'children',
                label: 'name',
                value: "id",
                checkStrictly: true
            },
        }
    },
    props: {
        options: Array,
        value: Number,
        height: {
            type: String,
            default: '30'
        },
        width: {
            type: String,
            default: null
        },
        // defaultCascadeProps: {
        //     type: Object,
        //     default: null,
        // },
    },

    methods:{

        onChange(e){
            this.$emit('change', e)
        }
    }
}
</script>

<style>
    .cascader {
        width: 100%;
    }
    .el-cascader {
        width: 100%;
    }
    .el-cascader .el-input .el-input__inner {
        background: rgb(28, 34, 43);
        color: #bcc9d4;
        height: 30px;
        font-size: 12px;
        width: 100%;

    }
    .el-cascader-menu__list {
        background: rgb(28, 34, 43);
        color: #bcc9d4;
        height: 30px;
        width: 200px;
    }

</style>
