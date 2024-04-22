<template>
  <div class="confession-detail" v-loading="loading">
    <div class="confession-image">
      <img :src="imageUrl" alt="image" class="confession-pic">
    </div>
    <div class="confession-info">
      <h1 class="confession-name">{{ confession?.name }}</h1>
      <i class="confession-description">{{ confession?.description }}</i>
      <p class="confession-content">{{ confession?.content }}</p>
      <p class="confession-author">Tác giả: {{ confession?.author }}</p>
      <p class="confession-date">{{ confession?.createdAt }}</p>
    </div>

    <div class="comment-container">
      <el-card class="comment-card" shadow="hover">
        <h2>Bình luận</h2>
        <el-input v-model="comment" placeholder="Nhập bình luận của bạn"></el-input>
        <el-button type="primary" @click="submitComment">Gửi</el-button>
      </el-card>
    </div>
  </div>

  <div class="comment-list">
    <h2>Bình luận</h2>
    <div class="comment-item" v-for="comment in comments">
      <img class="avatar" :src="'https://picsum.photos/200/' + comment.user.id * 100" alt="User Avatar">
      <div class="comment-content">
        <strong class="text-author" v-if="comment.user.name">{{ comment.user.name }}</strong>
        <strong class="text-author" v-else>Ẩn danh</strong>

        <p class="text-content-comment">{{ comment.content }}</p>
        <i class="smaller-text">{{ formatDate(comment.createdDate) }}</i>
      </div>
    </div>
  </div>

  <el-pagination
      v-model:page="currentPage"
      :page-size="pageSize"
      layout="prev, pager, next"
      :total="totalItems">
  </el-pagination>
</template>

<script setup lang="ts">
import {onMounted, ref, watch} from 'vue';
import { useRoute } from 'vue-router';
import {ConfessionService} from "@/services/confession";
import {processErrorMessage} from "@/helper/responseErrorHandle";

const route = useRoute();
const confession = ref(null);
const imageUrl = ref('');
const loading = ref(false);
const comments = ref([]);
const comment = ref('');

const currentPage = ref(1)
const totalItems = ref(0)
const pageSize = 10

onMounted(async () => {
  try {
    loading.value = true
    const id = route.params.id;
    confession.value = await ConfessionService.getById(id);
    imageUrl.value = "http://localhost:80/api/confessions/" + id + "/image";

    await getComments(currentPage.value - 1, id)
  } catch(e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình tải dữ liệu. " +
        "Vui lòng thử lại sau!")
  } finally {
    loading.value = false
  }
});

watch(currentPage, (newPage) => {
  getComments(newPage - 1, route.params.id);
});

async function getComments(page: number, id: number) {
  try {
    loading.value = true
    const id = route.params.id;
    const result = await ConfessionService.getComments(id, page);
    comments.value = result.content;
    totalItems.value = result.totalElements;
  } catch(e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình tải dữ liệu. " +
        "Vui lòng thử lại sau!")
  } finally {
    loading.value = false
  }
}

async function submitComment() {
  try {
    loading.value = true
    const id = route.params.id;
    await ConfessionService.addComment(id, comment.value);
    comment.value = '';
    await getComments(currentPage.value - 1);
  } catch(e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình gửi bình luận. " +
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
.confession-detail {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

.confession-image {
  flex: 1;
}

.confession-info {
  flex: 2;
  text-align: left;
  padding-left: 20px;
}

.confession-name {
  font-size: 2em;
  font-weight: bold;
}

.confession-pic {
  width: 80%;
  height: auto;
}

.confession-description,
.confession-content,
.confession-author,
.confession-date {
  margin-top: 1em;
}

.comment-item {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.comment-content {
  flex-grow: 1;
}

.smaller-text {
  font-size: 0.8em; /* Adjust this value to your liking */
}

.text-author {
  font-weight: bold;
  margin: 0px;
}

.text-content-comment {
  margin: 0px;
}


</style>