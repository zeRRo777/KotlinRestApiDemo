package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitdemo.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retService: PostService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        retService = RetrofitInstance
            .getRetofitInstance()
            .create(PostService::class.java)
        getRequestWithQueryParameters()
        //getRequestWithPathParameters()


    }

    private fun getRequestWithQueryParameters()
    {
        val responseLiveData : LiveData<Response<Post>> = liveData {
            val response = retService.getSortedPosts(3)
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val postsList: MutableListIterator<PostItem>? = it.body()?.listIterator()

            if (postsList != null)
            {
                while (postsList.hasNext()){
                    val postItem: PostItem = postsList.next()
                    val result : String = " " + "Post Title : ${postItem.title}" + "\n" +
                            " " + "Post id : ${postItem.id}" + "\n" +
                            " " + "User id : ${postItem.userId}" + "\n\n\n"

                    binding.textView.append(result)
                }
            }
        })
    }

    private fun getRequestWithPathParameters()
    {
        //path parameter example

         val pathResponse : LiveData<Response<PostItem>> = liveData {
             val response = retService.getPost(3)
             emit(response)
         }

         pathResponse.observe(this, Observer {
             val title = it.body()?.title
             Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
         })
    }
}