package com.aadya.gist.covid19.ui

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.FragmentCovid19fragmentBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.aadya.gist.commonviewpager.adapter.ui.ViewPagerFragment
import com.aadya.gist.covid19.adapter.Covid19Adapter
import com.aadya.gist.covid19.viewmodel.Covid19Factory
import com.aadya.gist.covid19.viewmodel.Covid19ViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.utils.*

class Covid19fragment : Fragment() {

    private lateinit var mBinding : FragmentCovid19fragmentBinding
    private lateinit var covid19Adapter : Covid19Adapter
    private  lateinit var  mViewModel: Covid19ViewModel
    private lateinit var navigationmenu : NavigationMenu
    private var mCommonUtils : CommonUtils = CommonUtils()
    private lateinit var mIncludedLayoutBinding : MainHeaderBinding
    private var mDrawerInterface: DrawerInterface? = null
    private var mBookMarkUnBookmarkInterface: BookMarkUnBookmarkInterface? = null
    private lateinit var mSessionManager: SessionManager
    private var mNewsListItemClickListner : NewsListItemClickListner? = null
    private lateinit var mAlertBuilderClass: AlertBuilderClass
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mDrawerInterface = context as DrawerInterface
        mBookMarkUnBookmarkInterface = context as BookMarkUnBookmarkInterface
        mNewsListItemClickListner = context as NewsListItemClickListner
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        intializeMembers(inflater, container)

        handleClickListner()

        setUpRecyclerView(mBinding.covid19RecyclerView)
        handleObserver()

        return mBinding.root
    }

    private fun handleClickListner() {
        mIncludedLayoutBinding.imgDrawer.setOnClickListener {
            mDrawerInterface?.setOnDrwawerClickResult()
        }
    }
    private fun setIncludedLayout() {
        mIncludedLayoutBinding = mBinding.mainheaderLayout

        mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(
            requireContext(),
            mSessionManager
        ).resources.getString(R.string.covidnews)
        if(mSessionManager.getUserDetailLoginModel()?.get(0)?.status == true)
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.VISIBLE
        else
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.GONE
    }

    private fun intializeMembers(inflater: LayoutInflater, container: ViewGroup?) {

        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!
        mAlertBuilderClass = AlertBuilderClass()

        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_covid19fragment,
            container,
            false
        )

        arguments?.let {
            navigationmenu = it.getParcelable("navigationmenu")!!

        }

        setIncludedLayout()
        mBinding.emptyTv.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                mSessionManager.getAppColor()!!
            )
        )
        mCommonUtils.setColorOfApp(
            mSessionManager.getAppColor(),
            mIncludedLayoutBinding,
            requireContext(),
            requireActivity(),
            mCommonUtils
        )
        mViewModel = ViewModelProvider(this, Covid19Factory(activity?.application)).get(
            Covid19ViewModel::class.java
        )
        mViewModel.getCovid19NewsList(navigationmenu)
    }


    private fun handleObserver() {
        mViewModel.getCovid19NewsListObserver().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                mBinding.emptyTv.visibility = View.VISIBLE
                mBinding.emptyTv.text = mCommonUtils.getLocaleContext(
                    requireContext(),
                    mSessionManager
                )?.resources?.getString(R.string.no_updated_news)
                mBinding.covid19RecyclerView.visibility = View.GONE
            } else {
                mBinding.emptyTv.visibility = View.GONE
                mBinding.covid19RecyclerView.visibility = View.VISIBLE
                covid19Adapter.notifyData(it)
            }
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
        covid19Adapter = Covid19Adapter(
            requireContext(),

            object : Covid19Adapter.Covid19ListItemClick {
                override fun onItemClick(
                    newsModelList: ArrayList<NewsModel>,
                    recyclerview_position: Int
                ) {
                    openViewPagerFragment(newsModelList, recyclerview_position)
                }
            },
            mBookMarkUnBookmarkInterface,
            mNewsListItemClickListner,object : playAudioClickListner{
                override fun onAudioClick(

                    newsModel: NewsModel

                ) {
                    var mediaPlayer : MediaPlayer = MediaPlayer.create(requireContext(),R.raw.sample)
                    mAlertBuilderClass.showAudioAppAlert(requireContext(),newsModel,mSessionManager,mediaPlayer)
                }

            }
           )
        recyclerView.adapter = covid19Adapter
    }

    companion object {

        @JvmStatic
        fun newInstance(navigationMenu: NavigationMenu) =
            Covid19fragment().apply {
                arguments = Bundle().apply {
                    putParcelable("navigationmenu", navigationMenu)
                }
            }
    }

    private fun openViewPagerFragment(
        newsModelList: ArrayList<NewsModel>,
        recyclerview_position: Int
    ) {

        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        ft?.replace(
            R.id.app_container,
            ViewPagerFragment.newInstance(newsModelList, 9, recyclerview_position)

        )
        ft!!.addToBackStack(null)
        ft.commit()
    }


}