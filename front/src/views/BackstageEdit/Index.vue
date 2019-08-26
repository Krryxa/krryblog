<template>
  <main>
    <section class="add-blog">
      <Breadcrumb>
        <BreadcrumbItem to="/">博客首页</BreadcrumbItem>
        <BreadcrumbItem :to="{name: 'list'}">后台中心</BreadcrumbItem>
        <BreadcrumbItem>博客编辑页</BreadcrumbItem>
      </Breadcrumb>
      <Form>
        <input
          type="text"
          maxlength="36"
          class="blog-title"
          v-model.trim="title"
          placeholder="博客标题..."
          style="width: 100%"
        >
        <mavon-editor
          :value="markdownDesc"
          :tabSize="2"
          ref="mdEdit"
          @imgAdd="addImg"
          @imgDel="delImg"
          @save="markdownSave"
          codeStyle="vs2015"
          placeholder="编辑内容，支持 Markdown"
          class="detail-article"
        ></mavon-editor>
        <FormItem label="博客描述：" style="padding-top: 42px">
          <Input
            v-model.trim="description"
            :autosize="{minRows: 4,maxRows: 10}"
            style="width: 460px"
            type="textarea"
            :rows="4"
            placeholder="为博客的写上简单描述吧~~"
          />
        </FormItem>
        <!-- upload image -->
        <uploadImg
          :id="id"
          :defaultList="defaultUploadList"
          :uploadImgUrl="uploadImgUrl"
          :imgName="imgName"
          @changeImg="changeImg"
        ></uploadImg>
        <FormItem label="分类存档：">
          <RadioGroup v-model="classifyId">
            <Radio :label="item.id" v-for="(item, index) in classifyList" :key="index">
              <span>{{item.name}}</span>
            </Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="个性标签：">
          <Input
            v-model.trim="label"
            placeholder="为博客添加标签吧~~ 英文逗号 , 分割"
            :maxlength="60"
            style="width: 360px"
          />
        </FormItem>
        <FormItem label="是否发布：">
          <i-switch size="large" v-model="statusFlag">
            <span slot="open">ON</span>
            <span slot="close">OFF</span>
          </i-switch>
        </FormItem>
      </Form>
      <div class="blog-btn">
        <Button type="primary" size="large" @click="beforeCommit">保存</Button>
        <Button style="margin-left: 50px" size="large" @click="back">返回</Button>
      </div>
    </section>
    <my-footer></my-footer>
  </main>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import '@/assets/css/markdown.css'
