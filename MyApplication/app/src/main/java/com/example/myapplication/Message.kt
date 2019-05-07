package com.example.myapplication

class Message {

    var id: Int? = null
    var author: String? = null
    var date: String? = null
    var content: String? = null

    constructor(id: Int, author: String, date: String ,content: String){
        this.id = id
        this.author = author
        this.date = date
        this.content = content
    }

}