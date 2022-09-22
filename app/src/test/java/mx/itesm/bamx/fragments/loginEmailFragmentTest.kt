package mx.itesm.bamx.fragments

import com.google.common.truth.Truth.assertThat
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class loginEmailFragmentTest{

    @Test
    fun `login with correct login and password`() {
        //given
        val objectUnderTest = loginEmailFragment()
        val correctLoginEmail = "jojoto@gmail.com"
        val correctLoginPassword = "qwerty12345"
        //when
        val result =  objectUnderTest.login(correctLoginEmail, correctLoginPassword)
        //then
        assertThat(result).isEqualTo(true)


    }
}