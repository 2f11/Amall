<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '审核'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      ref="dataForm"
      :model="dataForm"
      :rules="dataRule"
      label-width="80px"
      @keyup.enter.native="dataFormSubmit()"
    >
      <el-form-item
        label="状态"
        prop="status"
      >
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="1">通过</el-radio>
          <el-radio :label="-1">不通过</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button @click="visible = false">取消</el-button>
      <el-button
        type="primary"
        @click="dataFormSubmit()"
        >确定</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      dataForm: {
        commId: 0,
        status: 1
      },
      dataRule: {
        status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    init(row) {
      this.dataForm.commId = row.commId || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs.dataForm.resetFields()
        if (this.dataForm.commId) {
          this.dataForm.status = row.status
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl('/prod/prodComm'),
            method: 'put',
            data: this.$http.adornData({
              commId: this.dataForm.commId,
              status: this.dataForm.status
            })
          }).then(() => {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.visible = false
                this.$emit('refresh-data-list')
              }
            })
          })
        }
      })
    }
  }
}
</script>
