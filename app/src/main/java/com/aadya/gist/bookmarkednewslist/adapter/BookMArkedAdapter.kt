package com.aadya.gist.bookmarkednewslist.adapter

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
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.playAudioClickListner
import com.squareup.picasso.Picasso


class BookMArkedAdapter(
    context: Context?,
    NewsModelList: ArrayList<NewsModel>,
    indianewsItemClick: IndiaNewsListItemClick, mPlayAudioClickListner: playAudioClickListner
) : RecyclerView.Adapter<BookMArkedAdapter.MyViewHolder>() {

    private val NewsModelList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private val context: Context?
    private val indianewsItemClick: IndiaNewsListItemClick
    private lateinit var mSessionManager: SessionManager
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
            .inflate(R.layout.adapter_india_list, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val NewsModel: NewsModel = NewsModelList[i]
               with(viewHolder) {
                   binding.tvDatetime.text = NewsModel.created_date
                   binding.tvNewstitle.text = NewsModel.news_title

                   setAdapterColor(mSessionManager.getAppColor(), binding)

                   if(NewsModel.category_name.equals("Sport")){
                       Picasso.get()
                           .load(R.drawable.sports1)
                           .placeholder(R.drawable.applogo)

                           .error(R.drawable.applogo)

                           .into(binding.imgIndia, object : com.squareup.picasso.Callback {
                               override fun onSuccess() {


                               }

                               override fun onError(e: java.lang.Exception?) {

                               }
                           })
                   }
                   else  if(NewsModel.category_name.equals("World")){
                       Picasso.get()
                           .load(R.drawable.world1)
                           .placeholder(R.drawable.applogo)

                           .error(R.drawable.applogo)

                           .into(binding.imgIndia, object : com.squareup.picasso.Callback {
                               override fun onSuccess() {


                               }

                               override fun onError(e: java.lang.Exception?) {

                               }
                           })
                   }



binding.imgBookamrk.visibility = View.GONE
            binding.indiaMainLayout.setOnClickListener {
                indianewsItemClick.onItemClick(NewsModel)
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
        return NewsModelList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface IndiaNewsListItemClick {
        fun onItemClick(modelIndia: NewsModel)
    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterIndiaListBinding.bind(itemView)
    }

    init {
        this.NewsModelList.addAll(NewsModelList)
        this.context = context
        this.indianewsItemClick = indianewsItemClick
        mSessionManager = SessionManager.getInstance(context)!!
        this.playAudioClickListner  = mPlayAudioClickListner
    }


    private fun setAdapterColor(appColor: Int?, binding: AdapterIndiaListBinding) {


        val gd = GradientDrawable()
        gd.setStroke(1, appColor!!)
        gd.cornerRadius = 10F
        binding.imgIndia.setBackgroundDrawable(gd)

        binding.tvDatetime.setTextColor(
            ContextCompat.getColor(
                context!!,
                appColor!!
            )
        )

    }

}