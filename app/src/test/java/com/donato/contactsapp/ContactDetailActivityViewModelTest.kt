package com.donato.contactsapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.annotation.NonNull
import com.donato.contactsapp.data.model.ContactModel
import com.donato.contactsapp.data.repository.ContactsRepository
import com.donato.contactsapp.ui.detail.viewmodel.ContactDetailActivityViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotEquals
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
class ContactDetailActivityViewModelTest {

    @Mock
    private lateinit var mockRepository: ContactsRepository

    private lateinit var viewModel: ContactDetailActivityViewModel
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        viewModel = ContactDetailActivityViewModel(mockRepository)
        viewModel.compositeDisposable = CompositeDisposable()

        initRxJava()

    }

    @Test
    fun getContactError() {
        Mockito.`when`(mockRepository.getContact("5"))
                .thenReturn(Observable.create({
                    it.onError(NullPointerException("NullPointerException"))
                }))

        viewModel.loadContact("5")

        assertEquals("NullPointerException", viewModel.contactError().value)
        viewModel.dispose()

    }

    @Test
    fun getContactSuccess() {
        Mockito.`when`(mockRepository.getContact("5"))
                .thenReturn(Observable.just(
                        ContactModel("5", "Donato Antonini")))



        viewModel.loadContact("5")

        assertEquals("5", viewModel.contactResult().value?.id)
        assertEquals("Donato Antonini", viewModel.contactResult().value?.displayName)
        assertNotEquals("Giacomo", viewModel.contactResult().value?.displayName)
        viewModel.dispose()

    }

    @Test
    fun disposeTest() {

        Mockito.`when`(mockRepository.getContact("5"))
                .thenReturn(Observable.just(
                        ContactModel("5", "Donato Antonini")))

        viewModel.loadContact("5")

        assertEquals(1, viewModel.compositeDisposable.size())
        viewModel.dispose()
        assertEquals(0, viewModel.compositeDisposable.size())
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