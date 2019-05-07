package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_topic.*
import org.json.JSONObject
class TopicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        submitTopic.setOnClickListener(){
            val topicJSON = JSONObject()
            topicJSON.put("topic_title",topicTitle.text)
            topicJSON.put("topic_content", topicContent.text)

            val queue = Volley.newRequestQueue(this)

            val url = "${Config.URL}/topics/new"

            val stringRequest = JsonObjectRequest(Request.Method.POST,url,topicJSON,
                Response.Listener {
                        response ->
                    Toast.makeText(this, "Topic ajouté avec succé", Toast.LENGTH_LONG).show()
                }, Response.ErrorListener { error: VolleyError ->
                    Toast.makeText(this,"Error $error.message", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(stringRequest)
        }
    }
}
