<template>
  <div class="list row">
    <div class="col-md-8">
      <div class="input-group mb-3">
        <input type="text" class="form-control" placeholder="Search by title" v-model="title" />
        <div class="input-group-append">
          <button class="btn btn-outline-secondary" type="button" @click="searchTitle">
            Search
          </button>
        </div>
      </div>
    </div>
    <div class="col-md-8">
      <h4>Search Result</h4>
      <ul class="list-group">
        <li class="list-group-item" v-for="(item, index) in items" :key="index">
          <router-link :to="{ path: '/items/' + item.id }" append>{{ item.title }}</router-link>
          <div>
            {{ item.description }}
            <div>
              {{ "Price: " + item.price }}
              <div>
                {{ "Availability: " + item.availability }}
              </div>
            </div>
          </div>
        </li>
      </ul>
      <p>{{ message }}</p>
    </div>
  </div>
</template>

<script>
import ItemDataService from "../services/ItemDataService";

export default {
  name: "item-list-view",
  data() {
    return {
      items: [],
      currentItem: null,
      currentIndex: -1,
      title: "",
      message: ''
    };
  },
  methods: {
    retrieveItems() {
      ItemDataService.getAll()
        .then(response => {
          this.items = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
          this.message = 'Data could not be loaded.';
        });
    },

    searchTitle() {
      ItemDataService.findByTitle(this.title)
        .then(response => {
          this.items = response.data;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
          this.message = 'Data could not be loaded.';
        });
    }
  },
  mounted() {
    this.retrieveItems();
  }
};
</script>

<style>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
</style>