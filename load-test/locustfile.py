from locust import HttpUser, task, between, TaskSet


class UserBehavior(TaskSet):
    @task
    def get_user_detail(self):
        user_id = 1
        self.client.get(f'/')

    @task
    def home(self):
        self.client.get('/')


class LocustUser(HttpUser):
    host = "http://49.50.166.60:8080"
    tasks = [UserBehavior]
    wait_time = between(1, 4)
