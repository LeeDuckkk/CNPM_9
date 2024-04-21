<template>
  <div>
    <h1>Các kỹ thuật đá trong Taekwondo</h1>
    <div class="skill-container" v-loading="loading">
      <div class="skill-item" v-for="skill in skills" >
        <el-card class="card-skill" shadow="hover">
          <h2>#{{skill.id}} {{ skill.name }}</h2>
          <i>{{ skill.description }}</i>
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
import {SkillService} from "@/services/skill";
import {processErrorMessage} from "@/helper/responseErrorHandle";
import {ConfessionService} from "@/services/confession";

const skills = ref([])
const loading = ref(false)
const currentPage = ref(1)
const totalItems = ref(0)
const pageSize = 10

onMounted(async () => {
  await getSkills(currentPage.value - 1)
})

watch(currentPage, (newPage) => {
  getSkills(newPage - 1);
});

async function getSkills(page: number) {
  try {
    loading.value = true
    const result = await SkillService.list(page)
    skills.value = result.content
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
.card-skill {
  width: 800px;
  margin: 10px;
}

.skill-container {
  display: flex;
  flex-wrap: wrap;
}
</style>