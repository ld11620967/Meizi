package com.nilin.meizi

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_about.*


/**
 * Created by liangd on 2017/7/19.
 */
class AboutActivity : Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
//        toolbar.setTitle("关于")
//        setSupportActionBar(toolbar)
//        //返回按钮
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
//       project_home_btn.setOnClickListener(object : View.OnClickListener() {
//            override fun onClick(view: View) {
//                val uri = Uri.parse("https://git.oschina.net/ld11620967")
//                val it = Intent(Intent.ACTION_VIEW, uri)
//                startActivity(it)
//            }
//        })
    }


    //返回按钮点击事件
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
