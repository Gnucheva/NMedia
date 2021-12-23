package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим ,что в каждом уже есть сила , которая заставляет хотеть больше ,целиться выше , бежать быстрее. Наша миссия-помочь встать на пути роста и начать цепочку перемен > http://netolo.gy/fyb ",
            published = "21 мая в 18:36",
            likes = 1001,
            shares = 9000,
            views = 1000000,
            likedByMe = false
        )
        with(binding) {
            avatar.setImageResource(R.drawable.ic_launcher_foreground)
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCount.text = format(post.likes)
            shareCount.text = format(post.shares)
            viewsCount.text = format(post.views)
            if (post.likedByMe) {
                like?.setImageResource(R.drawable.liked)
            }

            root.setOnClickListener {
                Log.d("stuff", "stuff")
            }

            avatar.setOnClickListener {
                Log.d("stuff", "avatar")
            }

            like?.setOnClickListener {
                if (!post.likedByMe) {
                    like.setImageResource(R.drawable.liked)
                    post.likedByMe = !post.likedByMe
                    post.likes++
                    likeCount.text = post.likes.toString()
                } else {
                    like.setImageResource(R.drawable.like)
                    post.likedByMe = !post.likedByMe
                    post.likes--
                    likeCount.text = post.likes.toString()


                }
            }
            share.setOnClickListener {
                post.shares++
                shareCount.text = post.shares.toString()

            }
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

