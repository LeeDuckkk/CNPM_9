<template>
  <div>
    <h1>Danh sách sự kiện</h1>
    <div class="event-container" v-loading="loading">
      <div class="event-item" v-for="event in events" >
        <el-card class="card-event" shadow="hover">
          <div class="card-content">
            <div>
              <h2><strong>{{ event.name }}</strong></h2>
              <p>{{ event.content.substring(0, 100) }}...</p>
              <i>{{ formatDate(event.createdAt) }}</i>
            </div>
            <el-button class="detail-button" type="primary" @click="openDialog(event)">Xem chi tiết</el-button>
          </div>
        </el-card>
      </div>
    </div>
    <el-dialog v-model="dialogVisible" width="50%">
      <div class="dialog-content">
        <div class="dialog-header">
          <div>
            <h1>{{ selectedEvent?.name }}</h1>
            <i style="font-size: 15px;">{{ formatDate(selectedEvent?.createdAt) }}</i>
          </div>
          <img :src="randomPicUrl" alt="Random Pic" style="width: 100px; height: 100px;">
        </div>
        <p style="font-size: 17px;">{{ selectedEvent?.content }}</p>
      </div>
    </el-dialog>
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
import {EventService} from "@/services/event";
import {processErrorMessage} from "@/helper/responseErrorHandle";

const events = ref([])
const loading = ref(false)
const selectedEvent = ref(null)
const dialogVisible = ref(false)
const randomPicUrl = ref('https://source.unsplash.com/random')

const currentPage = ref(1)
const totalItems = ref(0)
const pageSize = 10

onMounted(async () => {
  await getEvents(currentPage.value - 1)
})

watch(currentPage, (newPage) => {
  getEvents(newPage - 1);
});

async function getEvents(page: number) {
  try {
    loading.value = true
    const result = await EventService.list(page)
    events.value = result.content
    totalItems.value = result.totalElements
  } catch (e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình tải dữ liệu. " +
        "Vui lòng thử lại sau!")
  } finally {
    loading.value = false
  }
}

function openDialog(event: any) {
  selectedEvent.value = event
  dialogVisible.value = true
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

.dialog-content {
  display: flex;
  flex-direction: column;
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>