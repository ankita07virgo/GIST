package com.aadya.gist.commonviewpager.adapter.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.FragmentViewpagerBinding
import com.aadya.gist.commonviewpager.adapter.adapter.ViewPagerDetailedAdapter
import com.aadya.gist.india.model.NewsModel

class ViewPagerFragment : Fragment() {

    private lateinit var mBinding : FragmentViewpagerBinding
    private lateinit var viewPagerDetailedAdapter: ViewPagerDetailedAdapter
    private  var mNewsList : ArrayList<NewsModel> = ArrayList()
    private var Open_detailedFragment: Int = 0
    private var recyclerview_position : Int =0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_viewpager,
            container,
            false
        )

        setAdapter()

        return mBinding.root
    }




    companion object {

        @JvmStatic
        fun newInstance(
            mNewsList: ArrayList<NewsModel>,
            fragment_value: Int,
            recyclerview_position: Int
        ) =

            ViewPagerFragment().apply {

                arguments = bundleOf(
                    "DetailedFragment" to mNewsList,
                    "Open_detailedFragment" to fragment_value,
                    "recyclerview_position" to recyclerview_position

                )


            }
    }


    private fun setAdapter() {

        viewPagerDetailedAdapter = ViewPagerDetailedAdapter(
            activity!!.supportFragmentManager, mNewsList,Open_detailedFragment
        )


        mBinding.detailedViewpager.adapter = viewPagerDetailedAdapter
        mBinding.detailedViewpager.currentItem = recyclerview_position

    }




    override fun onAttach(context: Context) {
        super.onAttach(context)

        mNewsList = arguments?.getParcelableArrayList<NewsModel>("DetailedFragment") as ArrayList<NewsModel>
        Open_detailedFragment = ((arguments?.getInt("Open_detailedFragment") as? Int)!!)
        recyclerview_position  = (arguments?.getInt("recyclerview_position"))!!
        Log.d("TAG","NewLIst=> $mNewsList  FragmentValue=> $Open_detailedFragment")
    }
}