package com.example.eh_ho

import com.example.eh_ho.data.SignUpModel
import org.junit.Assert.assertEquals
import org.junit.Test

class SignUpModelTest {

    @Test
    fun toJson_isCorrect() {
        val model = SignUpModel("test", "test@test.com", "12345")
        val json = model.toJson()

        assertEquals("test", json.get("name"))
        assertEquals("test@test.com", json.get("email"))
        assertEquals("test", json.get("username"))
        assertEquals("12345", json.get("password"))
        assertEquals(true, json.get("active"))
        assertEquals(true, json.get("approved"))
    }
}