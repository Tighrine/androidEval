let express = require('express'),
    router = express.Router();

let topicsController = require('./../controller/topicsController');

/**
 * (GET Method)
 */
router.get('/topics', topicsController.list_topics);

router.get('/topics/:id', topicsController.list_topic_mids);

router.get('/topics/:id/:mid', topicsController.list_topic_mid);

/**
 * (POST Method)
 */

router.post('/topics/new', topicsController.create_topic);

router.post('/topics/:id/new', topicsController.create_topic_msg);


module.exports = router;