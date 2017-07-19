package com.nilin.meizi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SimpleItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kcode.gankotlin.repository.Article
import com.nilin.meizi.model.Result
import kotlinx.android.synthetic.main.activity_main.*
import com.nilin.retrofit2_rxjava2_demo.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import android.view.MenuItem


class MainActivity : AppCompatActivity() {
    var adapter: MeiziAdapter? = null
    val pageSize = 10
    var pageNumber = 1
    var isRefresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, AboutActivity::class.java)

        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setTitle("妹子瀑布流")
        toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.about -> {
                        startActivity(intent)
                        finish()
                        return true
                    }
                }
                return false
            }
        })

        recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        var simpleAnimator: SimpleItemAnimator = recyclerview.itemAnimator as SimpleItemAnimator
        simpleAnimator.supportsChangeAnimations = false

        adapter = MeiziAdapter(this,R.layout.item_girls)

        recyclerview.adapter = adapter
        adapter!!.setOnLoadMoreListener({ loadMore() }, recyclerview)
        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener {
            adapter, view, position ->
            start2Detail(adapter.data[position] as Article)

        }

        fab.onClick {
            recyclerview.smoothScrollToPosition(0)
        }

        swipeLayout.setOnRefreshListener({
            pageNumber = 1
            isRefresh = true
            loadData(pageSize,pageNumber)
        })
        loadData(pageSize,pageNumber)
    }

    fun start2Detail(article: Article) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("url", article.url)
        startActivity(intent)
    }

    private fun loadMore() {
        pageNumber++
        loadData(pageSize, pageNumber)
    }

    protected fun loadData(pageSize: Int, pageNumber: Int) {
        val api = Api.Factory.create()
        api.getData("福利",pageSize, pageNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    result ->
                    parseResult(result)
                }, {})
    }

    fun parseResult(result: Result) {
        if (result.error) {
            loadError()
        }else{
            loadSuccess(result.results)
        }
        loadFinish()
    }

    fun loadError() {
            toast("读取失败！")
    }

    fun loadSuccess(data: List<Article>) {
        setUp(data)
    }

    private fun setUp(data: List<Article>) {
        if (isRefresh) {
            adapter!!.setNewData(data)
            isRefresh = false
        } else {
            adapter!!.addData(data)
        }
    }

    fun loadFinish() {
        if (swipeLayout!!.isRefreshing) {
            swipeLayout!!.isRefreshing = false
        }
        adapter!!.loadMoreComplete()
    }

}

