package ru.android.effectivemobiletest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.android.effectivemobiletest.databinding.MainActivityBinding
import ru.android.effectivemobiletest.ui.main.MainFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_EffectiveMobileTest)
        setContentView(R.layout.main_activity)
    }
}