package by.it.andersen.newsapikt.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.it.andersen.newsapikt.R
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main2)
    }
}