package com.aadya.gist.business.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aadya.aadyanews.R
import com.aadya.gist.business.model.BusinessNewsModel
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.india.model.RequestModel
import com.aadya.gist.india.model.ResponseModel
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.retrofit.APIResponseListener
import com.aadya.gist.retrofit.RetrofitService
import com.aadya.gist.utils.CommonUtils
import com.aadya.gist.utils.Connection
import retrofit2.Response

class BusinessNewsRepository(application: Application) {
    private var application : Application

    init {
        this.application = application
    }
    private val businessNewsLiveData = MutableLiveData<List<NewsModel>>()
    private val alertLiveData: MutableLiveData<AlertModel> = MutableLiveData<AlertModel>()
    private val progressLiveData = MutableLiveData<Int>()

    fun getProgressState(): MutableLiveData<Int>? {
        return progressLiveData
    }

    fun getAlertViewState(): MutableLiveData<AlertModel>? {
        return alertLiveData
    }

    fun getBusinessNewsListState() : MutableLiveData<List<NewsModel>> {
        return  businessNewsLiveData
    }

    fun getBusinessNewsList(navigationmenu: NavigationMenu) {
       /* var  mBusinessNewsList = ArrayList<BusinessNewsModel>()
        mBusinessNewsList.add(
            BusinessNewsModel(
                1,
                "Struggling to repay loans after moratorium period? Here is what you can do.",
               "Oct 30,2020","11:45 am",
                R.drawable.business1,"The coronavirus pandemic has resulted in a deep economic depression in the country as millions of people have lost their jobs or faced salary cuts. A major portion of such people had availed the six-month loan moratorium that was announced by the Reserve Bank of India (RBI) to help people tackle the Covid-induced economic slowdown.\n" +
                        "\n" +
                        "The loan moratorium period is now over and many have started repaying their dues to the bank after the period of temporary relief. However, an equally large number of people are."
            )
        )
        mBusinessNewsList.add(
            BusinessNewsModel(
                2,
                "Is an 11th hour Brexit deal still possible?",
                "Oct 30,2020","12:45 am",
                R.drawable.business2,"Since the inception of October, GBP/USD has increased by 1 per cent and GBP/INR has depreciated by 1.7 per cent despite the escalation of coronavirus cases in UK and Brexit related tensions between EU and UK. Meanwhile, the safe-haven dollar index declined by 0.83 per cent during this time period aiding the rally in Pound."
            )
        )
        mBusinessNewsList.add(
            BusinessNewsModel(
                3,
                "Tata-Mistry feud takes bitter turn over separation claims.",
                "Oct 30,2020","10:45 am",
                R.drawable.business3,"The legal tussle between Tata Sons and Shapoorji Pallonji (SP) Group seems to have taken another ugly turn as the latter has approached Supreme Court (SC) and claimed Rs 1.75 lakh crore from Tata Sons for its 18.37 per cent stake in it.\n" +
                        "\n" +
                        "SP Group, controlled by Pallonji Mistry and his family, has filed a fresh petition in Supreme Court as part of its plan to separate from the Tata Group.\n" +
                        "\n" +
                        "In its petition, the SP Group said, “Tata Sons is effectively a two-group company, with the Tata Group comprising Tata Trusts, Tata family members and Tata companies holding about 81.6 per cent of the equity share capital, and the Mistry family owning the balance 18.37 per cent.”\n" +
                        "\n" +
                        "Also Read | Tata vs Mistry: A Rs 1.48 lakh crore question\n" +
                        "\n" +
                        "The SP Group also said that the value of Tata Sons, which is the holding company of Tata Group, arises from its stake in listed equities, non-listed equities, the brand, cash balances and immovable assets.\n" +
                        "\n" +
                        "\"The value of 18.37 per cent stake of the SP Group in Tata Sons is more than Rs 1,75,000 crore,\" they added.\n" +
                        "\n" +
                        "The SP Group also highlighted that the disputes overvaluation can be eliminated by doing a pro-rata split of listed assets and pro-rate share of the brand. It also said that a third-party valuation can be done of the unlisted assets adjusted for net debt.\n" +
                        "\n" +
                        "It also told in its petition to SC that it can be given pro-rate shares in listed entities of Tata Group where Tata Sons currently own stake as a non-cash settlement.\n" +
                        "\n" +
                        "ADVERTISEMENT\n" +
                        "\n" +
                        "For instance, 72 per cent of Tata Consultancy Service (TCS) is owned by Tata Sons. Therefore, SP Group’s ownership in Tata Sons translates to a little over 13 per cent shareholding in TCS, valued at Rs 1.35 lakh crore at present market capitalisation.\n" +
                        "\n" +
                        "SP Group said that this process will help Tata Sons continue to have control over the underlying asset in TCS with over 51 per cent stake.\n" +
                        "\n" +
                        "The Mistry family also claimed that the pro-rata separation of assets and liabilities would be “fair and equitable” solution to all stakeholders.\n" +
                        "\n" +
                        "Tata Sons unhappy\n" +
                        "Tata Sons, however, is not happy with the separation demands sought by SP Group. Quoting top officials of Tata Sons, an Economic Times new report said the salt-to-software conglomerate is not at all inclined to make any concessions, including buying out SP Group’s stake.\n" +
                        "\n" +
                        "The Tata Group is likely to argue in court that the possible exit of SP Group from Tata Sons was not before the court, as per the report.\n" +
                        "\n" +
                        "It is worth mentioning that Tata Sons also disagrees with the Rs 1.75 lakh crore valuation put forward by SP Group. Tata Sons estimates the value of SP Group’s 18.37 per cent holding at approximately Rs 60,000 crore.\n" +
                        "\n" +
                        "“The SP Group can ask for whatever number they dream of but if they expect the court to even consider such unrealistic valuations, they need to do their homework again. The Tata Group does not even take such valuations seriously,” an official close to the development told The Economic Times.\n" +
                        "\n" +
                        "The official went on to say that the SP Group is “desperate for funds and, therefore, chalking out a separation plan”.\n" +
                        "\n" +
                        "Meanwhile, the Tata Group also disagreed with SP Group’s claim that Tata Sons is a “two-group company”. The fresh developments indicate that feud between Tata Sons and Mistry family is far from over.\n" +
                        "\n" +
                        "Also Read | Vindication, says Cyrus Mistry after winning war against Tata Group, returning as chairman."

            ))
        businessNewsLiveData.value = mBusinessNewsList*/

        if (Connection.getInstance().isNetworkAvailable(application)) {
            progressLiveData.value = CommonUtils.ProgressDialog.showDialog
            val indiarequestModel = RequestModel()
            indiarequestModel.category_id = navigationmenu.category_id.toString()
            RetrofitService().getNewsList(indiarequestModel,
                object : APIResponseListener {
                    override fun onSuccess(response: Response<Any>) {

                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog

                        val model: ResponseModel? = response.body() as ResponseModel?
                        try {
                            if (model?.statusCode == 200) {
                                if (response.body() != null) {


                                    businessNewsLiveData.value = model.newsList

                                }

                            } else if (model?.statusCode == 400) {
                                setAlert(
                                    application.getString(R.string.Navigation_error),
                                    application.getString(R.string.sorry_empty_ist),
                                    false
                                )
                            }


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }


                    }

                    override fun onFailure() {
                        progressLiveData.value = CommonUtils.ProgressDialog.dismissDialog
                    }
                })
        } else setAlert(
            application.getString(R.string.no_internet_connection),
            application.getString(R.string.not_connected_to_internet),
            false
        )
    }
    private fun setAlert(title: String, message: String, isSuccess: Boolean) {
        val drawable: Int = if (isSuccess) R.drawable.correct_icon else R.drawable.wrong_icon
        val color: Int = if (isSuccess) R.color.notiSuccessColor else R.color.notiFailColor
        alertLiveData.value = AlertModel(2000, title, message, drawable, color)
    }

}
