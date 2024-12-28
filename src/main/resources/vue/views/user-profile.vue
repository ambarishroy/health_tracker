<template id="user-profile">
  <app-layout>
    <div v-if="noUserFound">
      <p> We're sorry, we were not able to retrieve this user.</p>
      <p> View <a :href="'/users'">all users</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noUserFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> User Profile </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateUser()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteUser()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="user.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="user.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-email">Email</span>
            </div>
            <input type="email" class="form-control" v-model="user.email" name="email" placeholder="Email"/>
          </div>
        </form>
      </div>
      <div class="card-footer text-left">
        <ul>
          <li v-for="activity in activities" :key="activity.id">
            {{ activity.description }} for {{ activity.duration }} minutes
          </li>
          <li v-for="bp in bloodpressure" :key="bp.id">
            Blood Pressure: {{ bp.category }}
          </li>
          <li v-for="bmiData in bmi" :key="bmiData.id">
            BMI: {{ bmiData.calculatedBMI }}
          </li>
          <li v-for="step in steps" :key="step.id">
            Steps: {{ step.status }}
          </li>
          <li v-for="calorie in calorie" :key="calorie.id">
            Calorie: {{ calorie.calculatedCalorie }}
          </li>
          <li v-for="rate in rate" :key="rate.id">
            Rating: {{ rate.rating || "No rating available" }}/5
          </li>
        </ul>
        <p v-if="activities.length === 0 && bloodpressure.length === 0 && bmi.length === 0 && steps.length === 0 && calorie.length === 0 && rate.length === 0">
          No data available for this user.
        </p>
      </div>


    </div>
  </app-layout>
</template>

<script>
app.component("user-profile", {
  template: "#user-profile",
  data: () => ({
    user: null,
    noUserFound: false,
    activities: [],
    bloodpressure: [],
    bmi: [],
    rate: [],
    steps: [],
    calorie: []
  }),
  created: function () {
    const userId = this.$javalin.pathParams["user-id"];
    const url = `/api/users/${userId}`
    axios.get(url)
        .then(res => this.user = res.data)
        .catch(error => {
          console.log("No user found for id passed in the path parameter: " + error)
          this.noUserFound = true
        })
    axios.get(url + `/activities`)
        .then(res => this.activities = res.data)
        .catch(error => {
          console.log("No activities added yet (this is ok): " + error)
        })
    axios.get(url + `/bloodpressure`)
        .then((res) => {
          console.log("Raw Bloodpressure data:", res.data);

          const data = Array.isArray(res.data) ? res.data : [res.data];
          this.bloodpressure = data.filter(bp => bp.category && bp.category.trim() !== "");
          console.log("Filtered Bloodpressure data:", this.bloodpressure);
        })
        .catch((error) => {
          console.log("No bloodpressure data available or an error occurred:", error);
        });
    axios.get(url + `/bmi`)
        .then((res) => {
          console.log("Raw BMI data:", res.data); // Debug the API response
          const data = Array.isArray(res.data) ? res.data : [res.data]; // Normalize to array
          this.bmi = data.filter((bmi) => bmi.calculatedBMI); // Filter valid entries
          console.log("Filtered BMI data:", this.bmi);
        })
        .catch((error) => {
          console.log("No BMI data added yet (this is ok): " + error);
        });
    axios.get(url + `/steps`)
        .then((res) => {
          console.log("Raw steps data:", res.data); // Debug the API response
          const data = Array.isArray(res.data) ? res.data : [res.data]; // Normalize to array
          this.steps = data.filter((steps) => steps.status); // Filter valid entries
          console.log("Filtered steps data:", this.steps);
        })
        .catch((error) => {
          console.log("No steps data added yet (this is ok): " + error);
        });
    axios.get(url + `/calorie`)
        .then((res) => {
          console.log("Raw calorie data:", res.data); // Debug the API response
          const data = Array.isArray(res.data) ? res.data : [res.data]; // Normalize to array
          this.calorie = data.filter((cal) => cal.calculatedCalorie); // Filter valid entries
          console.log("Filtered steps data:", this.calorie);
        })
        .catch((error) => {
          console.log("No steps data added yet (this is ok): " + error);
        });
    axios.get(url + `/ratings`)
        .then((res) => {
          console.log("Raw ratings data:", res.data); // Log the raw response

          // Normalize response to array
          const data = Array.isArray(res.data) ? res.data : [res.data];

          // Assign the normalized array to `rate`
          this.rate = data;

          console.log("Normalized rating data:", this.rate);
        })
        .catch((error) => {
          console.log("No rating data added yet (this is ok): " + error);
        });

  },
  methods: {
    updateUser: function () {
      const userId = this.$javalin.pathParams["user-id"];
      const url = `/api/users/${userId}`
      axios.patch(url,
          {
            name: this.user.name,
            email: this.user.email
          })
          .then(response =>
              this.user.push(response.data))
          .catch(error => {
            console.log(error)
          })
      alert("User updated!")
    },
    deleteUser: function () {
      if (confirm("Do you really want to delete?")) {
        const userId = this.$javalin.pathParams["user-id"];
        const url = `/api/users/${userId}`
        axios.delete(url)
            .then(response => {
              alert("User deleted")
              //display the /users endpoint
              window.location.href = '/users';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    }

  }
});
</script>
