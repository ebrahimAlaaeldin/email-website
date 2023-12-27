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

    <v-pagination
      :total-visible="1"
      :length="100"
      v-model="currentPage"
    ></v-pagination>

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
      pageNumber: 1,
      currentPage: 1,
    };
  },
  computed: {
    // Calculate the total number of pages based on itemsPerPage
    totalPages() {
      return Math.ceil(this.filteredItems?.length / this.currentPage);
    },
    // Calculate the starting index for the current page
    startIndex() {
      return (this.currentPage - 1) * this.itemsPerPage;
    },
    // Get a slice of items based on the current page
    paginatedItems() {
      return this.filteredItems.slice(
        this.startIndex,
        this.startIndex + this.currentPage
      );
    },
   currentPage: {
      get() {
        return this.$store.getters.getPageNumber;
      },
      set(value) {
        this.$store.dispatch('updatePageNumber', value);
      },
    },
    sortCriteria: {
      get() {
        return this.$store.getters.getSortCriteria;
      },
      set(value) {
        this.$store.dispatch('updateSortCriteria', value);
      },
    },
  },
  methods: {
    confirmSearch() {
      console.log("Search button clicked");
      console.log("page number from store:", this.$store.getters.getPageNumber);
      console.log("sort criteria from store:", this.$store.getters.getSortCriteria);
      // Redirect to /search/username route
      this.$router.push({
        path: `/search/${this.username}/${this.searchCriteria}/${this.searchText}/${this.sortCriteria}/${this.pageNumber}/${this.selectedFolder}`,
      });

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
      this.$store.dispatch('updateSortCriteria', this.sortCriteria);
      this.$store.dispatch('updatePageNumber', this.pageNumber);
      // You can put additional logic here, such as triggering a search function
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
