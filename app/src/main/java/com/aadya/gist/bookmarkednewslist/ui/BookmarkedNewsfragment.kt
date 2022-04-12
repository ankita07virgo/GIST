package com.aadya.gist.bookmarkednewslist.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.FragmentBookmarkednewsBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.aadya.gist.bookmarkednewslist.adapter.BookMArkedAdapter
import com.aadya.gist.bookmarkednewslist.viewmodel.ListBookmarkedNewsFactory
import com.aadya.gist.bookmarkednewslist.viewmodel.ListBookmarkedNewsViewModel
import com.aadya.gist.breakingnews.ui.DetailedLifestyleNewsFragment
import com.aadya.gist.breakingnews.ui.DetailedNewsFragment
import com.aadya.gist.business.ui.DetailedBusinessFragment
import com.aadya.gist.covid19.ui.DetailedCovid19Fragment
import com.aadya.gist.entertainment.ui.DetailedEntertainmentFragment
import com.aadya.gist.health.ui.DetailedHealthFragment
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.india.ui.DetailedIndiaFragment
import com.aadya.gist.local.ui.DetailedLocalFragment
import com.aadya.gist.politics.ui.DetailedPoliticsFragment
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.sports.ui.DetailedSportsFragment
import com.aadya.gist.technology.ui.DetailedTechnologyFragment
import com.aadya.gist.utils.*
import com.aadya.gist.world.ui.DetailedWorldNewsFragment


class BookmarkedNewsfragment : Fragment() {

    private lateinit var mBinding: FragmentBookmarkednewsBinding
    private lateinit var bookmarkedAdapter: BookMArkedAdapter
    private lateinit var mViewModelList: ListBookmarkedNewsViewModel
    private lateinit var mIncludedLayoutBinding: MainHeaderBinding
    private var mDrawerInterface: DrawerInterface? = null
    private var mCommonUtils: CommonUtils = CommonUtils()
    private lateinit var mSessionManager: SessionManager
    private var value_open_downloadcontent_bookmark : Int = 0
    private lateinit var mAlertBuilderClass: AlertBuilderClass
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mDrawerInterface = context as DrawerInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        intializeMembers(inflater, container)
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


        setUpRecyclerView(mBinding.bookmarkedRecyclerView)
        loadFragment_According_condition()

        handleClickListner()


