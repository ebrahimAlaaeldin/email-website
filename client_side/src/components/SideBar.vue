<template>
  <v-app>
    <v-main>
      <v-container v-show="menu" id="sidebar">
        <v-col>
          <NewFolder @folderCreated="updateLinks" />
        </v-col>
        <v-col>
          <Compose :username="username" />
        </v-col>
        <v-list>
          <v-list-item v-for="(link, index) in links" :key="link.text" :to="link.route">
            <v-list-item-content>
              <v-list-item-title class="black--text">
                {{ link.text }}
              </v-list-item-title>
            </v-list-item-content>
            <!-- Conditionally render rename and delete buttons for added folders -->
            <v-list-item-action v-if="index >= defaultLinksCount">
              <v-btn @click="renameFolder(index)">Rename</v-btn>
              <v-btn @click="deleteFolder(index)">Delete</v-btn>
            </v-list-item-action>
          </v-list-item>
        </v-list>
      </v-container>

      <v-container>
        <router-view></router-view>
      </v-container>
    </v-main>

    <!-- Dialog for renaming folders -->
    <v-dialog v-model="renameDialog" max-width="500">
      <v-card>
        <v-card-title>
          Rename Folder
        </v-card-title>
        <v-card-text>
          <v-text-field v-model="newFolderName" label="New Folder Name"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn @click="renameConfirmed">Rename</v-btn>
          <v-btn @click="cancelRename">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import Compose from './Compose.vue';
import NewFolder from './NewFolder.vue';

export default {
  components: { Compose, NewFolder },
  props: { username: String, navigation: String },
  data() {
    return {
      menu: true,
      links: [
        { icon: 'mdi-inbox', text: 'Inbox', route: '/inbox/' + this.username },
        { icon: 'mdi-send', text: 'Sent', route: '/sent/' + this.username },
        { icon: 'mdi-delete', text: 'Trash', route: '/trash/' + this.username },
        { icon: 'mdi-email', text: 'Draft', route: '/draft/' + this.username },
        { icon: 'mdi-account', text: 'Contact', route: '/contact/' + this.username },
      ],
      defaultLinksCount: 5, // Number of default links
      // Dialog properties
      renameDialog: false,
      renamingIndex: null,
      newFolderName: '',
    };
  },
  methods: {
    updateLinks(folderName) {
      console.log('Updating links with folder:', folderName);
      this.links.push({
        icon: 'mdi-folder',
        text: folderName,
        route: `/${folderName}/${this.username}`, // Update the route as needed
      });
      console.log('Updated links:', this.links);
    },
    renameFolder(index) {
      // Show the rename dialog
      this.renamingIndex = index;
      this.renameDialog = true;
    },
    renameConfirmed() {
      // Implement logic to rename the folder at the specified index
      const folderIndex = this.renamingIndex;
      const newFolderName = this.newFolderName;
      // Update addedFolders array or perform any necessary action
      console.log(`Rename folder at index ${folderIndex} to ${newFolderName}`);

      // Close the dialog and reset values
      this.renameDialog = false;
      this.renamingIndex = null;
      this.newFolderName = '';
    },
    cancelRename() {
      // Cancel the rename operation
      this.renameDialog = false;
      this.renamingIndex = null;
      this.newFolderName = '';
    },
    deleteFolder(index) {
      // Implement logic to delete the folder at the specified index
      // Update addedFolders array or perform any necessary action
      console.log(`Delete folder at index ${index}`);
    },
  },
};
</script>

<style>
#sidebar {
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  width: 250px;
  background-color: #384b6c;
  padding-top: 60px; /* Adjust based on your header's height */
}

#sidebar v-list-item {
  color: #384b6c;
  background-color: #384b6c;
}

#sidebar v-btn {
  margin-right: 8px; /* Adjust spacing between buttons */
}
</style>
