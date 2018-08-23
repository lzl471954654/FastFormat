import kotlin.collections.ArrayList

public fun main(args: Array<String>) {

    println("字符串格式化性能测试 10w次")
    //Thread.sleep(20000)
    var start = System.currentTimeMillis()
    var end:Long
    var count = 100000

    while (count > 0){
        val s = String.format(
                "%n%%%s apple %5s banana %0#16x kit %-11s ,  , %1\$s,%3.6f",
                "First Param", "Second Param", 99999, "hello",123.223)
        //println(s)
        count--
        //println(s)
    }
    end = System.currentTimeMillis()
    println("String.format耗时："+(end-start))

    Thread.sleep(5000)

    start = System.currentTimeMillis()
    count = 100000
    while (count > 0){
        val s = fastFormat(
                "%n%%%s apple %5s banana %0#16x kit %-11s ,  , %1\$s,%3.6f",
                "First Param", "Second Param", 99999, "hello",123.223)
        //println(s)
        count--
        //println(s)
    }
    end = System.currentTimeMillis()
    println("fastFormat 耗时"+(end-start))
}

private val convertCharSet = hashSetOf(
        'd','x','o','s','f','n','%'
)

private val flagSet = hashSetOf(
        '0','-','#'
)

private val numberArray = arrayOf(
        0L,
        10L,
        100L,
        1000L,
        10000L,
        100000L,
        1000000L,
        10000000L,
        100000000L,
        1000000000L
)

private val systemLineSeparator = System.getProperty("line.separator","\n")


private fun cutDouble(double : Double, prec : Int):String{
    var data = double.toString()
    val pointIndex = data.indexOf('.')
    val precision = data.length - pointIndex -1
    var newp = prec
    var s = newp - precision
    if (newp > precision){
        val builder = StringBuilder(data)
        while ( s > 0){
            builder.append('0')
            s--
        }
        data = builder.toString()
    }else{
        val builder = StringBuilder()
        val datas = data.split('.')
        builder.append(datas[0])
        builder.append('.')
        var dataCount = 0
        while (newp > 0 && dataCount < datas[1].length){
            builder.append(datas[1][dataCount])
            dataCount++
            newp--
        }
        data = builder.toString()
    }
    return data
}