        return mBinding.root
    }

    private fun loadFragment_According_condition() {
        if(value_open_downloadcontent_bookmark == 0){
            //BookMark Fragment
            mIncludedLayoutBinding.tvMainheader.text = mCommonUtils.getLocaleContext(
                requireContext(),
                mSessionManager
            )?.resources?.getString(R.string.bookmarkheader)
            mViewModelList = ViewModelProvider(
                this,
                ListBookmarkedNewsFactory(
                    activity?.application
                )
            ).get(
                ListBookmarkedNewsViewModel::class.java
            )
            mViewModelList.getBookmarkedNewsList()
            handleObserver()
        }
        else if(value_open_downloadcontent_bookmark == 1){
            //Open DownloadContent Fragment
            mIncludedLayoutBinding.tvMainheader.text = mCommonUtils.getLocaleContext(
                requireContext(),
                mSessionManager
            )?.resources?.getString(R.string.DownloadedContent)
            if(mSessionManager.getDownloadedContenList()== null || mSessionManager.getDownloadedContenList()?.size == 0){
                mBinding.bookmarkedRecyclerView.visibility = View.GONE
                mBinding.emptyTv.visibility = View.VISIBLE

                mBinding.emptyTv.text = mCommonUtils.getLocaleContext(
                    requireContext(),
                    mSessionManager
                )?.resources?.getString(R.string.no_updated_news)
            }
            else if(mSessionManager.getDownloadedContenList()!= null &&  mSessionManager.getDownloadedContenList()?.size!! > 0){
                mBinding.bookmarkedRecyclerView.visibility = View.VISIBLE
                mBinding.emptyTv.visibility = View.GONE
                bookmarkedAdapter.notifyData(mSessionManager.getDownloadedContenList()!!)
            }
        }



    }

    private fun setIncludedLayout() {
        mIncludedLayoutBinding = mBinding.mainheaderLayout
        var context = LocaleHelper.setLocale(activity?.applicationContext,
            mSessionManager.getSelectedLanguage()?.let {
                mCommonUtils.setLanguage(
                    it
                )
            })

        if(mSessionManager.getUserDetailLoginModel()?.get(0)?.status == true)
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.VISIBLE
        else
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.GONE
    }

    private fun handleClickListner() {
        mIncludedLayoutBinding.imgDrawer.setOnClickListener {
            mDrawerInterface?.setOnDrwawerClickResult()
        }
    }

    private fun intializeMembers(inflater: LayoutInflater, container: ViewGroup?) {
        mAlertBuilderClass = AlertBuilderClass()
        arguments?.let {
            value_open_downloadcontent_bookmark = it.getInt("open_downloadcontent_bookmark")!!

        }

        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!

        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bookmarkednews,
            container,
            false
        )



    }

    private fun handleObserver() {
        mViewModelList.getBookmarkedNewsListObserver().observe(viewLifecycleOwner, Observer {

            if (it.isEmpty()) {
                mBinding.bookmarkedRecyclerView.visibility = View.GONE
                mBinding.emptyTv.visibility = View.VISIBLE

                mBinding.emptyTv.text = mCommonUtils.getLocaleContext(
                    requireContext(),
                    mSessionManager
                )?.resources?.getString(R.string.no_updated_news)
            } else {
                mBinding.bookmarkedRecyclerView.visibility = View.VISIBLE
                mBinding.emptyTv.visibility = View.GONE
                bookmarkedAdapter.notifyData(it)
            }

        })


        mViewModelList.getAlertViewState()?.observe(viewLifecycleOwner,
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


        mViewModelList.getProgressState()?.observe(viewLifecycleOwner,
            object : Observer<Int?> {
                override fun onChanged(progressState: Int?) {
                    if (progressState == null) return
                    if (progressState === CommonUtils.ProgressDialog.showDialog)
                        context?.let {
                            mCommonUtils.showProgress(
                                mCommonUtils.getLocaleContext(
                                    requireContext(),
                                    mSessionManager
                                )?.resources?.getString(
                                    R.string.pleasewait
                                ),
                                it,
                                mSessionManager.getAppColor()
                            )
                        }
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })
    }


    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(activity?.applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        bookmarkedAdapter = BookMArkedAdapter(activity?.applicationContext,
            ArrayList<NewsModel>(),
            object : BookMArkedAdapter.IndiaNewsListItemClick {
                override fun onItemClick(newsModel: NewsModel) {
                    openDetailedNewsFragment(newsModel)
                }
            }, object : playAudioClickListner {
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

        recyclerView.adapter = bookmarkedAdapter
    }


    private fun openDetailedNewsFragment(NewsModel: NewsModel) {

        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        if (NewsModel.category_id == "1") {
            ft?.replace(
                R.id.app_container,
                DetailedIndiaFragment.newInstance(NewsModel),
                "DetailedIndia"
            )
        }else if (NewsModel.category_id == "2") {
            ft?.replace(
                R.id.app_container,
                DetailedWorldNewsFragment.newInstance(NewsModel),
                "DetailedWorld"
            )
        }
        else if (NewsModel.category_id == "3") {
            ft?.replace(
                R.id.app_container,
                DetailedPoliticsFragment.newInstance(NewsModel),
                "DetailedPolitics"
            )
        } else if (NewsModel.category_id == "4") {
            ft?.replace(
                R.id.app_container,
                DetailedBusinessFragment.newInstance(NewsModel),
                "DetailedBuisness"
            )
        }else if (NewsModel.category_id == "5") {
            ft?.replace(
                R.id.app_container,
                DetailedSportsFragment.newInstance(NewsModel),
                "DetailedSports"
            )
        }else if (NewsModel.category_id == "6") {
            ft?.replace(
                R.id.app_container,
                DetailedNewsFragment.newInstance(NewsModel),
                "Breaking News"
            )
        }else if (NewsModel.category_id == "7") {
            ft?.replace(
                R.id.app_container,
                DetailedLocalFragment.newInstance(NewsModel),
                "Local"
            )
        }else if (NewsModel.category_id == "8") {
            ft?.replace(
                R.id.app_container,
                DetailedHealthFragment.newInstance(NewsModel),
                "Health"
            )
        }else if (NewsModel.category_id == "9") {
            ft?.replace(
                R.id.app_container,
                DetailedCovid19Fragment.newInstance(NewsModel),
                "Covid19"
            )
        }else if (NewsModel.category_id == "10") {
            ft?.replace(
                R.id.app_container,
                DetailedEntertainmentFragment.newInstance(NewsModel),
                "Entertainment"
            )
        }else if (NewsModel.category_id == "11") {
            ft?.replace(
                R.id.app_container,
                DetailedLifestyleNewsFragment.newInstance(NewsModel),
                "Lifestyle"
            )
        }else if (NewsModel.category_id == "12") {
            ft?.replace(
                R.id.app_container,
                DetailedTechnologyFragment.newInstance(NewsModel),
                "Technology"
            )
        } else   {
            ft?.replace(
                R.id.app_container,
                DetailedNewsFragment.newInstance(NewsModel),
                "Breaking News"
            )
        }


        ft!!.addToBackStack(null)
        ft.commit()
    }


    companion object {

        @JvmStatic
        fun newInstance(i: Int) =
            BookmarkedNewsfragment().apply {
                arguments = Bundle().apply {
                    putInt("open_downloadcontent_bookmark", i)
                }
            }
    }


}