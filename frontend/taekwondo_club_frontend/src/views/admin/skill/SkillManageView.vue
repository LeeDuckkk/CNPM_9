<template>
  <h1>Quản lý kỹ năng</h1>
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
                  placeholder="Lọc theo tên kỹ năng"
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
          class="add-skill-btn"
          type="primary"
          @click="showAddSkillDialog"
      >Thêm kỹ năng
      </CommonButton>
    </el-col>
  </el-row>

  <el-table
      v-loading="loading"
      empty-text="Không có dữ liệu"
      :data="skillsData"
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
        prop="description"
        label="Description"
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
            :title="`Bạn chắc chắn muốn xóa kỹ năng '${scope.row.name}'?`"
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

  <AddSkillDialog
      v-model="dialogAdd"
      @close="closeDialogAdd"
  />

  <EditSkillDialog
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
import {SkillService} from "@/services/skill";
import useRefs from '@/common/useRefs'
import CommonButton from "@/components/common/CommonButton.vue";
import EditSkillDialog from "@/components/skill/EditSkillDialog.vue";
import AddSkillDialog from "@/components/skill/AddSkillDialog.vue";

const dialogAdd = ref(false)
const dialogEdit = ref(false)
const idEdit = ref(null as unknown as number)

const loading = ref(false)
const skillsData = ref<any[] | null>(null);
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
  SkillService.list(newPage - 1);
});

async function loadData() {
  try {
    loading.value = true

    const res = await SkillService.list(currentPage.value - 1)
    let skills = res.content
    totalItems.value = res.totalElements
    skillsData.value = skills.map((skillDTO: any) => {
      return {
        id: skillDTO.id,
        name: skillDTO.name,
        description: skillDTO.description,
      }
    })
  } catch (e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình tải dữ liệu. " +
        "Vui lòng thử lại sau!")
  } finally {
    loading.value = false
  }
}

function showAddSkillDialog() {
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
    await SkillService.delete(id)
    await loadData()
    ElMessage.success("Xóa kỹ năng thành công!")
  } catch (e) {
    processErrorMessage(e,
        "Có lỗi đã xảy ra trong quá trình xóa kỹ năng. " +
        "Vui lòng thử lại sau!")
  }
}
</script>

<style scoped>
.add-skill-btn {
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