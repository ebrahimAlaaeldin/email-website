<template>

  <v-btn  color="black" @click="openDialog">Create New Folder</v-btn>

  <v-dialog v-model="dialog" max-width="500">
    <v-card>
      <v-card-title>Create New Folder</v-card-title>
      <v-card-text>
        <v-text-field v-model="folderName" label="Folder Name" required></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-btn @click="createFolder" color="primary">Create</v-btn>
        <v-btn @click="closeDialog">Cancel</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';
import {Folder} from './classes.js';
export default {
  props: {
    username: String,
  },
  data() {
    return {
      dialog: false,
      folderName: '',
    };
  },
  methods: {
    openDialog() {
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
      this.folderName = ''; // Clear the folder name when closing the dialog
    },
    createFolder() {
      // Emit an event with the folderName
      // this.$emit('folderCreated', this.folderName);

      axios.post(`http://192.168.116.205:8080/api/ayman/folder/create?folderName=${this.folderName}`)
        .then((response) => {
          console.log(response);
          // const newFolder = new Folder(response.data.folderId, this.folderName);
          // this.$emit('folderCreated', newFolder);
        })
        .catch((error) => {
          console.error(error);
        });

      // Add your logic here to handle folder creation with this.folderName
      // You can make an API call, use a Vuex store, etc.
      console.log('Creating folder:', this.folderName);

      // Close the dialog after creating the folder
      this.closeDialog();
    },
  },
};
</script>

