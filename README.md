The API endpoints and post and patch JSON are:

***User data***
Get all users- https://healthtracker-production-b3a5.up.railway.app/api/users
Get user by ID- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}
Add new user- https://healthtracker-production-b3a5.up.railway.app/api/users
Delete existing user- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}
Patch existing user- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}
POST json- 
{
    "name": "Donald Trump",
    "age": 54,
    "email": "DonamldTrump@gmail.com"
}
PATCH json-
{
    "name": "Donald J Trump",
    "age": 54,
    "email": "DonamldTrump@gmail.com"
}

***Rating endpoints***
Get all users who have rated- https://healthtracker-production-b3a5.up.railway.app/api/ratings
Get user by ID who have rated- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/ratings
Add new user rating- https://healthtracker-production-b3a5.up.railway.app/api/ratings
Delete existing user rating- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/ratings
Patch existing user rating- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/ratings
POST json-
{
    "rating" : 2,
    "userId" : 1
}
PATCH json-
{
    "rating" : 4
}

***BMI endpoints***
Get all users who want calculated BMI- https://healthtracker-production-b3a5.up.railway.app/api/bmi
Get user by ID who want calculated BMI- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/bmi
Add new user BMI- https://healthtracker-production-b3a5.up.railway.app/api/bmi
Delete existing user BMI- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/bmi
Patch existing user BMI- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/bmi
POST json-
{
    "weight" : 67,
    "height" : 1.8,
    "userId": 2
}
PATCH json-
{
    "weight" : 77,
    "height" : 1.8
}

***Calorie endpoints***
Get all users who want calculated calorie- https://healthtracker-production-b3a5.up.railway.app/api/calorie
Get user by ID who want calculated calorie- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/calorie
Add new user calorie- https://healthtracker-production-b3a5.up.railway.app/api/calorie
Delete existing user calorie- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/calorie
Patch existing user calorie- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/calorie
POST json-
{
    "weight" : 67,
    "height" : 1.8,
    "velocity" : 1.5,
    "userId": 2
}
PATCH json-
{
    "weight" : 87,
    "height" : 1.8,
    "velocity" : 1.5
}

***Steps tracking***
Get all users who track steps- https://healthtracker-production-b3a5.up.railway.app/api/steps
Get user by ID who track steps- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/steps
Add new user who want to track steps- https://healthtracker-production-b3a5.up.railway.app/api/steps
Delete existing user steps tracking- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/steps
Patch existing user steps tracking- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/steps
POST json-
{
    "steps" : 6107,
    "target" : 11000,
    "userId": 2
}
PATCH json-
{
    "steps" : 6187,
    "target" : 11000
}

***Blood pressure tracking***
Get all users who monitor their BP- https://healthtracker-production-b3a5.up.railway.app/api/bloodpressure
Get user by ID who monitor their BP- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/bloodpressure
Add new user who want to monitor their BP- https://healthtracker-production-b3a5.up.railway.app/api/bloodpressure
Delete existing user BP- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/bloodpressure
Patch existing user BP- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/bloodpressure
POST json-
{
    "lowerval": 72,
    "upperval": 116,
    "userId": 3
}
PATCH json-
{
    "lowerval": 82,
    "upperval": 126
}

***Activity tracking***
Get all users who added activities- https://healthtracker-production-b3a5.up.railway.app/api/activities
Get user by ID who added activities- https://healthtracker-production-b3a5.up.railway.app/api/users/{user-id}/activities
Add new user for adding activities- https://healthtracker-production-b3a5.up.railway.app/api/activities
POST json-
{
    "description": "Running",
    "duration": 19.5,
    "calories": 122,
    "started": "2020-06-10T06:59:27.258Z",
    "userId": 4
}

