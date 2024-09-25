@file:JvmName("NumberUtils")

package app.kotatsu.plugin.sdk.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

public fun Number.format(decimals: Int = 0, decPoint: Char = '.', thousandsSep: Char? = ' '): String {
	val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
	val symbols = formatter.decimalFormatSymbols
	if (thousandsSep != null) {
		symbols.groupingSeparator = thousandsSep
		formatter.isGroupingUsed = true
	} else {
		formatter.isGroupingUsed = false
	}
	symbols.decimalSeparator = decPoint
	formatter.decimalFormatSymbols = symbols
	formatter.minimumFractionDigits = decimals
	formatter.maximumFractionDigits = decimals
	return when (this) {
		is Float,
		is Double,
		-> formatter.format(this.toDouble())

		else -> formatter.format(this.toLong())
	}
}

public fun Float.toIntUp(): Int {
	val intValue = toInt()
	return if (this == intValue.toFloat()) {
		intValue
	} else {
		intValue + 1
	}
}

public infix fun Int.upBy(step: Int): Int {
	val mod = this % step
	return if (mod == 0) {
		this
	} else {
		this - mod + step
	}
}

public fun Number.formatSimple(): String {
	val raw = toString()
	return if (raw.endsWith(".0") || raw.endsWith(",0")) {
		raw.dropLast(2)
	} else {
		raw
	}
}

public fun Boolean.asInt() = if (this) 1 else 0
