package com.aadya.gist.breakingnews.ui

import android.app.DownloadManager
import android.content.*
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.FragmentBreakingNewsBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.aadya.gist.breakingnews.adapter.BreakingNewsAdapter
import com.aadya.gist.breakingnews.adapter.BreakingNewsVideosAdapter
import com.aadya.gist.breakingnews.viewmodel.BreakingNewsFactory
import com.aadya.gist.breakingnews.viewmodel.BreakingNewsViewModel
import com.aadya.gist.commonviewpager.adapter.ui.ViewPagerFragment
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.utils.*


/**
 * LatestNewsFragment is for showing the latest news and it is a landing page
 */
class BreakingNewsFragment : Fragment() {
    private lateinit var mBinding: FragmentBreakingNewsBinding
    private lateinit var mIncludedLayoutBinding: MainHeaderBinding
    private lateinit var mViewModel: BreakingNewsViewModel
    private lateinit var breakingNewsAdapter: BreakingNewsAdapter
    private lateinit var breakingNewsVideoAdapter: BreakingNewsVideosAdapter
    private lateinit var navigationmenu: NavigationMenu
    private var mCommonUtils: CommonUtils = CommonUtils()
    private var mDrawerInterface: DrawerInterface? = null
    private var mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface? = null
    private lateinit var mSessionManager: SessionManager
    private lateinit var mAlertBuilderClass: AlertBuilderClass
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mDrawerInterface = context as DrawerInterface
        mBookMarkUnBookmarkInterface = context as BookMarkUnBookmarkInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        intializeMembers(inflater, container)

        setAdapter()
        setUpRecyclerView(mBinding.newsRecyclerView)


        handleObservers()
        handleClickListner()

        return mBinding.root
    }



    private fun setIncludedLayout() {
        mIncludedLayoutBinding = mBinding.mainheaderLayout
        mIncludedLayoutBinding.tvMainheader.text = mCommonUtils.getLocaleContext(
            requireContext(),
            mSessionManager
        ).resources.getString(R.string.breakingnews)

        if(mSessionManager.getUserDetailLoginModel()!= null && mSessionManager.getUserDetailLoginModel()?.get(
                0
            )?.status!!)
        mIncludedLayoutBinding.imgLoggedIn.visibility = View.VISIBLE
        else
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.GONE



    }

    fun openYoutubeLink(youtubeID: String) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
        val intentBrowser =
            Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
        try {
            this.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            this.startActivity(intentBrowser)
        }

    }

    private fun intializeMembers(inflater: LayoutInflater, container: ViewGroup?) {
        mAlertBuilderClass = AlertBuilderClass()
        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_breaking_news,
            container,
            false
        )

        arguments?.let {
            navigationmenu = it.getParcelable("navigationmenu")!!

        }

        mViewModel = ViewModelProvider(
            this,
            BreakingNewsFactory(activity?.application)
        ).get(
            BreakingNewsViewModel::class.java
        )
        mCommonUtils = CommonUtils()

        setIncludedLayout()
        mCommonUtils.setColorOfApp(
            mSessionManager.getAppColor(),
            mIncludedLayoutBinding,
            requireContext(),
            requireActivity(), mCommonUtils
        )
        mBinding.emptyTv.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                mSessionManager.getAppColor()!!
            )
        )
        mViewModel.getBreakingNewsList(navigationmenu)
    }

    private fun handleClickListner() {
        mIncludedLayoutBinding.imgDrawer.setOnClickListener {
            mDrawerInterface?.setOnDrwawerClickResult()
        }
    }


    private fun handleObservers() {
        mViewModel.getBreakingNewsListObserver().observe(viewLifecycleOwner, Observer {

            if (it.isEmpty()) {
                mBinding.topLinear.visibility = View.GONE
                mBinding.emptyTv.visibility = View.VISIBLE
                mBinding.emptyTv.text = mCommonUtils.getLocaleContext(
                    requireContext(),
                    mSessionManager
                )?.resources?.getString(R.string.no_updated_news)
                mBinding.videosViewpager.visibility = View.GONE
            } else {
                it[0].video_url = "https://www.youtube.com/watch?v=WB-y7_ymPJ4"
                mBinding.topLinear.visibility = View.VISIBLE
                mBinding.emptyTv.visibility = View.GONE
                breakingNewsAdapter.notifyData(it)
                mViewModel.getBreakingVideoList(navigationmenu)
            }
        })

        mViewModel.getBreakingVideoListObserver().observe(viewLifecycleOwner, Observer {


            breakingNewsVideoAdapter = BreakingNewsVideosAdapter(
                activity!!.supportFragmentManager, it as ArrayList<String>
            )
            mBinding.videosViewpager.adapter = breakingNewsVideoAdapter
        })


        mViewModel.getAlertViewState()?.observe(viewLifecycleOwner,
            object : Observer<AlertModel?> {
                override fun onChanged(alertModel: AlertModel?) {
                    if (alertModel == null) return
                    mCommonUtils.showAlert(
                        alertModel.duration,
                        alertModel.title,
                        alertModel.message,
                        alertModel.drawable,
                        alertModel.color,
                        requireActivity(),
                        mSessionManager
                    )
                }
            })


        mViewModel.getProgressState()?.observe(viewLifecycleOwner,
            object : Observer<Int?> {
                override fun onChanged(progressState: Int?) {
                    if (progressState == null) return
                    if (progressState === CommonUtils.ProgressDialog.showDialog)
                        context?.let {
                            mCommonUtils.showProgress(
                                mCommonUtils.getLocaleContext(
                                    requireContext(),
                                    mSessionManager
                                )?.resources?.getString(R.string.pleasewait),
                                it,
                                mSessionManager.getAppColor()
                            )
                        }
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })
    }

    private fun setAdapter() {
        breakingNewsVideoAdapter = BreakingNewsVideosAdapter(
            activity!!.supportFragmentManager, ArrayList<String>()
        )
        mBinding.videosViewpager.adapter = breakingNewsVideoAdapter
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(activity?.applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        breakingNewsAdapter = BreakingNewsAdapter(
            activity!!.applicationContext,
            ArrayList<NewsModel>(),
            object : BreakingNewsAdapter.LatestNewsListItemClick {
                override fun onItemClick(
                    newsModelList: ArrayList<NewsModel>,
                    recyclerview_position: Int
                ) {
                    openDetailedNewsFragment(newsModelList, recyclerview_position)
                }
            }, mBookMarkUnBookmarkInterface, object : playAudioClickListner {
                override fun onAudioClick(

                    newsModel: NewsModel

                ) {
                    var mediaPlayer: MediaPlayer = MediaPlayer.create(
                        requireContext(),
                        R.raw.sample
                    )
                    mAlertBuilderClass.showAudioAppAlert(
                        requireContext(),
                        newsModel,
                        mSessionManager,
                        mediaPlayer
                    )
                }

            })
        recyclerView.adapter = breakingNewsAdapter
    }


    private fun openDetailedNewsFragment(
        newsModelList: ArrayList<NewsModel>,
        recyclerview_position: Int
    ) {

        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        ft?.replace(
            R.id.app_container,
            ViewPagerFragment.newInstance(newsModelList, 6, recyclerview_position)
        )
        ft!!.addToBackStack(null)
        ft.commit()

    }


    companion object {
        @JvmStatic
        fun newInstance(navigationMenu: NavigationMenu) =

            BreakingNewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("navigationmenu", navigationMenu)
                }
            }



    }





}