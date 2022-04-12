package com.aadya.gist.breakingnews.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.AdapterNewsListBinding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.BookMarkUnBookmarkInterface
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.playAudioClickListner
import kotlin.collections.ArrayList

class BreakingNewsAdapter(
    context: Context,
    breakingNewsModelList: ArrayList<NewsModel>,
    newsItemClick: LatestNewsListItemClick,
    mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?, mPlayAudioClickListner: playAudioClickListner
) : RecyclerView.Adapter<BreakingNewsAdapter.MyViewHolder>() {

    private val breakingNewsModelList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private val context: Context
    private val newsItemClick: LatestNewsListItemClick
    private val mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?
    private  var mSessionManager: SessionManager
    private var playAudioClickListner: playAudioClickListner? = null
    fun notifyData(breakingNewsModelList: List<NewsModel>) {
        if (this.breakingNewsModelList != null) {
            this.breakingNewsModelList.clear()
            this.breakingNewsModelList.addAll(breakingNewsModelList)
            notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_news_list, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val newsModel : NewsModel = breakingNewsModelList[i]
               with(viewHolder) {

                   setColorOfApp(mSessionManager.getAppColor(),binding)
            binding.tvTitle.text = newsModel.news_title
                   binding.tvDateTime.text = newsModel.created_date

            binding.latestNewsMainLayout.setOnClickListener {
                newsItemClick.onItemClick(breakingNewsModelList,i)
            }
            binding.imgBookamrk.setOnClickListener {

                binding.imgBookamrk.setOnClickListener {

                    if(newsModel.is_bookmark.equals("0")) {
                        binding.imgBookamrk .setColorFilter(
                            ContextCompat.getColor(context!!,
                                mSessionManager.getAppColor()!!))
                        mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(newsModel.news_id, "1")
                    }
                    else if(newsModel.is_bookmark.equals("1")){
                        binding.imgBookamrk .setColorFilter(
                            ContextCompat.getColor(context!!,
                                R.color.textcolor))
                        mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(newsModel.news_id, "0")
                    }
                }

                if(newsModel.is_bookmark.equals("1")){
                    binding.imgBookamrk .setColorFilter(
                        ContextCompat.getColor(context!!,
                            R.color.appcolor))
                }
                else if(newsModel.is_bookmark.equals(("0"))){
                    binding.imgBookamrk .setColorFilter(
                        ContextCompat.getColor(context!!,
                            R.color.textcolor))
                }
            }

                   binding.imgShare.setOnClickListener {
                       try {
                           val shareIntent = Intent(Intent.ACTION_SEND)
                           shareIntent.type = "text/plain"
                           shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)

                           var shareMessage = "\n${newsModel.news_title}\n${newsModel.short_description}\n${newsModel.short_description}\n${newsModel.video_url}"
                           shareMessage =
                               """
                        ${shareMessage}}
                        
                        
                        """.trimIndent()
                           shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                           context.startActivity(Intent.createChooser(shareIntent, "choose one"))
                       } catch (e: Exception) {
                           e.printStackTrace()
                       }
                   }

                   binding.imgAudio.setOnClickListener {

                       playAudioClickListner?.onAudioClick(newsModel)
                   }
        }
    }

    override fun getItemCount(): Int {
        return breakingNewsModelList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface LatestNewsListItemClick {
        fun onItemClick(modelLatest: ArrayList<NewsModel>, i: Int)
    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterNewsListBinding.bind(itemView)
    }

    init {
        this.breakingNewsModelList.addAll(breakingNewsModelList)
        this.context = context
        this.newsItemClick = newsItemClick
        this.mBookMarkUnBookmarkInterface = mBookMarkUnBookmarkInterface
        mSessionManager = SessionManager.getInstance(context)!!
        this.playAudioClickListner  = mPlayAudioClickListner
    }


    private fun setColorOfApp(appColor: Int?, binding:  AdapterNewsListBinding) {
        binding.tvDateTime.setTextColor(
            ContextCompat.getColor(
                context,
               appColor!!
            )
        )

    }



}


