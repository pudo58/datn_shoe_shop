<template>
  <!-- Button trigger modal -->
  <button v-show="false" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create-category" id="show-modal">
    Launch static backdrop modal
  </button>

  <!-- Modal -->
  <div class="modal hide" id="create-category"  aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">Modal title</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="addCategory()">
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="floatingCategoryName" v-model="category.name">
              <label for="floatingCategoryName">Tên danh mục</label>
            </div>
            <div class="form-floating mb-3">
              <textarea class="form-control" placeholder="Mô tả ...." id="floatingDesc" v-model="category.description"></textarea>
              <label for="floatingDesc">Mô tả</label>
            </div>
            <button type="submit" class="btn btn-success">Lưu</button>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import {CategoryService} from "@/core/service/category.service";
import {Category} from "@/core/model/category.model";
import { toast } from 'vue3-toastify';
export default defineComponent({
  name: 'categoryDetailComponent',
  data() {
    return {
      categoryService: new CategoryService(),
      openModalDialog : false
    }
  },
  props: {
    category: {
      type: Category,
      required: true
    },
  },
  methods: {
    addCategory() {
      if(this.category.id != null){
        this.categoryService.update(this.category).then((res) => {
        toast.success("Cập nhật thành công");
        });
        return;
      }
      this.categoryService.save(this.category).then((res) => {
          this.$emit('added-category', res);
      });

    },
    openModal(){
      document.getElementById('show-modal')?.click();
    }
  }
})
</script>