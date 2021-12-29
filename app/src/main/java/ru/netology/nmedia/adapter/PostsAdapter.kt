package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias OnLikeListener = (post: Post) -> Unit //ярлык для замещения naming  // = callback // пробрасывать данные обработки OnClick
typealias OnShareListener = (post: Post) -> Unit

// класс, отвечающий за предоставление View

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener

) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    //  ListAdapter — реализация RecyclerView.Adapter, которая работает в связке с DiffUtil

    override fun onCreateViewHolder( //создает экземпляр ViewHolder
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder { // создание ViewHolder
        val binding = CardPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ) // context - пространоство ресурсов
        return PostViewHolder(binding, onLikeListener, onShareListener)

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) { //позиция ViewHolder
        val post = getItem(position)
        holder.bind(post)
    }
}

// класс содержажий информацию о визуальном отображении конкретного элемента списка (котейнер для View)

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener, // логика работы с кликами
    private val onShareListener: OnShareListener

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {// переназначаются все поля
        binding.apply {// процесс подготовки View для отображения данных, соответствующих определённой позиции в адаптере
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
            like.setOnClickListener {
                onLikeListener(post)
            }
            share.setOnClickListener {
                onShareListener(post)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() { // DiffUtil — сравнивает два объекта.
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id // сравнивает id объектов
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem // сравнивает, что их содержимое одинаково
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

