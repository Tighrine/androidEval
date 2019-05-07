package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private var listTopics = ArrayList<Topic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val queue = Volley.newRequestQueue(this)
        val url = "${Config.URL}/topics"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val jsonArray = JSONArray(response)
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var obj = jsonArray.getJSONObject(jsonIndex)
                    var temp = Topic(obj.getInt("id"), obj.getString("topic_title"), obj.getString("topic_content"))
                    listTopics.add(temp)

                }
                var topicsAdapter = TopicAdapter(this, listTopics)
                topicListView.adapter = topicsAdapter
            },
            Response.ErrorListener { txtView.text = "That didn't work!" })
        queue.add(stringRequest)


        topicListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(this, "Click on $position", Toast.LENGTH_SHORT).show()
            var i = Intent(this, MessageActivity::class.java)
            i.putExtra("id", id + 1)
            startActivity(i)
        }

        addTopic.setOnClickListener(){
            var i = Intent(this, TopicActivity::class.java)
            startActivity(i)
        }

    }

    inner class TopicAdapter : BaseAdapter {

        private var topicsList = ArrayList<Topic>()
        private var context: Context? = null

        constructor(context: Context, topicsList: ArrayList<Topic>) : super() {
            this.topicsList = topicsList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val cv: CustomView

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.topic_layout, parent, false)
                cv = CustomView(view)
                view?.tag = cv
            } else {
                view = convertView
                cv = view.tag as CustomView
            }

            cv.tvTitle.text = topicsList[position].title
            cv.tvContent.text = topicsList[position].content

            return view
        }

        override fun getItem(position: Int): Any {
            return topicsList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return topicsList.size
        }
    }

    private class CustomView(view: View?) {
        val tvTitle: TextView
        val tvContent: TextView

        init {
            this.tvTitle = view?.findViewById(R.id.tvTitle) as TextView
            this.tvContent = view?.findViewById(R.id.tvContent) as TextView
        }
    }
}