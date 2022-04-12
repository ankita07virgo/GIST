package com.aadya.gist.utils

import com.aadya.gist.addnews.model.AddNewsRequestModel
import com.aadya.gist.navigation.model.NavigationMenu

class AlertDialogListener {
    interface LanguageDialogButtonListener {
        fun onItemClick(selectedLanguage : String)
    }

    interface SearchButtonListener {
        fun onSpinnerItemClick(navigationMenu: NavigationMenu)
    }

    interface AddNewsButtonListener {
        fun onSubmit(maddNewsRequestModel: AddNewsRequestModel)
    }
}