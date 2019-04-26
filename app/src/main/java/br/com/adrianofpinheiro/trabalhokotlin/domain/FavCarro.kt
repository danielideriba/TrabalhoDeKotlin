package br.com.adrianofpinheiro.trabalhokotlin.domain

import java.util.*

/**
 * Created by danielideriba on 26/04/19.
 */

data class FavCarro(
    var tipo: String = "",
    var nome: String = "",
    var desc: String = "",
    var urlFoto: String = "",
    var dataHoraCadastro: Date? = null
)