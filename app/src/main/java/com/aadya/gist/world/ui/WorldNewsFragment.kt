package com.aadya.gist.world.ui

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
import com.aadya.aadyanews.databinding.FragmentWorldNewsBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.aadya.gist.commonviewpager.adapter.ui.ViewPagerFragment
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.utils.*
import com.aadya.gist.world.adapter.WorldNewsAdapter
import com.aadya.gist.world.viewmodel.WorldNewsFactory
import com.aadya.gist.world.viewmodel.WorldNewsViewModel

/**
 * LatestNewsFragment is for showing the latest news and it is a landing page
 */
class WorldNewsFragment : Fragment() {
    private lateinit var mBinding: FragmentWorldNewsBinding
    private lateinit var mViewModel: WorldNewsViewModel
    private lateinit var worldNewsAdapter: WorldNewsAdapter
    private lateinit var mCommonUtils: CommonUtils
    private lateinit var navigationmenu: NavigationMenu
    private lateinit var mIncludedLayoutBinding: MainHeaderBinding
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

        setUpRecyclerView(mBinding.worldRecyclerView)

        handleObservers()
        handleClickListner()
        return mBinding.root
    }



    private fun setIncludedLayout() {
       mIncludedLayoutBinding = mBinding.mainheaderLayout
        mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.worldnews)
        if(mSessionManager.getUserDetailLoginModel()?.get(0)?.status == true)
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.VISIBLE
        else
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.GONE
    }
    private fun intializeMembers(inflater: LayoutInflater, container: ViewGroup?) {
        mAlertBuilderClass = AlertBuilderClass()
        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!

        arguments?.let {
            navigationmenu = it.getParcelable("navigationmenu")!!

        }

        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_world_news,
            container,
            false
        )

        mViewModel = ViewModelProvider(this, WorldNewsFactory(activity?.application)).get(
            WorldNewsViewModel::class.java
        )
        mCommonUtils = CommonUtils()

        mViewModel.getWorldNewsList(navigationmenu)
    }


    private fun handleClickListner() {
        mIncludedLayoutBinding.imgDrawer.setOnClickListener {
            mDrawerInterface?.setOnDrwawerClickResult()
        }
    }

    private fun handleObservers() {
        mViewModel.getWorldNewsListObserver().observe(viewLifecycleOwner, Observer {

            if (it.isEmpty()) {
                mBinding.emptyTv.visibility = View.VISIBLE
                mBinding.emptyTv.text = mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.no_updated_news)
                mBinding.worldRecyclerView.visibility = View.GONE
            } else {
                mBinding.emptyTv.visibility = View.GONE
                mBinding.worldRecyclerView.visibility = View.VISIBLE
                worldNewsAdapter.notifyData(it)
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
        worldNewsAdapter = WorldNewsAdapter(
            activity?.applicationContext,
            ArrayList<NewsModel>(),
            object : WorldNewsAdapter.WorldNewsListItemClick {
                override fun onItemClick(
                    worldNewsModelList: ArrayList<NewsModel>,
                    recyclerview_position: Int
                ) {
                    openDetailedWorldNewsFragment(worldNewsModelList, recyclerview_position)
                }
            }, mBookMarkUnBookmarkInterface,object : playAudioClickListner{
                override fun onAudioClick(

                    newsModel: NewsModel

                ) {
                    var mediaPlayer : MediaPlayer = MediaPlayer.create(requireContext(),R.raw.sample)
                    mAlertBuilderClass.showAudioAppAlert(requireContext(),newsModel,mSessionManager,mediaPlayer)
                }

            }
        )
        recyclerView.adapter = worldNewsAdapter
    }


    private fun openDetailedWorldNewsFragment(
        worldNewsModelList: ArrayList<NewsModel>,
        recyclerview_position: Int
    ) {

        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        ft?.replace(
            R.id.app_container,
            ViewPagerFragment.newInstance(worldNewsModelList, 2, recyclerview_position)
        )


        ft!!.addToBackStack(null)
        ft.commit()

    }


    companion object {

        @JvmStatic
        fun newInstance(navigationMenu: NavigationMenu?) =

            WorldNewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("navigationmenu", navigationMenu)
                }
            }
    }
}