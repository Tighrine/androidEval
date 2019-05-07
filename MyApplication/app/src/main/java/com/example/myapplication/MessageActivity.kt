package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_message.*
import org.json.JSONArray

class MessageActivity : AppCompatActivity() {

    private var listMessages = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val i = intent
        val id = i?.getIntExtra("id", 1)
        val queue = Volley.newRequestQueue(this)
        val url = "${Config.URL}/topics/$id"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val jsonArray = JSONArray(response)
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    var obj = jsonArray.getJSONObject(jsonIndex)
                    var temp = Message(obj.getInt("id"), obj.getString("author"), obj.getString("date"), obj.getString("text"))
                    listMessages.add(temp)

                }
                var messageAdapter = MessageAdapter(this, listMessages)
                msgListView.adapter = messageAdapter
            },
            Response.ErrorListener { //txtView.text = "That didn't work!"
                 })
        queue.add(stringRequest)


        msgListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(this, "Click on $position", Toast.LENGTH_SHORT).show()
        }

        addMsg.setOnClickListener(){
            var i = Intent(this, CreateMessage::class.java)
            i.putExtra("topicID", id)
            startActivity(i)
        }

    }

    inner class MessageAdapter : BaseAdapter {

        private var messagesList = ArrayList<Message>()
        private var context: Context? = null

        constructor(context: Context, messagesList: ArrayList<Message>) : super() {
            this.messagesList = messagesList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val cv: CustomView

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.message_layout, parent, false)
                cv = CustomView(view)
                view?.tag = cv
                Log.i("JSA", "set Tag for ViewHolder, position: $position")
            } else {
                view = convertView
                cv = view.tag as CustomView
            }

            cv.tvTitle.text = messagesList[position].author
            cv.tvContent.text = messagesList[position].content
            cv.tvDate.text = messagesList[position].date

            return view
        }

        override fun getItem(position: Int): Any {
            return messagesList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return messagesList.size
        }
    }

    private class CustomView(view: View?) {
        val tvTitle: TextView
        val tvContent: TextView
        val tvDate: TextView

        init {
            this.tvTitle = view?.findViewById(R.id.tvTitle) as TextView
            this.tvContent = view?.findViewById(R.id.tvContent) as TextView
            this.tvDate = view?.findViewById(R.id.tvDate) as TextView
        }
    }
}

