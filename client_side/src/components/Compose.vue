<template>
  <div class="text-center">
    <v-btn color="black" @click="dialog = true">Compose</v-btn>

    <v-dialog v-model="dialog" width="500" persistent>
      <v-card>
        <v-card-title class="grey lighten-2">
          Compose
          <v-spacer></v-spacer>
          <v-btn color="error" text @click="close"> Close </v-btn>
        </v-card-title>

        <v-card-text>
          <v-form class="px-3" ref="form">
            <v-text-field
              label="To"
              v-model="To"
              prepend-icon="person"
              autocomplete="off"
              :rules="emailRules"
              hint="For multiple receivers, separate handles with a comma ',' "
            ></v-text-field>

            <v-text-field
              label="Subject"
              v-model="Subject"
              prepend-icon="edit"
              autocomplete="off"
              :rules="inputRules"
            ></v-text-field>
            <v-textarea
              label="Message"
              v-model="content"
              prepend-icon="message"
              autocomplete="off"
              :rules="inputRules"
            ></v-textarea>
            <v-select
              v-model="priority"
              :items="['Superhigh', 'high', 'medium', 'low']"
              label="Priority"
              dense
            ></v-select>

  <v-row v-if="defaultAttachments?.length > 0">
    <v-col>
      <h4>Attachments:</h4>
      <ul>
        <li v-for="(attachment, index) in defaultAttachments" :key="index">
          <a :href="attachment.path" target="_blank" download>{{ attachment.filename }}</a>
          <v-btn text color="red" @click="removeAttachment(index)">Remove</v-btn>
        </li>
      </ul>
    </v-col>
  </v-row>

            <v-row>
              <v-btn color="success" @click="submit">Send</v-btn>
              <v-spacer></v-spacer>
              <v-file-input
                id="choose"
                multiple
                show-size
                counter
                placeholder="Choose File"
                truncate-length="15"
                ref="myFileInput"
              ></v-file-input>
              <v-spacer></v-spacer>
              <v-btn text color="yellow" @click="draft">Draft</v-btn>
            </v-row>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { PostEmailDto, Attachment } from "@/components/classes.js";
import axios from "axios";

