package com.aadya.gist.world.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.AdapterWorldListBinding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.BookMarkUnBookmarkInterface
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.playAudioClickListner

class WorldNewsAdapter(
    context: Context?,
    NewsList: ArrayList<NewsModel>,
    newsItemClick: WorldNewsListItemClick,
    mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?, mPlayAudioClickListner: playAudioClickListner
) : RecyclerView.Adapter<WorldNewsAdapter.MyViewHolder>() {

    private val NewsList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private val context: Context?
    private val newsItemClick: WorldNewsListItemClick
    private val mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?
    private var mSessionManager: SessionManager
    private var playAudioClickListner: playAudioClickListner? = null
    fun notifyData(worldNewsList: List<NewsModel>) {
        if (this.NewsList != null) {
            this.NewsList.clear()
            this.NewsList.addAll(worldNewsList)
            notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_world_list, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val NewsModel : NewsModel = NewsList[i]
               with(viewHolder) {
            binding.tvWorldTitle.text = NewsModel.news_title
                   binding.tvWorldDateTime.text = NewsModel.created_date
                   setColorOfApp(mSessionManager.getAppColor(), binding)
            binding.llWorld.setOnClickListener {
                newsItemClick.onItemClick(NewsList,i)
            }
                   binding.imgBookamrk.setOnClickListener {

                       if(NewsModel.is_bookmark.equals("0")) {
                           binding.imgBookamrk .setColorFilter(
                               ContextCompat.getColor(context!!,
                                   mSessionManager.getAppColor()!!))
                           mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(NewsModel.news_id, "1")
                       }
                       else if(NewsModel.is_bookmark.equals("1")){
                           binding.imgBookamrk .setColorFilter(
                               ContextCompat.getColor(context!!,
                                   R.color.textcolor))
                           mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(NewsModel.news_id, "0")
                       }
                   }

                   if(NewsModel.is_bookmark.equals("1")){
                       binding.imgBookamrk .setColorFilter(
                           ContextCompat.getColor(context!!,
                               mSessionManager.getAppColor()!!))
                   }
                   else if(NewsModel.is_bookmark.equals(("0"))){
                       binding.imgBookamrk .setColorFilter(
                           ContextCompat.getColor(context!!,
                               R.color.textcolor))
                   }
                   binding.imgShare.setOnClickListener {
                       try {
                           val shareIntent = Intent(Intent.ACTION_SEND)
                           shareIntent.type = "text/plain"
                           shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)

                           var shareMessage = "\n${NewsModel.news_title}\n${NewsModel.short_description}\n${NewsModel.short_description}\n${NewsModel.video_url}"
                           shareMessage =
                               """
                        ${shareMessage}}
                        
                        
                        """.trimIndent()
                           shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                           context?.startActivity(Intent.createChooser(shareIntent, "choose one"))
                       } catch (e: Exception) {
                           e.printStackTrace()
                       }
                   }
                   binding.imgAudio.setOnClickListener {

                       playAudioClickListner?.onAudioClick(NewsModel)
                   }
        }
    }

    override fun getItemCount(): Int {
        return NewsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface WorldNewsListItemClick {
        fun onItemClick(NewsModel: ArrayList<NewsModel>, NewsModel1: Int)
    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterWorldListBinding.bind(itemView)
    }

    init {
        this.NewsList.addAll(NewsList)
        this.context = context
        this.newsItemClick = newsItemClick
        this.mBookMarkUnBookmarkInterface = mBookMarkUnBookmarkInterface
        mSessionManager = SessionManager.getInstance(context)!!
        this.playAudioClickListner  = mPlayAudioClickListner
    }

    private fun setColorOfApp(appColor: Int?, binding: AdapterWorldListBinding) {
        binding.tvWorldDateTime.setTextColor(
            ContextCompat.getColor(
                context!!,
                appColor!!
            )
        )
    }


}