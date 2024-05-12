const express = require('express');
const app = express();
const port = 3000;
const HashStoreService = require('./hashStoreService');

app.get('/', (req, res) => {
    res.send('Home page');
});

app.post('/getHash', (req, res) => {
    HashStoreService.getHash("100").then(data => {
        res.status(data.status).send({message: data.message, data: data.data});
    }).catch(error => {
        res.status(error.status).send({message: error.message});
    });
});

app.post('/addNewHash', (req, res) => {
    data = {
        itemId: "100",
        hashValue: "dasfjkldajfsdkj",
    }
    HashStoreService.addNewHash(data).then(data => {
        res.send({message: data.message, data: data.data});

    }).catch(error => {
        res.send({message: error.message});
    });
});

app.listen(port, () => {
    console.log('Server started. Listening on port', port);
})