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
        <v-btn
          v-if="isTrashRoute && !isContactRoute && !isDraftRoute"
          @click="restoreSelected"
          >Restore</v-btn
        >
        <v-btn
          v-if="!isTrashRoute && !isContactRoute && !isDraftRoute"
          @click="openCopyToDialog"
          >Copy To</v-btn
        >
      </v-container>

      <v-spacer></v-spacer>
      <v-spacer></v-spacer>

      <v-container class="pa-0">
        <v-row>
          <!-- Inside the v-col for contacts -->
          <v-col
            v-if="isContactRoute"
            v-for="(contact, index) in contacts"
            :key="index"
            class="mb-4"
          >
            <v-card :class="getContactCardClass(index)">
              <v-card-title>{{ contact?.name }}</v-card-title>
              <v-card-text>{{ contact?.contactEmails?.join(', ') }}</v-card-text>
              <br>
              <v-btn @click="toggleContactSelect(index)">Select</v-btn>
              <v-btn @click="openEditDialog(contact)">Edit</v-btn>
            </v-card>
          </v-col>

          <v-col
            v-if="!isContactRoute"
            v-for="(mail, index) in mails"
            :key="index"
            class="mb-4"
          >
            <v-card :class="getCardClass(index)">
              <v-card-title>{{ mail?.subject }}</v-card-title>
              <v-card-subtitle>{{ mail?.sender }}</v-card-subtitle>
              <v-card-text>{{ mail?.content }}</v-card-text>
              <v-list>
                <v-list-item
                  v-for="(attachment, attachmentIndex) in mail?.attachments"
                  :key="attachmentIndex"
                >
                  <v-list-item-icon>
                    <v-icon>mdi-attachment</v-icon>
                  </v-list-item-icon>
                  <v-list-item-content>
                    {{ attachment?.filename }}
                  </v-list-item-content>
                  <v-list-item-action>
                    <v-btn @click="downloadAttachment(attachment)"
                      >Download</v-btn
                    >
                  </v-list-item-action>
                </v-list-item>
              </v-list>
              <v-card-actions>
                <v-btn @click="toggleSelect(index)">Select</v-btn>
                <!-- Conditionally render "Edit" or "View" button based on the route -->
                <v-btn v-if="!isDraftRoute" @click="openDialog(mail)"
                  >View</v-btn
                >
                <Compose
                  v-if="isDraftRoute"
                  :username="username"
                  :fromDraft="true"
                  :defaultMid="mail?.mid?.toString() || ''"
                  :defaultTo="mail?.sender?.toString() || ''"
                  :defaultSubject="mail?.subject?.toString() || ''"
                  :defaultContent="mail?.content?.toString() || ''"
                  :defaultPriority="mail?.priority?.toString() || ''"
                  :defaultAttachments="mail?.attachments || []"
                />
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>

        <!-- Email Details Dialog -->
        <v-dialog v-model="dialogVisible" max-width="600">
          <v-card>
            <v-card-title>{{ selectedMail?.subject }}</v-card-title>
            <v-card-subtitle>{{ selectedMail?.sender }}</v-card-subtitle>
            <v-card-text>{{ selectedMail?.content }}</v-card-text>
            <v-list>
              <v-list-item
                v-for="(
                  attachment, attachmentIndex
                ) in selectedMail?.attachments"
                :key="attachmentIndex"
              >
                <v-list-item-icon>
                  <v-icon>mdi-attachment</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  {{ selectedMail?.attachments[attachmentIndex].filename }}
                </v-list-item-content>
                <v-list-item-action>
                  <v-btn @click="downloadAttachment(attachment)"
                    >Download</v-btn
                  >
                </v-list-item-action>
              </v-list-item>
            </v-list>
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
    <!-- At the bottom of the template -->

    <!-- Edit Contact Dialog -->
  <!-- Edit Contact Dialog -->
  <v-dialog v-model="editDialogVisible">
    <v-card>
      <v-card-title>Edit Contact</v-card-title>
      <v-card-text>
        <v-text-field v-model="editedContact.name" label="Name"></v-text-field>
        <v-text-field
          v-model="editedContactEmailsString"
          label="Emails"
          readonly
        ></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-btn @click="closeEditDialog">Cancel</v-btn>
        <v-btn @click="updateContact">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  </v-app>
</template>

