<template>
  <h1>Quản lý sự kiện</h1>
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
              <el-input
                  :min="0"
                  placeholder="Tìm ID"
                  type="number"
              />
            </el-form-item>
          </el-col>
          <el-col :md="9">
            <el-form-item label="Tên" prop="name">
              <el-input
                  placeholder="Lọc theo tên sự kiện"
              />
            </el-form-item>
          </el-col>
          <el-col :md="3">
            <CommonButton @click="resetFilter" type="default">Đặt lại</CommonButton>
          </el-col>
        </el-row>
      </el-form>
    </el-col>
    <el-col :md="8">
      <CommonButton
          class="add-event-btn"
          type="primary"
          @click="showAddEventDialog"
      >Thêm sự kiện
      </CommonButton>
    </el-col>
  </el-row>

  <el-table
      v-loading="loading"
      empty-text="Không có dữ liệu"
      :data="eventsData"
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
        prop="name"
        min-width="100"
        label="Name"
        header-align="center"
        align="left"
        sortable
    />
    <el-table-column
        prop="content"
        label="Content"
        header-align="center"
        align="center"
    />
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
            :title="`Bạn chắc chắn muốn xóa sự kiện '${scope.row.name}'?`"
            confirm-button-text="Xác nhận"
            cancel-button-text="Hủy"
            @confirm="handleDelete(scope.row.id)"
        >
          <template #reference>
            <CommonButton link type="danger" :ref="toRef('DELETE_BTN')"
            >Xóa
            </CommonButton>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <AddEventDialog
      v-model="dialogAdd"
      @close="closeDialogAdd"
  />

  <EditEventDialog
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
import {EventService} from "@/services/event";
import useRefs from '@/common/useRefs'
import CommonButton from "@/components/common/CommonButton.vue";
import EditEventDialog from "@/components/event/EditEventDialog.vue";
import AddEventDialog from "@/components/event/AddEventDialog.vue";

const dialogAdd = ref(false)
const dialogEdit = ref(false)
const idEdit = ref(null as unknown as number)

const loading = ref(false)
const eventsData = ref<any[] | null>(null);
const rules = reactive<FormRules>({})
const currentPage = ref(1)
const totalItems = ref(0)
const pageSize = 10

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
  EventService.list(newPage - 1);
});

async function loadData() {
  try {
    loading.value = true

    const res = await EventService.list(currentPage.value - 1)
    let events = res.content
    totalItems.value = res.totalElements
    eventsData.value = events.map((eventDTO: any) => {
      return {
        id: eventDTO.id,
        name: eventDTO.name,
        content: eventDTO.content,
      }
    })
  } catch (e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình tải dữ liệu. " +
        "Vui lòng thử lại sau!")
  } finally {
    loading.value = false
  }
}

function showAddEventDialog() {
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
    await EventService.delete(id)
    await loadData()
    ElMessage.success("Xóa sự kiện thành công!")
  } catch (e) {
    processErrorMessage(e,
        "Có lỗi đã xảy ra trong quá trình xóa sự kiện. " +
        "Vui lòng thử lại sau!")
  }
}
</script>

<style scoped>
.add-event-btn {
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