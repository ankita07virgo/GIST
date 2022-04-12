package com.aadya.gist.covid19.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.AdapterCovid19ListBinding
import com.aadya.gist.covid19.ui.NewsListItemClickListner
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.utils.BookMarkUnBookmarkInterface
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.SessionManager
import com.aadya.gist.utils.playAudioClickListner
import com.squareup.picasso.Picasso


class Covid19Adapter(
    context: Context,
    covid19ItemClick: Covid19ListItemClick,
    mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?,
    mNewsListItemClickListner: NewsListItemClickListner?,
    mPlayAudioClickListner: playAudioClickListner

) : RecyclerView.Adapter<Covid19Adapter.MyViewHolder>() {

    private val NewsModelList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private val context: Context
    private val covid19ItemClick: Covid19ListItemClick
    private val mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface?
    private val mNewsListItemClickListner: NewsListItemClickListner?
    private lateinit var mSessionManager: SessionManager
    private lateinit var mCommonUtils: CommonUtils
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
            .inflate(R.layout.adapter_covid19_list, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val newsModel: NewsModel = NewsModelList[i]
        with(viewHolder) {
            Picasso.get()
                .load(R.drawable.covid1)
                .placeholder(R.drawable.applogo)

                .error(R.drawable.applogo)

                .into(binding.imgCovid19, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {


                    }

                    override fun onError(e: java.lang.Exception?) {

                    }
                })
            binding.tvCovid19Title.text = newsModel.news_title
            binding.tvCovid19DateTime.text = mCommonUtils.getDatIne_MMM_dd_yyyy_format(newsModel.created_date)

            binding.covid19MainLayout.setOnClickListener {
                covid19ItemClick.onItemClick(NewsModelList, i)
            }

            binding.imgDownloadcovid.setOnClickListener {

                mNewsListItemClickListner?.onItemClick(newsModel)
            }

            //https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID
            binding.imgSharecovid.setOnClickListener {
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

            binding.imgBookamrkcovid.setOnClickListener {

                if (newsModel.is_bookmark.equals("0")) {
                    binding.imgBookamrkcovid.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.appcolor
                        )
                    )
                    mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(newsModel.news_id, "1")
                } else if (newsModel.is_bookmark.equals("1")) {
                    binding.imgBookamrkcovid.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.black
                        )
                    )
                    mBookMarkUnBookmarkInterface?.setOnBookMarkClickResult(newsModel.news_id, "0")
                }
            }

            if (newsModel.is_bookmark.equals("1")) {
                binding.imgBookamrkcovid.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        mSessionManager.getAppColor()!!
                    )
                )
            } else if (newsModel.is_bookmark.equals(("0"))) {
                binding.imgBookamrkcovid.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.textcolor
                    )
                )
            }
            setAdapterColor(mSessionManager.getAppColor(), binding)

            binding.imgAudiocovid.setOnClickListener {

                playAudioClickListner?.onAudioClick(newsModel)
        }


            }


    }

    private fun setAdapterColor(appColor: Int?, binding: AdapterCovid19ListBinding) {

        binding.tvCovid19DateTime.setTextColor(
            ContextCompat.getColor(
                context,
                appColor!!
            )
        )
        val gd = GradientDrawable()
        gd.setStroke(1, appColor!!)
        gd.cornerRadius = 10F
        binding.imgCovid19.setBackgroundDrawable(gd)
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

    interface Covid19ListItemClick {
        fun onItemClick(mCovid19ModelList: ArrayList<NewsModel>, newsModel: Int)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AdapterCovid19ListBinding.bind(itemView)
    }

    init {
        this.NewsModelList.addAll(NewsModelList)
        this.context = context
        this.covid19ItemClick = covid19ItemClick
        this.mBookMarkUnBookmarkInterface = mBookMarkUnBookmarkInterface
        this.mNewsListItemClickListner = mNewsListItemClickListner
        mSessionManager = SessionManager.getInstance(context)!!
        mCommonUtils = CommonUtils()
        this.playAudioClickListner  = mPlayAudioClickListner
    }


}