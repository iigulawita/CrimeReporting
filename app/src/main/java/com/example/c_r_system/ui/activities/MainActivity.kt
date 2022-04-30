package com.example.c_r_system.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.c_r_system.R
import com.example.c_r_system.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        initNavigation()
        // Navigate to SigIn if not already authenticated
        auth = Firebase.auth
        if (auth.currentUser?.email == null) {
            navController.navigate(R.id.action_global_signIn)
        }
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomAppBar.setupWithNavController(navController)
        setSupportActionBar(binding.bottomAppBar)


    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null)
            navController.navigate(R.id.action_global_signIn)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.About_us -> navController.navigate(R.id.action_mainFragment_to_aboutFragment)
            R.id.Sign_Out ->
            {
                auth.signOut()
                navController.navigate(R.id.signInFragment)

            }

        }
        return true


    }





    companion object {
        private const val TAG: String = "MainActivity"
    }

    internal fun showFabBottomAppBar() {
       binding.bottomAppBar.performShow()

    }

}


