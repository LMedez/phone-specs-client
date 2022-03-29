package com.luc.data

import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.luc.data.remote.firebase.auth.AuthenticationDataSource
import com.luc.data.remote.firebase.auth.AuthenticationDataSourceImpl
import com.luc.data.remote.firebase.firestore.FirestoreData
import com.luc.data.remote.firebase.firestore.FirestoreDataImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    @Mock
    lateinit var firebaseFirestore: FirebaseFirestore

    lateinit var authenticationDataSource: AuthenticationDataSource

    lateinit var firestoreData: FirestoreData


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        firebaseAuth.useEmulator("10.0.2.2", 9099)
        firestoreData = FirestoreDataImpl(firebaseFirestore)
        authenticationDataSource = AuthenticationDataSourceImpl(firebaseAuth, firestoreData)
    }


    @Test
    fun addition_isCorrect() {
        val firebase = firebaseAuth.signInAnonymously()

        firebase.addOnCompleteListener {

            assertThat(it.result).isNotNull()
        }

    }


}