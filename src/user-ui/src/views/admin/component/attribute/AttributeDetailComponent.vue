<template>
  <!-- Button trigger modal -->
  <button v-show="false" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create-attribute" id="show-modal">
    Launch static backdrop modal
  </button>

  <!-- Modal -->
  <div class="modal hide" id="create-attribute"  aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">Modal title</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="addAttribute()">
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="floatingCategoryName" v-model="attribute.name">
              <label for="floatingCategoryName">Tên thuộc tính</label>
            </div>
            <div class="form-floating mb-3">
             <select class="form-select" id="floatingDesc" v-model="attribute.type">
               <option value="Integer">Kiểu số nguyên</option>
               <option value="Double">Kiểu số thực</option>
               <option value="Date">Kiểu ngày</option>
               <option value="String">Kiểu Chuỗi</option>
                <option value="Boolean">Kiểu Boolean</option>
              </select>
              <label for="floatingDesc">Kiểu dữ liệu</label>
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
import { toast } from 'vue3-toastify';
import {Attribute} from "@/core/model/attribute.model";
import {AttributeService} from "@/core/service/attribute.service";
export default defineComponent({
  name: 'AttributeDetailComponent',
  data() {
    return {
      attributeService: new AttributeService(),
      openModalDialog : false
    }
  },
  props: {
    attribute: {
      type: Attribute,
      required: true
    },
  },
  methods: {
    addAttribute() {
      console.log(this.attribute);
      if(this.attribute.id != null){
        this.attributeService.update(this.attribute).then((res) => {
          toast.success("Cập nhật thành công");
        });
        return;
      }
      this.attributeService.save(this.attribute).then((res) => {
        this.$emit('added-attribute', res);
      });

    },
    openModal(){
      document.getElementById('show-modal')?.click();
    }
  }
})
</script>