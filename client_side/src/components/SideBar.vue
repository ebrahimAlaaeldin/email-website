<template>
  <v-app>
    <v-main>
      <v-container v-show="menu" id="sidebar">
        <v-col>
          <NewFolder @folderCreated="updateLinks" :username="username" />
          <!-- <v-btn @click="setHashMap">Set Hash Map</v-btn> -->
        </v-col>
        <v-col>
            <Compose :username="username" :fromDraft="false" />
        </v-col>
        <v-col class="text-center">
        <v-btn  color="black" @click="showFilterDialog">Filter</v-btn>
        </v-col>
        <v-col class="text-center">
         <v-btn color="black" @click="openContactDialog">Add Contact</v-btn>
        </v-col>
        <v-list>
          <v-list-item v-for="(link, index) in links"  :to="link.route" :key="link.text" >
            <v-list-item-content>
              <v-list-item-title class="black--text">
                {{ link.text }}
              </v-list-item-title>
            </v-list-item-content>
            <!-- Conditionally render rename and delete buttons for added folders -->
              <v-list-item-action v-if="index >= defaultLinksCount">
            <br>
              <v-btn @click="renameFolder(index)">Rename</v-btn>
              <v-btn @click="deleteFolder(index)">Delete</v-btn>
            </v-list-item-action>
          </v-list-item>
          
        </v-list>
      </v-container>
    </v-main>

    <!-- Dialog for renaming folders or filtering emails -->
    <v-dialog v-model="isFilterDialog" max-width="500">
      <v-card v-if="isFilterDialog">
        <v-card-title>
          Filter Emails
        </v-card-title>
        <v-card-text>
          <v-select v-model="filterType" :items="filterOptions" label="Filter By"></v-select>
          <v-text-field v-model="filterValue" placeholder="Enter filter value"></v-text-field>
          <v-select v-model="filterFolder" :items="folderOptions" label="Select Folder"></v-select>
        </v-card-text>
        <v-card-actions>
          <v-btn @click="filterConfirmed">Filter</v-btn>
          <v-btn @click="cancelFilter">Cancel</v-btn>
        </v-card-actions>

      </v-card>
            </v-dialog>
    <v-dialog v-model="renameDialog" max-width="500">

      <v-card v-if="renameDialog">
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

     <!-- Dialog for renaming folders, filtering emails, or adding contacts -->
    <v-dialog v-model="isAddContactDialog" max-width="500">
      <!-- ... (existing card content) ... -->
      <!-- Add a new card for adding contacts -->
      <v-card v-if="isAddContactDialog">
        <v-card-title>
          Add Contact
        </v-card-title>
        <v-card-text>
          <v-text-field v-model="contactName" label="Contact Name"></v-text-field>
          <v-text-field v-model="contactEmail" label="Contact Email"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn @click="addContact">Add</v-btn>
          <v-btn @click="cancelAddContact">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import Compose from './Compose.vue';
