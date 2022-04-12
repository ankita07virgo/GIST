package com.aadya.gist.india.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.AdapterIndiaListBinding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.BookMarkUnBookmarkInterface
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.playAudioClickListner
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class IndiaNewsAdapter(
    context: Context?,
    indiaNewsModelList: ArrayList<NewsModel>,
    indianewsItemClick: IndiaNewsListItemClick,
    mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?, mPlayAudioClickListner: playAudioClickListner
) : RecyclerView.Adapter<IndiaNewsAdapter.MyViewHolder>() {

    private val indiaNewsModelList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private val context: Context?
    private val indianewsItemClick: IndiaNewsListItemClick
    private val mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?
    private  var mSessionManager: SessionManager
    private var playAudioClickListner: playAudioClickListner? = null
    fun notifyData(indiaNewsModelList: List<NewsModel>) {
        if (this.indiaNewsModelList != null) {
            this.indiaNewsModelList.clear()
            this.indiaNewsModelList.addAll(indiaNewsModelList)
            notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_india_list, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val indiaNewsModel: NewsModel = indiaNewsModelList[i]
               with(viewHolder) {
                   binding.tvDatetime.text = indiaNewsModel.created_date
                   binding.tvNewstitle.text = indiaNewsModel.news_title

                   setColorOfApp(mSessionManager.getAppColor(),binding)
                   Picasso.get()
                       .load(R.drawable.india1)
                       .placeholder(R.drawable.applogo)

                       .error(R.drawable.applogo)

                       .into(binding.imgIndia, object : com.squareup.picasso.Callback {
                           override fun onSuccess() {


                           }

                           override fun onError(e: java.lang.Exception?) {

                           }
                       })

            binding.indiaMainLayout.setOnClickListener {
                indianewsItemClick.onItemClick(indiaNewsModelList,i)
            }

                   binding.imgBookamrk.setOnClickListener {

                       if(indiaNewsModel.is_bookmark.equals("0")) {
                           binding.imgBookamrk .setColorFilter(
                               ContextCompat.getColor(context!!,
                                   mSessionManager.getAppColor()!!))
                           mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(indiaNewsModel.news_id, "1")
                       }
                       else if(indiaNewsModel.is_bookmark.equals("1")){
                           binding.imgBookamrk .setColorFilter(
                               ContextCompat.getColor(context!!,
                                   mSessionManager.getAppColor()!!))
                           mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(indiaNewsModel.news_id, "0")
                       }
                   }

                   if(indiaNewsModel.is_bookmark.equals("1")){
                       binding.imgBookamrk .setColorFilter(
                           ContextCompat.getColor(context!!,
                               R.color.appcolor))
                   }
                   else if(indiaNewsModel.is_bookmark.equals(("0"))){
                       binding.imgBookamrk .setColorFilter(
                           ContextCompat.getColor(context!!,
                               R.color.textcolor))
                   }
                   binding.imgShare.setOnClickListener {
                       try {
                           val shareIntent = Intent(Intent.ACTION_SEND)
                           shareIntent.type = "text/plain"
                           shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)

                           var shareMessage = "\n${indiaNewsModel.news_title}\n${indiaNewsModel.short_description}\n${indiaNewsModel.short_description}\n${indiaNewsModel.video_url}"
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

                       playAudioClickListner?.onAudioClick(indiaNewsModel)
                   }
        }
    }

    override fun getItemCount(): Int {
        return indiaNewsModelList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface IndiaNewsListItemClick {
        fun onItemClick(modelIndia: ArrayList<NewsModel>, indiaNewsModel: Int)
    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterIndiaListBinding.bind(itemView)
    }

    init {
        this.indiaNewsModelList.addAll(indiaNewsModelList)
        this.context = context
        this.indianewsItemClick = indianewsItemClick
        this.mBookMarkUnBookmarkInterface = mBookMarkUnBookmarkInterface
        mSessionManager = SessionManager.getInstance(context)!!
        this.playAudioClickListner  = mPlayAudioClickListner
    }


    private fun setColorOfApp(appColor: Int?, binding:  AdapterIndiaListBinding) {
        binding.tvDatetime.setTextColor(
            ContextCompat.getColor(
                context!!,
                appColor!!
            )
        )

        val gd = GradientDrawable()
        gd.setStroke(1, appColor!!)
        gd.cornerRadius = 10F
        binding.imgIndia.setBackgroundDrawable(gd)

    }

}