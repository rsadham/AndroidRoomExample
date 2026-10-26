package com.example.mytestroom.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mytestroom.presentation.viewModels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataScreen(viewModel: UserViewModel = hiltViewModel()) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (state.editUserId != null) {
                            "Edit User"
                        } else {
                            "Add User"
                        }
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = state.userName,
                onValueChange = { newText ->
                    viewModel.updateUserName(newText)
                },
                placeholder = {
                    Text("Enter User Name")
                },
                label = {
                    Text("User Name")
                },
                maxLines = 1
            )

            OutlinedTextField(
                value = state.userLocation,
                onValueChange = { newText ->
                    viewModel.updateUserLocation(newText)
                },
                placeholder = {
                    Text("Enter User Location")
                },
                label = {
                    Text("User Location")
                },
                maxLines = 1
            )

            Button(
                onClick = {
                    viewModel.saveUser()
                },
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save")
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                items(items = state.users) { user ->

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        {

                            Text(text = user.userName, style = MaterialTheme.typography.titleMedium)

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = user.userLocation,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Button(
                                    onClick = {
                                        viewModel.editUser(user)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Edit")
                                }

                                Button(
                                    onClick = {
                                        viewModel.deleteUser(user)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = "Delete")
                                }

                            }
                        }
                    }
                }
            }
        }


    }
}