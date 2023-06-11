package com.example.appfirestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// Obter instância do Firestore
private val db = FirebaseFirestore.getInstance()

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

        // Configurar Firebase
        Firebase

            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Estados mutáveis para os valores dos campos de texto
                    val nome = remember { mutableStateOf("") }
                    val endereco = remember { mutableStateOf("") }
                    val bairro = remember { mutableStateOf("") }
                    val cep = remember { mutableStateOf("") }
                    val cidade = remember { mutableStateOf("") }
                    val estado = remember { mutableStateOf("") }

                    // Título do formulário
                    Text(
                        text = "Formulário",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00CCCC)
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Campo de texto para o nome
                    OutlinedTextField(
                        value = nome.value,
                        onValueChange = { nome.value = it },
                        label = { Text("Nome") },
                        placeholder = { Text("Informe o nome") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    // Campo de texto para o endereço
                    OutlinedTextField(
                        value = endereco.value,
                        onValueChange = { endereco.value = it },
                        label = { Text("Endereço") },
                        placeholder = { Text("Informe o endereço") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    // Campo de texto para o bairro
                    OutlinedTextField(
                        value = bairro.value,
                        onValueChange = { bairro.value = it },
                        label = { Text("Bairro") },
                        placeholder = { Text("Informe o bairro") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    // Campo de texto para o CEP
                    OutlinedTextField(
                        value = cep.value,
                        onValueChange = { cep.value = it },
                        label = { Text("CEP") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    // Campo de texto para a cidade
                    OutlinedTextField(
                        value = cidade.value,
                        onValueChange = { cidade.value = it },
                        label = { Text("Cidade") },
                        placeholder = { Text("Informe a cidade") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    // Campo de texto para o estado
                    OutlinedTextField(
                        value = estado.value,
                        onValueChange = { estado.value = it },
                        label = { Text("Estado") },
                        placeholder = { Text("Informe o estado") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    // Botão para criar o registro no Firestore
                    Button(
                        onClick = {
                            val data = hashMapOf(
                                "nome" to nome.value,
                                "endereco" to endereco.value,
                                "bairro" to bairro.value,
                                "cep" to cep.value,
                                "cidade" to cidade.value,
                                "estado" to estado.value
                            )
                            // Adicionar o registro ao Firestore
                            db.collection("registros")
                                .add(data)
                                .addOnSuccessListener {
                                    // Registro criado com sucesso, limpar os campos de texto
                                    nome.value = ""
                                    endereco.value = ""
                                    bairro.value = ""
                                    cep.value = ""
                                    cidade.value = ""
                                    estado.value = ""
                                }
                                .addOnFailureListener {
                                    // Ocorreu um erro ao criar o registro
                                }
                        },
                        modifier = Modifier.padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF00CCCC))
                    ) {
                        Text("Criar Registro", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}