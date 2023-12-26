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
          <v-btn type="submit"  color="primary">Submit</v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      username: '',
    };
  },
  methods: {
    submitUsername() {
      if (this.username) {
        // Redirect to /index/username route
        this.$router.push( '/inbox/' + this.username);
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
