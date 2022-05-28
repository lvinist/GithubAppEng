package com.dicoding.submission2.ui.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.submission2.R
import com.dicoding.submission2.databinding.ActivityDetailUserBinding
import com.dicoding.submission2.ui.folls.SectionPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var userDetail: String

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
    }

    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var sectionPagerAdapter: SectionPagerAdapter

    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra(EXTRA_USERNAME).toString()
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar_url = intent.getStringExtra(EXTRA_AVATAR_URL).toString()
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,username)

        sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager, lifecycle, bundle)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            viewPager.adapter = sectionPagerAdapter

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                when(position) {
                    0 -> tab.text = "Followers"
                    1 -> tab.text = "Following"
                }
            }.attach()
        }

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    name.text = it.name
                    tvUsername.text = it.login
                    location.text = "Location : ${it.location}"
                    repository.text = "${it.public_repos} Repository"
                    if (it.company != null){
                        company.text = "Company: ${it.company}"
                    }
                    followers.text = "${it.followers} Followers"
                    following.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(profile)

                    userDetail = "Github User:\nName: ${it.name}\nUsername: @${it.login}\nRepository: ${it.public_repos}\nFollowers: ${it.followers}\nFollowing: ${it.following}\nCompany: ${it.company}\nLocation: ${it.location}"
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavUser(id)
            withContext(Dispatchers.Main){
                if(count != null) {
                    if (count > 0) {
                        binding.fabFavToggle.isChecked = true
                        _isChecked = true
                    } else {
                        binding.fabFavToggle.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.fabFavToggle.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.addToFavUser(username, id, avatar_url)
                Toast.makeText(this@DetailUserActivity, "$username is added to favorite user", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.removeFavUser(id)
                Toast.makeText(this@DetailUserActivity, "$username is removed from favorite user", Toast.LENGTH_SHORT).show()
            }
            binding.fabFavToggle.isChecked = _isChecked
        }

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail User"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_share -> {
                val shareUser = userDetail
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareUser)
                shareIntent.type = "text/html"
                startActivity(Intent.createChooser(shareIntent, "Share using"))
                return true
            }
            else -> return true
        }
    }
}