import NewFolder from './NewFolder.vue';
import {computed} from 'vue';
import { useStore } from 'vuex';
import axios from 'axios';
export default {
    setup() {
    const store = useStore();


    const setHashMap = () => {
      const newHashMap = { key1: 'value1', key2: 'value2' };
      console.log('Setting hash map:', newHashMap);
      store.dispatch('updateHashMap', newHashMap);
      console.log('Hash Map:', store.state.hashMap);  
    };

  

    return {
      setHashMap,
    };
  },
  
  components: { Compose, NewFolder },
  props: { username: String, navigation: String },
  data() {
    return {
      menu: true,
    links: [
      { icon: 'mdi-account', text: 'Contact' ,route: `/contact/${this.username}` },
    ],
      defaultLinksCount: 5, // Number of default links
      // Dialog properties
      renameDialog: false,
      isFilterDialog: false,
      renamingIndex: null,
      newFolderName: '',
      // Filter dialog properties
      filterType: 'Subject',
      filterValue: '',
      filterFolder: '',
      filterOptions: ['Subject', 'Sender'],
      folderOptions: ["Inbox", "Sent", "Drafts"], // Add more folders as needed
            isAddContactDialog: false,
      contactName: '',
      contactEmail: '',
    };
  },
   mounted() {
    // Call your function when the component is mounted
    this.loadData(); // Call the function to load data on mount
  },

  methods: {
    // Add a method to load data using axios
    loadData() {
      axios.get(`http://192.168.116.205:8080/api/${this.username}/folder/list`)
        .then(response => {
          // Assuming response.data is an array of objects with properties like text and route
          const newData = response.data.map(item => ({
            text: item.folderName,
            folderId: item.folderId,
            route: `/folder/${item.folderName}/${this.username}`,
          }));

          // Update the links array with the received data
          this.links = [...this.links, ...newData];

          console.log('Updated links:', this.links);
          this.$store.dispatch('updateHashMap', this.links);
        
        })
        .catch(error => {
          console.error('Error loading data:', error);
        });
    },
    updateLinks(folderName) {
      console.log('Updating links with folder:', folderName);

      // Access the Vuex store
      const hashmap = this.$store.getters.getHashMap;

      if (folderName in hashmap) {
        // Do something if the folderName is already in the hash map
      } else {
        hashmap[folderName] = `folder/${folderName}/${this.username}`;
      }

      const route = hashmap[folderName];

      // Dispatch the updateHashMap action to update the store
      this.$store.dispatch('updateHashMap', hashmap);
      console.log('Hash Map:', this.$store.state.hashMap);

      // Update the links array
      this.links.push({
        icon: 'mdi-folder',
        text: "folder/"+folderName,
        route: route,
      });

      console.log('Updated links:', this.links);
    },
    renameFolder(index) {
      // Show the rename dialog
      this.renamingIndex = index;
      this.renameDialog = true;
      this.isFilterDialog = false;
    },
    renameConfirmed() {
      // Implement logic to rename the folder at the specified index
      const folderIndex = this.renamingIndex;
      const newFolderName = this.newFolderName;
      let fids=Object.values(this.$store.getters.getHashMap).map(obj => obj.folderId)
      let ids=Object.values(fids);

      // Update addedFolders array or perform any necessary action
      console.log(`Rename folder at index ${folderIndex} to ${newFolderName}`);
      axios.post(`http://192.168.116.205:8080/api/${this.username}/folder/rename?folderId=${ids[this.renamingIndex]}&newFolderName=${newFolderName}`)
        .then((response) => {
          console.log(response);
          // const newFolder = new Folder(response.data.folderId, this.folderName);
          // this.$emit('folderCreated', newFolder);
        })
        .catch((error) => {
          console.error(error);
        });

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
      let fids=Object.values(this.$store.getters.getHashMap).map(obj => obj.folderId)
      let ids=Object.values(fids);
        axios.post(`http://192.168.116.205:8080/api/${this.username}/folder/delete?folderId=${ids[index]}`)
        .then((response) => {
          window.location.reload();

          console.log(response);
          // const newFolder = new Folder(response.data.folderId, this.folderName);
          // this.$emit('folderCreated', newFolder);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    // New method to show the filter dialog
    showFilterDialog() {
      this.isFilterDialog = true;
      this.renameDialog = false;
      this.isAddContactDialog = false;
    },
    filterConfirmed() {
      // Implement logic to filter emails based on the selected options
      console.log('Filtering emails:');
      console.log('Filter Type:', this.filterType);
      console.log('Filter Value:', this.filterValue);
      console.log('Filter Folder:', this.filterFolder);

      // Close the dialog and reset values
      this.isFilterDialog = false;
      this.renameDialog = false;
      this.filterType = 'Subject';
      this.filterValue = '';
      this.filterFolder = '';
    },
    cancelFilter() {
      // Cancel the filter operation
      this.isFilterDialog = false;
      this.renameDialog = false;
      this.filterType = 'Subject';
      this.filterValue = '';
      this.filterFolder = '';
    },
     // Method to open the contact addition dialog
    openContactDialog() {
      this.isFilterDialog = false;
      this.renameDialog = false;
      this.isAddContactDialog = true;
    },

    // Method to add a contact
    addContact() {
      // Validate contact name and email
      if (this.contactName && this.contactEmail) {
        // Perform logic to add the contact (e.g., make an API request)
        // ...

        // Close the dialog and reset values
        this.isAddContactDialog = false;
        this.renameDialog = false;
        this.contactName = '';
        this.contactEmail = '';
      } else {
        // Display an error message or handle invalid input
        alert('Please enter both contact name and email.');
      }
    },

    // Method to cancel adding a contact
    cancelAddContact() {
      // Close the dialog and reset values
      this.isAddContactDialog = false;
      this.renameDialog = false;
      this.contactName = '';
      this.contactEmail = '';
    },

  },
};
</script>

<style>
/* ... existing styles ... */
</style>

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
