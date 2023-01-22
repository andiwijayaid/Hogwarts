package id.andiwijaya.hogwarts.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.andiwijaya.hogwarts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }
}