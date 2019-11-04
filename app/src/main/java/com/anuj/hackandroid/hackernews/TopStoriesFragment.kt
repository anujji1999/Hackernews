package com.anuj.hackandroid.hackernews

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.top_stories.*
import okhttp3.*
import java.io.IOException
import java.net.URL
import kotlin.collections.ArrayList


class TopStoriesFragment : Fragment() {


    var p1  = ArrayList<Post>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.top_stories,container,false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        Log.e("Tag","Initializing Adapter")

        var adapt = PostAdapter(p1,requireContext())
        var divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)


        rv.addItemDecoration(divider)
        rv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        rv.adapter = adapt

        var lists = ArrayList<Int>()

        val gson = Gson()



        val listType = object : TypeToken<List<Int>>() {}.type

        var postUrl = "https://hacker-news.firebaseio.com/v0/topstories.json"
        var url2 = "https://hacker-news.firebaseio.com/v0/item/"

        val client : OkHttpClient = OkHttpClient()

        val request = Request.Builder()
            .url(URL(postUrl))
            .build()

        val response = client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string()

                result?.let {

                    lists = gson.fromJson(result,listType)
                    lists.forEach {

                        //Log.e("Found Id  ",it.toString())

                        val client2 = OkHttpClient()
                        val request2 = Request.Builder()
                            .url(URL("$url2$it.json"))
                            .build()

                        val response2 = client.newCall(request2).enqueue(object : Callback{
                            override fun onFailure(call: Call, e: IOException) {

                            }

                            override fun onResponse(call: Call, response: Response) {

                                val result2  = response.body()?.string()

                                result2?.let {


                                    //Log.e("Caution!!","DataSetChanged")

                                    activity?.runOnUiThread {
                                        p1.add(gson.fromJson(result2,Post::class.java))
                                        adapt.notifyDataSetChanged() }


                                }

                            }

                        })





                    }


                }





            }
         })



















    }




}