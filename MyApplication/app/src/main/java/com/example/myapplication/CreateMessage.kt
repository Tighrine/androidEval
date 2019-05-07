package com.example.myapplication

import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_create_message.*
import org.json.JSONObject
import java.time.LocalDate

class CreateMessage : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_message)

        var id = intent.getIntExtra("topicId", 1)

        submitMessage.setOnClickListener(){
            val msgJSON = JSONObject()
            msgJSON.put("author", auteur.text)
            msgJSON.put("text", body.text)
            msgJSON.put("date", LocalDate.now().toString())

            val queue = Volley.newRequestQueue(this)

            val url = "${Config.URL}/topics/$id/new"

            val stringRequest = JsonObjectRequest(Request.Method.POST,url,msgJSON,
                Response.Listener {

                }, Response.ErrorListener {

                }
            )
            queue.add(stringRequest)
        }
    }
}
