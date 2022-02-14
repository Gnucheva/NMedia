package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.adapter.onInteractionListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils.showToast
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()

        //функция, которая будет вызвана при завершении NewPostActivity
        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        val adapter = PostsAdapter(object : onInteractionListener {

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                editPostLauncher.launch(post.content)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent(Intent.ACTION_SEND) // Создаётся intent на отправку текста.
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, post.content)
                    .let {
                        Intent.createChooser(
                            it,
                            null
                        ) // Создаётся intent на показ Chooser'а (меню выбора)
                    }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent) // startActivity приводит к запуску компонента Activity через выбор

                } else {
                    showToast(R.string.app_not_found)
                }
            }

            override fun onVideo(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(post.video)
                }
                if (intent.resolveActivity(packageManager) != null) { //проверят установлено ли приложение Youtube
                    startActivity(intent)
                } else {
                    showToast(R.string.app_not_found)
                }
            }

        })
        binding.list.adapter = adapter
        // Подписываемся на обновление data во viewModel
        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)
        })

        binding.add.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}




