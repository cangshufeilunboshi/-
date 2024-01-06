<template>
  <div>
    <el-header>
      <el-page-header @back="goBack" :content="content">
      </el-page-header>
    </el-header>
    <van-form @submit="onSubmit">
      <van-field v-model="title" name="标题" label="输入投票标题" placeholder="投票标题" :rules="[{ required: true, message: '标题' }]"
        maxlength="10" />
      <el-container>
        <el-aside width="200px">
          <el-button type="primary" icon="el-icon-circle-plus-outline" round @click="add">增加选项</el-button>

        </el-aside>
        <el-main>
          <div v-for="item in voteops" :key="item">
            <el-input placeholder="请输入内容" v-model="item.input" clearable>
            </el-input>
          </div>
        </el-main>
      </el-container>
      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>
  </div>
</template>
<script>
import Vue from 'vue'
// 2.暴露组件 export
export default {
  name: 'newvote',
  data () {
    return {
      input: '',
      voteops: [{ id: 0, input: '' }],
      radio: '1',
      num: 0,
      text: '',
      title: '',
      img: '',
      content: '发布帖子',
      fileList: ''
    }
  },
  methods: {
    add () {
      this.num++
      this.voteops.push({ id: this.num, input: '' })
      console.log(this.num + this.voteops[0].input)
    },
    handleChange (value) {
      console.log(value)
    },
    goBack () {
      history.back()
    },
    uploadImg (f) {
      let that = this
      // console.log(f.file)
      const file = f.file
      const form = new FormData()
      form.append('upfile', file)
      this.$axios.post('/upload', form)
        .then(function (response) {
          // console.log(response)
          console.log('图片链接是' + response.data)
          that.img = response.data
          // console.log(that.img)
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    onSubmit (values) {
      let that = this
      let i = 0
      while (i <= this.num) {
        // console.log(this.text)
        this.text += this.voteops[i].input + ';'
        i++
      }

      console.log(this.text)

      const formdata = new FormData()
      formdata.append('title', this.title)
      formdata.append('text', this.text)
      var username = localStorage.getItem('user')
      formdata.append('username', username)
      this.$axios.post('/newvote', formdata)
        .then(function (response) {
          console.log(response)
          if (response.data.id === 1) {
            Vue.prototype.$message({
              message: '发布成功',
              type: 'success'
            })
            that.$router.push({ name: 'mycon' })
          }
        }).catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>
