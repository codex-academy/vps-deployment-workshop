document.addEventListener("DOMContentLoaded", function(){
    const greetingsApp = new Vue({
        el : ".app",
        data : {
            firstName : "",
            language : "",
            message: "",
            greetedPeople : [],
            showGreetedList : false,
            counter : 0
        },
        methods: {
            greet : function() {
                const self = this;
                const params = {
                    firstName: self.firstName,
                    language: self.language
                };
                axios
                    .post("/api/greet", params)
                    .then(function(result){
                        const data = result.data;
                        self.message = data.message;
                        self.counter = data.counter;
                        self.greetedUsers();
                    });
            },
            greetedUsers : function() {
                const self = this;
                axios
                    .get("/api/greeted")
                    .then(function(result){
                        const data = result.data;
                        self.greetedPeople = data;
                        self.counter = self.greetedPeople.length;
                    });
            },
            hideGreeted : function() {
                this.showGreetedList = false;
            },
            showGreeted : function() {
                this.showGreetedList = true;
                this.greetedUsers();
            }
        }
    });

    greetingsApp.greetedUsers();
});