public fun fastFormat(format: String, vararg args: Any): String? {
    var indexOfArgs = 0
    val builder = StringBuilder(format.length * 2)
    var i = 0
    val flags = ArrayList<Char>(4)
    val numberBuilder = StringBuilder(8)
    while (i < format.length) {

        val c = format[i]
        if (c != '%') {
            builder.append(c)
            i++
            continue
        }
        if ( i + 1 >= format.length)
            throw IllegalArgumentException("nothing after % !")
        i++

        if ( format[i] == 'n'){
            builder.append(systemLineSeparator)
            i++
            continue
        }
        else if (format[i] == '%'){
            builder.append('%')
            i++
            continue
        }

        var index = 0
        var width = 0
        var precision = 0
        var convertChar:Char? = null
        flags.clear()


        fun parseFlags(){
            while ( i < format.length){
                if ( format[i] in flagSet)
                    flags.add(format[i])
                else
                    break
                i++
            }
        }

        fun parseWidthAndPrecision(){
            numberBuilder.delete(0,numberBuilder.length)
            var pointCount = 0
            if ( i >= format.length || format[i] == '0')
                return
            while (i < format.length){
                if (format[i] in '0'..'9'){
                    numberBuilder.append(format[i])
                }else if (format[i] == '.'){
                    if (pointCount == 0){
                        if (numberBuilder.isNotEmpty()){
                            width = numberBuilder.toString().toInt()
                            numberBuilder.delete(0,numberBuilder.length)
                        }
                        pointCount++
                    }
                }else
                    break
                i++
            }
            if (numberBuilder.isNotEmpty()){
                if (pointCount != 0)
                    precision = numberBuilder.toString().toInt()
                else
                    width = numberBuilder.toString().toInt()
            }
        }

        fun parseConvert(){
            if (i < format.length && format[i] in convertCharSet){
                convertChar = format[i]
                i++
            }else{
                throw IllegalArgumentException("Unknown convert char : ${format[i]}")
            }
        }

        fun parseIndex(){
            numberBuilder.delete(0,numberBuilder.length)
            val tempIndex = i
            while ( i < format.length){
                if ( format[i] in '1'..'9'){
                    numberBuilder.append(format[i])
                }else
                    break
                i++
            }
            if (format[i] != '$'){
                i = tempIndex
                //println("praseIndex:${numberBuilder.toString()} no used")
                return
            }
            if (numberBuilder.isNotEmpty()){
                index = numberBuilder.toString().toInt()
                i++
            }
            //println("praseIndex:${numberBuilder.toString()}")
        }

        fun append( char: Char, count : Int){
            var countC = count
            while ( countC > 0){
                builder.append(char)
                countC--
            }
        }

        fun cutDoubleNew(double: Double,prec: Int):String{
            if (prec > 9)
                return cutDouble(double,prec)
            numberBuilder.delete(0,numberBuilder.length)
            val left = double.toInt()
            val right = Math.round(double * numberArray[prec] - left * numberArray[prec])
            return numberBuilder.append(left.toString()).append('.').append(right.toString()).toString()
        }


        fun stringPrint(){
            convertChar ?: throw IllegalArgumentException("Missing convert char after % ")
            var nowIndex = 0
            if (index == 0){
                nowIndex = indexOfArgs
                indexOfArgs++
            }else{
                nowIndex = index - 1
            }
            val data = args[nowIndex]
            val leftAlign = flags.contains('-')

            when(convertChar!!){
                'c' ->{
                    if (data !is Char && data !is Number)
                        throw IllegalArgumentException("args[$nowIndex] is not match type Char ")
                    val nowChar = data as Char
                    if (leftAlign){
                        builder.append(nowChar)
                        append(' ',width-1)
                    }else{
                        append(' ',width-1)
                        builder.append(nowChar)
                    }
                }
                's' ->{
                    val string = data.toString()
                    val count = width - string.length
                    if (leftAlign){
                        builder.append(string)
                        append(' ',count)
                    }else{
                        append(' ',count)
                        builder.append(string)
                    }
                }
                'd','x','o' ->{
                    if ( data !is Number && data !is Char && data !is String )
                        throw IllegalArgumentException("args[$nowIndex] type is ${data.javaClass.name} , is not match type Number ")
                    val radix = when (convertChar!!){
                        'd' -> 10
                        'x' -> 16
                        'o' -> 8
                        else ->
                                throw IllegalArgumentException("Unknown error Happened , Convert Char has already changed , check memory error ?")
                    }
                    val n = if (data is Number)
                        data.toInt().toString(radix)
                    else if (data is Char)
                        data.toInt().toString(radix)
                    else
                        (data as String)

                    val zeroAppend = flags.contains('0')
                    val prefix = flags.contains('#')
                    val prefixChar = when (radix) {
                        16 -> "0x"
                        8 -> "0"
                        else -> ""
                    }
                    if (zeroAppend && leftAlign){
                        throw IllegalArgumentException(" flag - and 0 both exist")
                    }
                    when {
                        zeroAppend -> {
                            if (prefix){
                                builder.append(prefixChar)
                                append('0',width-n.length-prefixChar.length)
                                builder.append(n)
                            }else{
                                append('0',width-n.length)
                                builder.append(n)
                            }
                        }
                        leftAlign -> {
                            if (prefix){
                                builder.append(prefixChar)
                                builder.append(n)
                                append(' ',width-n.length-prefixChar.length)
                            }else{
                                builder.append(n)
                                append(' ',width-n.length)
                            }
                        }
                        else -> {
                            if (prefix){
                                append(' ',width-n.length-prefixChar.length)
                                builder.append(prefixChar)
                                builder.append(n)
                            }else{
                                append(' ',width-n.length)
                                builder.append(n)
                            }
                        }
                    }
                }
                'f' ->{
                    if ( data !is Number && data !is Char && data !is String  )
                        throw IllegalArgumentException("args[$nowIndex] type is ${data.javaClass.name} , is not match type Number ")
                    val n = if (data is Number)
                        data.toDouble()
                    else if (data is Char)
                        data.toDouble()
                    else
                        (data as String).toDouble()
                    val zeroAppend = flags.contains('0')
                    if (zeroAppend && leftAlign){
                        throw IllegalArgumentException(" flag - and 0 both exist")
                    }
                    val ns = cutDoubleNew(n,precision)
                    when{
                        zeroAppend->{
                            append('0',width-ns.length)
                            builder.append(ns)
                        }
                        leftAlign->{
                            builder.append(ns)
                            append(' ',width-ns.length)
                        }
                        else->{
                            append(' ',width-ns.length)
                            builder.append(ns)
                        }
                    }
                }
                else->{
                    throw IllegalArgumentException("Not support convert Char :$convertChar")
                }
            }
        }

        parseIndex()
        parseFlags()
        parseWidthAndPrecision()
        parseConvert()
        stringPrint()
    }


    return builder.toString()
}
