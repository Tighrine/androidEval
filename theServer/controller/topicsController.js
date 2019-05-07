var exports = module.exports = {};

var fs = require('fs');
var topicsJsonFile = JSON.parse(fs.readFileSync('./json/topics.json', 'utf8'));

exports.list_topics = function(req, res) {

    if (topicsJsonFile.length === 0) {
        res.json({message: 'Topics list is empty! Please add new topic!'});
    } else {
        res.json(topicsJsonFile);
    }
};

exports.list_topic = function(req, res) {
    let topics_messages_IDs = [];

    if (topicsJsonFile.length === 0) {
        res.json({message: 'Topics list is empty! Please add new topic!'});
    } else {
        if (req.params.id) {
            topicsJsonFile.forEach((topic) => {
                if (topic.id === req.params.id) {
                    topic.topic_messages.forEach((topic_msg) => {
                        topics_messages_IDs.push({id: topic_msg.id});
                    });

                    res.json(topics_messages_IDs);
                }
            });
        }
    }
};

exports.list_topic_mids = function(req, res) {

    if (topicsJsonFile.length === 0) {
        res.json({message: 'Topics list is empty! Please add new topic!'});
    } else {
        if (req.params.id) {
            topicsJsonFile.forEach((topic) => {
                if (topic.id === req.params.id) {
                    res.json(topic.topic_messages)
                }
            });
        }
    }
};

exports.list_topic_mid = function(req, res) {

    if (topicsJsonFile.length === 0) {
        res.json({message: 'Topics list is empty! Please add new topic!'});
    } else {
        if (req.params.id && req.params.mid) {
            topicsJsonFile.forEach((topic) => {
                if (topic.id === req.params.id) {
                    topic.topic_messages.forEach((msg) => {
                        if(msg.id === req.params.mid)
                            res.json(msg)
                    })
                }
            });
        }
    }
};

exports.create_topic = function(req, res) {
    let newTopic = {};

    newTopic.id = (topicsJsonFile.length + 1).toString();

    if (req.body.topic_title) {
        newTopic.topic_title = req.body.topic_title;
    }

    if (req.body.topic_content) {
        newTopic.topic_content = req.body.topic_content;
    }

    newTopic.topic_messages = [];

    topicsJsonFile.push(newTopic);

    fs.writeFile ("./json/topics.json", JSON.stringify(topicsJsonFile), function(err) {
            if (err) throw err;
            res.json('Topic added successfully!');
        }
    );
};

exports.create_topic_msg = function(req, res) {
    let newTopicMessage = {};

    if (req.params.id) {
        if (req.body.author) {
            newTopicMessage.author = req.body.author;
        }

        if (req.body.date) {
            newTopicMessage.date = req.body.date;
        }

        if (req.body.text) {
            newTopicMessage.text = req.body.text;
        }

        topicsJsonFile.forEach((topic) => {
            if (topic.id === req.params.id) {


                if (topic.topic_messages.length === 0) {
                    newTopicMessage.id = "1";
                    topic.topic_messages.push(newTopicMessage);

                } else {
                    newTopicMessage.id = (topic.topic_messages.length + 1).toString();
                    topic.topic_messages.push(newTopicMessage);
                }
            }
        });
    }

    fs.writeFile ("./json/topics.json", JSON.stringify(topicsJsonFile), function(err) {
            if (err) throw err;
            res.json('Topic message added successfully!');
        }
    );
};