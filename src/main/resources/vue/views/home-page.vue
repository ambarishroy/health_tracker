<!-- the "home-page" element is passed as a parameter to VueComponent in the JavalinConfig file -->
<template id="home-page">
  <app-layout>
    <div class="row">
      <div class="col">
        <div class="card">
          <h5 class="card-header">Registered Users</h5>
          <div class="card-body">
            <h5 class="card-title">{{users.length}} users</h5>
            <a href="/users" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">Blood pressure</h5>
          <div class="card-body">
            <h5 class="card-title">{{bloodpressure.length}} entries</h5>
            <a href="/bloodpressure" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">BMI</h5>
          <div class="card-body">
            <h5 class="card-title">{{bmi.length}} entries</h5>
            <a href="/bmi" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">User ratings</h5>
          <div class="card-body">
            <h5 class="card-title">{{ratings.length}} entries</h5>
            <a href="/ratings" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">Steps</h5>
          <div class="card-body">
            <h5 class="card-title">{{steps.length}} entries</h5>
            <a href="/steps" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">Calories burnt</h5>
          <div class="card-body">
            <h5 class="card-title">{{calorie.length}} entries</h5>
            <a href="/calorie" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
    </div>

  </app-layout>
</template>

<script>
app.component('home-page',
    {
      template: "#home-page",
      data: () => ({
        users: [],
        activities: [],
        bloodpressure: [],
        bmi: [],
        rating: [],
        steps: [],
        calorie: []
      }),
      created() {
        axios.get("/api/users")
            .then(res => this.users = res.data)
            .catch(() => alert("Error while fetching users"));
        axios.get("/api/bloodpressure")
            .then(res => this.bloodpressure = res.data)
            .catch(() => alert("Error while fetching bloodpressure data"));
        axios.get("/api/bmi")
            .then(res => this.bmi = res.data)
            .catch(() => alert("Error while fetching bmi data"));
        axios.get("/api/ratings")
            .then(res => this.ratings = res.data)
            .catch(() => alert("Error while fetching ratings data"));
        axios.get("/api/steps")
            .then(res => this.steps = res.data)
            .catch(() => alert("Error while fetching steps data"));
        axios.get("/api/calorie")
            .then(res => this.calorie = res.data)
            .catch(() => alert("Error while fetching calorie data"));
      }
    });
</script>

