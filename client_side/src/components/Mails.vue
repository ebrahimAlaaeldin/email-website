<template>
  <v-app>
    <Header :username="username" />

    <!-- Sidebar -->
    <v-container id="sidebar" class="pa-0">
      <Navbar :username="username" navigation="Inbox" />
    </v-container>

    <!-- Main Content -->
    <v-main class="main-content">
      <!-- Subheader with Trash Icon and Copy To/Restore Button -->
      <v-container class="subheader">
        <v-icon @click="deleteSelected" class="mr-4">mdi-delete</v-icon>
        <!-- Conditionally render the appropriate button based on the route -->
        <v-btn v-if="isTrashRoute && !isContactRoute && !isDraftRoute" @click="restoreSelected">Restore</v-btn>
        <v-btn v-if="!isTrashRoute && !isContactRoute && !isDraftRoute" @click="openCopyToDialog">Copy To</v-btn>
      </v-container>

      <v-spacer></v-spacer>
      <v-spacer></v-spacer>

      <v-container class="pa-0">
        <v-row>
          <v-col v-for="(mail, index) in mails" :key="index" class="mb-4">
            <v-card :class="getCardClass(index)">
              <v-card-title>{{ mail.subject }}</v-card-title>
              <v-card-subtitle>{{ mail.sender }}</v-card-subtitle>
              <v-card-text>{{ mail.content }}</v-card-text>
              <v-card-actions>
                <v-btn @click="toggleSelect(index)">Select</v-btn>
                <v-btn @click="openDialog(mail)">View</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>

        <!-- Email Details Dialog -->
        <v-dialog v-model="dialogVisible" max-width="600">
          <v-card>
            <v-card-title>{{ selectedMail.subject }}</v-card-title>
            <v-card-subtitle>{{ selectedMail.sender }}</v-card-subtitle>
            <v-card-text>{{ selectedMail.content }}</v-card-text>
            <v-card-actions>
              <v-btn @click="closeDialog">Close</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <!-- Copy To Dialog -->
        <v-dialog v-model="isCopyToDialogOpen">
          <v-card>
            <v-card-title>Select Folder</v-card-title>
            <v-card-text>
              <!-- Use v-select for the dropdown -->
              <v-select
                v-model="selectedFolder"
                :items="folderNames"
                label="Select Folder"
              ></v-select>
            </v-card-text>
            <v-card-actions>
              <v-btn @click="closeCopyToDialog">Cancel</v-btn>
              <v-btn @click="copyToSelectedFolder">Copy</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Navbar from "@/components/SideBar.vue";
import Header from "@/components/Header";

export default {
  name: "Inbox",
  components: { Navbar, Header },
  data() {
    return {
      username: this.$route.params.username,
      folders: [
        { name: "Folder1" },
        { name: "Folder2" },
        { name: "Folder3" },
        { name: "Folder4" },
        { name: "Folder5" },
      ],
      isCopyToDialogOpen: false,
      selectedFolder: null,
      mails: [
        // ... existing mail data ...
      ],
      select: [],
      dialogVisible: false,
      selectedMail: null,
      trashRouteName: 'trash',
      contactRouteName: 'contact',
      draftRouteName: 'draft',
    };
  },
  computed: {
    folderNames() {
      return this.folders.map((folder) => folder.name);
    },
    isTrashRoute() {
      return this.$route.name === this.trashRouteName;
    },
    isContactRoute() {
      return this.$route.name === this.contactRouteName;
    },
    isDraftRoute() {
      return this.$route.name === this.draftRouteName;
    },
  },
  methods: {
    toggleSelect(index) {
      this.select = Object.assign([], this.select, {
        [index]: !this.select[index],
      });
    },
    getCardClass(index) {
      return this.select[index] ? "selected-card" : "";
    },
    openDialog(mail) {
      this.selectedMail = mail;
      this.dialogVisible = true;
    },
    closeDialog() {
      this.dialogVisible = false;
    },
    deleteSelected() {
      console.log("Deleting selected emails");
    },
    restoreSelected() {
      console.log("Restoring selected emails");
    },
    openCopyToDialog() {
      this.isCopyToDialogOpen = true;
    },
    closeCopyToDialog() {
      this.isCopyToDialogOpen = false;
      this.selectedFolder = null;
    },
    copyToSelectedFolder() {
      if (this.selectedFolder) {
        console.log("Copying to:", this.selectedFolder.name);
        this.closeCopyToDialog();
      }
    },
  },
};
</script>

<style scoped>
.selected-card {
  background-color: #304859;
}
</style>
<style>
.main-content {
  margin-left: 300px;
  margin-top: 50px;
}
</style>
<style scoped>
.subheader {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-right: 16px;
}

.subheader v-icon {
  cursor: pointer;
}
</style>
