<template>
  <div class="mod-prod-comm">
    <avue-crud
      ref="crud"
      :page="page"
      :data="dataList"
      :table-loading="dataListLoading"
      :option="tableOption"
      @search-change="searchChange"
      @on-load="getDataList"
    >
      <template
        slot="menu"
        slot-scope="scope"
      >
        <el-button
          v-if="isAuth('prod:comm:update')"
          type="primary"
          size="small"
          icon="el-icon-edit"
          @click="showUpdateHandle(scope.row)"
          >审核</el-button
        >
        <el-button
          v-if="isAuth('prod:comm:delete')"
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="deleteHandle(scope.row.commId)"
          >删除</el-button
        >
      </template>
    </avue-crud>
    <add-or-update
      v-if="addOrUpdateVisible"
      ref="addOrUpdate"
      @refresh-data-list="getDataList"
    />
  </div>
</template>

<script>
import { tableOption } from '@/crud/prod/prodComm'
import AddOrUpdate from './prod-comm-add-or-update'
export default {
  components: {
    AddOrUpdate
  },
  data() {
    return {
      dataList: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10 // 每页显示多少条
      },
      dataListLoading: false,
      tableOption: tableOption,
      addOrUpdateVisible: false
    }
  },
  methods: {
    getDataList(page, params) {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/prod/prodComm/page'),
        method: 'get',
        params: this.$http.adornParams(
          Object.assign(
            {
              current: page.currentPage,
              size: page.pageSize
            },
            params
          )
        )
      }).then(({ data }) => {
        this.dataList = data.records
        this.page.total = data.total
        this.dataListLoading = false
      })
    },
    // 新增 / 修改
    showUpdateHandle(row) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row)
      })
    },
    // 删除
    deleteHandle(id) {
      this.$confirm('确定进行删除操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/prod/prodComm/' + id),
          method: 'delete',
          data: this.$http.adornData()
        }).then(() => {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500,
            onClose: () => {
              this.getDataList(this.page)
            }
          })
        })
      })
    },
    searchChange(params, done) {
      this.getDataList(this.page, params)
      done()
    }
  }
}
</script>
