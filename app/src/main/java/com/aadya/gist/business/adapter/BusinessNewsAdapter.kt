package com.aadya.gist.business.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.AdapterBusinessListBinding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.BookMarkUnBookmarkInterface
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.playAudioClickListner
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class BusinessNewsAdapter(
    context: Context?,
    NewsModelList: ArrayList<NewsModel>,
    businessnewsItemClick: BusinessNewsListItemClick,
    mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?, mPlayAudioClickListner: playAudioClickListner
) : RecyclerView.Adapter<BusinessNewsAdapter.MyViewHolder>() {

    private val NewsModelList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private val context: Context?
    private val businessnewsItemClick: BusinessNewsListItemClick
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
            .inflate(R.layout.adapter_business_list, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val newsModel = NewsModelList[i]
               with(viewHolder) {
            binding.tvBusinessTitle.text = newsModel.news_title
                   binding.tvBusinessDateTime.text = newsModel.created_date

                   setColorOfApp(mSessionManager.getAppColor(),binding)
            binding.businessMainLayout.setOnClickListener {
                businessnewsItemClick.onItemClick(NewsModelList,i)
            }

                   Picasso.get()
                       .load(R.drawable.business1)
                       .placeholder(R.drawable.applogo)

                       .error(R.drawable.applogo)

                       .into(binding.imgBusiness, object : com.squareup.picasso.Callback {
                           override fun onSuccess() {


                           }

                           override fun onError(e: java.lang.Exception?) {

                           }
                       })
                   binding.imgBookamrk.setOnClickListener {

                       if(newsModel.is_bookmark.equals("0")) {
                           binding.imgBookamrk .setColorFilter(
                               ContextCompat.getColor(context!!,
                                   R.color.appcolor))
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
                              mSessionManager.getAppColor()!!))
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

    interface BusinessNewsListItemClick {
        fun onItemClick(newsModel: ArrayList<NewsModel>, newsModel1: Int)
    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterBusinessListBinding.bind(itemView)
    }

    init {
        this.NewsModelList.addAll(NewsModelList)
        this.context = context
        this.businessnewsItemClick = businessnewsItemClick
        this.mBookMarkUnBookmarkInterface = mBookMarkUnBookmarkInterface
        mSessionManager = SessionManager.getInstance(context)!!
        this.playAudioClickListner  = mPlayAudioClickListner
    }


    private fun setColorOfApp(appColor: Int?, binding:  AdapterBusinessListBinding) {
        binding.tvBusinessDateTime.setTextColor(
            ContextCompat.getColor(
                context!!,
                appColor!!
            )
        )
        val gd = GradientDrawable()
        gd.setStroke(1, appColor!!)
        gd.cornerRadius = 10F
        binding.imgBusiness.setBackgroundDrawable(gd)
    }

}