<script>
import Navbar from "@/components/SideBar.vue";
import Header from "@/components/Header";
import Compose from "@/components/Compose.vue";
import axios from "axios";
import VueCookies from "vue-cookies";
import { mapGetters } from "vuex";
import { get_email } from "./classes.js";
export default {
    created() {
    // Fetch mails and contacts on component creation
    this.fetchData();
  },
  mounted() {
    // Retrieve userId from cookies
    this.userId = VueCookies.get("userId");
    if (this.userId) {
      console.log("Retrieved userId from cookies:", this.userId);
    } else {
      console.log("UserId not found in cookies.");
    }
  },
  watch: {
    $route: function (to, from) {
      this.mails = [];
      this.conatcts=[]
      // This will be called whenever the route changes
      console.log("Route changed:", to, from);
      if (to.params.foldername?.toLowerCase() === "inbox") {
        this.load_mails(to);
        console.log("Inbox route");
      } else if (to.params.foldername?.toLowerCase() === "trash") {
        this.load_mails(to);
        console.log("Trash route");
      } else if (to.params.foldername?.toLowerCase() === "draft") {
        this.load_mails(to);
        console.log("Draft route");
      } else if (to.name === "contact") {
        this.load_conatcts();
        console.log("Contact route");
      } else if (to.params.foldername?.toLowerCase() === "sent") {
        this.load_mails(to);
        console.log("Sent route");}
      else if (to.name=="search")
      {
        this.search_mails();
        console.log("Search route");
      
      } else if (
        to.params.foldername?.toLowerCase() !== "inbox" &&
        to.params.foldername?.toLowerCase() !== "trash" &&
        to.params.foldername?.toLowerCase() !== "draft" &&
        to.name !== "contact" &&
        to.params.foldername?.toLowerCase() !== "sent"
      ) {
        this.load_mails(to);
        console.log("Folder route");
      }
    },
    getHashMap: function (newHashMap) {
      // Log the hashmap whenever it changes
      console.log("Hashmap changed:", newHashMap);
    },
  },
  name: "Inbox",
  components: { Navbar, Header, Compose },
  data() {
    return {
      username: this.$route.params.username,
      folders: [],
      isCopyToDialogOpen: false,
      selectedFolder: null,
      mails: [],
      contacts: 
      [
        ],
      
      select: [],
      selected_contacts: [],
      dialogVisible: false,
      selectedMail: null,
      trashRouteName: "trash",
      contactRouteName: "contact",
      draftRouteName: "draft",
         editDialogVisible: false,
      editedContact: {
        name: '',
        contactEmails: [],
      },
      selectedContactIndex: null,
    };
  },
  computed: {
    ...mapGetters(["getHashMap"]),
    folderNames() {
      return this.folders.map((folder) => folder.name);
    },
    isTrashRoute() {
      return (
        this.$route.params.foldername?.toLowerCase() === this.trashRouteName
      );
    },
    isContactRoute() {
      console.log("Route name:", this.$route.name);
      return this.$route.name === this.contactRouteName;
    },
    isDraftRoute() {
      return (
        this.$route.params.foldername?.toLowerCase() === this.draftRouteName
      );
    },
     editedContactEmailsString() {
      // Combine contact emails into a single string separated by commas
      return this.editedContact.contactEmails.join(', ');
    },
  },
methods: {
    fetchData() {
      if (this.$route.name === "contact") {
        this.load_contacts();
      } else {
        this.load_mails(this.$route);
      }
    },
    search_mails()
    {
      let searchfor=this.$route.params.searchfor
      let searchby=this.$route.params.searchby
      let fid=this.$route.params.FolderId
      const emailObject = new get_email(
        VueCookies.get("userId"),
        fid,
        searchby,
        searchfor,
        this.$store.getters.getSortCriteria,
        this.$store.getters.getPageNumber,
        10
      );
        const apiUrl = "http://192.168.116.205:8080/api/mohamed/email/list";

      // Send the POST request with the object as the request payload
      axios
        .post(apiUrl, emailObject)
        .then((response) => {
          // Handle the response
          const responseData = response.data;

          this.mails = responseData.emails;
          const numberOfPages = responseData.numberOfPages;

          console.log("Response:", response.data);
          console.log("attachment1", this.mails[0].attachments[0].filename);
        })
        .catch((error) => {
          // Handle errors
          console.error("Error:", error);
        });
    },
    load_conatcts() {
      this.mails = [];
      this.conatcts = [];
      // Define the API endpoint where you want to send the POST request
      const apiUrl = "http://192.168.116.205:8080/api/mohamed/contacts/list";

      // Send the POST request with the object as the request payload
      axios
        .get(apiUrl)
        .then((response) => {
          // Handle the response
          const responseData = response.data;

          this.contacts = responseData;
          console.log("Response:", response.data);
          console.log("contacts", this.contacts[0].name);
        })
        .catch((error) => {
          // Handle errors
          console.error("Error:", error);
        });
    },
    load_mails(to) {
      this.conatcts = [];
      console.log("loading mails");
      console.log("folders", this.folders);
      this.folders = this.$store.getters.getHashMap;
      const keyToCheck = to.params.foldername;

      if (this.folders[keyToCheck] !== undefined) {
        console.log(`${keyToCheck} exists in the hashmap.`);
      } else {
        console.log(`${keyToCheck} does not exist in the hashmap.`);
      }
      let fid = this.folders[keyToCheck];
      // Create an instance of the get_email class

      const emailObject = new get_email(
        VueCookies.get("userId"),
        fid,
        "body",
        "b",
        this.$store.getters.getSortCriteria,
        this.$store.getters.getPageNumber,
        10
      );

      // Define the API endpoint where you want to send the POST request
      const apiUrl = "http://192.168.116.205:8080/api/mohamed/email/list";

      // Send the POST request with the object as the request payload
      axios
        .post(apiUrl, emailObject)
        .then((response) => {
          // Handle the response
          const responseData = response.data;

          this.mails = responseData.emails;
          const numberOfPages = responseData.numberOfPages;

          console.log("Response:", response.data);
          console.log("attachment1", this.mails[0].attachments[0].filename);
        })
        .catch((error) => {
          // Handle errors
          console.error("Error:", error);
        });
    },
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
      if(this.isTrashRoute || this.isDraftRoute || this.isContactRoute) 
      {
        let req='email'
        if(this.isContactRoute)
        {
            req='contact'
        }
        this.mails.forEach((mail, index) => {
          if (this.select[index]) {
            console.log("Deleting mail:", mail);
            const apiUrl = `http://192.168.116.205:8080/api/mohamed/${req}/delete`
            axios
              .post(apiUrl, {
                emailId: mail.id,
              })
              .then((response) => {
                // Handle the response
                const responseData = response.data;
                console.log("Response:", response.data);
                location.reload();
              })
              .catch((error) => {
                // Handle errors
                console.error("Error:", error);
              });
            }
            }
            )
      
      }
      else
      {
      this.mails.forEach((mail, index) => {
        if (this.select[index]) {
          console.log("Deleting mail:", mail);
          const apiUrl = "http://"}})
      }
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
    downloadAttachment(attachment) {
      const fileContent = attachment.content;
      const blob = new Blob([fileContent], {
        type: "application/octet-stream",
      });

      const link = document.createElement("a");
      link.href = window.URL.createObjectURL(blob);
      link.download = attachment.name;
      link.click();
    },
    openCompose(mail) {
      // Handle opening the Compose component for editing
      console.log("Open Compose for editing:", mail);
      // Example: You might emit an event or update a state to open the Compose component
    },
    toggleContactSelect(index) {
      this.selected_contacts = Object.assign([], this.selected_contacts, {
        [index]: !this.selected_contacts[index],
      });
      console.log(" Selected contacts:", this.selected_contacts);
    },
    getContactCardClass(index) {
      return this.selected_contacts[index] ? "selected-contact" : "";
    },
   openEditDialog(contact) {
      // Open the edit dialog and populate it with the contact's information
      this.editedContact = { ...contact };
      this.selectedContactIndex = this.contacts.indexOf(contact);
      this.editDialogVisible = true;
    },
    closeEditDialog() {
      // Close the edit dialog
      this.editDialogVisible = false;
      this.editedContact = { name: '', contactEmails: [] };
      this.selectedContactIndex = null;
    },
    updateContact() {
      // Update the contact and close the edit dialog
      console.log('Updating contact:', this.editedContact)
      if (this.selectedContactIndex !== null) {

        this.$set(this.contacts, this.selectedContactIndex, this.editedContact);
        // Make an API call to update the contact on the server if needed
      }
      this.closeEditDialog();
    },
  },
};
</script>

<style scoped>
.selected-card,
.selected-contact {
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
