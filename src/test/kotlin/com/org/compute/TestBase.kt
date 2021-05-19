package com.org.compute

import org.json.simple.parser.JSONParser
import java.io.FileReader

open class TestBase {

    fun lambdaInputFromFile(path:String):String {
        val reader = FileReader(path)
        val jsonParser = JSONParser()
        return jsonParser.parse(reader).toString()
    }
}
