package com.aadya.gist.breakingnews.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aadya.gist.breakingnews.ui.BreakingNewsVideosFragment
import com.aadya.gist.utils.CommonUtils


class BreakingNewsVideosAdapter(
    fragmentManager: FragmentManager,
    private val videosList: ArrayList<String>
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var videopathList: ArrayList<String> = ArrayList<String>()
    private  var mCommonUtils : CommonUtils = CommonUtils()
    override fun getItem(position: Int): Fragment {
        return BreakingNewsVideosFragment.newInstance(videopathList[position])
    }


    override fun getCount(): Int {
        return videopathList.size
    }



    init {
        this.videopathList.addAll(videosList)
    }


}