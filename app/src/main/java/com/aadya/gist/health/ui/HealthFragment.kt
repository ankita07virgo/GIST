package com.aadya.gist.health.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.FragmentHealthBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.aadya.gist.commonviewpager.adapter.ui.ViewPagerFragment
import com.aadya.gist.health.adapter.HealthNewsAdapter
import com.aadya.gist.health.viewmodel.HealthNewsFactory
import com.aadya.gist.health.viewmodel.HealthNewsViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.utils.*
import com.squareup.picasso.Picasso


class HealthFragment : Fragment() {
    private lateinit var mBinding : FragmentHealthBinding
    private lateinit var mHealthAdapter : HealthNewsAdapter
    private lateinit var mViewModel: HealthNewsViewModel
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        intializeMembers(inflater,container)
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
        setUpRecyclerView(mBinding.healthRecyclerView)
        handleObserver()
        handleClickListner()
        return mBinding.root
    }

    private fun handleClickListner() {
        mIncludedLayoutBinding.imgDrawer.setOnClickListener {
            mDrawerInterface?.setOnDrwawerClickResult()
        }
    }

    private fun setIncludedLayout() {
        mIncludedLayoutBinding = mBinding.mainheaderLayout
        mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.healthnews)
        if(mSessionManager.getUserDetailLoginModel()?.get(0)?.status == true)
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.VISIBLE
        else
            mIncludedLayoutBinding.imgLoggedIn.visibility = View.GONE

    }

    private fun intializeMembers(inflater: LayoutInflater, container: ViewGroup?) {
        mAlertBuilderClass = AlertBuilderClass()
        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_health,
            container,
            false
        )
        arguments?.let {
            navigationmenu = it.getParcelable("navigationmenu")!!

        }

        mViewModel = ViewModelProvider(this, HealthNewsFactory(activity?.application)).get(
            HealthNewsViewModel::class.java
        )

        mViewModel.getHealthNewsList(navigationmenu)
    }

    private fun handleObserver() {
        mViewModel.getHealthNewsListObserver().observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                mBinding.topRelative.visibility = View.GONE
                mBinding.emptyTv.visibility = View.VISIBLE
                mBinding.emptyTv.text =  mCommonUtils.getLocaleContext(requireContext(), mSessionManager)?.resources?.getString(R.string.no_updated_news)
            }
            else {
                mBinding.topRelative.visibility = View.VISIBLE
                mBinding.emptyTv.visibility = View.GONE
                mHealthAdapter.notifyData(it)
                Picasso.get()
                    .load(R.drawable.health1)
                    .placeholder(R.drawable.applogo)

                    .error(R.drawable.applogo)

                    .into( mBinding.imgHealthnews, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {


                        }

                        override fun onError(e: java.lang.Exception?) {

                        }
                    })
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
                            mCommonUtils.getLocaleContext(requireContext(), mSessionManager)?.resources?.getString(R.string.pleasewait),
                            it,
                            mSessionManager.getAppColor()
                        ) }
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })
    }

    companion object {

        @JvmStatic
        fun newInstance(navigationMenu: NavigationMenu) =
            HealthFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("navigationmenu", navigationMenu)
                }
            }
            }
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(activity?.applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        mHealthAdapter = HealthNewsAdapter(activity?.applicationContext,
            ArrayList<NewsModel>(),
            object : HealthNewsAdapter.HealthNewsListItemClick {
                override fun onItemClick(newsModelList: ArrayList<NewsModel>, recyclerview_position: Int) {
                    openDetailedHealthFragment(newsModelList, recyclerview_position)
                }
            },mBookMarkUnBookmarkInterface,object : playAudioClickListner{
                override fun onAudioClick(

                    newsModel: NewsModel

                ) {
                    var mediaPlayer : MediaPlayer = MediaPlayer.create(requireContext(),R.raw.sample)
                    mAlertBuilderClass.showAudioAppAlert(requireContext(),newsModel,mSessionManager,mediaPlayer)
                }

            })
        recyclerView.adapter = mHealthAdapter
    }


    private fun openDetailedHealthFragment(
        newsModelList: ArrayList<NewsModel>,
        recyclerview_position: Int
    ) {

        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        ft?.replace(
            R.id.app_container,
            ViewPagerFragment.newInstance(newsModelList, 8, recyclerview_position)
        )
        ft!!.addToBackStack(null)
        ft.commit()
    }
}