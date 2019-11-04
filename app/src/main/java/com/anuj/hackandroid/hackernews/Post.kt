package com.anuj.hackandroid.hackernews

data class Post(
	val score: Int? = null,
	val by: String? = null,
	val id: Int? = null,
	val time: Int? = null,
	val title: String? = null,
	val type: String? = null,
	val descendants: Int? = null,
	val url: String? = null,
	val kids: List<Int?>? = null
)
