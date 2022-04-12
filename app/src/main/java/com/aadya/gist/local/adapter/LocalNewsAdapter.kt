package com.aadya.gist.local.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.AdapterLocalListBinding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.BookMarkUnBookmarkInterface
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.playAudioClickListner
import com.squareup.picasso.Picasso

class LocalNewsAdapter(
    context: Context?,
    newsModelList: ArrayList<NewsModel>,
    localnewsItemClick: LocalNewsListItemClick,
    mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?, mPlayAudioClickListner: playAudioClickListner
) : RecyclerView.Adapter<LocalNewsAdapter.MyViewHolder>() {

    private val newsModelList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private val context: Context?
    private val localnewsItemClick: LocalNewsListItemClick
    private val mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?
    private var mSessionManager: SessionManager
    private var playAudioClickListner: playAudioClickListner? = null
    fun notifyData(newsModelList: List<NewsModel>) {
        if (this.newsModelList != null) {
            this.newsModelList.clear()
            this.newsModelList.addAll(newsModelList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {

        val itemView: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_local_list, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val newsModel = newsModelList[i]
        with(viewHolder) {
            binding.tvLocalDate.text = newsModel.created_date
            binding.tvLocalnews.text = newsModel.news_title


            setColorOfApp(mSessionManager.getAppColor(), binding)
            binding.localMainLayout.setOnClickListener {
                localnewsItemClick.onItemClick(newsModelList,i)
            }

            Picasso.get()
                .load(R.drawable.delhiimg)
                .placeholder(R.drawable.applogo)

                .error(R.drawable.applogo)

                .into(binding.imgLocal, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {


                    }

                    override fun onError(e: java.lang.Exception?) {

                    }
                })
            binding.imgBookamrk.setOnClickListener {

                if (newsModel.is_bookmark.equals("0")) {
                    binding.imgBookamrk.setColorFilter(
                        ContextCompat.getColor(
                            context!!,
                            mSessionManager.getAppColor()!!
                        )
                    )
                    mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(newsModel.news_id, "1")
                } else if (newsModel.is_bookmark.equals("1")) {
                    binding.imgBookamrk.setColorFilter(
                        ContextCompat.getColor(
                            context!!,
                            R.color.textcolor
                        )
                    )
                    mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(newsModel.news_id, "0")
                }
            }

            if (newsModel.is_bookmark.equals("1")) {
                binding.imgBookamrk.setColorFilter(
                    ContextCompat.getColor(
                        context!!,
                        mSessionManager.getAppColor()!!
                    )
                )
            } else if (newsModel.is_bookmark.equals(("0"))) {
                binding.imgBookamrk.setColorFilter(
                    ContextCompat.getColor(
                        context!!,
                        R.color.textcolor
                    )
                )
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
        return newsModelList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface LocalNewsListItemClick {
        fun onItemClick(modelState: ArrayList<NewsModel>, newsModel: Int)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterLocalListBinding.bind(itemView)
    }

    init {
        this.newsModelList.addAll(newsModelList)
        this.context = context
        this.localnewsItemClick = localnewsItemClick
        this.mBookMarkUnBookmarkInterface = mBookMarkUnBookmarkInterface
        mSessionManager = SessionManager.getInstance(context)!!
        this.playAudioClickListner  = mPlayAudioClickListner
    }


    private fun setColorOfApp(appColor: Int?, binding: AdapterLocalListBinding) {
        binding.tvLocalDate.setTextColor(
            ContextCompat.getColor(
                context!!,
                appColor!!
            )
        )
        val gd = GradientDrawable()
        gd.setStroke(1, appColor!!)
        gd.cornerRadius = 10F
        binding.imgLocal.setBackgroundDrawable(gd)
    }


}