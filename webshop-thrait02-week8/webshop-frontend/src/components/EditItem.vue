<template>
  <div v-if="currentItem" class="edit-form">
    <h4>Item</h4>
    <form>
      <div class="form-group">
        <label for="title">Title</label>
        <input type="text" class="form-control" id="title"
          v-model="currentItem.title" v-validate="'required|min:3|max:20'"
        />
        <span>{{ errors.first("title") }}</span>
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <input type="text" class="form-control" id="description"
          v-model="currentItem.description" v-validate="'required|min:3|max:20'"
        />
        <span>{{ errors.first("description") }}</span>
      </div>
      <div class="form-group">
        <label for="price">Price</label>
        <input type="text" class="form-control" id="price"
          v-model="currentItem.price" v-validate="'required|decimal:2'"
        />
        <span>{{ errors.first("price") }}</span>
      </div>
      <div class="form-group">
        <label for="availability">Availability</label>
        <input type="number" class="form-control" id="availability"
          v-model="currentItem.availability" v-validate="'max_value:100'"
        />
        <span>{{ errors.first("availability") }}</span>
      </div>
    </form>

    <button class="btn btn-danger mr-2"
      @click="deleteItem">
      Delete
    </button>

    <button type="submit" class="btn btn-success"
      @click="updateItem" :disabled="errors.any() || !isComplete"> 
      Update
    </button>
    <p>{{ message }}</p>
  </div>
  <div v-else>
    <p>{{ message }}</p>
  </div>
</template>

<script>
import ItemDataService from "../services/ItemDataService";

export default {
  name: "edit-item-view",
  data() {
    return {
      currentItem: null,
      message: ''
    };
  },
  computed: {
    isComplete() {
      return this.item.title && this.item.description && this.item.price && this.item.availability;
    },
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

    updateItem() {
      ItemDataService.update(this.currentItem.id, this.currentItem)
        .then(response => {
          console.log(response.data);
          this.message = 'The item was updated successfully!';
        })
        .catch(e => {
          console.log(e);
          this.message = 'The item could not be updated';
        });
    },

    deleteItem() {
      ItemDataService.delete(this.currentItem.id)
        .then(response => {
          console.log(response.data);
          this.$router.push({ name: "items" });
        })
        .catch(e => {
          console.log(e);
          this.message = 'The item could not be deleted';
        });
    }
  },
  mounted() {
    this.message = '';
    this.getItem(this.$route.params.id);
  }
};
</script>

<style>
.edit-form {
  max-width: 300px;
  margin: auto;
}
</style>