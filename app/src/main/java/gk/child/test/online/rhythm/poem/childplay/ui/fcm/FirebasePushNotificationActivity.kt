package gk.child.test.online.rhythm.poem.childplay.ui.fcm
import android.os.Bundle
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import gk.child.test.online.rhythm.poem.childplay.R
import gk.child.test.online.rhythm.poem.childplay.ui.BaseActivity
import java.io.IOException

class FirebasePushNotificationActivity : BaseActivity() {
    private val TAG = "MyFirebaseToken"
    override fun initData() {

    }

    override fun initListeners() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_push_notification)
        initView()
    }

    private fun initView() {
        //This method will use for fetching Token
        Thread(Runnable {
            try {
                Log.i(TAG, FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID), "FCM"))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }
}