package io.github.arwazkhan189.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import io.github.arwazkhan189.socialapp.dasos.PostDao
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class CreatePostActivity : AppCompatActivity() {

    private lateinit var etPostInput: EditText
    private lateinit var btnPost: Button
    private lateinit var postDao: PostDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        etPostInput = findViewById(R.id.etPostInput)
        btnPost = findViewById(R.id.btnPost)
        postDao = PostDao()
        btnPost.setOnClickListener {
            val input = etPostInput.text.toString()
            if (input.isNotEmpty()) {
                postDao.addPost(input)
                finish()
            }
        }

    }
}