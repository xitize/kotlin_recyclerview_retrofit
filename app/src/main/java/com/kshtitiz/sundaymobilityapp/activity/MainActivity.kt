package com.kshtitiz.sundaymobilityapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.kshtitiz.sundaymobilityapp.R
import com.kshtitiz.sundaymobilityapp.adapter.UserAdapter
import com.kshtitiz.sundaymobilityapp.api.WebAccess
import com.kshtitiz.sundaymobilityapp.modal.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),
    UserAdapter.ClickListener {

    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var fabAddUser: FloatingActionButton

    private lateinit var mainLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv_user_list)
        progressBar = findViewById(R.id.ProgressBar_load_User)
        fabAddUser = findViewById(R.id.fab_add_item)
        mainLayout = findViewById(R.id.cl_main_user_show)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        userAdapter =
            UserAdapter(
                mutableListOf(),
                this
            )
        recyclerView.adapter = userAdapter

        fabAddUser.setOnClickListener {

            /*Generating random names and image_url*/
            val names = listOf(
                "Kshitiz Mishra",
                "Rohit Mehra",
                "Krunal Pandey",
                "Namita Deshmukh",
                "Pooja Shrestha"
            )

            val image_url = listOf(
                "https://blog.circle.com/content/images/2018/09/divya.png",
                "https://billie-scott.com/wp-content/uploads/2018/03/billie-gravatar.jpg",
                "https://w0.pngwave.com/png/260/973/computer-icons-clipping-path-user-profile-executive-png-clip-art-thumbnail.png"
            )

            val user = User(
                names.random(),
                image_url.random(),
                "user"
            )

            Snackbar.make(mainLayout, "item added", Snackbar.LENGTH_SHORT).show()


            userAdapter.addItem(user)
            recyclerView.postDelayed({
                recyclerView.scrollToPosition(userAdapter.userList.size - 1)
            }, 1000)
            //   recyclerView.scrollToPosition(position)
        }

        GlobalScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            val webResponse = WebAccess.usersApi.getUserAsync().await()

            if (webResponse.isSuccessful) {
                Log.d("KTZ", "${webResponse.body()}")

                val userList: List<User>? = webResponse.body()

                if (userList != null) {
                    progressBar.visibility = View.GONE
                    userAdapter.userList = userList as MutableList<User>
                    userAdapter.notifyDataSetChanged()
                }
            } else {
                progressBar.visibility = View.GONE
                Log.d("KTZ", "Error ${webResponse.code()}")
                //  Toast.makeText(this@MainActivity, "Error ${webResponse.code()}", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun clickRow(user: User, position: Int) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("image_url", user.avatar_url)

        //  Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show()

        intent.putExtra("position", "" + position)
        startActivityForResult(intent, 1234)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234) {
            if (resultCode == Activity.RESULT_OK) {
                val pos = data?.getStringExtra("pos")?.toInt()
                if (pos != null) {
                    userAdapter.removeUserAtPosition(position = pos)
                }
            }
        }
    }
}
