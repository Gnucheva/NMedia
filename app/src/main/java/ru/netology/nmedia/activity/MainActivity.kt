package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                avatar.setImageResource(R.drawable.ic_launcher_foreground)
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likeCount.text = format(post.likes)
                shareCount.text = format(post.share)
                viewsCount.text = format(post.views)
                like.setImageResource(
                    if (post.likedByMe) R.drawable.liked else R.drawable.like
                )
            }
        }
        binding.like.setOnClickListener {
            viewModel.like()
        }
        binding.share.setOnClickListener {
            viewModel.share()
        }
    }
}

fun format(number: Long): String {
    if (number >= 1000000) return String.format("%.2f M", (number / 1000000).toFloat())
    if (number >= 10000) return String.format("%d K", number / 1000)
    if (number >= 1000) return String.format("%.1f K", (number / 1000).toFloat())
    return number.toString()
}
/*
        1.1К отображается по достижении 1100
        После 10К сотни перестают отображаться
        После 1M сотни тысяч отображаются в формате 1.3M
*/

