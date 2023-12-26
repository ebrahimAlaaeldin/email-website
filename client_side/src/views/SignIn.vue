<template>
  <v-container fluid fill-height>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-form @submit.prevent="submitUsername">
          <v-text-field
            v-model="username"
            label="Enter your username"
            outlined
            solo-inverted
          ></v-text-field>
          <v-btn type="submit" color="primary">Submit</v-btn>
        </v-form>
      </v-col>
    </v-row>

    <!-- Display user information -->
    <div v-if="user">
      <h2>User Information</h2>
      <p>User ID: {{ user.userId }}</p>
      <p>Username: {{ user.username }}</p>
    </div>
  </v-container>
</template>

<script>
import axios from 'axios';
import { User } from '@/components/classes.js';

export default {
  data() {
    return {
      username: '',
      user: null,
    };
  },
  methods: {
    async submitUsername() {
      if (this.username) {
        try {
          const response = await axios.get(`/api/user/${this.username}`); // Replace with your actual API endpoint
          const userData = response.data; // Assuming the server returns user data
          this.user = new User(userData.userId, userData.username);
        } catch (error) {
          console.error('Error fetching user data:', error);
        }
      } else {
        alert('Please enter a username');
      }
    },
  },
};
</script>

<style scoped>
/* Add any additional styling if needed */
</style>
