package ru.geeekbrains.myfilmapp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ru.geeekbrains.myfilmapp.R
import ru.geeekbrains.myfilmapp.databinding.MainActivityBinding

private const val IS_ADULT_KEY = "ADULT_CONTENT_KEY"

class MainActivity : AppCompatActivity() {

    private var id: Int = 0
    private var isAdultEnabled = false
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.adult_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        id = item.itemId
        if (id == R.id.adult_menu_item){
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean(IS_ADULT_KEY, !isAdultEnabled)
            editor.apply()
        }
        return super.onOptionsItemSelected(item)
    }

}
