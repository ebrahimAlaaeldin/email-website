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
import VueCookies from 'vue-cookies';

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
          const response = await axios.post(`http://192.168.116.205:8080/api/login?username=${this.username}`);
          const userData = response.data;

          // Save userId in cookies
          VueCookies.set('userId', userData.userId);

          this.user = new User(userData.userId, userData.username);
          console.log('User data:', this.user);
          this.$router.push(`/folder/inbox/${this.username}`);
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
