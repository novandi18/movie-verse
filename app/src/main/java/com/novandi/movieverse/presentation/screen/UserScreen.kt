package com.novandi.movieverse.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.novandi.core.data.response.Resource
import com.novandi.core.data.source.remote.response.LoginRequest
import com.novandi.core.data.source.remote.response.LogoutRequest
import com.novandi.core.domain.model.User
import com.novandi.core.utils.isInternetAvailable
import com.novandi.core.utils.toGravatarImageUrl
import com.novandi.core.utils.toTmdbImageUrl
import com.novandi.movieverse.R
import com.novandi.movieverse.presentation.ui.component.MovieDialog
import com.novandi.movieverse.presentation.ui.component.UserSkeleton
import com.novandi.movieverse.presentation.ui.theme.Black
import com.novandi.movieverse.presentation.ui.theme.Gray
import com.novandi.movieverse.presentation.ui.theme.MovieVerseTheme
import com.novandi.movieverse.presentation.ui.theme.Red40
import com.novandi.movieverse.presentation.ui.theme.Red80
import com.novandi.movieverse.presentation.ui.theme.SemiBlack
import com.novandi.movieverse.presentation.ui.theme.White
import com.novandi.movieverse.presentation.ui.theme.rubikFamily
import com.novandi.movieverse.presentation.viewmodel.UserViewModel

