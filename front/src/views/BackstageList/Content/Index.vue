<template>
  <section class="list">
    <h1>~Wellcome {{ userName }}~</h1>
    <router-link :to="{ name: 'edit' }">
      <Button type="success" class="add-button">Add</Button>
    </router-link>
    <a href="javascript:void(0)" @click="Logout" class="modify-buttom"
      >Logout</a
    >
    <a href="javascript:void(0)" @click="showDialog" class="modify-buttom"
      >Modify Personal Information</a
    >
    <router-link :to="{ name: 'music' }" class="modify-buttom"
      >Go to music</router-link
    >
    <Modal
      width="400"
      v-model="showModel"
      title="Modifying personal information"
    >
      <Form
        :model="userForm"
        :rules="rule"
        label-position="top"
        class="user-form"
        ref="userForm"
      >
        <FormItem label="User Name" prop="newName">
          <i-input
            v-model="userForm.newName"
            autocomplete="off"
            :maxlength="10"
            width="100"
            placeholder="Please enter your name..."
          ></i-input>
        </FormItem>
        <p class="text-right">
          <a href="javascript:void(0)" @click="showPW = !showPW">{{
            showPWmsg
          }}</a>
        </p>
        <template v-if="showPW">
          <FormItem label="Original Password" prop="originPW">
            <i-input
              v-model="userForm.originPW"
              type="password"
              autocomplete="off"
              :maxlength="20"
              placeholder="Please enter your original password..."
            ></i-input>
          </FormItem>
          <FormItem label="New Password" prop="newPW">
            <i-input
              v-model="userForm.newPW"
              type="password"
              autocomplete="off"
              :maxlength="20"
              placeholder="Please enter your new password..."
            ></i-input>
          </FormItem>
          <FormItem label="Repeat The New Password" prop="reNewPW">
            <i-input
              v-model="userForm.reNewPW"
              type="password"
              autocomplete="off"
              :maxlength="20"
              placeholder="Please repeat enter your new password..."
            ></i-input>
          </FormItem>
        </template>
      </Form>
      <div slot="footer">
        <Button type="warning" size="large" @click="cancel">Cancel</Button>
        <Button type="primary" size="large" @click="confirmUser"
          >Confirm</Button
        >
      </div>
    </Modal>
    <Table border stripe :columns="columns" :data="blogList"></Table>
  </section>
</template>

