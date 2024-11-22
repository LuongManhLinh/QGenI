package com.example.qgeni.ui.screens.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qgeni.data.model.MockListeningPracticeItem
import com.example.qgeni.data.model.MockReadingPracticeItem
import com.example.qgeni.data.preferences.ThemeMode
import com.example.qgeni.ui.screens.HomeScreen
import com.example.qgeni.ui.screens.login.ForgotPasswordScreen
import com.example.qgeni.ui.screens.login.SignInScreen
import com.example.qgeni.ui.screens.login.SignUpScreen
import com.example.qgeni.ui.screens.login.VerificationScreen
import com.example.qgeni.ui.screens.practices.ListeningPracticeListScreen
import com.example.qgeni.ui.screens.practices.ListeningPracticeScreen
import com.example.qgeni.ui.screens.practices.ReadingPracticeListScreen
import com.example.qgeni.ui.screens.practices.ReadingPracticeScreen
import com.example.qgeni.ui.screens.practices.SelectionScreen
import com.example.qgeni.ui.screens.profile.ChangeInformationScreen
import com.example.qgeni.ui.screens.profile.ProfileScreen
import com.example.qgeni.ui.screens.uploads.ListeningPracticeGeneratorScreen
import com.example.qgeni.ui.screens.uploads.ReadingPracticeGeneratorScreen
import com.example.qgeni.ui.screens.welcome.WelcomeScreen

sealed class Screen(val route: String) {
    data object ForgotPassword : Screen("forgot_password")
    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")
    data object Verification : Screen("verification")
    data object ListeningPractice : Screen("listening_practice")
    data object ListeningPracticeList: Screen("listening_practice_list")
    data object ListeningPracticeGenerator : Screen("listening_practice_generator")
    data object ReadingPractice : Screen("reading_practice")
    data object ReadingPracticeList: Screen("reading_practice_list")
    data object ReadingPracticeGenerator : Screen("reading_practice_generator")
    data object Selection : Screen("selection")
    data object Welcome : Screen("welcome")
    data object Home : Screen("home")
    data object Profile: Screen("profile")
    data object ChangeInfo: Screen("change_information")
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QGNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    currentTheme: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier
    ) {
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackClick = { navController.navigateUp() },
                onNextButtonClick = {}
            )
        }
        composable(
            Screen.SignIn.route,
//            enterTransition = {
//                slideIntoContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Left,
//                    tween(
//                        durationMillis = 500,
//                        delayMillis = 100
//                    )
//                )
//            }
        ) {
            SignInScreen(
                onBackClick = { navController.navigateUp() },
                onNextButtonClick = { navController.navigate(Screen.Home.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) },
                onSignUpClick = { navController.navigate(Screen.SignUp.route) }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onBackClick = { navController.navigateUp() },
                onNextButtonClick = { navController.navigate(Screen.SignIn.route) },
                onSignInClick = { navController.navigate(Screen.SignIn.route) }
            )
        }
        composable(Screen.Verification.route) {
            VerificationScreen(
                otpValue = "",
                onBackClick = { navController.navigateUp() },
                onNextButtonClick = { navController.navigate(Screen.SignIn.route) }
            )
        }
        composable(Screen.ListeningPractice.route) {
            ListeningPracticeScreen(
                listeningPracticeItem = MockListeningPracticeItem.listeningPracticeItem,
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Screen.ReadingPractice.route) {
            ReadingPracticeScreen(
                readingPracticeItem = MockReadingPracticeItem.readingPracticeItem,
                onBackClick = { navController.navigateUp() },
            )
        }
        composable(Screen.Selection.route) {
            SelectionScreen(
                onBackClick = { navController.navigateUp() },
                onListeningListClick = { navController.navigate(Screen.ListeningPracticeList.route)},
                onReadingListClick = { navController.navigate(Screen.ReadingPracticeList.route) }
            )
        }
        composable(
            Screen.Welcome.route,
//            exitTransition = {
//                slideOutOfContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Left,
//                    tween(
//                        durationMillis = 500,
//                        delayMillis = 100
//                    )
//                )
//            }
        ) {
            WelcomeScreen(
                onNextButtonClick = { navController.navigate(Screen.SignIn.route) }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onListeningClick = { navController.navigate(Screen.ListeningPracticeGenerator.route) },
                onReadingClick = { navController.navigate(Screen.ReadingPracticeGenerator.route) },
                onBackpackClick = { navController.navigate(Screen.Selection.route) },
                onAvatarClick = { navController.navigate(Screen.Profile.route) }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.navigateUp() },
                onChangeInfoClick = { navController.navigate(Screen.ChangeInfo.route) },
                currentTheme = currentTheme,
                onThemeChange = onThemeChange
            )
        }
        composable(Screen.ListeningPracticeList.route) {
            ListeningPracticeListScreen(
                listeningPracticeItemList =
                MockListeningPracticeItem.listeningPracticeItemList,
                onBackClick = { navController.navigateUp() },
                onDeleteClick = {},
                onItemClick = { navController.navigate(Screen.ListeningPractice.route) }
            )
        }
        composable(Screen.ListeningPracticeGenerator.route) {
            ListeningPracticeGeneratorScreen(
                onBackClick = { navController.navigateUp() },
                onNextButtonClick = {},
                onLeaveButtonClick = { navController.navigate(Screen.ListeningPracticeList.route) }
            )
        }
        composable(Screen.ReadingPracticeList.route) {
            ReadingPracticeListScreen(
                readingPracticeItemList =
                MockReadingPracticeItem.readingPracticeItemList,
                onBackClick = { navController.navigateUp() },
                onDeleteClick = {},
                onItemClick = { navController.navigate(Screen.ReadingPractice.route) }
            )
        }
        composable(Screen.ReadingPracticeGenerator.route) {
            ReadingPracticeGeneratorScreen(
                onBackClick = { navController.navigateUp() },
                onNextButtonClick = {},
                onLeaveButtonClick = { navController.navigate(Screen.ReadingPracticeList.route) }
            )
        }
        composable(Screen.ChangeInfo.route) {
            ChangeInformationScreen(
                onBackClick = { navController.navigateUp() },
                onNextButtonClick = {}
            )
        }
    }
}
