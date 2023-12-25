<template>
  <v-app-bar app color="#384b6c">
    <v-toolbar-title class="ml-4">Hi, {{ username }}</v-toolbar-title>

    <!-- Search text field -->
    <v-text-field
      v-model="searchText"
      placeholder="Search..."
      outlined
      hide-details
      class="search"
    ></v-text-field>

    <!-- Search criteria dropdown -->
    <v-select
      v-model="searchCriteria"
      :items="searchCriteriaOptions"
      label="Search Criteria"
      outlined
      hide-details
      class="search-criteria"
    ></v-select>

    <!-- Sorting criteria dropdown -->
    <!-- Sorting criteria dropdown -->
    <v-select
      v-model="sortCriteria"
      :items="sortCriteriaOptions"
      label="Sort By"
      outlined
      hide-details
      class="sort-criteria"
      @change="changeSortCriteria"
    ></v-select>

    <!-- Search button -->
    <v-btn icon @click="confirmSearch">
      <v-icon>mdi-magnify</v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script>
export default {
  props: {
    username: String,
  },
  data() {
    return {
      searchText: "",
      searchCriteria: "Subject", // Default search criteria
      searchCriteriaOptions: ["Subject", "Sender Email", "Priority"], // Add more criteria as needed
      sortCriteria: "Date", // Default sort criteria
      sortCriteriaOptions: ["Date", "Sender Email", "Priority", "Subject"], // Add more criteria as needed
    };
  },
  methods: {
    confirmSearch() {
      console.log(
        "Starting search:",
        this.searchText,
        "with criteria:",
        this.searchCriteria
      );
      console.log("Sorting by:", this.sortCriteria);
      // You can put additional logic here, such as triggering a search function
    },
changeSortCriteria() {
  console.log('changeSortCriteria method called');
  console.log('Sorting by:', this.sortCriteria);
  // You can put additional logic here, such as triggering a sorting function
}

  },
};
</script>

<style>
.search {
  width: 100px !important;
}

.search-criteria,
.sort-criteria {
  width: 150px !important;
}
</style>
