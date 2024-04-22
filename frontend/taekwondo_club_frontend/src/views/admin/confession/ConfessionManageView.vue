<template>
  <h1>Quản lý confession</h1>
  <br/>
  <el-row :gutter="12">
    <el-col :md="16">
      <el-form
          label-position="left"
          :ref="toRef('FORM_FILTER')"
          :rules="rules"
          style="max-width: 800px"
      >
        <el-row :gutter="10">
          <el-col :md="3">
            <el-form-item label="ID" prop="id">
              <el-input v-model="filter.id" placeholder="ID..." />
            </el-form-item>
          </el-col>
          <el-col :md="9">
            <el-form-item label="Name" prop="name">
              <el-input v-model="filter.name" placeholder="Name..." />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-col>
    <el-col :md="8">
      <CommonButton
          class="add-confession-btn"
          type="primary"
          @click="showAddConfessionDialog"
      >Thêm confession
      </CommonButton>
    </el-col>
  </el-row>

  <el-table
      v-loading="loading"
      empty-text="Không có dữ liệu"
      :data="confessionsData"
      border
      style="width: 100%;
          border: 1px solid #ddd;
          border-radius: 4px;
          background-color: #fff;
          box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);"
  >
    <el-table-column
        prop="id"
        label="ID"
        width="65"
        header-align="center"
        align="center"
        sortable
    />
    <el-table-column
        prop="image"
        label="Image"
        header-align="center"
        align="center"
    >
      <template #default="scope">
        <img :src="'http://localhost:80/api/confessions/' + scope.row.id + '/image'" alt="image" style="width: 80px; height: 80px;"/>
      </template>
    </el-table-column>
    <el-table-column
        prop="name"
        min-width="100"
        label="Name"
        header-align="center"
        align="left"
        sortable
    />
    <el-table-column
        prop="description"
        label="Description"
        header-align="center"
        align="center"
    />
    <el-table-column
        prop="content"
        label="Content"
        header-align="center"
        align="center"
    />
    <el-table-column
        prop="author"
        label="Author"
        header-align="center"
        align="center"
    />
    <el-table-column
        :formatter="tableDateFormatter"
        prop="createdAt"
        label="Created At"
        header-align="center"
        align="center"
    />
    <el-table-column
        prop="isApproved"
        label="Is Approved"
        header-align="center"
        align="center"
    >
      <template #default="scope">
        <el-tag
            v-if="scope.row.isApproved"
            type="success"
        >Đã duyệt
        </el-tag>
        <el-tag
            v-else
            type="danger"
        >Chưa duyệt
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column
        fixed="right"
        label="Action"
        width="180"
        header-align="center"
        align="center"
    >
      <template #default="scope">
        <CommonButton
            @click="handleEdit(scope.row.id)"
            size="mini"
            link
            type="primary"
        >Chỉnh sửa
        </CommonButton>
        <el-popconfirm
            :title="`Bạn chắc chắn muốn xóa lời thú nhận '${scope.row.name}'?`"
            confirm-button-text="Xác nhận"
            cancel-button-text="Hủy"
            @confirm="handleDelete(scope.row.id)"
        >
          <template #reference>
            <CommonButton link type="danger" :ref="toRef('DELETE_BTN')">
              Xóa
            </CommonButton>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <AddConfessionDialog
      v-model="dialogAdd"
      @close="closeDialogAdd"
  />

  <EditConfessionDialog
      v-model="dialogEdit"
      :id="idEdit"
      @close="closeDialogEdit"
  />

  <el-pagination
      v-model:page="currentPage"
      :page-size="pageSize"
      layout="prev, pager, next"
      :total="totalItems">
  </el-pagination>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref, watch} from 'vue'
import {processErrorMessage} from '@/helper/responseErrorHandle'
import type {FormRules} from 'element-plus'
import {ElMessage} from 'element-plus'
import {useRouter} from "vue-router";
import {ConfessionService} from "@/services/confession";
import useRefs from '@/common/useRefs'
import CommonButton from "@/components/common/CommonButton.vue";
import EditConfessionDialog from "@/components/confession/EditConfessionDialog.vue";
import AddConfessionDialog from "@/components/confession/AddConfessionDialog.vue";
import {tableDateFormatter} from "@/helper/Table";

const dialogAdd = ref(false)
const dialogEdit = ref(false)
const idEdit = ref(null as unknown as number)

const loading = ref(false)
const confessionsData = ref<any[] | null>(null);
const rules = reactive<FormRules>({})
const currentPage = ref(1)
const totalItems = ref(0)
const pageSize = 10

const filter = reactive({
  id: '',
  name: '',
  isApproved: '',
})

function closeDialogEdit() {
  dialogEdit.value = false
  loadData()
}

function closeDialogAdd() {
  dialogAdd.value = false
  loadData()
}

let {$refs, toRef} = useRefs();

const RefNames = {
  DELETE_BTN: 'DELETE_BTN_',
  EDIT_BTN: 'EDIT_BTN_',
  TABLE: 'TABLE',
  FORM_FILTER: 'FORM_FILTER',
  RELOAD_BTN: 'RELOAD_BTN'
}

onMounted(async () => {
  await loadData()
})

watch(currentPage, (newPage) => {
  ConfessionService.list(newPage - 1);
});

async function loadData() {
  try {
    loading.value = true

    const res = await ConfessionService.all_confession(currentPage.value - 1)
    let confessions = res.content
    totalItems.value = res.totalElements
    confessionsData.value = confessions.map((confessionDTO: any) => {
      return {
        id: confessionDTO.id,
        image: confessionDTO.image,
        name: confessionDTO.name,
        description: confessionDTO.description,
        content: confessionDTO.content,
        author: confessionDTO.author,
        createdAt: confessionDTO.createdAt,
        isApproved: confessionDTO.isApproved,
      }
    })
  } catch (e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình tải dữ liệu. " +
        "Vui lòng thử lại sau!")
  } finally {
    loading.value = false
  }
}

function showAddConfessionDialog() {
  dialogAdd.value = true
}

function resetFilter() {
  $refs.get(RefNames.FORM_FILTER)?.resetFields()
  loadData()
}

const router = useRouter()

function handleEdit(id: number) {
  idEdit.value = id
  dialogEdit.value = true
}

async function handleDelete(id: number) {
  try {
    await ConfessionService.delete(id)
    await loadData()
    ElMessage.success("Xóa lời thú nhận thành công!")
  } catch (e) {
    processErrorMessage(e,
        "Có lỗi đã xảy ra trong quá trình xóa lời thú nhận. " +
        "Vui lòng thử lại sau!")
  }
}
</script>

<style scoped>
.add-confession-btn {
  float: right;
  margin-bottom: 1.5rem;
}

.pagination-block {
  margin-top: 15px;
  float: right;
}

.el-table .warning-row {
  background: oldlace;
}

h1 {
  color: #333;
  font-size: 2em;
  margin-bottom: 1rem;
}
</style>