package gk.child.test.online.rhythm.poem.childplay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import gk.child.test.online.rhythm.poem.childplay.ui.BaseActivity
import gk.child.test.online.rhythm.poem.childplay.ui.CompleteActivity
import java.io.IOException


class MainActivity : BaseActivity() {

    private val ACTIVITY_CALLBACK = 1
    private var reviewInfo: ReviewInfo? = null
    private lateinit var reviewManager: ReviewManager
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val TAG = "MyFirebaseToken"
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    var id: Nothing? =null
    var  name: Nothing? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow ,R.id.mapsFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        //Create the ReviewManager instance
        reviewManager = ReviewManagerFactory.create(this)

        //Request a ReviewInfo object ahead of time (Pre-cache)
        val requestFlow = reviewManager.requestReviewFlow()
        requestFlow.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                //Received ReviewInfo object
                reviewInfo = request.result
            } else {
                //Problem in receiving object
                reviewInfo = null
            }
        }


            startActivityForResult(
                Intent(this, CompleteActivity::class.java),
                ACTIVITY_CALLBACK
            )


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ACTIVITY_CALLBACK && resultCode == Activity.RESULT_OK) {
            Handler().postDelayed({
                reviewInfo?.let {
                    val flow = reviewManager.launchReviewFlow(this@MainActivity, it)
                    flow.addOnSuccessListener {
                        //Showing toast is only for testing purpose, this shouldn't be implemented
                        //in production app.
                        Toast.makeText(
                            this@MainActivity,
                            "Thanks for the feedback!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    flow.addOnFailureListener {
                        //Showing toast is only for testing purpose, this shouldn't be implemented
                        //in production app.
                        Toast.makeText(this@MainActivity, "${it.message}", Toast.LENGTH_LONG).show()
                    }
                    flow.addOnCompleteListener {
                        //Showing toast is only for testing purpose, this shouldn't be implemented
                        //in production app.
                        Toast.makeText(this@MainActivity, "Completed!", Toast.LENGTH_LONG).show()
                    }
                }
            }, 3000)
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun initView() {
        //This method will use for fetching Token
        Thread(Runnable {
            try {
                FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID), "FCM")?.let {
                    Log.i(TAG,
                        it
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun initData() {


    }

    override fun initListeners() {

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}