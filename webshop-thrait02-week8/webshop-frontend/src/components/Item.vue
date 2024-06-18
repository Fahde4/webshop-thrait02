<template>
  <div v-if="currentItem" class="view-form">
    <h4>Item</h4>
    <form>
      <div class="form-group">
        <div>
          Title
        </div>
        <div>
          {{ currentItem.title }}
        </div>
      </div>
      <div class="form-group">
        <div>
          Description
        </div>
        <div>
          {{ currentItem.description }}
        </div>
      </div>
      <div class="form-group">
        <div>
          Price
        </div>
        <div>
          {{ currentItem.price }}
        </div>
      </div>
      <div class="form-group">
        <div>
          Availability
        </div>
        <div>
          {{ currentItem.availability }}
        </div>
      </div>
    </form>
  
    <button type="edit" class="btn btn-success" @click="editItem">
      Edit
    </button>
  </div>
  <div v-else>
    <p>{{ message }}</p>
  </div>
</template>
  
<script>
import ItemDataService from "../services/ItemDataService";
  
export default {
  name: "item-view",
  data() {
    return {
      currentItem: null,
      message: ''
    };
  },
  methods: {
    getItem(id) {
      ItemDataService.get(id)
        .then(response => {
          this.currentItem = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
          this.message = 'Data could not be loaded.';
        });
      },
  
    editItem() {
      this.$router.push({ name: "edit-item" });
    },
  
  },
  mounted() {
    this.getItem(this.$route.params.id);
  }
};
</script>
  
<style>
.view-form {
  max-width: 300px;
  margin: auto;
}
</style>