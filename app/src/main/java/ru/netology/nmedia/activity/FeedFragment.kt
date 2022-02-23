package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.adapter.onInteractionListener
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils.showToast
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel


class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::
        requireParentFragment
    )

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    val Fragment.packageManager get() = activity?.packageManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostsAdapter(object : onInteractionListener {

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply
                    { textArg = post.content })
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
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

            override fun onVideo(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(post.video)
                }

                if (intent.resolveActivity(packageManager!!) != null) {
                    startActivity(intent)

                } else {
                    showToast(R.string.app_not_found)
                }
            }

            override fun viewPost(post: Post) {
                viewModel.selectPost(post)
                findNavController().navigate(R.id.action_feedFragment_to_unitPostFragment)
            }
        })

        binding.list.adapter = adapter
        // Подписываемся на обновление data во viewModel
        viewModel.data.observe(viewLifecycleOwner,
            { posts ->
                adapter.submitList(posts)
            })

        binding.add.setOnClickListener {
            val bundle = Bundle().apply {
                putString("content", "Напишите текст поста...")
            }
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment, bundle)
        }

        return binding.root
    }
}




