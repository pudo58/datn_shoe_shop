<template>
  <!-- Button trigger modal -->
  <button v-show="false" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create-publisher" id="show-modal">
    Launch static backdrop modal
  </button>

  <!-- Modal -->
  <div class="modal hide" id="create-publisher"  aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">Chi tiết</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="addPublisher()">
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="floatingPublisherName" v-model="publisher.name">
              <label for="floatingPublisherName">Tên nhà sản xuất</label>
            </div>
            <div class="form-floating mb-3">
              <input class="form-control" placeholder="example.com" id="floatingDesc" v-model="publisher.website">
              <label for="floatingDesc">Link website</label>
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
import {PublisherService} from "@/core/service/publisher.service";
import {Publisher} from "@/core/model/publisher.model";
import { toast } from 'vue3-toastify';
export default defineComponent({
  name: 'publisherDetailComponent',
  data() {
    return {
      publisherService: new PublisherService(),
      openModalDialog : false
    }
  },
  props: {
    publisher: {
      type: Publisher,
      required: true
    },
  },
  methods: {
    addPublisher() {
      if(this.publisher.id != null){
        this.publisherService.update(this.publisher).then((res) => {
          toast.success("Cập nhật thành công");
        });
        return;
      }
      this.publisherService.save(this.publisher).then((res) => {
        this.$emit('added-publisher', res);
      });

    },
    openModal(){
      document.getElementById('show-modal')?.click();
    }
  }
})
</script>