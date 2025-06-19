package com.example.justmovie

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.justmovie.ui.movies.ComposeSandbox
import com.example.justmovie.ui.theme.JustMovieTheme
import org.junit.Rule
import org.junit.Test

class SandboxTesting {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun initRender(){
        testRule.setContent {
            JustMovieTheme {
                ComposeSandbox()
            }
        }

        // Test if component are displayed
        testRule.onNodeWithTag("textField").assertIsDisplayed()
        testRule.onNodeWithTag("clearBtn").assertIsDisplayed()
    }

    @Test
    fun inputTest(){
        testRule.setContent {
            JustMovieTheme {
                ComposeSandbox()
            }
        }

        testRule.onNodeWithTag("textField").performTextInput("Hello World")
        testRule.onNodeWithTag("textField").assert(hasText("Hello World"))
        testRule.onNodeWithTag("clearBtn").performClick()
        testRule.onNodeWithTag("textField").assert(hasText(""))
        testRule.onNodeWithTag("displayInput").assert(hasText(""))
    }

}