<script>
import { updateUser, updateBlogNoTime, getLogout } from '@/service'
import { loading } from '@/mixins/loading'
import { PERSON_MODIFY_RULE } from '@/views/service/rules'
export default {
  props: {
    blogList: {
      type: Array
    }
  },
  mixins: [loading],
  data() {
    return {
      showModel: false,
      showPW: false,
      userForm: {
        newName: '',
        originPW: '',
        newPW: '',
        reNewPW: ''
      },
      rule: PERSON_MODIFY_RULE.call(this),
      columns: [
        {
          title: 'ID',
          width: 50,
          key: 'id'
        },
        {
          title: 'Title',
          width: 151,
          key: 'title',
          render: (h, params) => {
            return h(
              'router-link',
              {
                props: {
                  to: `/${params.row.id}`
                }
              },
              params.row.title
            )
          }
        },
        {
          title: 'Archive',
          width: 97,
          key: 'classify',
          render: (h, params) => {
            return h(
              'router-link',
              {
                props: {
                  to: `/category/${params.row.classifyId}`
                }
              },
              params.row.classify
            )
          }
        },
        {
          title: 'Author',
          width: 76,
          key: 'userName'
        },
        {
          title: 'Label',
          width: 111,
          key: 'label',
          render: (h, params) => {
            let labelList = params.row.label.split(',')
            let len = labelList.length
            return h(
              'div',
              {},
              labelList &&
                labelList[0] &&
                labelList.map((ele, index) => {
                  return h(
                    'router-link',
                    {
                      class: { 'lable-name': true, 'only-one': len === 1 },
                      props: {
                        to: `/tag/${ele}`
                      }
                    },
                    [h('span', {}, ele)]
                  )
                })
            )
          }
        },
        {
          title: 'Clicks \xa0\xa0/Comment',
          width: 94,
          key: 'hitComment'
        },
        {
          title: 'CreateTime',
          width: 104,
          key: 'createTime'
        },
        {
          title: 'UpdateTime',
          width: 107,
          key: 'updateTime'
        },
        {
          title: 'Status',
          width: 79,
          key: 'status',
          align: 'center',
          render: (h, params) => {
            return h(
              'i-switch',
              {
                props: {
                  value: !!params.row.status
                },
                on: {
                  'on-change': val => {
                    this.setStatus(params.row.id, val)
                  }
                }
              },
              [
                h('Icon', {
                  props: {
                    type: 'md-checkmark'
                  },
                  slot: 'open'
                }),
                h('Icon', {
                  props: {
                    type: 'md-close'
                  },
                  slot: 'close'
                })
              ]
            )
          }
        },
        {
          title: 'Top',
          width: 79,
          key: 'isTop',
          align: 'center',
          render: (h, params) => {
            return h(
              'i-switch',
              {
                props: {
                  value: !!params.row.isTop
                },
                on: {
                  'on-change': val => {
                    this.setIsTop(params.row.id, val)
                  }
                }
              },
              [
                h('Icon', {
                  props: {
                    type: 'md-checkmark'
                  },
                  slot: 'open'
                }),
                h('Icon', {
                  props: {
                    type: 'md-close'
                  },
                  slot: 'close'
                })
              ]
            )
          }
        },
        {
          title: 'Operation',
          key: 'action',
          width: 130,
          align: 'center',
          render: (h, params) => {
            return h('div', {}, [
              h(
                'router-link',
                {
                  props: {
                    to: {
                      name: 'edit',
                      params: {
                        id: params.row.id,
                        imgName: params.row.imageName,
                        uploadImgUrl: params.row.image
                      }
                    }
                  }
                },
                [
                  h(
                    'Button',
                    {
                      props: {
                        type: 'primary',
                        size: 'small'
                      },
                      style: {
                        marginRight: '10px'
                      }
                    },
                    'MOD'
                  )
                ]
              ),
              h(
                'Button',
                {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.beforeRemove(params.row['id'], params.row['title'])
                    }
                  }
                },
                'DEL'
              )
            ])
          }
        }
      ]
    }
  },
  computed: {
    userName() {
      return this.$store.getters['user/username']
    },
    userId() {
      return this.$store.getters['user/id']
    },
    showPWmsg() {
      return this.showPW ? 'Pick up, No modify password' : 'Modify password'
    }
  },
  methods: {
    showDialog() {
      this.showModel = true
      this.userForm['newName'] = this.userName
    },
    Logout() {
      this.$Modal.confirm({
        title: 'notification~',
        content: `<p>Do you want to logout ？</p>`,
        okText: 'Confirm',
        cancelText: 'Cancel',
        onOk: async () => {
          await getLogout()
          this.$store.dispatch('user/CLEARUSER')
          sessionStorage.clear()
          this.$router.push('/')
        }
      })
    },
    // 确认修改个人信息
    confirmUser() {
      this.$refs['userForm'].validate(async valid => {
        if (valid) {
          this.openLoading('Modifying~~')
          let reqData = {
            id: this.userId,
            name: this.userForm['newName']
          }
          if (this.showPW) {
            // 如果修改密码
            reqData = Object.assign({}, reqData, {
              originWord: this.userForm['originPW'],
              password: this.userForm['newPW']
            })
          }
          let res = await updateUser(reqData)
          this.$Spin.hide()
          if (res !== 'success') {
            this.$Message.error(res)
          } else {
            // 修改成功，更改 session 和 vuex 中的数据
            sessionStorage.setItem('username', reqData.name)
            this.$store.dispatch('user/SETUSERNAME', reqData.name)
            this.$Message.success('Modified success!')
            this.cancel()
          }
        } else {
        }
      })
    },
    cancel() {
      this.showModel = false
      this.$refs['userForm'].resetFields()
      this.showPW = false
    },
    // 设置发布状态
    async setStatus(id, val) {
      console.log(id, val)
      this.openLoading('Modifying~~')
      let reqData = {
        id: id,
        status: val ? 1 : 0
      }
      let msg = await updateBlogNoTime(reqData)
      if (msg === 'success') {
        // 同步已查询出来的数据
        this.$emit('handleChangeBlog', reqData)
        this.$Message.success('Modified success!')
      } else {
        this.$Message.error('Error, Failure to Modify...')
      }
      this.$Spin.hide()
    },
    // 设置置顶状态
    async setIsTop(id, val) {
      console.log(id, val)
      let reqData = {
        id: id,
        isTop: val ? 1 : 0
      }
      let loadMsg = reqData.isTop ? 'Topping~~' : 'No topping~~'
      this.openLoading(loadMsg)
      let msg = await updateBlogNoTime(reqData)
      if (msg === 'success') {
        // 同步已查询出来的数据
        this.$emit('handleChangeBlog', reqData)
        this.$Message.success('Modified success!')
      } else {
        this.$Message.error('Error, Failure to Modify...')
      }
      this.$Spin.hide()
    },
    beforeRemove(id, title) {
      console.log('删除id：' + id)
      this.$Modal.confirm({
        title: 'notification~',
        content: `<p>Do you want to delete the blog “${title}” ？</p>`,
        okText: 'Confirm',
        cancelText: 'Cancel',
        onOk: () => {
          this.remove(id)
        }
      })
    },
    async remove(id) {
      this.openLoading('Deleting~~')
      let reqData = {
        id: id,
        isDelete: 1
      }
      let msg = await updateBlogNoTime(reqData)
      if (msg === 'success') {
        // refresh local data
        this.$emit('deleteBlog', id)
        this.$Message.success('Delete successful!')
      } else {
        this.$Message.error('Error, Failure to delete...')
      }
      this.$Spin.hide()
    }
  },
  components: {}
}
</script>

<style lang="scss" scoped>
section {
  animation: fadeIn 0.6s linear;
  padding: 90px 0 0px;
  width: 1080px;
  margin: 0 auto;

  h1 {
    text-align: center;
    color: #ff5050;
    text-shadow: 1px 1px 2px #adadad;
    font-size: 36px;
  }

  .add-button {
    float: left;
    width: 80px;
    margin-top: -15px;
  }

  .modify-buttom {
    float: right;
    margin-left: 18px;
  }

  .ivu-table-wrapper {
    margin-top: 28px;
  }
}
.text-right {
  text-align: right;
}
</style>
<style lang="scss">
.list {
  .ivu-table-wrapper {
    .ivu-table-tbody {
      .ivu-table-cell {
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;

        .lable-name {
          display: inline-block;

          &:not(:last-child) {
            margin-right: 10px;
          }
          &.only-one {
            margin-right: 0;
          }
        }
      }
    }

    .ivu-checkbox-input {
      cursor: url(../../../assets/pic/cursor.cur), pointer !important;
    }
  }
}
.user-form {
  padding: 8px 20px;
}
</style>
