package com.example.justmovie

import com.example.justmovie.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class APiTesting {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val contentType = "application/json; charset=UTF8".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(converterFactory)
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getUsers()  {
        // Given
        /*val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                [
                    {"id": 1, "name": "John"},
                    {"id": 2, "name": "Jane"}
                ]
            """.trimIndent())
        mockWebServer.enqueue(mockResponse)

        // When
        val users = apiService.getNowPlayingMovies(1)

        // Then
        assertEquals(2, users.size)
        assertEquals("John", users[0].name)*/
    }

}