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

    <!-- Folder selection dropdown -->
    <v-select
      v-model="selectedFolder"
      :items="folderOptions"
      label="Select Folder"
      outlined
      hide-details
      class="folder-select"
    ></v-select>

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
      selectedFolder: "", // New property for folder selection
      folderOptions: ["Inbox", "Sent", "Drafts"], // Add more folders as needed
      searchCriteria: "Subject",
      searchCriteriaOptions: ["Subject", "Sender Email", "Priority"],
      sortCriteria: "Date",
      sortCriteriaOptions: ["Date", "Sender Email", "Priority", "Subject"],
    };
  },
  methods: {
    confirmSearch() {
      // Redirect to /search/username route
      this.$router.push({ path: `/search/${this.username}` });

      // Additional logic (if needed)
      console.log(
        "Starting search in folder:",
        this.selectedFolder,
        "with criteria:",
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
    },
  },
};
</script>

<style>
.search {
  width: 100px !important;
}

.folder-select,
.search-criteria,
.sort-criteria {
  width: 150px !important;
}
</style>