import '@/assets/css/github.css'
import {
  getEditBlogDetail,
  updateBlog,
  addBlog,
  getBlogCount,
  uploadContent,
  deleteFile
} from '@/service'
import { loading } from '@/mixins/loading'
export default {
  mixins: [loading],
  data() {
    return {
      id: this.$route.params.id || 0,
      title: '',
      markdownDesc: '',
      translateDesc: '',
      description: '',
      imgName: '',
      uploadImgUrl: '',
      classifyId: 1,
      label: '',
      blogCount: 0,
      statusFlag: true,

      defaultUploadList: []
    }
  },
  computed: {
    status() {
      return +this.statusFlag
    },
    // 从接口查询出分类
    classifyList() {
      return this.$store.getters['blog/classify']
    },
    // 获取用户 ID
    userId() {
      return this.$store.getters['user/id']
    }
  },
  created() {
    if (this.id) {
      this.getBlogInfo()
    } else {
      // 新增查博客总数，用于 markdown 上传图片时文件夹的命名id
      this.getBlogCount()
    }
  },
  methods: {
    async getBlogInfo() {
      // get blog when edit
      document.title = document.title.replace('新增', '编辑')

      this.imgName = this.$route.params['imgName']
      this.uploadImgUrl = this.$route.params['uploadImgUrl']
      this.defaultUploadList = [
        {
          name: this.imgName,
          url: window.location.origin + '/krryblog/' + this.uploadImgUrl
        }
      ]

      let res = await getEditBlogDetail(this.id)
      let status = res.status
      let blogObj = res.data
      // 404 的标题在 axios 拦截器已经定义
      if (status !== 404) {
        this.title = blogObj['title']
        this.markdownDesc = blogObj['content_md']
        this.statusFlag = !!blogObj['status']
        this.description = blogObj['description']
        this.classifyId = blogObj['classifyId']
        this.label = blogObj['label']
      }
    },
    async getBlogCount() {
      this.blogCount = await getBlogCount()
    },

    async addImg(pos, $file) {
      let formData = new FormData()
      formData.append('imgFile', $file)
      let id = this.id || this.blogCount + 1
      let result = await uploadContent(id, formData)
      this.$refs.mdEdit.$img2Url(pos, result.url)
    },
    async delImg(name) {
      let id = this.id || this.blogCount + 1
      let res = await deleteFile({ filePath: `upload/content/${id}/${name}` })
      if (res === 'success') {
        this.$Message.success('删除成功！')
      } else {
        this.$Message.error('删除失败！')
      }
    },

    // markdown save
    markdownSave(value, render) {
      this.markdownDesc = value
      this.translateDesc = render
    },

    // from child
    changeImg(name, url) {
      this.imgName = name
      this.uploadImgUrl = url
    },

    // save and commit
    beforeCommit() {
      let markdownSaveBtn = document.getElementsByClassName('fa-mavon-floppy-o')
      markdownSaveBtn[0].click()
      if (this.title === '') {
        this.$Message.warning('先输入博客标题哦~~')
      } else if (this.translateDesc.trim() === '') {
        this.$Message.warning('先输入博客内容哦~~')
      } else if (this.description === '') {
        this.$Message.warning('先简单描述一下博客哦~~')
      } else if (this.uploadImgUrl === '') {
        this.$Message.warning('先上传封面图片哦~~')
      } else {
        let reqData = this.convertParams()
        this.commit(reqData)
      }
    },
    async commit(reqData) {
      console.log(reqData)
      this.openLoading('正在保存~~')
      if (this.id > 0) {
        // is edit
        console.log('是编辑，id：' + this.id)
        reqData = Object.assign({}, reqData, { id: this.id })
        console.log(reqData)
        let msg = await updateBlog(reqData)
        if (msg === 'success') {
          this.$router.push(`/${this.id}`)
        } else {
          this.$Message.error('出错了呢，修改失败...')
        }
      } else {
        let id = await addBlog(reqData)
        console.log('保存的id：' + id)
        this.$router.push(`/${id}`)
      }
      this.$Spin.hide()
    },
    convertParams() {
      return {
        userId: this.userId,
        title: this.title,
        content_md: this.markdownDesc,
        content_hm: this.translateDesc,
        description: this.description,
        imageName: this.imgName,
        image: this.uploadImgUrl,
        classifyId: this.classifyId,
        label: this.label,
        status: this.status
      }
    },
    back() {
      window.history.back(-1)
    }
  },
  components: {
    uploadImg: () => import('./UploadImg'),
    mavonEditor
  }
}
</script>

<style lang='scss' scoped>
section {
  animation: fadeIn 0.6s linear;
  padding: 50px;

  .ivu-breadcrumb {
    padding-bottom: 30px;
  }

  .blog-title {
    height: 40px;
    outline: none;
    padding: 0 10px;
    font-size: 14px;
    text-align: center;
    border: none;
    border-top: 1px solid #ddd;
    color: #666;
  }

  .v-note-wrapper {
    z-index: 1009;
  }

  .ivu-form-item {
    padding-top: 24px;
  }

  .blog-btn {
    text-align: center;

    button {
      width: 160px;
    }
  }

  .ivu-radio-wrapper {
    margin-right: 18px;
    cursor: url(../../assets/pic/cursor.cur), pointer !important;
  }
}
</style>
<style lang="scss">
.add-blog {
  .v-note-wrapper .v-note-op .v-left-item .op-icon,
  .op-icon input,
  .ivu-radio,
  .ivu-radio-input {
    cursor: url(../../assets/pic/cursor.cur), pointer !important;
  }

  .content-input-wrapper {
    padding: unset !important;

    textarea {
      font-size: 14px !important;
      padding: 8px 25px 15px 25px;
    }
  }

  .ivu-form-item {
    .ivu-form-item-label {
      font-size: 14px;
    }
  }

  .v-note-wrapper {
    .v-note-show {
      .v-show-content {
        ul li {
          list-style: initial !important;
        }
      }
    }
    .v-note-img-wrapper {
      img {
        cursor: zoom-out;
        max-width: 86%;
        max-height: 86%;
      }
    }
  }

  .ivu-upload-list-remove {
    display: none;
  }
}
</style>
