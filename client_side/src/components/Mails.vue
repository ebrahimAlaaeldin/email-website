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
        <v-col v-if="isContactRoute" v-for="(contact, index) in contacts" :key="index" class="mb-4">
          <v-card :class="getContactCardClass(index)">
            <v-card-title>{{ contact.name }}</v-card-title>
            <v-card-subtitle>{{ contact.email }}</v-card-subtitle>
            <v-btn @click="toggleContactSelect(index)">Select</v-btn> <!-- Add select button -->
          </v-card>
        </v-col>

          <v-col
            v-if="!isContactRoute"
            v-for="(mail, index) in mails"
            :key="index"
            class="mb-4"
          >
            <v-card :class="getCardClass(index)">
              <v-card-title>{{ mail.subject }}</v-card-title>
              <v-card-subtitle>{{ mail.sender }}</v-card-subtitle>
              <v-card-text>{{ mail.content }}</v-card-text>
              <v-list>
                <v-list-item
                  v-for="(attachment, attachmentIndex) in mail.attachments"
                  :key="attachmentIndex"
                >
                  <v-list-item-icon>
                    <v-icon>mdi-attachment</v-icon>
                  </v-list-item-icon>
                  <v-list-item-content>
                    {{ attachment.name }}
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
                  :defaultAttachments="mail.attachments || []"
                />
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
            <v-list>
              <v-list-item
                v-for="(
                  attachment, attachmentIndex
                ) in selectedMail.attachments"
                :key="attachmentIndex"
              >
                <v-list-item-icon>
                  <v-icon>mdi-attachment</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  {{ attachment.name }}
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

  </v-app>
</template>

<script>
import Navbar from "@/components/SideBar.vue";
import Header from "@/components/Header";
import Compose from "@/components/Compose.vue";
import { mapGetters } from 'vuex';

export default {
  watch: {
    '$route': function (to, from) {
      // This will be called whenever the route changes
      console.log('Route changed:', to, from);
      if (to.params.foldername === 'inbox') {
        console.log('Inbox route');
      }
      else if (to.params.foldername === 'trash') {
        console.log('Trash route');
      }
      else if (to.params.foldername === 'draft') {
        console.log('Draft route');
      }
      else if (to.name === 'contact') {
        console.log('Contact route');
      }
      else if (to.params.foldername === 'sent') {
        console.log('Sent route');
      }
      else if (to.params.foldername !== 'inbox' && to.params.foldername !== 'trash' && to.params.foldername !== 'draft' && to.name !== 'contact' && to.params.foldername !== 'sent' ){
        console.log('Folder route');
      }
    },
    'getHashMap': function (newHashMap) {
      // Log the hashmap whenever it changes
      console.log('Hashmap changed:', newHashMap);
    }
  },
  name: "Inbox",
  components: { Navbar, Header, Compose },
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
        {
          subject: "Subject 1",
          sender: "Sender 1",
          content: "Content 1",
          attachments: [
            {
              name: "Attachment1.txt",
              content: "File content for Attachment1",
            },
            {
              name: "Attachment2.txt",
              content: "File content for Attachment2",
            },
          ],
        },
        {
          subject: "Subject 2",
          sender: "Sender 2",
          content: "Content 2",
          attachments: [
            {
              name: "Attachment3.txt",
              content: "File content for Attachment3",
            },
          ],
        },
        {
          subject: "Subject 3",
          sender: "Sender 3",
          content: "Content 3",
        },
        {
          subject: "Subject 4",
          sender: "Sender 4",
          content: "Content 4",
          attachments: [
            {
              name: "Attachment4.txt",
              content: "File content for Attachment4",
            },
          ],
        },
        {
          subject: "Subject 5",
          sender: "Sender 5",
          content: "Content 5",
        },
      ],
      contacts: [
        {
          name: "Contact 1",
          email: "hi@fd.com",
        },
        {
          name: "Contact 2",
          email: "HDJ",
        },
        {
          name: "Contact 3",
          email: "HDJ",
        },
        {
          name: "Contact 4",
          email: "HDJ",
        },
        {
          name: "Contact 5",
          email: "HDJ",
        },
      ],
      select: [],
      selected_contacts: [],
      dialogVisible: false,
      selectedMail: null,
      trashRouteName: "trash",
      contactRouteName: "contact",
      draftRouteName: "draft",
    };
  },
  computed: {
     ...mapGetters(['getHashMap']),
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
      return this.$route.params.foldername === this.draftRouteName;
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
      const hashMapFromStore = this.getHashMap;
    console.log('HashMap from store:', hashMapFromStore);
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
      console.log(" Selected contacts:", this.selected_contacts)
      },
    getContactCardClass(index) {

      return this.selected_contacts[index] ? "selected-contact" : "";
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