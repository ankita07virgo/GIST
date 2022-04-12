package com.aadya.gist.navigation.ui

import android.Manifest
import android.animation.Animator
import android.app.DownloadManager
import android.content.*
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aadya.aadyanews.R
import com.aadya.aadyanews.databinding.ActivityMainBinding
import com.aadya.aadyanews.databinding.ActivityNavigationBinding
import com.aadya.aadyanews.databinding.AppContentBinding
import com.aadya.gist.addnews.model.AddNewsRequestModel
import com.aadya.gist.addnews.viewmodel.addnewsFactory
import com.aadya.gist.addnews.viewmodel.addnewsViewModel
import com.aadya.gist.addtag.model.AddTagRequestModel
import com.aadya.gist.addtag.viewmodel.addtagFactory
import com.aadya.gist.addtag.viewmodel.addtagViewModel
import com.aadya.gist.addtag.viewmodel.currentweatherViewModel
import com.aadya.gist.bookmark.model.BookMarkRequestModel
import com.aadya.gist.bookmark.viewmodel.BookmarkFactory
import com.aadya.gist.bookmark.viewmodel.BookmarkViewModel
import com.aadya.gist.bookmarkednewslist.ui.BookmarkedNewsfragment
import com.aadya.gist.breakingnews.ui.BreakingNewsFragment
import com.aadya.gist.breakingnews.ui.LifestyleFragment
import com.aadya.gist.business.ui.BusinessFragment
import com.aadya.gist.covid19.ui.Covid19fragment
import com.aadya.gist.covid19.ui.NewsListItemClickListner
import com.aadya.gist.currentweather.model.CurrentWeatherRequestModel
import com.aadya.gist.currentweather.viewmodel.currentweatherFactory
import com.aadya.gist.entertainment.ui.EntertainmentFragment
import com.aadya.gist.health.ui.HealthFragment
import com.aadya.gist.india.model.NewsModel
import com.aadya.gist.india.ui.IndiaFragment
import com.aadya.gist.local.ui.LocalFragment
import com.aadya.gist.login.ui.LoginActivity
import com.aadya.gist.login.ui.SplashActivity
import com.aadya.gist.navigation.adapter.NavigationMenuAdapter
import com.aadya.gist.navigation.model.NavigationMenu
import com.aadya.gist.navigation.viewmodel.NavigationModelFactory
import com.aadya.gist.navigation.viewmodel.NavigationViewModel
import com.aadya.gist.politics.ui.Politicsfragment
import com.aadya.gist.registration.model.AlertModel
import com.aadya.gist.registration.model.RegistrationRequestModel
import com.aadya.gist.sports.ui.SportsFragment
import com.aadya.gist.technology.ui.TechnologyFragment
import com.aadya.gist.utils.*
import com.aadya.gist.world.ui.WorldNewsFragment
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import eu.dkaratzas.android.inapp.update.Constants.UpdateMode
import eu.dkaratzas.android.inapp.update.InAppUpdateManager
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class NavigationActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener, DrawerInterface,
    BookMarkUnBookmarkInterface, InAppUpdateManager.InAppUpdateHandler,NewsListItemClickListner {
    private lateinit var mBinding: ActivityNavigationBinding
    private lateinit var mIncludedLayoutBinding: AppContentBinding
    private lateinit var mIncludedActivityMainBinding: ActivityMainBinding
    private lateinit var mViewModel: NavigationViewModel
    private lateinit var mCommonUtils: CommonUtils
    private lateinit var mAlertBuilderClass: AlertBuilderClass
    private lateinit var mSessionManager: SessionManager
    private val BREAKING_FRAGMENT = 1
    private val COVID19_FRAGMENT = 2
    private val POLITICS_FRAGMENT = 3
    private val MYNEWS_FRAGMENT = 4
    private var DEFAULT_FRAGMENT = BREAKING_FRAGMENT
    private lateinit var mAddTagViewModel: addtagViewModel
    private lateinit var mAddNewsViewModel: addnewsViewModel
    private lateinit var mBookMarkViewModel: BookmarkViewModel
    var isFABOpen = false
    private lateinit var inAppUpdateManager: InAppUpdateManager
    private val urlsAdded = ArrayList<String>()
    private lateinit var downloadManager : DownloadManager
    private var enq :Long = 0
    private lateinit var mCurrentWeatherViewModel: currentweatherViewModel
    private var currentTemp : Double = 0.0
    var latitude : String = ""
    var longitude : String = ""
    var locationManager: LocationManager? = null
    private val REQUEST_LOCATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeMembers()
        setUpRecyclerView(mBinding.navRecyclerView)
        setupDrawerLayout()
        handleObservers()
        handleClickListner()
        intializeADDTAGMembers()
        handleAddTagObservers()
        intializeBookMarkMembers()
        handleBookMarkObservers()
        intializeADDNEWSMembers()
        intializeCurrentWeather()
        handleCurrentWeatherObservers()
        handleAddNewsObservers()
        val navigationMenu: NavigationMenu = NavigationMenu("6", "BreakingNews")
        launchFragment(BreakingNewsFragment.newInstance(navigationMenu), "LatestNewsFragment")
        inAppUpdateManager = InAppUpdateManager.Builder(this, 11)
            .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
            .mode(UpdateMode.FLEXIBLE)
            .useCustomNotification(true)
            .handler(this)

        inAppUpdateManager.checkForAppUpdate()
     getCurrentLocation()


    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@NavigationActivity, Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@NavigationActivity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {


            locationManager= getSystemService(LOCATION_SERVICE) as LocationManager
            if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                OnGPS()
            } else {
                getLocation()
            }
        }
    }
    private fun OnGPS() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, which ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })

        val alertDialog: android.app.AlertDialog? = builder.create()
        alertDialog?.show()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@NavigationActivity, Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@NavigationActivity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val locationGPS = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                val lat = locationGPS.latitude
                val longi = locationGPS.longitude
                latitude = lat.toString()
                longitude = longi.toString()
                Toast.makeText(
                    this,
                    "Your Location: \nLatitude: $latitude\nLongitude: $longitude",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleAddNewsObservers() {
        mAddNewsViewModel.getAddNewsObserver().observe(this, Observer {
            if (it == null) return@Observer
            /* mViewModel.createNavigationMenu()*/
            mCommonUtils.ToastPrint(
                application.applicationContext,
                mCommonUtils.getLocaleContext(
                    this@NavigationActivity,
                    mSessionManager
                ).resources.getString(
                    R.string.addnews_successful
                )
            )
        })

        mAddNewsViewModel.getAddNewsAlertViewState()?.observe(this,
            object : Observer<AlertModel?> {
                override fun onChanged(alertModel: AlertModel?) {
                    if (alertModel == null) return
                    mCommonUtils.showAlert(
                        alertModel.duration,
                        alertModel.title,
                        alertModel.message,
                        alertModel.drawable,
                        alertModel.color,
                        this@NavigationActivity,
                        mSessionManager
                    )
                }
            })


        mAddNewsViewModel.getAddNewsProgressState()?.observe(this,
            object : Observer<Int?> {
                override fun onChanged(progressState: Int?) {
                    if (progressState == null) return
                    if (progressState === CommonUtils.ProgressDialog.showDialog)
                        this@NavigationActivity.let {
                            mCommonUtils.showProgress(
                                mCommonUtils.getLocaleContext(
                                    this@NavigationActivity,
                                    mSessionManager
                                ).resources.getString(R.string.pleasewait),
                                it,
                                mSessionManager.getAppColor()
                            )
                        }
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })
    }

    private fun intializeADDNEWSMembers() {
        mAddNewsViewModel = ViewModelProvider(
            this,
            addnewsFactory(this@NavigationActivity.application)
        ).get(
            addnewsViewModel::class.java
        )
    }



    private fun handleCurrentWeatherObservers() {
        mCurrentWeatherViewModel.getcurrentweatherObserver().observe(this, Observer {
            if (it == null) return@Observer
            currentTemp = it.main.temp - 273
            mIncludedLayoutBinding.bottomNavigationMenu.menu.getItem(4).title =
                currentTemp.roundToInt().toString() + "\u2103"


        })

        mCurrentWeatherViewModel.getcurrentweatherViewState()?.observe(this,
            object : Observer<AlertModel?> {
                override fun onChanged(alertModel: AlertModel?) {
                    if (alertModel == null) return
                    mCommonUtils.showAlert(
                        alertModel.duration,
                        alertModel.title,
                        alertModel.message,
                        alertModel.drawable,
                        alertModel.color,
                        this@NavigationActivity,
                        mSessionManager
                    )
                }
            })


        mCurrentWeatherViewModel.getcurrentweatherProgressState()?.observe(this,
            object : Observer<Int?> {
                override fun onChanged(progressState: Int?) {
                    if (progressState == null) return
                    if (progressState === CommonUtils.ProgressDialog.showDialog)
                        this@NavigationActivity.let {
                            mCommonUtils.showProgress(
                                mCommonUtils.getLocaleContext(
                                    this@NavigationActivity,
                                    mSessionManager
                                ).resources.getString(R.string.pleasewait),
                                it,
                                mSessionManager.getAppColor()
                            )
                        }
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })
    }

    private fun handleAddTagObservers() {
        mAddTagViewModel.getAddTagObserver().observe(this, Observer {
            if (it == null) return@Observer
            mViewModel.createNavigationMenu()
            mCommonUtils.ToastPrint(
                application.applicationContext,
                mCommonUtils.getLocaleContext(
                    this@NavigationActivity,
                    mSessionManager
                ).resources.getString(
                    R.string.addtag_successfully
                )
            )
        })

        mAddTagViewModel.getAddTagAlertViewState()?.observe(this,
            object : Observer<AlertModel?> {
                override fun onChanged(alertModel: AlertModel?) {
                    if (alertModel == null) return
                    mCommonUtils.showAlert(
                        alertModel.duration,
                        alertModel.title,
                        alertModel.message,
                        alertModel.drawable,
                        alertModel.color,
                        this@NavigationActivity,
                        mSessionManager
                    )
                }
            })


        mAddTagViewModel.getAddTagProgressState()?.observe(this,
            object : Observer<Int?> {
                override fun onChanged(progressState: Int?) {
                    if (progressState == null) return
                    if (progressState === CommonUtils.ProgressDialog.showDialog)
                        this@NavigationActivity.let {
                            mCommonUtils.showProgress(
                                mCommonUtils.getLocaleContext(
                                    this@NavigationActivity,
                                    mSessionManager
                                ).resources.getString(R.string.pleasewait),
                                it,
                                mSessionManager.getAppColor()
                            )
                        }
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })
    }

    private fun handleBookMarkObservers() {
        mBookMarkViewModel.getBookMarkObserver().observe(this, Observer {

        })

        mBookMarkViewModel.getBookMarkAlertViewState()?.observe(this,
            object : Observer<AlertModel?> {
                override fun onChanged(alertModel: AlertModel?) {
                    if (alertModel == null) return
                    mCommonUtils.showAlert(
                        alertModel.duration,
                        alertModel.title,
                        alertModel.message,
                        alertModel.drawable,
                        alertModel.color,
                        this@NavigationActivity,
                        mSessionManager
                    )
                }
            })


        mBookMarkViewModel.getBookMarkProgressState()?.observe(this,
            object : Observer<Int?> {
                override fun onChanged(progressState: Int?) {
                    if (progressState == null) return
                    if (progressState === CommonUtils.ProgressDialog.showDialog)
                        this@NavigationActivity.let {
                            mCommonUtils.showProgress(
                                mCommonUtils.getLocaleContext(
                                    this@NavigationActivity,
                                    mSessionManager
                                ).resources.getString(R.string.pleasewait),
                                it,
                                mSessionManager.getAppColor()
                            )
                        }
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })
    }

    private fun intializeADDTAGMembers() {
        mAddTagViewModel = ViewModelProvider(
            this,
            addtagFactory(this@NavigationActivity.application)
        ).get(
            addtagViewModel::class.java
        )
    }

    private fun intializeBookMarkMembers() {
        mBookMarkViewModel = ViewModelProvider(
            this,
            BookmarkFactory(this@NavigationActivity.application)
        ).get(
            BookmarkViewModel::class.java
        )
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleClickListner() {

        mIncludedActivityMainBinding.fab.setOnClickListener(View.OnClickListener {
            if (!isFABOpen) {
                showFABMenu()
            } else {
                closeFABMenu()
            }
        })

        mIncludedActivityMainBinding.fabBGLayout.setOnClickListener(View.OnClickListener { closeFABMenu() })
        mIncludedActivityMainBinding.fabsearchcategory.setOnClickListener {
            if (isFABOpen)
                closeFABMenu()


            mAlertBuilderClass.showSearchCategoryAlert(this@NavigationActivity, object :
                AlertDialogListener.SearchButtonListener {
                override fun onSpinnerItemClick(navigationMenu: NavigationMenu) {
                    callFragment_According_To_CategoryId(navigationMenu)
                }

            }, mSessionManager, mCommonUtils)
        }
        mIncludedActivityMainBinding.fabaddnews.setOnClickListener {
            if (isFABOpen)
                closeFABMenu()



            var mRegistrationRequestModel: List<RegistrationRequestModel>? =
                SessionManager.getInstance(applicationContext)?.getUserDetailLoginModel()

            if (mRegistrationRequestModel != null && mRegistrationRequestModel[0].user_type.equals("1")) {
                mAlertBuilderClass.showAddNewsAlert(this@NavigationActivity, object :
                    AlertDialogListener.AddNewsButtonListener {
                    override fun onSubmit(maddNewsRequestModel: AddNewsRequestModel) {
                        mAddNewsViewModel.getAddNews(maddNewsRequestModel)
                    }

                }, mSessionManager, mCommonUtils)
            } else {
                val drawable: Int = R.drawable.wrong_icon
                val color: Int = R.color.notiFailColor
                mCommonUtils.showAlert(
                    2000,
                    mCommonUtils.getLocaleContext(
                        this@NavigationActivity,
                        mSessionManager
                    ).resources.getString(
                        R.string.login_error_tag
                    ),
                    mCommonUtils.getLocaleContext(
                        this@NavigationActivity,
                        mSessionManager
                    ).resources.getString(
                        R.string.loginadmin_error
                    ),
                    drawable,
                    color,
                    this@NavigationActivity,
                    mSessionManager
                )
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateToLoginActivity()

                }, 3000)

            }

        }





        mIncludedActivityMainBinding.fabAddTag.setOnClickListener {
            if (isFABOpen)
                closeFABMenu()




            var mRegistrationRequestModel: List<RegistrationRequestModel>? =
                SessionManager.getInstance(applicationContext)?.getUserDetailLoginModel()



            if (mRegistrationRequestModel != null) {
                mAlertBuilderClass.showAddTAGAlert(
                    mSessionManager,
                    this@NavigationActivity,

                    object :
                        AlertDialogListener.LanguageDialogButtonListener {
                        override fun onItemClick(addedtag: String) {
                            val maddTagRequestModel: AddTagRequestModel = AddTagRequestModel()
                            maddTagRequestModel.category_name = addedtag
                            mAddTagViewModel.getAddTag(maddTagRequestModel)
                        }

                    }, mCommonUtils
                )
            } else {
                val drawable: Int = R.drawable.wrong_icon
                val color: Int = R.color.notiFailColor
                mCommonUtils.showAlert(
                    2000,
                    mCommonUtils.getLocaleContext(
                        this@NavigationActivity,
                        mSessionManager
                    ).resources.getString(
                        R.string.login_error_tag
                    ),
                    mCommonUtils.getLocaleContext(
                        this@NavigationActivity,
                        mSessionManager
                    ).resources.getString(
                        R.string.login_error
                    ),
                    drawable,
                    color,
                    this@NavigationActivity,
                    mSessionManager
                )
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateToLoginActivity()

                }, 3000)

            }

        }


        mBinding.tvSelLang.setOnClickListener {
            mAlertBuilderClass.selectLanguageDialog(this@NavigationActivity, object :
                AlertDialogListener.LanguageDialogButtonListener {
                override fun onItemClick(selectedLanguage: String) {

                    setLanguage(selectedLanguage)


                    mSessionManager.setSelectedLanguage(selectedLanguage)
                }

            }, mSessionManager)
        }

        mBinding.tvAppInfo.setOnClickListener {
            if (mBinding.appInfoLayout.visibility == View.VISIBLE) {
                mBinding.appInfoLayout.visibility = View.GONE

            } else {
                mBinding.appInfoLayout.visibility = View.VISIBLE
                mBinding.scrollView.post {
                    mBinding.scrollView.fullScroll(View.FOCUS_DOWN)
                }
            }
        }

        mBinding.tvCustomizeApp.setOnClickListener {
            mAlertBuilderClass.showCustomizedAppAlert(this@NavigationActivity, object :
                AlertDialogListener.LanguageDialogButtonListener {
                override fun onItemClick(selectedLanguage: String) {


                }

            }, mSessionManager)
        }

        var colorsTxt = arrayOf<String>()
        colorsTxt = applicationContext.resources.getStringArray(R.array.colors)
        val colors: ArrayList<String> = ArrayList()
        for (i in colorsTxt.indices) {
            colors.add(colorsTxt[i])
        }
        mCommonUtils.LogPrint("TAG", "Colors=>" + colors)

        mBinding.tvChangeColorApp.setOnClickListener {
            MaterialColorPickerDialog
                .Builder(this@NavigationActivity)                    // Pass Activity Instance
                .setColors(                            // Pass Predefined Hex Color
                    arrayListOf(
                        "#3d5ba5", "#2f64e7", "#626262", "#743434", "#651e1e",
                        "#6f4848", "#a0bd7f", "#ff0000", "#ffbf00", "#996666"
                    )
                )
                .setColorListener { color, colorHex ->
                    setSelectedAppColor(colorHex)
                  //  changeAppIconColor()
                    val intent = intent // from getIntent()
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    finish()
                    val intent1 = Intent(this@NavigationActivity, SplashActivity::class.java)
                    startActivity(intent1)
                }
                .show()

        }
    }

    private fun changeAppIconColor() {
        try {
            val pkg = "com.aadya.aadyanews" //your package name
            val icon: Drawable = this@NavigationActivity.packageManager.getApplicationIcon(pkg)
            icon.setColorFilter(
                ContextCompat.getColor(this@NavigationActivity, R.color.option3),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        } catch (ne: PackageManager.NameNotFoundException) {
        }
    }

    private fun setSelectedAppColor(color: String) {
        when (color) {
            "#3d5ba5" -> mSessionManager.setAppColor(R.color.option1)
            "#2f64e7" -> mSessionManager.setAppColor(R.color.option2)
            "#626262" -> mSessionManager.setAppColor(R.color.option3)
            "#743434" -> mSessionManager.setAppColor(R.color.option4)
            "#651e1e" -> mSessionManager.setAppColor(R.color.option5)
            "#6f4848" -> mSessionManager.setAppColor(R.color.option6)
            "#a0bd7f" -> mSessionManager.setAppColor(R.color.option7)
            "#ff0000" -> mSessionManager.setAppColor(R.color.option8)
            "#ffbf00" -> mSessionManager.setAppColor(R.color.option9)
            "#996666" -> mSessionManager.setAppColor(R.color.option10)
            else -> mSessionManager.setAppColor(R.color.appcolor)

        }
    }

    private fun setLanguage(selectedLanguage: String) {
        when (selectedLanguage) {
            "hindi" -> {
                setLocale("hi")
            }
            "english" -> {
                setLocale("en")
            }
            "bengali" -> {
                setLocale("bn")

            }
            "marathi" -> {
                setLocale("mr")

            }
            "punjabi" -> {
                setLocale("pa")
            }
            "gujarati" -> {
                setLocale("gu")

            }
            else -> {
                setLocale("hi")
            }
        }
    }

    private fun handleObservers() {

        mViewModel.getNavigationViewState()?.observe(this,
            { t ->
                var navigatiomenuList: ArrayList<NavigationMenu>? =
                    t as ArrayList<NavigationMenu>

                if (navigatiomenuList != null) {

                    /*  var navigationmenu_download: NavigationMenu = NavigationMenu(
                        "01",
                        "DownloadedContent"
                    )
                    mSessionManager.setCategoryList(navigatiomenuList)
                    navigatiomenuList.add(navigationmenu_download)*/

                    var navigationmenu: NavigationMenu = NavigationMenu("0", "Logout")
                    mSessionManager.setCategoryList(navigatiomenuList)
                    navigatiomenuList.add(navigationmenu)
                }


                val navigationMenuAdapter = NavigationMenuAdapter(this@NavigationActivity,
                    navigatiomenuList,
                    object : NavigationMenuAdapter.NavigationMenuListener {


                        override fun onItemClick(navigationMenu: NavigationMenu?) {
                            mBinding.drawerLayout.closeDrawer(GravityCompat.START)

                            callFragment_According_To_CategoryId(navigationMenu)


                        }
                    })
                mBinding.navRecyclerView.adapter = navigationMenuAdapter
            })



        mViewModel.getAlertViewState()?.observe(this,
            object : Observer<AlertModel?> {
                override fun onChanged(alertModel: AlertModel?) {
                    if (alertModel == null) return
                    mCommonUtils.showAlert(
                        alertModel.duration,
                        alertModel.title,
                        alertModel.message,
                        alertModel.drawable,
                        alertModel.color,
                        this@NavigationActivity,
                        mSessionManager
                    )
                }
            })


        mViewModel.getProgressState()?.observe(this,
            object : Observer<Int?> {
                override fun onChanged(progressState: Int?) {
                    if (progressState == null) return
                    if (progressState === CommonUtils.ProgressDialog.showDialog)
                        mCommonUtils.showProgress(
                            mCommonUtils.getLocaleContext(
                                this@NavigationActivity,
                                mSessionManager
                            ).resources.getString(
                                R.string.pleasewait
                            ),
                            this@NavigationActivity,
                            mSessionManager.getAppColor()
                        )
                    else if (progressState === CommonUtils.ProgressDialog.dismissDialog)
                        mCommonUtils.dismissProgress()
                }
            })


    }

    private fun callFragment_According_To_CategoryId(navigationMenu: NavigationMenu?) {
        if (navigationMenu?.category_id == "1") {

            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                IndiaFragment.newInstance(navigationMenu),
                "IndiaFragment"
            )
        } else if (navigationMenu?.category_id == "2") {
            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                WorldNewsFragment.newInstance(navigationMenu),
                "WorldFragment"
            )
        } else if (navigationMenu?.category_id == "3") {
            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, true, true)
            mIncludedLayoutBinding.bottomNavigationMenu.selectedItemId = R.id.navigation_politics
            /*  launchFragment(
                  Politicsfragment.newInstance(navigationMenu),
                  "PoliticsFragment"
              )*/
        } else if (navigationMenu?.category_id == "4") {
            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                BusinessFragment.newInstance(navigationMenu),
                "BusinessFragment"
            )
        } else if (navigationMenu?.category_id == "5") {
            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                SportsFragment.newInstance(navigationMenu),
                "SportsFragment"
            )
        } else if (navigationMenu?.category_id == "6") {
            /* launchFragment(BreakingNewsFragment.newInstance(navigationMenu), "LatestNewsFragment")*/
            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, true, true)
            mIncludedLayoutBinding.bottomNavigationMenu.selectedItemId =
                R.id.navigation_breakingnews
        } else if (navigationMenu?.category_id == "7") {
            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                LocalFragment.newInstance(navigationMenu),
                "LocalFragment"
            )
        } else if (navigationMenu?.category_id == "8") {

            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                HealthFragment.newInstance(navigationMenu),
                "HealthFragment"
            )
        } else if (navigationMenu?.category_id == "9") {
            /*  launchFragment(
                  Covid19fragment.newInstance(navigationMenu),
                  "Covid19fragment"
              )*/
            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, true, true)
            mIncludedLayoutBinding.bottomNavigationMenu.selectedItemId = R.id.navigation_Covid19

        } else if (navigationMenu?.category_id == "10") {

            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                EntertainmentFragment.newInstance(navigationMenu),
                "EntertainmentFragment"
            )
        } else if (navigationMenu?.category_id == "11") {
            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                LifestyleFragment.newInstance(navigationMenu),
                "LifeStyleFragment"
            )
        } else if (navigationMenu?.category_id == "12") {
            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                TechnologyFragment.newInstance(navigationMenu),
                "TechnologyFragment"
            )
        } else if (navigationMenu?.category_id == "0") {
            mAlertBuilderClass.showLogOutAlert(this@NavigationActivity, object :
                AlertDialogListener.LanguageDialogButtonListener {
                override fun onItemClick(selectedLanguage: String) {
                    finish()
                }

            }, mSessionManager, mCommonUtils)
        }/* else if (navigationMenu?.category_id == "01") {

            mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, false, true)
            launchFragment(
                BookmarkedNewsfragment.newInstance(1),
                "DownloadContent"
            )
        }*/ else {
            launchFragment(
                WorldNewsFragment.newInstance(navigationMenu),
                "WorldFragment"
            )
        }
    }

    private fun setupDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this,
            mBinding.drawerLayout,
            mIncludedLayoutBinding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.drawerArrowDrawable.color = Color.WHITE
        mBinding.drawerLayout.addDrawerListener(toggle)
        mBinding.drawerLayout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(view: View, v: Float) {}
            override fun onDrawerOpened(view: View) {
                if (mBinding.appInfoLayout.visibility == View.VISIBLE)
                    mBinding.appInfoLayout.visibility = View.GONE
            }

            override fun onDrawerClosed(view: View) {
                mBinding.navRecyclerView.visibility = View.VISIBLE
            }

            override fun onDrawerStateChanged(i: Int) {}
        })
        toggle.syncState()


    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun initializeMembers() {
        mSessionManager = SessionManager.getInstance(application)!!
        mCommonUtils = CommonUtils()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation)

        mIncludedLayoutBinding = mBinding.appContentLayout
        mIncludedActivityMainBinding = mIncludedLayoutBinding.includedActivityMain
        setColorOfApp(mSessionManager.getAppColor())

        mViewModel = ViewModelProvider(this, NavigationModelFactory(application)).get(
            NavigationViewModel::class.java
        )
        mViewModel.createNavigationMenu()



        mAlertBuilderClass = AlertBuilderClass()

        mIncludedLayoutBinding.bottomNavigationMenu.setOnNavigationItemSelectedListener(this)
        mIncludedLayoutBinding.bottomNavigationMenu.menu.getItem(0).isChecked = true
        mIncludedLayoutBinding.bottomNavigationMenu.menu.getItem(0).title =
            mCommonUtils.getLocaleContext(this@NavigationActivity, mSessionManager).resources.getString(
                R.string.breakingnews
            )
        mIncludedLayoutBinding.bottomNavigationMenu.menu.getItem(1).title =
            mCommonUtils.getLocaleContext(this@NavigationActivity, mSessionManager).resources.getString(
                R.string.covid19
            )
        mIncludedLayoutBinding.bottomNavigationMenu.menu.getItem(2).title =
            mCommonUtils.getLocaleContext(this@NavigationActivity, mSessionManager).resources.getString(
                R.string.politics
            )
        mIncludedLayoutBinding.bottomNavigationMenu.menu.getItem(3).title =
            mCommonUtils.getLocaleContext(this@NavigationActivity, mSessionManager).resources.getString(
                R.string.mynews
            )
        mIncludedActivityMainBinding.tvSearchcategory.text =  mCommonUtils.getLocaleContext(
            this@NavigationActivity,
            mSessionManager
        ).resources.getString(R.string.search_Category)
        mIncludedActivityMainBinding.tvAddtag.text =  mCommonUtils.getLocaleContext(
            this@NavigationActivity,
            mSessionManager
        ).resources.getString(R.string.add_tag)
        mIncludedActivityMainBinding.tvAddnews.text =  mCommonUtils.getLocaleContext(
            this@NavigationActivity,
            mSessionManager
        ).resources.getString(R.string.add_news)
        mBinding.tvSelLang.text = mCommonUtils.getLocaleContext(
            this@NavigationActivity,
            mSessionManager
        ).resources.getString(R.string.select_lang)
        mBinding.tvChangeColorApp.text = mCommonUtils.getLocaleContext(
            this@NavigationActivity,
            mSessionManager
        ).resources.getString(R.string.cust_color_app)
        mBinding.tvAppInfo.text = mCommonUtils.getLocaleContext(
            this@NavigationActivity,
            mSessionManager
        ).resources.getString(R.string.app_info)
        mBinding.tvContactus.text = mCommonUtils.getLocaleContext(
            this@NavigationActivity,
            mSessionManager
        ).resources.getString(R.string.contact_us)
        mBinding.tvVersion.text = mCommonUtils.getLocaleContext(
            this@NavigationActivity,
            mSessionManager
        ).resources.getString(R.string.version)
        mBinding.tvHeader.text = mCommonUtils.getLocaleContext(
            this@NavigationActivity,
            mSessionManager
        ).resources.getString(R.string.app_name)
    }

    private fun intializeCurrentWeather() {
        mCurrentWeatherViewModel = ViewModelProvider(
            this,
            currentweatherFactory(this@NavigationActivity.application)
        ).get(
            currentweatherViewModel::class.java
        )

        var currentWeatherRequestModel : CurrentWeatherRequestModel = CurrentWeatherRequestModel()
        currentWeatherRequestModel.lat = 28.535517 //26.238947
        currentWeatherRequestModel.lon = 77.391029 //73.024307
        currentWeatherRequestModel.appid = "9afcfc14890078fb1c08830d28b57374"

        mCurrentWeatherViewModel.getcurrentweather(currentWeatherRequestModel)
    }

    private fun setColorOfApp(appColor: Int?) {
        when (appColor) {
            R.color.option1 -> {

                setBottomNavigation(R.color.option1)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option1
                    )
            }
            R.color.option2 -> {
                setBottomNavigation(R.color.option2)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option2
                    )
            }

            R.color.option3 -> {
                setBottomNavigation(R.color.option3)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option3
                    )
            }
            R.color.option4 -> {
                setBottomNavigation(R.color.option4)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option4
                    )
            }
            R.color.option5 -> {
                setBottomNavigation(R.color.option5)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option5
                    )
            }

            R.color.option6 -> {
                setBottomNavigation(R.color.option6)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option6
                    )
            }

            R.color.option7 -> {

                setBottomNavigation(R.color.option7)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option7
                    )
            }

            R.color.option8 -> {
                setBottomNavigation(R.color.option8)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option8
                    )
            }

            R.color.option9 -> {
                setBottomNavigation(R.color.option9)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option9
                    )
            }

            R.color.option10 -> {

                setBottomNavigation(R.color.option10)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector_with_option10
                    )
            }

            else -> {
                setBottomNavigation(R.color.appcolor)
                mIncludedLayoutBinding.bottomNavigationMenu.itemBackground =
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bottom_navigation_backgroundselector
                    )
            }
        }

    }

    private fun setBottomNavigation(color: Int) {


        mIncludedActivityMainBinding.fabAddTag.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(applicationContext, color))
        mIncludedActivityMainBinding.fabsearchcategory.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(applicationContext, color))
        mIncludedActivityMainBinding.fabaddnews.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(applicationContext, color))
        mIncludedActivityMainBinding.fab.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(applicationContext, color))
        mIncludedActivityMainBinding.tvAddnews.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )
        mIncludedActivityMainBinding.tvAddtag.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )
        mIncludedActivityMainBinding.tvSearchcategory.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )

        mBinding.navView.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )
        mBinding.tvSelLang.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )
        mBinding.tvCustomizeApp.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )
        mBinding.tvChangeColorApp.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )
        mBinding.tvAppInfo.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )
        mBinding.tvContactus.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )
        mBinding.tvVersion.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                color
            )
        )

        val bottomMenuIconTintList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ), intArrayOf(
                ContextCompat.getColor(
                    applicationContext,
                    color
                ),
                ContextCompat.getColor(
                    applicationContext,
                    R.color.white
                )
            )
        )


        mIncludedLayoutBinding.bottomNavigationMenu.itemIconTintList = bottomMenuIconTintList
        mIncludedLayoutBinding.bottomNavigationMenu.itemTextColor = bottomMenuIconTintList
    }

    override fun onBackPressed() {
        if (isFABOpen) {
            closeFABMenu()
        }
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val mfragment: Fragment? = supportFragmentManager.findFragmentById(R.id.app_container)
            if (mfragment is BreakingNewsFragment || mfragment is Covid19fragment || mfragment is Politicsfragment || mfragment is BookmarkedNewsfragment) {
                finish()
            } else super.onBackPressed()

        }

    }

    private fun launchFragment(fragment: Fragment, tag: String) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.app_container, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mIncludedLayoutBinding.bottomNavigationMenu.menu.setGroupCheckable(0, true, true)
        when (item.itemId) {
            R.id.navigation_breakingnews -> {
                DEFAULT_FRAGMENT = BREAKING_FRAGMENT
                val navigationMenu: NavigationMenu = NavigationMenu("6", "BreakingNews")
                launchFragment(
                    BreakingNewsFragment.newInstance(navigationMenu),
                    "BreakingNewsFragment"
                )
                return true
            }
            R.id.navigation_Covid19 -> {
                DEFAULT_FRAGMENT = COVID19_FRAGMENT
                val navigationMenu: NavigationMenu = NavigationMenu("9", "Covid19")
                launchFragment(Covid19fragment.newInstance(navigationMenu), "Covid19fragment")
                return true
            }
            R.id.navigation_politics -> {
                DEFAULT_FRAGMENT = POLITICS_FRAGMENT
                val navigationMenu: NavigationMenu = NavigationMenu("3", "Politics")

                launchFragment(Politicsfragment.newInstance(navigationMenu), "Politicsfragment")
                return true
            }
            R.id.navigation_mynews -> {
                DEFAULT_FRAGMENT = MYNEWS_FRAGMENT
                launchFragment(BookmarkedNewsfragment.newInstance(0), "BookMarkedfragment")
                return true
            }
        }

        return false
    }

    override fun setOnDrwawerClickResult() {
        mBinding.drawerLayout.openDrawer(GravityCompat.START)
    }


    override fun setOnBookMarkClickResult(newsid: String?, bookmarkStatus: String?) {
        val bookMarkRequestModel: BookMarkRequestModel = BookMarkRequestModel()
        bookMarkRequestModel.is_bookmark = bookmarkStatus
        bookMarkRequestModel.news_id = newsid
        mBookMarkViewModel.bookMarkNews(bookMarkRequestModel)
    }

    private fun showFABMenu() {
        isFABOpen = true
        mIncludedActivityMainBinding.fabLayout1.visibility = View.VISIBLE
        mIncludedActivityMainBinding.fabLayout2.visibility = View.VISIBLE
        mIncludedActivityMainBinding.fabLayout3.visibility = View.VISIBLE
        mIncludedActivityMainBinding.fabBGLayout.visibility = View.VISIBLE
        mIncludedActivityMainBinding.fab.animate().rotationBy(180F)
        mIncludedActivityMainBinding.fabLayout1.animate()
            .translationY(-resources.getDimension(R.dimen.standard_55))
        mIncludedActivityMainBinding.fabLayout2.animate()
            .translationY(-resources.getDimension(R.dimen.standard_100))
        mIncludedActivityMainBinding.fabLayout3.animate()
            .translationY(-resources.getDimension(R.dimen.standard_145))
    }

    private fun closeFABMenu() {
        isFABOpen = false
        mIncludedActivityMainBinding.fabBGLayout.visibility = View.GONE
        mIncludedActivityMainBinding.fab.animate().rotation(0f)
        mIncludedActivityMainBinding.fabLayout1.animate().translationY(0f)
        mIncludedActivityMainBinding.fabLayout2.animate().translationY(0f)
        mIncludedActivityMainBinding.fabLayout2.animate().translationY(0f).setListener(object :
            Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                if (!isFABOpen) {
                    mIncludedActivityMainBinding.fabLayout1.visibility = View.GONE
                    mIncludedActivityMainBinding.fabLayout2.visibility = View.GONE
                }
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })

        mIncludedActivityMainBinding.fabLayout3.animate().translationY(0f)
        mIncludedActivityMainBinding.fabLayout3.animate().translationY(0f).setListener(object :
            Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                if (!isFABOpen) {
                    mIncludedActivityMainBinding.fabLayout1.visibility = View.GONE
                    mIncludedActivityMainBinding.fabLayout2.visibility = View.GONE
                    mIncludedActivityMainBinding.fabLayout3.visibility = View.GONE
                }
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

    override fun onInAppUpdateError(code: Int, error: Throwable?) {
        mCommonUtils.ToastPrint(application.applicationContext, "Error$error")
    }

    override fun onInAppUpdateStatus(status: InAppUpdateStatus?) {
        if (status!!.isDownloaded) {
            val rootView = window.decorView.findViewById<View>(android.R.id.content)
            val snackbar: Snackbar = Snackbar.make(
                rootView,
                "An update has just been downloaded.",
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setAction("RESTART") { view ->

                // Triggers the completion of the update of the app for the flexible flow.
                inAppUpdateManager.completeUpdate()
            }
            snackbar.show()
        }
    }

    fun setLocale(localeName: String) {
        var myLocale = Locale(localeName)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, NavigationActivity::class.java)
        refresh.putExtra(mSessionManager.getSelectedLanguage(), localeName)
        this@NavigationActivity.finish()
        startActivity(refresh)
    }

    override fun onItemClick(newsModel: NewsModel) {

        TedPermission.with(this@NavigationActivity)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    downloadFromUrl(newsModel.video_url)
                }

                override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>) {
                    Toast.makeText(
                        this@NavigationActivity,
                        "Unable to procees without required permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
            .setDeniedMessage("Unable to run app without these permission\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check()

    }
    private fun downloadFromUrl(url: String?) {
        try {
            val url = "https:\\/\\/youtu.be\\/JsrIgiafx_A"
            urlsAdded.add(url!!)
            var path : String =  Environment.DIRECTORY_DOWNLOADS
            var uri : Uri = Uri.parse(url)
            var subpath : String = "GISTNEWS_"+System.currentTimeMillis().toString()
            mCommonUtils.LogPrint("TAG", "PAth=>$path" + "SubPath=>$subpath")
            this@NavigationActivity.registerReceiver(
                attachmentDownloadCompleteReceive, IntentFilter(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE
                )
            )
            val request = DownloadManager.Request(Uri.parse(url))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setMimeType(getMimeType(uri.toString()))
            request.setTitle("Download")
            request.setDescription("Downloading Your File")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                path
            )
             downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
             enq =downloadManager.enqueue(request)

        } catch (exception: Exception) {
            mCommonUtils.ToastPrint(this@NavigationActivity, "" + exception.printStackTrace())
            if (urlsAdded.isNotEmpty()) {
                urlsAdded.removeAt(urlsAdded.size - 1)
            }
        }
    }
    var attachmentDownloadCompleteReceive: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            mCommonUtils.ToastPrint(this@NavigationActivity, "" + intent.toString())
            val action = intent.action
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)
                val query = DownloadManager.Query()
                query.setFilterById(this@NavigationActivity.enq)
                downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                val c: Cursor = downloadManager.query(query)
                if (c.moveToFirst()) {
                    val columnIndex: Int = c.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                        val uriString: String =
                            c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                        mCommonUtils.LogPrint("TAG", "" + uriString)
                    }
                }
            }
        }
    }

    /**
     * Used to get MimeType from url.
     *
     * @param url Url.
     * @return Mime Type for the given url.
     */
    private fun getMimeType(url: String): String? {
        var type: String? = null
        val extension: String = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            val mime: MimeTypeMap = MimeTypeMap.getSingleton()
            type = mime.getMimeTypeFromExtension(extension)
        }
        return type
    }







}


