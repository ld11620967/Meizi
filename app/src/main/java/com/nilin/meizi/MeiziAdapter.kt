package com.nilin.meizi

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kcode.gankotlin.repository.Article


class MeiziAdapter(var context: Context, layoutId:Int) : BaseQuickAdapter<Article, BaseViewHolder>(layoutId) {

    override fun convert(viewHolder: BaseViewHolder?, article: Article?) {

        val image: ImageView = viewHolder!!.getView<ImageView>(R.id.image)
        Glide.with(context).load(article!!.url).into(image)
    }
}


