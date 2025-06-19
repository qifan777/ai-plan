<script setup lang="ts">
import { onMounted, ref } from 'vue'
import type { SlideshowDto } from '@/apis/__generated/model/dto'
import { api } from '@/utils/api-instance.ts'

const slideShowList = ref<SlideshowDto['SlideshowRepository/COMPLEX_FETCHER_FOR_FRONT'][]>([])
onMounted(async () => {
  slideShowList.value = (
    await api.slideshowForFrontController.query({
      body: {
        sorts: [
          {
            property: 'sort',
            direction: 'ASC',
          },
        ],
        pageSize: 20,
        query: { valid: true },
      },
    })
  ).content
})
</script>

<template>
  <div class="home-view">
    <div class="carousel">
      <el-carousel height="300px" motion-blur>
        <el-carousel-item
          class="carousel-item"
          v-for="(item, index) in slideShowList"
          :key="item.id"
        >
          <el-image
            preview-teleported
            class="slideshow"
            :src="item.picture"
            fit="contain"
            :initial-index="index"
            :preview-src-list="slideShowList.map((row) => row.picture)"
          ></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<style scoped lang="scss">
.home-view {
  .carousel {
    background-color: white;
    margin-bottom: 10px;
    overflow: hidden;
    border-radius: 5px;

    .slideshow {
      height: 100%;
      width: 100%;
    }
  }
}
</style>
