<template>
  <div class="submit-form">
    <div v-if="!submitted">
      <div class="form-group">
        <label for="title">Title</label>
        <input class="form-control" id="title" required v-model="item.title" v-validate="'required|min:3|max:20'" name="title" type="text" />
        <span>{{ errors.first("title") }}</span>
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <input class="form-control" id="description" required v-model="item.description" v-validate="'required|min:3|max:20'" name="description"
          type="text" />
          <span>{{ errors.first("description") }}</span>
      </div>
      <div class="form-group">
        <label for="price">Price</label>
        <input class="form-control" id="price" required v-model="item.price" v-validate="'required|decimal:2'" name="price" type="text" />
        <span>{{ errors.first("price") }}</span>
      </div>
      <div class="form-group">
        <label for="availability">Availability</label>
        <input class="form-control" id="availability" required v-model="item.availability" v-validate="'max_value:100'" name="availability"
          type="number" />
          <span>{{ errors.first("availability") }}</span>
      </div>

      <button @click="saveItem" class="btn btn-success" :disabled="errors.any() || !isComplete">Submit</button>
      <p>{{ message }}</p>
    </div>

    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newItem">Add</button>
    </div>
  </div>
</template>

<script>
import ItemDataService from "../services/ItemDataService";

export default {
  name: "add-item-view",
  data() {
    return {
      item: {
        id: null,
        title: "",
        description: "",
        price: "",
        availability: 0
      },
      submitted: false,
      message: ''
    };
  },
  computed: {
    isComplete() {
      return this.item.title && this.item.description && this.item.price && this.item.availability;
    },
  },
  methods: {
    saveItem() {
      var data = {
        title: this.item.title,
        description: this.item.description,
        price: this.item.price,
        availability: this.item.availability,
      };
      
      ItemDataService.create(data)
        .then(response => {
          this.item.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
        })
        .catch(e => {
          console.log(e);
          this.message = 'The item could not be added';
        });
    },

    newItem() {
      this.submitted = false;
      this.item = {};
    }
  }
};
</script>

<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>