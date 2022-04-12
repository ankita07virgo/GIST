package com.aadya.gist.commonviewpager.adapter.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
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
import com.aadya.gist.sports.ui.DetailedSportsFragment
import com.aadya.gist.technology.ui.DetailedTechnologyFragment
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.world.ui.DetailedWorldNewsFragment


class ViewPagerDetailedAdapter(
    fragmentManager: FragmentManager,
    mNewsList: ArrayList<NewsModel>,
    open_detailedfragment: Int,


) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var mNewsList: ArrayList<NewsModel> = ArrayList<NewsModel>()
    private  var mCommonUtils : CommonUtils = CommonUtils()
    private var open_detailedfragment : Int = 0
    override fun getItem(position: Int): Fragment {
        return when (open_detailedfragment) {
            1 ->return DetailedIndiaFragment.newInstance(mNewsList[position])
            2 ->return DetailedWorldNewsFragment.newInstance(mNewsList[position])
            3 ->return DetailedPoliticsFragment.newInstance(mNewsList[position])
            4 ->return DetailedBusinessFragment.newInstance(mNewsList[position])
            5 ->return DetailedSportsFragment.newInstance(mNewsList[position])
            6 ->return DetailedNewsFragment.newInstance(mNewsList[position])
            7 ->return DetailedLocalFragment.newInstance(mNewsList[position])
            8 ->return DetailedHealthFragment.newInstance(mNewsList[position])
            9 -> return DetailedCovid19Fragment.newInstance(mNewsList[position])
            10 ->return DetailedEntertainmentFragment.newInstance(mNewsList[position])
            11 ->return DetailedLifestyleNewsFragment.newInstance(mNewsList[position])
            12 ->return DetailedTechnologyFragment.newInstance(mNewsList[position])
            else -> return DetailedNewsFragment.newInstance(mNewsList[position])
        }

    }
    fun notifyData(covidNewsList: ArrayList<NewsModel>) {
        if (this.mNewsList != null) {
            this.mNewsList.clear()
            this.mNewsList.addAll(covidNewsList)
            notifyDataSetChanged()
        }
    }

    override fun getCount(): Int {
        return mNewsList.size
    }



    init {
        this.mNewsList.addAll(mNewsList)
        this.open_detailedfragment = open_detailedfragment
    }

   
}