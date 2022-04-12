package com.aadya.gist.bookmark.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aadya.gist.bookmark.model.BookMarkRequestModel
import com.aadya.gist.bookmark.model.BookMarkResponseModel
import com.aadya.gist.registration.model.AlertModel

class BookmarkViewModel(bookMarkRepository: BookMarkRepository) : ViewModel() {
    private var bookMarkRepository: BookMarkRepository = bookMarkRepository


    fun bookMarkNews(bookMarkRequestModel: BookMarkRequestModel) {
        bookMarkRepository.BookMarkNews(bookMarkRequestModel)
   }

    fun getBookMarkObserver() : MutableLiveData<List<BookMarkResponseModel>> {
        return  bookMarkRepository.bookMarkNewsState()
    }
    fun getBookMarkProgressState(): LiveData<Int?>? {
        return bookMarkRepository.getProgressState()
    }

    fun getBookMarkAlertViewState(): LiveData<AlertModel?>? {
        return bookMarkRepository.getAlertViewState()
    }

}