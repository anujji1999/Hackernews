package com.anuj.hackandroid.hackernews

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.view_story.*


class StoryFragment : Fragment
{
    var url : String? = null


    constructor() : super()

    constructor(recievedUrl : String ) : super(){
        url = recievedUrl
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_story,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(url==null) {
            if(requireActivity().intent.data!=null) {
                url = requireActivity().intent.data.toString()
            }
        }


        if(url!=null) {
            webV.setWebViewClient(WebViewClient())
            webV.getSettings().setLoadsImagesAutomatically(true)
            webV.getSettings().setJavaScriptEnabled(true)
            webV.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
            webV.loadUrl(url)
        }


    }




}