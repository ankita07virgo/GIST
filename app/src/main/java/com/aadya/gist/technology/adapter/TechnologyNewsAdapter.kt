package com.aadya.gist.technology.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.AdapterTechnologyListBinding
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.BookMarkUnBookmarkInterface
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.playAudioClickListner
import com.squareup.picasso.Picasso

class TechnologyNewsAdapter(
    context: Context?,
    NewsModelList: ArrayList<NewsModel>,
    technologyNewsListItemClick: TechnologyNewsListItemClick,
    mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?, mPlayAudioClickListner: playAudioClickListner
) : RecyclerView.Adapter<TechnologyNewsAdapter.MyViewHolder>() {

    private val NewsModelList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private val context: Context?
    private val technologyNewsListItemClick: TechnologyNewsListItemClick
    private val mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?
    private var mSessionManager: SessionManager
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
            .inflate(R.layout.adapter_technology_list, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val NewsModel = NewsModelList[i]
        with(viewHolder) {
            binding.tvTechnologyDatetime.text = NewsModel.created_date
            binding.tvTechnologyTitle.text = NewsModel.news_title
            setColorOfApp(mSessionManager.getAppColor(), binding)
            binding.technologyMainLayout.setOnClickListener {
                technologyNewsListItemClick.onItemClick(NewsModelList,i)
            }

            Picasso.get()
                .load(R.drawable.technology1)
                .placeholder(R.drawable.applogo)

                .error(R.drawable.applogo)

                .into(binding.imgTechnology, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {


                    }

                    override fun onError(e: java.lang.Exception?) {

                    }
                })
            binding.imgBookamrk.setOnClickListener {

                if (NewsModel.is_bookmark.equals("0")) {
                    binding.imgBookamrk.setColorFilter(
                        ContextCompat.getColor(
                            context!!,
                            mSessionManager.getAppColor()!!
                        )
                    )
                    mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(NewsModel.news_id, "1")
                } else if (NewsModel.is_bookmark.equals("1")) {
                    binding.imgBookamrk.setColorFilter(
                        ContextCompat.getColor(
                            context!!,
                            R.color.textcolor
                        )
                    )
                    mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(NewsModel.news_id, "0")
                }
            }

            if (NewsModel.is_bookmark.equals("1")) {
                binding.imgBookamrk.setColorFilter(
                    ContextCompat.getColor(
                        context!!,
                        mSessionManager.getAppColor()!!
                    )
                )
            } else if (NewsModel.is_bookmark.equals(("0"))) {
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

    interface TechnologyNewsListItemClick {
        fun onItemClick(newsModel: ArrayList<NewsModel>, NewsModel: Int)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterTechnologyListBinding.bind(itemView)
    }

    init {
        this.NewsModelList.addAll(NewsModelList)
        this.context = context
        this.technologyNewsListItemClick = technologyNewsListItemClick
        this.mBookMarkUnBookmarkInterface = mBookMarkUnBookmarkInterface
        mSessionManager = SessionManager.getInstance(context)!!
        this.playAudioClickListner  = mPlayAudioClickListner
    }


    private fun setColorOfApp(appColor: Int?, binding: AdapterTechnologyListBinding) {
        binding.tvTechnologyDatetime.setTextColor(
            ContextCompat.getColor(
                context!!,
                appColor!!
            )
        )
        val gd = GradientDrawable()
        gd.setStroke(1, appColor!!)
        gd.cornerRadius = 10F
        binding.imgTechnology.setBackgroundDrawable(gd)
    }

}