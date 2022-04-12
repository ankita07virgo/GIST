package com.aadya.gist.entertainment.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.AdapterEntertainmentListBinding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.BookMarkUnBookmarkInterface
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.playAudioClickListner
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class EntertainmentNewsAdapter(
    context: Context?,
    NewsModelList: ArrayList<NewsModel>,
    entertainmentnewsItemClick: EntertainmentNewsListItemClick,
    mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?, mPlayAudioClickListner: playAudioClickListner
) : RecyclerView.Adapter<EntertainmentNewsAdapter.MyViewHolder>() {

    private val NewsModelList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private val context: Context?
    private val entertainmentnewsItemClick: EntertainmentNewsListItemClick
    private val mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?
    private  var mSessionManager: SessionManager
    private var playAudioClickListner: playAudioClickListner? = null
    fun notifyData(NewsModelList: List<NewsModel>) {
        if (this.NewsModelList != null) {
            this.NewsModelList.clear()
            this.NewsModelList.addAll(NewsModelList)
            notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_entertainment_list, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val newsModel : NewsModel = NewsModelList[i]
               with(viewHolder) {
            binding.tvDatetime.text = newsModel.created_date
                   binding.tvEntertainmenttitle.text = newsModel.news_title

                   setColorOfApp(mSessionManager.getAppColor(),binding)
            binding.entertainmentMainLayout.setOnClickListener {
                entertainmentnewsItemClick.onItemClick(NewsModelList,i)
            }

                   Picasso.get()
                       .load(R.drawable.entertainment1)
                       .placeholder(R.drawable.applogo)

                       .error(R.drawable.applogo)

                       .into(binding.imgEntertainment, object : com.squareup.picasso.Callback {
                           override fun onSuccess() {


                           }

                           override fun onError(e: java.lang.Exception?) {

                           }
                       })

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
                           context?.startActivity(Intent.createChooser(shareIntent, "choose one"))
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
        return NewsModelList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface EntertainmentNewsListItemClick {
        fun onItemClick(entertainmentNewsModel: ArrayList<NewsModel>, newsModel: Int)
    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterEntertainmentListBinding.bind(itemView)
    }

    init {
        this.NewsModelList.addAll(NewsModelList)
        this.context = context
        this.entertainmentnewsItemClick = entertainmentnewsItemClick
        this.mBookMarkUnBookmarkInterface = mBookMarkUnBookmarkInterface
        mSessionManager = SessionManager.getInstance(context)!!
        this.playAudioClickListner  = mPlayAudioClickListner
    }


    private fun setColorOfApp(appColor: Int?, binding:  AdapterEntertainmentListBinding) {
        binding.tvDatetime.setTextColor(
            ContextCompat.getColor(
                context!!,
                appColor!!
            )
        )
        val gd = GradientDrawable()
        gd.setStroke(1, appColor!!)
        gd.cornerRadius = 10F
        binding.imgEntertainment.setBackgroundDrawable(gd)
    }

}