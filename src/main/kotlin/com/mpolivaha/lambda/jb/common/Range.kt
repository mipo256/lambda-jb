package com.mpolivaha.lambda.jb.common

/**
 *
 * @param from - from, inclusive
 * @param to - to. inclusive
 *
 * @author Mikhail Polivakha
 */
class Range(
    private val from: Int,
    private val to: Int,
) {

    companion object {
        fun inclusive(from: Int, to: Int) : Range {
            return Range(from, to)
        }
    }
}