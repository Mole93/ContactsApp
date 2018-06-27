package com.donato.contactsapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.annotation.NonNull
import com.donato.contactsapp.data.model.ContactModel
import com.donato.contactsapp.data.repository.ContactsRepository
import com.donato.contactsapp.ui.list.viewmodel.ContactListViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


/**
 * @author Donato Antonini <antoninidonato@gmail.com>
 */
class ContactListViewModelTest {

    @Mock
    private lateinit var mockRepository: ContactsRepository

    private lateinit var viewModel: ContactListViewModel
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        viewModel = ContactListViewModel(mockRepository)
        viewModel.compositeDisposable = CompositeDisposable()

        initRxJava()


    }


    @Test
    fun getContactsError() {
        Mockito.`when`(mockRepository.getContacts())
                .thenReturn(Observable.create({
                    it.onError(NullPointerException("NullPointerException"))
                }))

        viewModel.loadContacts()

        assertEquals("NullPointerException",  viewModel.contactsError().value)
        viewModel.dispose()

    }


    @Test
    fun getContactsSuccess() {
        Mockito.`when`(mockRepository.getContacts())
                .thenReturn(Observable.just(listOf(
                        ContactModel("1", "Donato Antonini"),
                        ContactModel("2", "Donato Antonini"),
                        ContactModel("3", "Donato Antonini"),
                        ContactModel("4", "Donato Antonini"))))



        viewModel.loadContacts()

        assertEquals("3",  viewModel.contactsResult().value?.get(2)?.id)
        assertEquals("Donato Antonini",  viewModel.contactsResult().value?.get(2)?.displayName)
        assertNotEquals("Giacomo",  viewModel.contactsResult().value?.get(2)?.displayName)
        assertEquals(1,  viewModel.compositeDisposable.size())
        viewModel.dispose()

    }

   @Test
   fun disposeTest() {

       Mockito.`when`(mockRepository.getContacts())
               .thenReturn(Observable.just(listOf(
                       ContactModel("1", "Donato Antonini"),
                       ContactModel("2", "Donato Antonini"),
                       ContactModel("3", "Donato Antonini"),
                       ContactModel("4", "Donato Antonini"))))


       viewModel.loadContacts()

       assertEquals(1,  viewModel.compositeDisposable.size())
       viewModel.dispose()
       assertEquals(0,  viewModel.compositeDisposable.size())
   }


    private fun initRxJava() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Scheduler.Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
    }

}