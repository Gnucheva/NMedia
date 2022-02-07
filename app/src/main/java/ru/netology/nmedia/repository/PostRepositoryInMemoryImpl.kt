package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим ,что в каждом уже есть сила , которая заставляет хотеть больше ,целиться выше , бежать быстрее. Наша миссия-помочь встать на пути роста и начать цепочку перемен > http://netolo.gy/fyb ",
            published = "21 мая в 18:36",
            likes = 100,
            share = 90,
            views = 1_000_000,
            likedByMe = false
        ), // create mock data
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Напоминаем про бесплатный курс «Основы Figma» \n" +
                    "\n" +
                    "Удобство Figma — в том, что над одним проектом могут работать одновременно несколько человек.\n" +
                    "\n" +
                    "В течение курса сможете создать прототип мобильного приложения, макет сайта и рекламные баннеры. Познакомитесь с возможностями редактора, освоите азы веб-дизайна и пополните портфолио.\n" +
                    "\n" +
                    "Приступить можно в любое удобное время → https://netolo.gy/hxn ",
            published = "18 сентября в 10:12",
            likes = 99,
            share = 100,
            views = 100_000,
            likedByMe = false

        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "«Раньше меня тоже вела дорога приключений, а потом мне прострелили колено».\n" +
                    "\n" +
                    "Разобраться в разуме NPC из Skyrim поможет наша статья → https://netolo.gy/hxp",
            published = "19 сентября в 10:12",
            likes = 300,
            share = 3000,
            views = 300,
            likedByMe = false

        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "С цветами всё просто: необходимы полив, тепло и свет \n" +
                    "\n" +
                    "А как вырастить руководителя рассказываем в статье → https://netolo.gy/hxo",
            published = "19 сентября в 10:12",
            likes = 400,
            share = 4000,
            views = 400,
            likedByMe = false

        ),
    ).reversed()

    private val data = MutableLiveData(posts) // создали оболочку из MutableLiveData

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes + (if (it.likedByMe) -1 else 1)
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(share = it.share + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }
}