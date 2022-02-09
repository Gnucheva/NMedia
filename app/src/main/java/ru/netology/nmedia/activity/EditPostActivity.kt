package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPost2Binding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPost2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val previousContent = intent?.getStringExtra(Intent.EXTRA_TEXT)
        binding.edit.setText("$previousContent")
        binding.edit.requestFocus();
        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.edit.text.isBlank() || binding.edit.text.toString() == previousContent) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.edit.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }

    }
}
