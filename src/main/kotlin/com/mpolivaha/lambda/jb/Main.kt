package com.mpolivaha.lambda.jb

import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.annotations.QuarkusMain

@QuarkusMain
class Main {

    companion object {

        @JvmStatic
        fun main(vararg args: String) {
            Quarkus.run(*args)
        }
    }
}