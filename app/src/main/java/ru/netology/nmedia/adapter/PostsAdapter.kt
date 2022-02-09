package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface onInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}
}

// класс, отвечающий за предоставление View

class PostsAdapter(
    private val onInteractionListener: onInteractionListener,
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
        return PostViewHolder(binding, onInteractionListener)

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) { //позиция ViewHolder
        val post = getItem(position)
        holder.bind(post)
    }
}

// класс содержажий информацию о визуальном отображении конкретного элемента списка (котейнер для View)

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: onInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {// переназначаются все поля
        binding.apply {// процесс подготовки View для отображения данных, соответствующих определённой позиции в адаптере
            avatar.setImageResource(R.drawable.ic_launcher_foreground)
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.text = format(post.likes)
            share.text = format(post.share)
            views.text = format(post.views)
            like.isChecked = post.likedByMe
            like.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post) // пункты меню
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.editFromOptionsMenu -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show() // показ меню
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

