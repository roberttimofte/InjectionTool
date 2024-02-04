const _ = require('lodash');
const express = require('express')
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 3000;

let users = [];

class User {
    name = "";
    email = "";
    password = "";
    is_admin = "";

    constructor(user) {
        Object.assign(this, user);
    }
}

app.use(cors());

// Configuring body parser middleware
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.post('/user', (req, res) => {
    const user = new User(_.pick(req.body, ["name", "email", "password"]));

    console.log(user);
    users.push(user);

    res.send('User is added to the database');
});

app.listen(port, () => console.log(`Hello world app listening on port ${port}!`));