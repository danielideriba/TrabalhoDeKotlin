package br.com.adrianofpinheiro.trabalhokotlin.domain

import java.util.*

/**
 * Created by danielideriba on 25/04/19.
 */

data class TokenPush(
    val token: String = "",
    var dataHoraCadastro: Date? = null
)