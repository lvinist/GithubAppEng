package com.dicoding.submission2.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.data.User
import com.dicoding.submission2.data.local.FavoriteUser
import com.dicoding.submission2.databinding.ActivityFavoriteBinding
import com.dicoding.submission2.ui.details.DetailUserActivity
import com.dicoding.submission2.ui.main.UserAdapter


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatar_url)
                startActivity(intent)
            }

        })

        binding.apply {
            rvFavUser.setHasFixedSize(true)
            rvFavUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavUser.adapter = adapter
        }

        viewModel.getFavoriteUser().observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url,
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}