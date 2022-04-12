package com.aadya.gist.india.ui

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
import com.aadya.aadyanews.databinding.FragmentIndiaBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.aadya.gist.commonviewpager.adapter.ui.ViewPagerFragment
import com.aadya.gist.india.adapter.IndiaNewsAdapter
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.india.viewmodel.IndiaNewsFactory
import com.aadya.gist.india.viewmodel.IndiaNewsViewModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.utils.*
import com.squareup.picasso.Picasso


class IndiaFragment : Fragment() {
    private lateinit var mBinding: FragmentIndiaBinding
    private lateinit var mIndiaAdapter: IndiaNewsAdapter
    private lateinit var mViewModel: IndiaNewsViewModel
    private lateinit var navigationmenu : NavigationMenu
    private lateinit var mIncludedLayoutBinding : MainHeaderBinding
    private var mCommonUtils : CommonUtils = CommonUtils()
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
        setUpRecyclerView(mBinding.indiaRecyclerView)
        mViewModel.getIndiaNewsList(navigationmenu)
        handleObserver()
        handleClickListner()
        return mBinding.root
    }

    private fun intializeMembers(inflater: LayoutInflater, container: ViewGroup?) {
        mAlertBuilderClass = AlertBuilderClass()
        mSessionManager = SessionManager.getInstance(activity?.applicationContext)!!
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_india,
            container,
            false
        )
        mIncludedLayoutBinding = mBinding.mainheaderLayout

        mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.indianews)
        arguments?.let {
            navigationmenu = it.getParcelable("navigationmenu")!!

        }

        mViewModel = ViewModelProvider(this, IndiaNewsFactory(activity?.application)).get(
            IndiaNewsViewModel::class.java
        )

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

    private fun handleObserver() {
        mViewModel.getINdiaNewsListObserver().observe(viewLifecycleOwner, Observer {
            mCommonUtils.LogPrint("TAG","Size=>"+it.size)
            if(it.isEmpty()){
                mBinding.topRelative.visibility = View.GONE
                mBinding.emptyTv.visibility = View.VISIBLE
                mBinding.emptyTv.text = mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.no_updated_news)
                mBinding.indiaRecyclerView.visibility = View.GONE
            }
            else{
                mBinding.topRelative.visibility = View.VISIBLE
                mBinding.emptyTv.visibility = View.GONE
                mBinding.indiaRecyclerView.visibility = View.VISIBLE
                mIndiaAdapter.notifyData(it)

                Picasso.get()
                    .load(R.drawable.india3)
                    .placeholder(R.drawable.applogo)

                    .error(R.drawable.applogo)

                    .into(mBinding.imgIndianews, object : com.squareup.picasso.Callback {
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
                            mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.pleasewait),
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
            IndiaFragment().apply {
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
        mIndiaAdapter = IndiaNewsAdapter(activity?.applicationContext,
            ArrayList<NewsModel>(),
            object : IndiaNewsAdapter.IndiaNewsListItemClick {
                override fun onItemClick(
                    indiaNewsModellist: ArrayList<NewsModel>,
                    recyclerview_position: Int
                ) {
                    openDetailedIndiaFragment(indiaNewsModellist,recyclerview_position)
                }
            },mBookMarkUnBookmarkInterface,object : playAudioClickListner{
                override fun onAudioClick(

                    newsModel: NewsModel

                ) {
                    var mediaPlayer : MediaPlayer = MediaPlayer.create(requireContext(),R.raw.sample)
                    mAlertBuilderClass.showAudioAppAlert(requireContext(),newsModel,mSessionManager,mediaPlayer)
                }

            })
        recyclerView.adapter = mIndiaAdapter
    }


    private fun openDetailedIndiaFragment(
        indiaNewsModellist: ArrayList<NewsModel>,
        recyclerview_position: Int
    ) {

        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        ft?.replace(
            R.id.app_container,
            ViewPagerFragment.newInstance(indiaNewsModellist, 1, recyclerview_position)
        )
        ft!!.addToBackStack(null)
        ft.commit()
    }
}