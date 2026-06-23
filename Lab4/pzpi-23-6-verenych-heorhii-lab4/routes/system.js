const express = require('express');
const router  = express.Router();
const os      = require('os');

router.get('/instance', (req, res) => {
    res.json({
        status:                 'OK',
        service:                'SportManager Backend',
        currentBackendInstance: os.hostname(),
        pid:                    process.pid,
        message:                'This request was processed by one backend container behind Nginx Load Balancer',
        time:                   new Date().toISOString(),
    });
});

module.exports = router;
