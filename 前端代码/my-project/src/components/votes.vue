<template>
  <div>
    <!-- <router-link to="getonline"></router-link> -->
    <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad"
      style="height: 100%;width: 100%;overflow-y: auto;">
      <!-- <van-cell v-for="item in list" :key="item" :title="item" /> -->
      <van-cell value="内容" @click="clickcell(car)" v-for="car in list" :key="car" is-link>
        <!-- 使用 title 插槽来自定义标题 -->
        <span class=" custom-title">{{ car.title }}</span>
        <br>
      </van-cell>
    </van-list>
  </div>
</template>
<script>
// let that = this
// var car = { name: '标题', model: 500, color: 'white' }
export default {
  name: 'votes',
  data () {
    return {
      list: '',
      loading: false,
      finished: false
      // refreshing: false
    }
  },
  beforeMount () {
    let that = this
    this.$axios.post('/getvotes')
      .then(function (response) {
        console.log(response)
        if (response.data.id === 1) {
          that.list = response.data.obj
          console.log(that.list)
        } else {
        }
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  methods: {
    clickcell (values) {
      console.log('values:' + values.id)
      this.$router.push({ name: 'newonline', params: { obj: values } }).then(function () {
        // console.log(111111)
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    onLoad () {
      // console.log(111)
      setTimeout(() => {
        // if (this.refreshing) {
        //   // this.message.obj = []
        //   this.refreshing = false
        // }
        for (let i = 0; i < 10; i++) {
          this.list.push(this.list.length + 1)
        }
        this.loading = false
        if (this.list.length >= 40) {
          this.finished = true
        }
      }, 1000)
    }
    // onRefresh () {
    //   // 清空列表数据
    //   this.finished = false
    //   // 重新加载数据
    //   // 将 loading 设置为 true，表示处于加载状态
    //   this.loading = true
    //   this.onLoad()
    // }
  }
}
</script>
<style></style>
