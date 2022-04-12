package com.aadya.gist.entertainment.ui

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
import com.aadya.aadyanews.databinding.FragmentEntertainmentBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.aadya.gist.commonviewpager.adapter.ui.ViewPagerFragment
import com.aadya.gist.entertainment.adapter.EntertainmentNewsAdapter
import com.aadya.gist.entertainment.viewmodel.EntertainmentNewsFactory
import com.aadya.gist.entertainment.viewmodel.EntertainmentNewsViewModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.utils.*
import com.squareup.picasso.Picasso


class EntertainmentFragment : Fragment() {
    private lateinit var mBinding : FragmentEntertainmentBinding
    private lateinit var mEntertainmentNewsAdapter: EntertainmentNewsAdapter
    private lateinit var mViewModel: EntertainmentNewsViewModel
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
        setUpRecyclerView(mBinding.entertainmentRecyclerView)
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
        mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.entertainmentnews)
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
            R.layout.fragment_entertainment,
            container,
            false
        )
        arguments?.let {
            navigationmenu = it.getParcelable("navigationmenu")!!

        }

        mViewModel = ViewModelProvider(this, EntertainmentNewsFactory(activity?.application)).get(
            EntertainmentNewsViewModel::class.java
        )
        mViewModel.getEntertainmentNewsList(navigationmenu)
    }

    private fun handleObserver() {
        mViewModel.getEntertainmentNewsListObserver().observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                mBinding.emptyTv.visibility = View.VISIBLE
                mBinding.emptyTv.text =  mCommonUtils.getLocaleContext(requireContext(), mSessionManager)?.resources?.getString(R.string.no_updated_news)
                mBinding.topLinear.visibility = View.GONE
            }
            else {
                mBinding.emptyTv.visibility = View.GONE
                mBinding.topLinear.visibility = View.VISIBLE
                mEntertainmentNewsAdapter.notifyData(it)
                Picasso.get()
                    .load(R.drawable.entertainment1)
                    .placeholder(R.drawable.applogo)

                    .error(R.drawable.applogo)

                    .into( mBinding.imgEntertainmentnews, object : com.squareup.picasso.Callback {
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
            EntertainmentFragment().apply {
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
        mEntertainmentNewsAdapter = EntertainmentNewsAdapter(activity?.applicationContext,
            ArrayList<NewsModel>(),
            object : EntertainmentNewsAdapter.EntertainmentNewsListItemClick {
                override fun onItemClick(newsModelList: ArrayList<NewsModel>, recyclerview_position: Int) {
                    openDetailedStateFragment(newsModelList,recyclerview_position)
                }
            },mBookMarkUnBookmarkInterface,object : playAudioClickListner{
            override fun onAudioClick(

                newsModel: NewsModel

            ) {
                var mediaPlayer : MediaPlayer = MediaPlayer.create(requireContext(),R.raw.sample)
                mAlertBuilderClass.showAudioAppAlert(requireContext(),newsModel,mSessionManager,mediaPlayer)
            }

        })
        recyclerView.adapter = mEntertainmentNewsAdapter
    }


    private fun openDetailedStateFragment(newsModelList: ArrayList<NewsModel>, recyclerview_position: Int) {

        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        //  activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        ft?.replace(
            R.id.app_container,
            ViewPagerFragment.newInstance(newsModelList, 10, recyclerview_position)
        )
        ft!!.addToBackStack(null)
        ft.commit()
    }
}