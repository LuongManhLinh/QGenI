package com.example.qgeni.ui.screens.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.qgeni.data.preferences.ThemeMode
import com.example.qgeni.data.preferences.UserPreferenceManager
import com.example.qgeni.ui.screens.HomeScreen
import com.example.qgeni.ui.screens.login.ForgotPasswordScreen
import com.example.qgeni.ui.screens.login.ResetPasswordScreen
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
    data object ResetPassword: Screen("reset_password")
    data object ListeningPractice : Screen("listening_practice/{idHexString}")
    data object ListeningPracticeList: Screen("listening_practice_list")
    data object ListeningPracticeGenerator : Screen("listening_practice_generator")
    data object ReadingPractice : Screen("reading_practice/{idHexString}")
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

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier
    ) {

        composable(
            Screen.Welcome.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(
                        durationMillis = 500,
                        delayMillis = 100
                    )
                )
            }
        ) {
            WelcomeScreen(
                onNextButtonClick = {
                    UserPreferenceManager.loadUserId(context)
                    val userId = UserPreferenceManager.getUserId()
                    if (userId == null) {
                        navController.navigate(Screen.SignIn.route)
                    } else {
                        navController.navigate(Screen.Home.route)
                    }
                }
            )
        }


        composable(
            Screen.SignIn.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(
                        durationMillis = 500,
                        delayMillis = 100
                    )
                )
            }
        ) {
            SignInScreen(
                onBackClick = { navController.navigate(Screen.Welcome.route) },
                onSignInSuccess = { navController.navigate(Screen.Home.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) },
                onSignUpClick = { navController.navigate(Screen.SignUp.route) }
            )
        }


        composable(Screen.SignUp.route) {
            SignUpScreen(
                onBackClick = { navController.navigateUp() },
                onSignUpSuccess = { navController.navigate(Screen.SignIn.route) },
                onSignInClick = { navController.navigate(Screen.SignIn.route) }
            )
        }


        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackClick = { navController.navigateUp() },
                onEmailVerified = {navController.navigate(Screen.Verification.route)}
            )
        }


        composable(Screen.Verification.route) {
            VerificationScreen(
                onBackClick = { navController.navigateUp() },
                onOTPVerified = { navController.navigate(Screen.ResetPassword.route) }
            )
        }

        composable(Screen.ResetPassword.route) {
            ResetPasswordScreen(
                onBackClick = { navController.navigateUp() },
                onPasswordChangeDone = { navController.navigate(Screen.SignIn.route) }
            )
        }

        composable(
            route = Screen.ListeningPractice.route,
            arguments = listOf(navArgument("idHexString") { type = NavType.StringType })
        ) { backStackEntry ->
            ListeningPracticeScreen(
                idHexString = backStackEntry.arguments?.getString("idHexString") ?: "",
                onBackClick = { navController.navigate(Screen.ListeningPracticeList.route) },
                onNavigatingToPracticeRepo = { navController.navigate(Screen.ListeningPracticeList.route) }
            )
        }

        composable(
            Screen.ReadingPractice.route,
            arguments = listOf(navArgument("idHexString") { type = NavType.StringType })
        ) { backStackEntry ->
            ReadingPracticeScreen(
                idHexString = backStackEntry.arguments?.getString("idHexString") ?: "",
                onNextButtonClick = {navController.navigate(Screen.ReadingPracticeList.route)},
                onBackClick = { navController.navigate(Screen.ReadingPracticeList.route) }
            )
        }

        composable(Screen.Selection.route) {
            SelectionScreen(
                onBackClick = { navController.navigate(Screen.Home.route) },
                onListeningListClick = { navController.navigate(Screen.ListeningPracticeList.route)},
                onReadingListClick = { navController.navigate(Screen.ReadingPracticeList.route) }
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
                onLogOutClick = {
                    UserPreferenceManager.removeUserId(context)
                    navController.navigate(Screen.SignIn.route)
                }, //thêm vào
                currentTheme = currentTheme,
                onThemeChange = onThemeChange
            )
        }

        composable(Screen.ListeningPracticeList.route) {
            ListeningPracticeListScreen(
                onBackClick = { navController.navigate(Screen.Selection.route) },
                onItemClick = {
                    navController.navigate(
                        Screen.ListeningPractice.route.replace("{idHexString}", it)
                    )
                }
            )
        }

        composable(Screen.ListeningPracticeGenerator.route) {
            ListeningPracticeGeneratorScreen(
                onBackClick = { navController.navigate(Screen.Home.route) },
                onLeaveButtonClick = { navController.navigate(Screen.ListeningPracticeList.route) }
            )
        }

        composable(Screen.ReadingPracticeList.route) {
            ReadingPracticeListScreen(
                onBackClick = { navController.navigate(Screen.Selection.route) },
                onItemClick = {
                    navController.navigate(
                        Screen.ReadingPractice.route.replace("{idHexString}", it)
                    )
                }
            )
        }

        composable(Screen.ReadingPracticeGenerator.route) {
            ReadingPracticeGeneratorScreen(
                onBackClick = { navController.navigate(Screen.Home.route) },
                onNextButtonClick = {},
                onLeaveButtonClick = { navController.navigate(Screen.ReadingPracticeList.route) }
            )
        }

        composable(Screen.ChangeInfo.route) {
            ChangeInformationScreen(
                onBackClick = { navController.navigateUp() },
                onNextButtonClick = { navController.navigate(Screen.Profile.route)}
            )
        }
    }
}
