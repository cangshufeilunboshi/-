<template>
  <div>
    <el-header>
      <el-page-header @back="goBack" :content="content">
      </el-page-header>
    </el-header>
    <van-form @submit="onSubmit">
      <van-checkbox-group v-model="result" direction="horizontal" :max="1" style="margin-left: 30%;">
        <van-checkbox name="1" style="margin-top: 10px;">普通帖子</van-checkbox>
        <van-checkbox name="2" style="margin-top: 10px;" v-show="isad">公告</van-checkbox>
      </van-checkbox-group>
      <van-field v-model="title" name="标题" label="输入标题" placeholder="标题" :rules="[{ required: true, message: '标题' }]"
        maxlength="10" />
      <van-field v-model="text" name="正文" label="正文" placeholder="正文" :rules="[{ required: true, message: '正文' }]" />

      <el-upload class="avatar-uploader" action="#" :show-file-list="true" :limit="1" list-type="picture"
        :http-request="uploadImg">
        <el-button size="small" type="primary">点击上传</el-button>
        <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
      </el-upload>

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
  name: 'newart',
  data () {
    return {
      text: '',
      title: '',
      img: '',
      result: [],
      isad: false,
      content: '发布帖子',
      fileList: ''
    }
  },
  beforeMount () {
    let that = this
    var can = localStorage.getItem('can')
    if (can === '3') {
      that.isad = true
    }
  },
  methods: {

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
      const formdata = new FormData()
      formdata.append('title', this.title)
      formdata.append('text', this.text)
      formdata.append('img', this.img)
      formdata.append('can', this.result[0])
      var username = localStorage.getItem('user')
      formdata.append('username', username)
      console.log(formdata)
      this.$axios.post('/newart', formdata)
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
