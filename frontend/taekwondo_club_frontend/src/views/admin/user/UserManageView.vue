<template>
  <h1>Quản lý người dùng</h1>
  <br />

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
  </el-table>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
import {processErrorMessage} from '@/helper/responseErrorHandle'
import type {FormRules} from 'element-plus'
import {ElMessage} from 'element-plus'
import { tableDateFormatter, tableFullDateTimeFormatter } from '@/helper/Table'
import {useRouter} from "vue-router";
import FAIcon from '@/components/common/FAIcon.vue'
import {UserService} from "@/services/user";
import useRefs from '@/common/useRefs'

const dialogAdd = ref(false)
const dialogEdit = ref(false)
const idEdit = ref(null as unknown as number)

const loading = ref(false)
const usersData = ref<any[] | null>(null);
const rules = reactive<FormRules>({})

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

async function loadData() {
  try {
    loading.value = true

    let users = await UserService.listForAdmin(0)
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

const router = useRouter()

function handleEdit(id: number) {
  idEdit.value = id
  dialogEdit.value = true
}

async function handleDelete(id: number) {
  $refs.get(RefNames.DELETE_BTN + id)?.setLoading(true)
  try {
    // await deleteUser(id)
    await loadData()
    ElMessage.success("Xóa người dùng thành công!")
  } catch (e) {
    processErrorMessage(e,
        "Có lỗi đã xảy ra trong quá trình xóa người dùng. " +
        "Vui lòng thử lại sau!")
  } finally {
    $refs.get(RefNames.DELETE_BTN + id)?.setLoading(false)
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
