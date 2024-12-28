<template id="user-ratings">
  <app-layout>
  <div class="uploader container mt-5">
    <h1 class="mb-4 text-center">Upload Ratings CSV</h1>
    <div class="d-flex justify-content-center align-items-center">
      <label for="file-upload" class="btn btn-primary">
        <i class="fas fa-upload"></i> Choose File
      </label>
      <input
          type="file"
          id="file-upload"
          ref="file"
          class="d-none"
          @change="handleFileUpload"
          accept=".csv"
      />
      <button
          class="btn btn-success ms-3"
          :disabled="csvData.length === 0"
          @click="uploadData"
      >
        <i class="fas fa-paper-plane"></i> Upload Data
      </button>
    </div>
    <div
        v-if="statusMessage"
        :class="['mt-3', 'text-center', statusClass === 'success' ? 'text-success' : 'text-danger']"
    >
      {{ statusMessage }}
    </div>
  </div>
    </app-layout>
</template>

<script>
app.component("user-ratings", {
  template: "#user-ratings",
  data() {
    return {
      csvData: [], // Array to store parsed CSV data
      statusMessage: "", // Feedback message for users
      statusClass: "", // Styling class for the feedback message
    };
  },
  methods: {
    handleFileUpload() {
      const file = this.$refs.file.files[0];

      if (!file) {
        this.statusMessage = "Please select a file.";
        this.statusClass = "error";
        return;
      }

      const reader = new FileReader();

      reader.readAsText(file);

      reader.onload = () => {
        try {
          const content = reader.result;
          const rows = content.split("\n").map((row) => row.split(","));
          const headers = rows.shift().map((header) => header.trim());

          if (
              !headers.includes("rating") ||
              !headers.includes("userId")
          ) {
            throw new Error(
                "Invalid CSV format. Required headers: rating, userId."
            );
          }

          this.csvData = rows.map((row) => {
            const data = {};
            headers.forEach((header, index) => {
              data[header] = row[index]?.trim();
            });
            return data;
          });

        } catch (error) {
          this.statusMessage = `Error: ${error.message}`;
          this.statusClass = "error";
        }
      };

      reader.onerror = () => {
        this.statusMessage = "Error reading the file.";
        this.statusClass = "error";
      };
    },
    async uploadData() {
      const endpoint = "http://localhost:7001/api/ratings";

      try {
        const responses = await Promise.all(
            this.csvData.map((entry) =>
                axios.post(endpoint, {
                  rating: parseFloat(entry.rating),
                  userId: parseInt(entry.userId),
                })
            )
        );

        const successfulUploads = responses.filter(
            (response) => response.status === 200
        );

        this.statusMessage = `Records successfully uploaded.`;
        this.statusClass = "success";
      } catch (error) {
        this.statusMessage = `Upload failed: ${error.message}`;
        this.statusClass = "error";
      }
    },
  },
});
</script>
