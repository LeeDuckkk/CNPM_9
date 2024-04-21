<template>
  <div>
    <h1>Danh sách Confession</h1>
    <div class="confession-container" v-loading="loading">
      <div class="confession-item" v-for="confession in confessions" >
        <el-card class="card-confession" shadow="hover">
          <div class="card-content">
            <div>
              <h2><strong>{{ confession.name }}</strong></h2>
              <p>{{ confession.description }}</p>
              <i>{{ formatDate(confession.createdAt) }}</i>
            </div>
            <el-button class="detail-button" type="primary" @click="redirectToDetail(confession)">Xem chi tiết</el-button>
          </div>
        </el-card>
      </div>
    </div>

    <el-pagination
        v-model:page="currentPage"
        :page-size="pageSize"
        layout="prev, pager, next"
        :total="totalItems">
    </el-pagination>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref, watch} from 'vue';
import {ConfessionService} from "@/services/confession";
import {processErrorMessage} from "@/helper/responseErrorHandle";
import {router} from "@/router";

const confessions = ref([])
const loading = ref(false)
const currentPage = ref(1)
const totalItems = ref(0)
const pageSize = 10

onMounted(async () => {
  await getConfessions(currentPage.value - 1)
})

watch(currentPage, (newPage) => {
  getConfessions(newPage - 1);
});

async function redirectToDetail(confession: any) {
  await router.push({name: 'ConfessionDetail', params: {id: confession.id}})
}

async function getConfessions(page: number) {
  try {
    loading.value = true
    const result = await ConfessionService.list(page)
    confessions.value = result.content
    totalItems.value = result.totalElements
  } catch (e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình tải dữ liệu. " +
        "Vui lòng thử lại sau!")
  } finally {
    loading.value = false
  }
}


function formatDate(dateString: string) {
  const date = new Date(dateString);
  const day = date.getDate().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Months are 0-based
  const year = date.getFullYear();
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const seconds = date.getSeconds().toString().padStart(2, '0');

  return `${hours}:${minutes}:${seconds} ${day}:${month}:${year}`;
}
</script>


<style scoped>
.card-content {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-direction: column;
}

.detail-button {
  align-self: flex-end;
}
</style>