@Composable
fun UserScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val requestToken by viewModel.requestToken.collectAsStateWithLifecycle()
    val validateAccount by viewModel.validateAccount.collectAsStateWithLifecycle()
    val login by viewModel.authResult.collectAsStateWithLifecycle()
    val sessionId by viewModel.sessionId.observeAsState()
    val userFromDB by viewModel.userFromDB.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(requestToken is Resource.Loading) {
        when (requestToken) {
            is Resource.Loading -> viewModel.onLoadingChange(true)
            is Resource.Success -> {
                if (requestToken?.data != null) {
                    val request = LoginRequest(
                        viewModel.username,
                        viewModel.password,
                        requestToken?.data!!.token
                    )
                    viewModel.validateAccount(request)
                }
            }
            is Resource.Error -> {
                viewModel.onLoadingChange(false)
                Toast.makeText(context, requestToken?.message, Toast.LENGTH_SHORT).show()
            }
            else -> viewModel.onLoadingChange(false)
        }
    }

    LaunchedEffect(validateAccount is Resource.Loading) {
        when (validateAccount) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                viewModel.onLoadingChange(false)
                if (validateAccount?.data != null) {
                    if (validateAccount?.data!!.success) {
                        val request = LoginRequest(
                            viewModel.username,
                            viewModel.password,
                            validateAccount?.data!!.requestToken.toString()
                        )
                        viewModel.login(request)
                    } else {
                        Toast.makeText(context, validateAccount?.data!!.statusMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            is Resource.Error -> {
                viewModel.onLoadingChange(false)
                Toast.makeText(context, validateAccount?.message, Toast.LENGTH_SHORT).show()
            }
            else -> viewModel.onLoadingChange(false)
        }
    }

    LaunchedEffect(login is Resource.Loading) {
        when (login) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                viewModel.onLoadingChange(false)
                if (login?.data != null) {
                    if (login?.data!!.success) {
                        viewModel.setSessionId(true, login?.data!!.sessionId.toString())
                        viewModel.onUsernameChange("")
                        viewModel.onPasswordChange("")
                    } else {
                        Toast.makeText(context, login?.data!!.statusMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            is Resource.Error -> {
                viewModel.onLoadingChange(false)
                Toast.makeText(context, login?.message, Toast.LENGTH_SHORT).show()
            }
            else -> viewModel.onLoadingChange(false)
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Black)) {
        if (sessionId?.isNotEmpty() == true) {
            if (userFromDB == null || userFromDB?.id == 0) viewModel.getUser(sessionId!!)
            AuthorizedScreen(viewModel, context, userFromDB, sessionId)
        } else {
            UnauthorizedScreen(viewModel)
        }
    }
}

@Composable
private fun UnauthorizedScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocus = LocalFocusManager.current
    val context = LocalContext.current

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.unauthorized)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.size(100.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(id = R.string.unauthenticated),
            color = White,
            fontWeight = FontWeight.Bold,
            fontFamily = rubikFamily,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            lineHeight = 38.sp
        )
        Text(
            modifier = Modifier.padding(bottom = 24.dp, top = 4.dp),
            text = stringResource(id = R.string.unauthenticated_desc),
            color = White,
            fontFamily = rubikFamily,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            modifier = Modifier.padding(bottom = 16.dp),
            value = viewModel.username,
            onValueChange = viewModel::onUsernameChange,
            enabled = !viewModel.loading,
            placeholder = {
                Text(text = stringResource(id = R.string.username))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = stringResource(id = R.string.username)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = White,
                focusedBorderColor = White,
                unfocusedBorderColor = Gray,
                focusedTextColor = White,
                unfocusedTextColor = Gray,
                unfocusedLeadingIconColor = Gray,
                focusedLeadingIconColor = White,
                disabledBorderColor = Gray,
                disabledTextColor = Gray,
                disabledLeadingIconColor = Gray
            ),
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier.padding(bottom = 32.dp),
            value = viewModel.password,
            onValueChange = viewModel::onPasswordChange,
            enabled = !viewModel.loading,
            placeholder = {
                Text(text = stringResource(id = R.string.password))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Key,
                    contentDescription = stringResource(id = R.string.password)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = White,
                focusedBorderColor = White,
                unfocusedBorderColor = Gray,
                focusedTextColor = White,
                unfocusedTextColor = Gray,
                unfocusedLeadingIconColor = Gray,
                focusedLeadingIconColor = White,
                disabledBorderColor = Gray,
                disabledTextColor = Gray,
                disabledLeadingIconColor = Gray
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        viewModel.onPasswordVisibleChange(!viewModel.passwordVisible)
                    }
                ) {
                    Icon(
                        imageVector = if (viewModel.passwordVisible)
                            Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                        contentDescription = stringResource(id = R.string.password)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else
                PasswordVisualTransformation(),
            singleLine = true
        )
        ElevatedButton(
            onClick = {
                if (viewModel.username.isNotEmpty() && viewModel.password.isNotEmpty()) {
                    if (isInternetAvailable(context)) {
                        localFocus.clearFocus()
                        keyboardController?.hide()
                        viewModel.requestToken()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.no_internet),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.field_required),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            enabled = !viewModel.loading,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Red40,
                contentColor = White,
                disabledContainerColor = Red80
            )
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.signin_account),
                    color = White,
                    fontFamily = rubikFamily,
                    fontSize = 18.sp
                )
                AnimatedVisibility(visible = viewModel.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = White,
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthorizedScreen(
    viewModel: UserViewModel = hiltViewModel(),
    context: Context,
    userFromDB: User? = null,
    sessionId: String? = null
) {
    val user by viewModel.user.collectAsStateWithLifecycle()
    val favoriteMovies by viewModel.favoriteMovies.collectAsStateWithLifecycle()
    val ratedMovies by viewModel.ratedMovies.collectAsStateWithLifecycle()
    val watchlistMovies by viewModel.watchlistMovies.collectAsStateWithLifecycle()
    var data by remember { mutableStateOf<User?>(null) }
    var favorite by remember { mutableIntStateOf(-1) }
    var rated by remember { mutableIntStateOf(-1) }
    var watchlist by remember { mutableIntStateOf(-1) }
    val openLogoutDialog = remember { mutableStateOf(false) }

    if (userFromDB != null && userFromDB.id != 0) data = userFromDB

    if (data != null) {
        if (favorite != -1 && rated != -1 && watchlist != -1) {
            data = data!!.copy(favoriteMovies = favorite, ratedMovies = rated, watchlistMovies = watchlist)
            viewModel.insertUser(data!!)
        }
    }

    LaunchedEffect(key1 = user is Resource.Loading) {
        when (user) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                if (user?.data != null) {
                    data = user?.data
                    viewModel.setAccountId(true, user?.data!!.id.toString())
                    viewModel.getFavoriteMoviesTotal(user?.data!!.id)
                    viewModel.getRatedMoviesTotal(user?.data!!.id)
                    viewModel.getWatchlistMoviesTotal(user?.data!!.id)
                }
            }
            is Resource.Error -> {
                Toast.makeText(context, user?.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    LaunchedEffect(key1 = favoriteMovies is Resource.Loading) {
        when (favoriteMovies) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                if (favoriteMovies?.data != null) favorite = favoriteMovies?.data!!.toInt()
            }
            is Resource.Error -> {
                Toast.makeText(context, favoriteMovies?.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    LaunchedEffect(key1 = ratedMovies is Resource.Loading) {
        when (ratedMovies) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                if (ratedMovies?.data != null) rated = ratedMovies?.data!!.toInt()
            }
            is Resource.Error -> {
                Toast.makeText(context, ratedMovies?.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    LaunchedEffect(key1 = watchlistMovies is Resource.Loading) {
        when (watchlistMovies) {
            is Resource.Loading -> {}
            is Resource.Success -> {
                if (watchlistMovies?.data != null) watchlist = watchlistMovies?.data!!.toInt()
            }
            is Resource.Error -> {
                Toast.makeText(context, watchlistMovies?.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    when {
        openLogoutDialog.value -> {
            MovieDialog(
                onDismissRequest = { openLogoutDialog.value = false },
                onConfirmation = {
                    viewModel.logout(LogoutRequest(sessionId!!))
                    viewModel.deleteUser()
                    viewModel.setSessionId(false, "")
                    viewModel.setAccountId(false, "")
                    openLogoutDialog.value = false
                },
                dialogTitle = context.getString(R.string.logout),
                dialogText = context.getString(R.string.logout_desc),
                icon = Icons.AutoMirrored.Rounded.Logout,
                confirmationText = stringResource(id = R.string.logout_confirm)
            )
        }
    }

    if (data != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 60.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Black, CircleShape),
                model = data?.tmdb.toTmdbImageUrl().ifEmpty {
                    data?.gravatar.toGravatarImageUrl()
                },
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.image_placeholder),
                error = painterResource(id = R.drawable.image_error),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = data?.name.toString().ifEmpty {
                        stringResource(id = R.string.anonymous)
                    },
                    color = White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    fontFamily = rubikFamily
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "@${data?.username}",
                    color = White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = rubikFamily
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .width(64.dp)
                        .clickable { },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (data?.ratedMovies != null) {
                        Text(
                            text = data?.ratedMovies.toString(),
                            color = White,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = rubikFamily
                        )
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = White
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.rated),
                        color = White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = rubikFamily
                    )
                }

                VerticalDivider(
                    modifier = Modifier
                        .height(32.dp)
                        .width(1.dp),
                    color = SemiBlack
                )

                Column(
                    modifier = Modifier
                        .width(64.dp)
                        .clickable { },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (data?.ratedMovies != null) {
                        Text(
                            text = data?.favoriteMovies.toString(),
                            color = White,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = rubikFamily
                        )
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = White
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.favorite),
                        color = White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = rubikFamily
                    )
                }

                VerticalDivider(
                    modifier = Modifier
                        .height(32.dp)
                        .width(1.dp),
                    color = SemiBlack
                )

                Column(
                    modifier = Modifier
                        .width(64.dp)
                        .clickable { },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (data?.ratedMovies != null) {
                        Text(
                            text = data?.watchlistMovies.toString(),
                            color = White,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontFamily = rubikFamily
                        )
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = White
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.watchlist),
                        color = White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = rubikFamily
                    )
                }
            }
            ElevatedButton(
                modifier = Modifier.padding(top = 32.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Red40,
                    contentColor = White
                ),
                onClick = { openLogoutDialog.value = true }
            ) {
                Icon(
                    modifier = Modifier.padding(end = 6.dp),
                    imageVector = Icons.AutoMirrored.Rounded.Logout,
                    contentDescription = stringResource(id = R.string.logout)
                )
                Text(
                    text = stringResource(id = R.string.logout),
                    color = White,
                    fontFamily = rubikFamily,
                    fontSize = 16.sp
                )
            }
        }
    } else UserSkeleton()
}

@Preview(showBackground = true)
@Composable
private fun UnauthorizedScreenPreview() {
    MovieVerseTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Black)
        ) {
            UnauthorizedScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthorizedScreenPreview() {
    MovieVerseTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Black)
        ) {
            AuthorizedScreen(context = LocalContext.current)
        }
    }
}