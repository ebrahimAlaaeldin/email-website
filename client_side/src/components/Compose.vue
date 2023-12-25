<template>
  <div class="text-center">
    <v-btn color="black" @click="dialog = true">Compose</v-btn>
          
    <v-dialog v-model="dialog" width="500">


      <v-card>
        <v-card-title class="grey lighten-2">
          Compose
          <v-spacer></v-spacer>
          <v-btn color="error" text @click="close">
            close
          </v-btn>
        </v-card-title>

        <v-card-text>
          <v-form class="px-3" ref="form">
            <v-text-field
              label="To"
              v-model="To"
              prepend-icon="person"
              autocomplete="off"
              :rules="emailRules"
              hint="for multiple receivers seperate handles with a comma ',' "
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
import {
  Mail
} from "@/components/classes.js";
import axios from "axios";
export default {
  props: { 
    username: String,
    defaultTo: String,  // Add prop for default receivers
    defaultSubject: String,  // Add prop for default subject
    defaultContent: String,  // Add prop for default body
    defaultPriority: String,  // Add prop for default priority
  },
  // computed: {
  //   // Add computed property for default sender
  //   defaultFrom() {
  //     return this.username;
  //   },
  // },
  data() {
    return {
      To: this.defaultTo || "",  // Use prop value as default or an empty string
      From: this.username,
      Subject: this.defaultSubject || "",  // Use prop value as default or an empty string
      content: this.defaultContent || "",  // Use prop value as default or an empty string
      priority: this.defaultPriority || "low",  // Use prop value as default or "low"

      dialog: false,
      dialogDraft: false,
      date: new Date().toISOString(),
      inputRules: [
        (v) => !!v || "This field is required"
      ],
      emailRules: [
        (v) => !!v || "This field is required",
        (v) => {
          const emails = v.split(',').map(email => email.trim());
          return emails.every(email => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) ||
            "Invalid email format. Ensure each email has '@' and '.com'";
        },
      ],
    };
  },
  methods: {
    close() {
      console.log(this.defaultContent);
      console.log('Close button clicked');
      this.dialog = false;
      this.clear();
      this.$refs.myFileInput.reset();
    },
    clear() {
      (this.To = ""),
      (this.Subject = ""),
      (this.content = ""),
      (this.priority = "low");
    },
    submit() {
      let array = this.To.split(",");
      if (this.$refs.form.validate()) {
        var bodyFormData = new FormData();
        var mail=new Mail(
              this.From,
              array,
              this.Subject,
              this.content,
              this.date,
              this.priority,
              
            );
        bodyFormData.append(
          "mail",
          JSON.stringify(
            mail
            )
          );
        let g = document.getElementById("choose").files;
        for (let i = 0; i < g.length; i++) {
          bodyFormData.append("file", g[i]);
        }
        var formObject = {};
    bodyFormData.forEach(function(value, key){
        formObject[key] = value;
    });

    // Log the object to the console
    console.log(formObject);
        var size = 0;
        for (let i = 0; i < g.length; i++) {
          size = size + g[i].size;
        }
        if (size > 1001000000) {
          alert(
            "You exceeded the maximum size for the attachments which is 1giga ,Kindly Remove the attachments"
          );
        } else {
          if (size > 20000000) {
            alert(
              "Uploading high size attachments will take a few moments >> Press OK to continue"
            );
          }
          axios({
            method: "post",
            url: "http://localhost:8765/observatory/api/mail",
            data: bodyFormData,
            headers: { "Content-Type": "multipart/form-data" },
          }).then((response) => {
            if (response.data) {
              this.clear();
              this.dialog = false;
              this.$refs.myFileInput.reset();
              g = null;
              location.reload();
            } else {
              console.log("sent:"+bodyFormData);
              alert("one or all recievers are wrong");
            }
          });
        }
      }
    },
    draft() {
      let array = this.To.split(",");
      let g = document.getElementById("choose").files;
      if (
        this.To == "" &&
        this.Subject == "" &&
        this.content == "" &&
        g.length == 0
      ) {
        this.dialog = false;
        alert("all data are empty so nothing is saved");
        return;
      }
      var bodyFormData = new FormData();
      bodyFormData.append(
        "mail",
        JSON.stringify(
 new Mail(
              this.From,
              array,
              this.Subject,
              this.content,
              this.date,
              this.priority,
              
            )
        )
      );
      for (let i = 0; i < g.length; i++) {
        bodyFormData.append("file", g[i]);
      }
      var size = 0;
      for (let i = 0; i < g.length; i++) {
        size = size + g[i].size;
      }
      if (size > 1001000000) {
        alert(
          "You exceeded the maximum size for the attachments which is 1giga ,Kindly Remove the attachments"
        );
      } else {
        if (size > 20000000) {
          alert(
            "Uploading high size attachments will take a few moments >> Press OK to continue"
          );
        }
        axios({
          method: "post",
          url: "",
          data: bodyFormData,
          headers: { "Content-Type": "multipart/form-data" },
        });

        this.clear();
        this.dialog = false;
        this.$refs.myFileInput.reset();
        location.reload();
      }
    },
  },
};
</script>

<style></style>
