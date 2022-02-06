package com.peter.flickrsearch.framework.datasource.responses

data class MainResponse (
    val stat : String?,
    val photos : Response?
)


data class Response(
    val page: Int?,

    val pages: Int?,

    val perpage: Int?,

    val total: Int?,

    var photo: List<PhotoResponse>?,
)


