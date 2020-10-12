package cess.com.br.zapviewer.common.extension

fun String?.toDoubleOrZero(): Double {
    return this?.toDouble() ?: 0.0
}