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
                  :defaultMid="mail?.emailId?.toString() || ''"
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
                :items="folder_names"
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
import { get_email,Contact,Transfer } from "./classes.js";
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
     this.fetchData();

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
      folders: this.$store.getters.getHashMap,
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
    isSentRoute() {
      return (
        this.$route.params.foldername?.toLowerCase() === "sent"
      );},
     editedContactEmailsString() {
      // Combine contact emails into a single string separated by commas
      return this.editedContact.contactEmails.join(', ');
    },
    folder_names: function () {
      // Log the folder names whenever they change
      let folders=this.$store.getters.getHashMap
      let folderNames = Object.values(folders).map(obj => obj.text);
      return folderNames;
    },
    folder_ids: function () {
      // Log the folder ids whenever they change
      let folders=this.$store.getters.getHashMap
      let folderIds = Object.values(folders).map(obj => obj.folderId);
      return folderIds;
    },

  },
methods: {

    search_mails()
    {
      let searchfor=this.$route.params.searchfor
      let searchby=this.$route.params.searchby
      let fid=this.$route.params.FolderId
      if(fid === undefined)
      {
        load_conatcts();
        this.contacts=this.contacts.filter(contact => contact.name.toLowerCase().includes(searchfor.toLowerCase()))
      }
      else{
      const emailObject = new get_email(
        VueCookies.get("userId"),
        fid,
        searchby,
        searchfor,
        this.$store.getters.getSortCriteria,
        this.$store.getters.getPageNumber-1,
        10
      );
        const apiUrl = `http://192.168.116.205:8080/api/${this.username}/email/list`;

      // Send the POST request with the object as the request payload
      axios
        .post(apiUrl, emailObject)
        .then((response) => {
          // Handle the response
          const responseData = response.data;

          this.mails = responseData.emails;
          const numberOfPages = responseData.numberOfPages;
          const sortCriteria= this.$store.getters.getSortCriteria
          let sortedmails=this.mails
          switch(sortCriteria)
          {
            case "sender":
              this.sortedmails.sort((a, b) =>
            a?.sender.localeCompare(b?.sender)
          );
              break;
            case "subject":
              this.sortedmails.sort((a, b) =>
            a?.subject.localeCompare(b?.subject)
          );
              break;
            case "body":
              this.sortedmails.sort((a, b) =>
            a?.content.localeCompare(b?.content)
          );
              break;
            case "timestamp":
              this.sortedmails.sort((a, b) =>
            a?.timestamp.localeCompare(b?.timestamp)
          );
              break;
            case "priority":
              this.sortedmails.sort((a, b) =>
            a?.priority.localeCompare(b?.priority)
          );
              break;
          }
          this.mails=sortedmails
          console.log("Response:", response.data);
          console.log("attachment1", this.mails[0].attachments[0].filename);
        })
        .catch((error) => {
          // Handle errors
          console.error("Error:", error);
        });
        }
    },
    load_conatcts() {
      this.mails = [];
      this.conatcts = [];
      // Define the API endpoint where you want to send the POST request
      const apiUrl = `http://192.168.116.205:8080/api/${this.username}/contacts/list`;

      // Send the POST request with the object as the request payload
      axios
        .get(apiUrl)
        .then((response) => {
          // Handle the response
          const responseData = response.data;

          this.contacts = responseData;
          //sort this.contacts by name
          let sortedList = this.contacts.sort((a, b) =>
            a?.name.localeCompare(b?.name)
          );
          this.contacts = sortedList;
console.log(sortedList);

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
      console.log("folders", this.folders);
      const keyToCheck = to.params.foldername;
      let fids=Object.values(this.$store.getters.getHashMap).map(obj => obj.folderId)
      console.log("fids",fids)
      let namesF=Object.values(this.$store.getters.getHashMap).map(obj => obj.text)
      let fid=fids[namesF.indexOf(keyToCheck)]

      // Create an instance of the get_email class

      const emailObject = new get_email(
        VueCookies.get("userId"),
        fid,
        "body",
        null,
        this.$store.getters.getSortCriteria,
        this.$store.getters.getPageNumber-1,
        10
      );

      // Define the API endpoint where you want to send the POST request
      const apiUrl = `http://192.168.116.205:8080/api/${this.username}/email/list`;

      // Send the POST request with the object as the request payload
      axios
        .post(apiUrl, emailObject)
        .then((response) => {
          // Handle the response
          const responseData = response.data;

          this.mails = responseData.emails;
          const numberOfPages = responseData.numberOfPages;
   const sortCriteria= this.$store.getters.getSortCriteria
          let sortedmails=this.mails
          switch(sortCriteria)
          {
            case "sender":
              this.sortedmails?.sort((a, b) =>
            a?.sender.localeCompare(b?.sender)
          );
              break;
            case "subject":
              this.sortedmails?.sort((a, b) =>
            a?.subject.localeCompare(b?.subject)
          );
              break;
            case "body":
              this.sortedmails?.sort((a, b) =>
            a?.content.localeCompare(b?.content)
          );
              break;
            case "timestamp":
              this.sortedmails?.sort((a, b) =>
            a?.timestamp.localeCompare(b?.timestamp)
          );
              break;
            case "priority":
              this.sortedmails?.sort((a, b) =>
              a?.priority.localeCompare(b?.priority)
            )?.reverse();

              break;
          }
          this.mails=sortedmails
          console.log("Response:", response.data);
          console.log("attachment1", this.mails[0]?.attachments[0]?.filename);
        })
        .catch((error) => {
          // Handle errors
          console.error("Error:", error);
        });
    },
        fetchData() {
      if (this.$route.name === "contact") {
        this.load_conatcts();
      } else {
        this.load_mails(this.$route);
      }
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
      if(this.isTrashRoute || this.isDraftRoute || this.isSentRoute ) 
      {
        console.log("HEre")
        let req='email'
        let ids=[]
        this.mails.forEach((mail, index) => {
          if (this.select[index]) {
            console.log("Deleting mail:", mail);
            ids.push(mail.emailId)
            }
          })
            const apiUrl = `http://192.168.116.205:8080/api/${this.username}/${req}/delete?emailId=${ids}`
            axios
              .post(apiUrl)
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
      else if(this.isContactRoute)
      {
        let ids=[]
        console.log("Deleting selected contacts");
        console.log("selected contacts",this.selected_contacts)
        this.contacts.forEach((contact, index) => {
           if (this.selected_contacts[index]) {
            console.log("Deleting contact:", contact);
            ids.push(contact.contactId)
           }

        })
         console.log("ids",ids)
          
            const apiUrl = `http://192.168.116.205:8080/api/${this.username}/contacts/delete?contactId=${ids}`;
            axios
              .post(apiUrl)
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
      let selected_emails_ids = [];
      let folders=this.$store.getters.getHashMap;
      this.mails.forEach((mail, index) => {
        if (this.select[index]) {
          console.log("Restoring mail:", mail);
          selected_emails_ids.push(mail.emailId);
        }
      });
      console.log("selected_emails_ids", selected_emails_ids);
      const apiUrl = `http://192.168.116.205:8080/api/${this.username}/email/move`;
      const transferObject = new Transfer(
        VueCookies.get("userId"),
        selected_emails_ids,
        1
      );
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
      console.log("Copying selected emails to:", this.selectedFolder);
      if (this.selectedFolder) {
let fids=Object.values(this.$store.getters.getHashMap).map(obj => obj.folderId)
      console.log("fids",fids)
      let namesF=Object.values(this.$store.getters.getHashMap).map(obj => obj.text)
      let fid=fids[namesF.indexOf(this.selectedFolder)]

        let ids=[]
        this.mails.forEach((mail, index) => {
          if (this.select[index]) {
            console.log("Copying mail:", mail);
            ids.push(mail.emailId)
          }
        })
        const apiUrl = `http://192.168.116.205:8080/api/${this.username}/email/copy?emailId=${ids}&folderId=${fid}`;
        axios.post(apiUrl)
        .then((response) => {
          // Handle the response
          const responseData = response.data;
          console.log("Response:", response.data);
          location.reload();
        })
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

        axios.post(`http://192.168.116.205:8080/api/${this.username}/contacts/rename?contactId=${this.editedContact.contactId}&newName=${this.editedContact.name}`)
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
