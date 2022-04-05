package com.luc.data

import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.luc.common.NetworkStatus
import com.luc.common.model.UserProfile
import com.luc.data.remote.firebase.auth.AuthenticationDataSource
import com.luc.data.remote.firebase.auth.AuthenticationDataSourceImpl
import com.luc.data.remote.firebase.firestore.FirestoreData
import com.luc.data.remote.firebase.firestore.FirestoreDataImpl
import io.mockk.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {


    lateinit var firebaseAuth: FirebaseAuth

   /* private val firebaseFirestore: FirebaseFirestore = mockk(relaxed = true)

    private val firestoreData: FirestoreData = FirestoreDataImpl(firebaseFirestore)

    private val authenticationDataSource: AuthenticationDataSource =
        AuthenticationDataSourceImpl(firebaseAuth, firestoreData)
*/

    @Before
    fun setup() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.useEmulator("10.0.2.2", 9099)
    }


    @Test
    fun addition_isCorrect() {
        runBlocking {
            val data = firebaseAuth.signInAnonymously().await()
            assertThat(data).isEqualTo("somes")
        }

        /*    val user = spyk(authenticationDataSource)
            coEvery { user.signInAnonymous("jose") } returns NetworkStatus.Success(UserProfile("jose"))
            coVerify(exactly = 1) { user.someFun() }*/
    }


}