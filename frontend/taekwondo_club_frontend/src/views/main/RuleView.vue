<template>
  <div>
    <h1>Quy định của câu lạc bộ</h1>
    <div class="rule-container" v-loading="loading">
      <div class="rule-item" v-for="rule in rules" >
        <el-card class="card-rule" shadow="hover">
          <h2>#{{rule.id}} {{ rule.name }}</h2>
          <i>{{ rule.description }}</i>
        </el-card>
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
import {RuleService} from "@/services/rule";
import {processErrorMessage} from "@/helper/responseErrorHandle";
import {ConfessionService} from "@/services/confession";

const rules = ref([])
const loading = ref(false)
const currentPage = ref(1)
const totalItems = ref(0)
const pageSize = 10

onMounted(async () => {
  await getRules(currentPage.value - 1)
})

watch(currentPage, (newPage) => {
  getRules(newPage - 1);
});

async function getRules(page: number) {
  try {
    loading.value = true
    const result = await RuleService.list(page)
    rules.value = result.content
    totalItems.value = result.totalElements
  } catch (e) {
    processErrorMessage(e, "Có lỗi đã xảy ra trong quá trình tải dữ liệu. " +
        "Vui lòng thử lại sau!")
  } finally {
    loading.value = false
  }
}
</script>


<style scoped>
.card-rule {
  width: 800px;
  margin: 10px;
}

.rule-container {
  display: flex;
  flex-wrap: wrap;
}
</style>