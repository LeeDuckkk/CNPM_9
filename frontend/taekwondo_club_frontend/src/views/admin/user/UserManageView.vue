<template>
  <h1>Quản lý người dùng</h1>
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
                  placeholder="Lọc theo tên người dùng"
              />
            </el-form-item>
          </el-col>
          <el-col :md="9">
            <el-form-item label="Email" prop="email">
              <el-input
                  type="email"
                  placeholder="Lọc theo email người dùng"
              />
            </el-form-item>
          </el-col>
          <el-col :md="3">
            <CommonButton type="default" @click="resetFilter"
            >Đặt lại
            </CommonButton>
          </el-col>
        </el-row>
      </el-form>
    </el-col>
    <el-col :md="8">
      <CommonButton
          class="add-user-btn"
          type="primary"
          @click="showAddUserDialog"
      >Thêm người dùng
      </CommonButton>
    </el-col>
  </el-row>

  <el-table
      v-loading="loading"
      empty-text="Không có dữ liệu"
      :data="usersData"
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
        prop="email"
        label="Email"
        width="220"
        header-align="center"
        align="center"
    />
    <el-table-column
        prop="username"
        label="Username"
        header-align="center"
        align="center"
    />
    <el-table-column
        prop="phoneNumber"
        label="Phone Number"
        header-align="center"
        align="center"
    />
    <el-table-column
        :formatter="tableDateFormatter"
        prop="birthday"
        label="Birthday"
        header-align="center"
        align="center"
    />
    <el-table-column
        prop="birthPlace"
        label="Birth Place"
        header-align="center"
        align="center"
    />
    <!--    <el-table-column-->
    <!--        prop="hobby"-->
    <!--        label="Hobby"-->
    <!--        header-align="center"-->
    <!--        align="center"-->
    <!--    />-->
    <!--    <el-table-column-->
    <!--        prop="hatred"-->
    <!--        label="Hatred"-->
    <!--        header-align="center"-->
    <!--        align="center"-->
    <!--    />-->
    <!--    <el-table-column-->
    <!--        prop="strength"-->
    <!--        label="Strength"-->
    <!--        header-align="center"-->
    <!--        align="center"-->
    <!--    />-->
    <!--    <el-table-column-->
    <!--        prop="weakness"-->
    <!--        label="Weakness"-->
    <!--        header-align="center"-->
    <!--        align="center"-->
    <!--    />-->
    <!--    <el-table-column-->
    <!--        prop="lifeMotto"-->
    <!--        label="Life Motto"-->
    <!--        header-align="center"-->
    <!--        align="center"-->
    <!--    />-->
    <!--    <el-table-column-->
    <!--        prop="achievement"-->
    <!--        label="Achievement"-->
    <!--        header-align="center"-->
    <!--        align="center"-->
    <!--    />-->
    <el-table-column
        :formatter="tableDateFormatter"
        prop="joinDate"
        label="Join Date"
        header-align="center"
        align="center"
    />
    <el-table-column
        prop="placement"
        label="Placement"
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
            :title="`Bạn chắc chắn muốn xóa người dùng '${scope.row.name}'?`"
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

  <AddUserDialog
      v-model="dialogAdd"
      @close="closeDialogAdd"
  />

  <EditUserDialog
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
import {tableDateFormatter} from '@/helper/Table'
import {useRouter} from "vue-router";
import {UserService} from "@/services/user";
import useRefs from '@/common/useRefs'
import AddUserDialog from "@/components/user/AddUserDialog.vue";
import CommonButton from "@/components/common/CommonButton.vue";
import EditUserDialog from "@/components/user/EditUserDialog.vue";

const dialogAdd = ref(false)
const dialogEdit = ref(false)
const idEdit = ref(null as unknown as number)

const loading = ref(false)
const usersData = ref<any[] | null>(null);
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
  UserService.listForAdmin(newPage - 1);
});

async function loadData() {
  try {
    loading.value = true

    const res = await UserService.listForAdmin(currentPage.value - 1)
    let users = res.content
    totalItems.value = res.totalElements
    usersData.value = users.map((userDTO: any) => {
      return {
        id: userDTO.id,
        name: userDTO.name,
        email: userDTO.email,
        username: userDTO.username,
        phoneNumber: userDTO.phoneNumber,
        birthday: userDTO.birthday,
        birthPlace: userDTO.birthPlace,
        hobby: userDTO.hobby,
        hatred: userDTO.hatred,
        strength: userDTO.strength,
        weakness: userDTO.weakness,
        lifeMotto: userDTO.lifeMotto,
        achievement: userDTO.achievement,
        joinDate: userDTO.joinDate,
        placement: userDTO.placement,
      }
    })
    console.log(usersData)
  } catch (e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình tải dữ liệu. " +
        "Vui lòng thử lại sau!")
  } finally {
    loading.value = false
  }
}

function showAddUserDialog() {
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
  // $refs.get(RefNames.DELETE_BTN + id)?.setLoading(true)
  console.log(id)
  try {
    await UserService.delete(id)
    await loadData()
    ElMessage.success("Xóa người dùng thành công!")
  } catch (e) {
    processErrorMessage(e,
        "Có lỗi đã xảy ra trong quá trình xóa người dùng. " +
        "Vui lòng thử lại sau!")
  } finally {
    // $refs.get(RefNames.DELETE_BTN + id)?.setLoading(false)
  }
}
</script>

<style scoped>
.add-user-btn {
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
