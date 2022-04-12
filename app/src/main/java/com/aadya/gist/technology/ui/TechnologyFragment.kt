package com.aadya.gist.technology.ui

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
import com.aadya.aadyanews.databinding.FragmentTechnologyBinding
import com.aadya.aadyanews.databinding.MainHeaderBinding
import com.aadya.gist.commonviewpager.adapter.ui.ViewPagerFragment
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.technology.adapter.TechnologyNewsAdapter
import com.aadya.gist.technology.viewmodel.TechnologyNewsFactory
import com.aadya.gist.technology.viewmodel.TechnologyNewsViewModel
import com.aadya.gist.utils.*
import com.squareup.picasso.Picasso


class TechnologyFragment : Fragment() {
    private lateinit var mBinding : FragmentTechnologyBinding
    private lateinit var mTechnologyNewsAdapter: TechnologyNewsAdapter
    private lateinit var mViewModel: TechnologyNewsViewModel
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
        setUpRecyclerView(mBinding.technologyRecyclerView)
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
        mIncludedLayoutBinding.tvMainheader.text =mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.technologynews)
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
            R.layout.fragment_technology,
            container,
            false
        )
        arguments?.let {
            navigationmenu = it.getParcelable("navigationmenu")!!

        }


        mViewModel = ViewModelProvider(this, TechnologyNewsFactory(activity?.application)).get(
            TechnologyNewsViewModel::class.java
        )

        mViewModel.getTechnologyNewsList(navigationmenu)

    }

    private fun handleObserver() {
        mViewModel.getTechnologyNewsListObserver().observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                mBinding.emptyTv.visibility = View.VISIBLE
                mBinding.emptyTv.text = mCommonUtils.getLocaleContext(requireContext(),mSessionManager).resources.getString(R.string.no_updated_news)
                mBinding.topLinear.visibility = View.GONE
            }
            else {
                mBinding.emptyTv.visibility = View.GONE
                mBinding.topLinear.visibility = View.VISIBLE
                mTechnologyNewsAdapter.notifyData(it)
                Picasso.get()
                    .load(R.drawable.technology1)
                    .placeholder(R.drawable.applogo)

                    .error(R.drawable.applogo)

                    .into( mBinding.imgTechnologynews, object : com.squareup.picasso.Callback {
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
            TechnologyFragment().apply {
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
        mTechnologyNewsAdapter = TechnologyNewsAdapter(activity?.applicationContext,
            ArrayList<NewsModel>(),
            object : TechnologyNewsAdapter.TechnologyNewsListItemClick {
                override fun onItemClick(newsModelList: ArrayList<NewsModel>, recyclerview_position: Int) {
                    openDetailedTechnologyFragment(newsModelList,recyclerview_position)
                }
            },mBookMarkUnBookmarkInterface,object : playAudioClickListner{
                override fun onAudioClick(

                    newsModel: NewsModel

                ) {
                    var mediaPlayer : MediaPlayer = MediaPlayer.create(requireContext(),R.raw.sample)
                    mAlertBuilderClass.showAudioAppAlert(requireContext(),newsModel,mSessionManager,mediaPlayer)
                }

            })
        recyclerView.adapter = mTechnologyNewsAdapter
    }


    private fun openDetailedTechnologyFragment(
        newsModelList: ArrayList<NewsModel>,
        recyclerview_position: Int
    ) {

        val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        ft?.replace(
            R.id.app_container,
            ViewPagerFragment.newInstance(newsModelList, 12, recyclerview_position)
        )
        ft!!.addToBackStack(null)
        ft.commit()
    }
}