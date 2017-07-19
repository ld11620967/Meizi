package com.nilin.meizi

import android.view.animation.AnimationUtils
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.animation.Animation
import android.app.Activity
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_welcome.*


/**
 * Created by liangd on 2017/7/19.
 */
class SplashActivity : Activity() {

    private var imgAnimation: Animation? = null

    private var textAnimation: Animation? = null

//    var intent:Intent= getIntent()

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            goHome()
        }
    }

    /**
     * 进入主页
     */
    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
//        intent.setClass(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome)
        //启动服务
        startService(Intent(this, MainActivity::class.java))

        mHandler.sendEmptyMessageDelayed(GO_HOME, TIME)

        imgAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
        textAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim)
        welcomeImg.startAnimation(imgAnimation)
        welcomeText.startAnimation(textAnimation)
    }

    companion object {

        private val TIME = 1200L

        private val GO_HOME = 101
    }
}