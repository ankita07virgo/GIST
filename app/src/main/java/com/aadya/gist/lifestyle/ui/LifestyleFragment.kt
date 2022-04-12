package com.aadya.gist.breakingnews.ui

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
import com.aadya.aadyanews.databinding.FragmentLifestyleNewsBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.aadya.gist.breakingnews.adapter.lifestyleNewsAdapter
import com.aadya.gist.breakingnews.viewmodel.LifestyleNewsViewModel
import com.aadya.gist.commonviewpager.adapter.ui.ViewPagerFragment
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.lifestyle.viewmodel.LifestyleNewsFactory
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.utils.*


/**
 * LatestNewsFragment is for showing the latest news and it is a landing page
 */
class LifestyleFragment : Fragment() {
    private lateinit var mBinding: FragmentLifestyleNewsBinding
    private lateinit var mViewModel: LifestyleNewsViewModel
    private lateinit var lifestyleNewsAdapter : lifestyleNewsAdapter
    private lateinit var navigationmenu : NavigationMenu
    private var mCommonUtils : CommonUtils = CommonUtils()
    private lateinit var mIncludedLayoutBinding : MainHeaderBinding
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

        initializeMembers(inflater,container)
       setIncludedLayout()
        mCommonUtils.setColorOfApp(
            mSessionManager.getAppColor(),
            mIncludedLayoutBinding,
            requireContext(),
            requireActivity(),mCommonUtils
        )
        mBinding.emptyTv.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                mSessionManager.getAppColor()!!
            )
        )

        setUpRecyclerView(mBinding.lifestyleRecyclerView)
        handleClickListner()
        handleObservers()

        return mBinding.root
    }

    private fun handleClickListner() {
        mIncludedLayoutBinding.imgDrawer.setOnClickListener {
            mDrawerInterface?.setOnDrwawerClickResult()
        }
    }

    private fun setIncludedLayout() {
        mIncludedLayoutBinding = mBinding.mainheaderLayout
        mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.Lifestylenews)
        if(mSessionManager.getUserDetailLoginModel()?.get(0)?.status == true)
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.VISIBLE
        else
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.GONE
    }

    private fun initializeMembers(inflater: LayoutInflater, container: ViewGroup?) {
        mAlertBuilderClass = AlertBuilderClass()
        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_lifestyle_news,
            container,
            false
        )
        arguments?.let {
            navigationmenu = it.getParcelable("navigationmenu")!!

        }

        mViewModel = ViewModelProvider(this,
            LifestyleNewsFactory(activity?.application)
        ).get(
            LifestyleNewsViewModel::class.java
        )
        mCommonUtils = CommonUtils()
        mViewModel.getLifeStyleNewsList(navigationmenu)

    }

    private fun handleObservers() {
        mViewModel.getLifeStyleNewsListObserver().observe(viewLifecycleOwner, Observer {

            if(it.isEmpty()){
                mBinding.lifestyleRecyclerView.visibility = View.GONE
                mBinding.emptyTv.visibility = View.VISIBLE
                mBinding.emptyTv.text = mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.no_updated_news)
            }
            else {
                mBinding.lifestyleRecyclerView.visibility = View.VISIBLE
                mBinding.emptyTv.visibility = View.GONE
                lifestyleNewsAdapter.notifyData(it)
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
                        context?.let { mCommonUtils.showProgress(
                            mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.pleasewait),
                            it,
                            mSessionManager.getAppColor()
                        ) }
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
         lifestyleNewsAdapter = lifestyleNewsAdapter(
             activity!!.applicationContext,
            ArrayList<NewsModel>(),
            object : lifestyleNewsAdapter.LatestNewsListItemClick {
                override fun onItemClick(newsModellist: ArrayList<NewsModel>, recyclerview_position: Int) {
                    openDetailedNewsFragment(newsModellist,recyclerview_position)
                }
            },mBookMarkUnBookmarkInterface,object : playAudioClickListner{
                 override fun onAudioClick(

                     newsModel: NewsModel

                 ) {
                     var mediaPlayer : MediaPlayer = MediaPlayer.create(requireContext(),R.raw.sample)
                     mAlertBuilderClass.showAudioAppAlert(requireContext(),newsModel,mSessionManager,mediaPlayer)
                 }

             })
        recyclerView.adapter = lifestyleNewsAdapter
    }


    private fun openDetailedNewsFragment(newsModellist: ArrayList<NewsModel>, recyclerview_position: Int) {

        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        ft?.replace(
            R.id.app_container,
            ViewPagerFragment.newInstance(newsModellist, 11, recyclerview_position)
        )
        ft!!.addToBackStack(null)
        ft.commit()
    }


    companion object {
        @JvmStatic
        fun newInstance(navigationMenu: NavigationMenu) =
            LifestyleFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("navigationmenu", navigationMenu)
                }
            }


    }
}