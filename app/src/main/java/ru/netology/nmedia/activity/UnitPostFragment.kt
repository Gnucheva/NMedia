package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.format
import ru.netology.nmedia.databinding.FragmentCardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class UnitPostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::
        requireParentFragment
    )

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCardPostBinding.inflate(inflater, container, false)

        val post = viewModel.currentPost.value ?: Post()
        viewModel.currentPost.observe(viewLifecycleOwner, {

            binding.apply {
                avatar.setImageResource(R.drawable.ic_launcher_foreground)
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.text = format(post.likes)
                share.text = format(post.share)
                views.text = format(post.views)
                videoName.text = post.videoName
                like.isChecked = post.likedByMe
            }
        })

        with(binding) {
            like.setOnClickListener {
                viewModel.likeById(post.id)
                sync(post.id)
            }

            share.setOnClickListener {
                viewModel.shareById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
            playButton.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(post.video)
                }
                startActivity(intent)
            }
            videoImage.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(post.video)
                }
                startActivity(intent)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post) // пункты меню
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                viewModel.removeById(post.id)
                                findNavController().navigateUp()
                                true
                            }
                            R.id.editFromOptionsMenu -> {
                                viewModel.edit(post)
                                findNavController().navigate(R.id.action_unitPostFragment_to_editPostFragment,
                                    Bundle().apply
                                    { textArg = post.content })
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
        return binding.root
    }

    private fun sync(id: Long) {
        viewModel.currentPost.value = viewModel.findById(id)
    }
}


