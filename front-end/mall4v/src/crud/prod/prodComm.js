export const tableOption = {
  border: true,
  index: true,
  indexLabel: '序号',
  stripe: true,
  menuAlign: 'center',
  align: 'center',
  editBtn: false,
  delBtn: false,
  addBtn: false,
  column: [
    { label: '商品ID', prop: 'prodId' },
    { label: '用户ID', prop: 'userId' },
    { label: '评分', prop: 'score', type: 'rate' },
    { label: '内容', prop: 'content' },
    {
      label: '图片',
      prop: 'pics',
      type: 'upload',
      listType: 'picture-card',
      loadText: '附件上传中，请稍等',
      span: 24,
      propsHttp: {
        url: '/admin/file/upload',
        res: 'data'
      },
      action: '/admin/file/upload'
    },
    {
      label: '状态',
      prop: 'status',
      type: 'select',
      dicData: [
        { label: '未审核', value: 0 },
        { label: '已审核', value: 1 },
        { label: '审核失败', value: -1 }
      ]
    },
    { label: '评价时间', prop: 'recTime', type: 'datetime', format: 'yyyy-MM-dd hh:mm:ss', valueFormat: 'yyyy-MM-dd hh:mm:ss' }
  ]
}