export default {
  props: {
    username: String,
    fromDraft: Boolean,
    defaultMid: String,
    defaultTo: String,
    defaultSubject: String,
    defaultContent: String,
    defaultPriority: String,
    defaultAttachments: Array,
  },
  data() {
    return {
      Mid: this.defaultMid || "",
      To: this.defaultTo || "",
      From: this.username,
      Subject: this.defaultSubject || "",
      content: this.defaultContent || "",
      priority: this.defaultPriority || "low",
      attachments: this.defaultAttachments || [],
      dialog: false,
      date: new Date().toISOString(),
      inputRules: [(v) => !!v || "This field is required"],
      emailRules: [
        (v) => !!v || "This field is required",
        (v) => {
          const emails = v.split(",").map((email) => email.trim());
          return (
            emails.every((email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) ||
            "Invalid email format. Ensure each email has '@' and '.com'"
          );
        },
      ],
    };
  },
  methods: {
    close() {
      console.log(this.defaultAttachments);
      console.log(this.attachments);
      // this.defaultAttachments=this.attachments;
      console.log("Close button clicked");
      this.dialog = false;
      this.clear();
      this.$refs.myFileInput.reset();
            window.location.reload();

    },
    clear() {
      this.To = "";
      this.Subject = "";
      this.content = "";
      this.priority = "low";
    },
 removeAttachment(index) {
      // Remove the specified attachment from the defaultAttachments array
      // this.defaultAttachments.splice(index, 1);
      this.attachments.splice(index, 1);
    },
    submit() {
      // // let array = this.To.split(",");
      // if (this.$refs.form.validate()) {
        console.log("Send button clicked");
        console.log("defaultMid:", this.defaultMid);
        switch (this.priority) {
          case "Superhigh":
            this.priority = 1;
            break;
          case "high":
            this.priority = 2;
            break;
          case "medium":
            this.priority = 3;
            break;
          case "low":
            this.priority = 4;
            break;
          default:
            break;
        }

        var bodyFormData = new FormData();
        let mail = new PostEmailDto(
          this.Mid,
          this.From,
          this.To,
          this.Subject,
          this.content,
          this.date,
          this.priority,
          this.attachments,
          this.isDraft
        );
        let g = document.getElementById("choose").files;
        // g = Array.from(g);
        // this.attachments.forEach((attachment) => {
        //   g.append(attachment);
        // });
        let url=`http://192.168.116.205:8080/api/${this.username}/email/create`
        bodyFormData.append("file", g[0]);
        if (this.fromDraft) {
          url=`http://192.168.116.205:8080/api/${this.username}/email/draft`
          bodyFormData.append("emailId", mail.emailId);
        } else {
          bodyFormData.append("emailId", -1);
        }
        bodyFormData.append("sender", mail.sender);
        bodyFormData.append("receivers", JSON.stringify(mail.receivers));
        bodyFormData.append("subject", mail.subject);
        bodyFormData.append("body", mail.body);
        bodyFormData.append("timestamp", mail.timestamp);
        bodyFormData.append("priority", 1);
        bodyFormData.append("isDraft", 0);

        var size = 0;
        for (let i = 0; i < g.length; i++) {
          size = size + g[i].size;
        }

        if (size > 1001000000) {
          alert("You exceeded the maximum size for the attachments which is 1 giga. Kindly remove the attachments.");
        } else {
          if (size > 20000000) {
            alert("Uploading high-size attachments will take a few moments. Press OK to continue.");
          }
          mail.attachments = g;
          fetch(url, {
            method: "POST",
            body: bodyFormData,
          })
            .then((response) => {
              if (response.data) {
                this.clear();
                this.dialog = false;
                this.$refs.myFileInput.reset();
                g = null;
                window.location.reload();
              } else {
                console.log("sent:" + bodyFormData);
                alert("One or all receivers are wrong");
              }
            })
            .catch((error) => {
              console.error("Error sending mail:", error);
            });
        }
      // }
    },

    draft() {
      console.log("Draft button clicked");
      // let array = this.To.split(",");
      // if (this.$refs.form.validate()) {
        switch (this.priority) {
          case "Superhigh":
            this.priority = 1;
            break;
          case "high":
            this.priority = 2;
            break;
          case "medium":
            this.priority = 3;
            break;
          case "low":
            this.priority = 4;
            break;
          default:
            break;
        }

        var bodyFormData = new FormData();
        let mail = new PostEmailDto(
          this.Mid,
          this.From,
          this.To,
          this.Subject,
          this.content,
          this.date,
          this.priority,
          this.attachments,
          this.isDraft
        );
let g = document.getElementById("choose").files;
        this.attachments.forEach((attachment) => {
          g.append(attachment);
        });

        bodyFormData.append("file", g[0]);
        let url="http://192.168.116.205:8080/api/${this.username}/email/create"
        if (this.fromDraft) {
          url="http://192.168.116.205:8080/api/${this.username}/email/draft"
          bodyFormData.append("emailId", mail.emailId);
        } else {
          bodyFormData.append("emailId", -1);
        }
        bodyFormData.append("sender", mail.sender);
        bodyFormData.append("receivers", JSON.stringify(mail.receivers));
        bodyFormData.append("subject", mail.subject);
        bodyFormData.append("body", mail.body);
        bodyFormData.append("timestamp", mail.timestamp);
        bodyFormData.append("priority", 1);
        bodyFormData.append("isDraft", 1);

        var size = 0;
        for (let i = 0; i < g.length; i++) {
          size = size + g[i].size;
        }

        if (size > 1001000000) {
          alert("You exceeded the maximum size for the attachments which is 1 giga. Kindly remove the attachments.");
        } else {
          if (size > 20000000) {
            alert("Uploading high-size attachments will take a few moments. Press OK to continue.");
          }
          mail.attachments = g;
          fetch(url, {
            method: "POST",
            body: bodyFormData,
          })
            .then((response) => {
              if (response.data) {
                this.clear();
                this.dialog = false;
                this.$refs.myFileInput.reset();
                g = null;
                window.location.reload();
              } else {
                console.log("sent:" + bodyFormData);
                alert("One or all receivers are wrong");
              }
            })
            .catch((error) => {
              console.error("Error sending mail:", error);
            });
        }
      // }
    },
  },
};
</script>

<style></style>
