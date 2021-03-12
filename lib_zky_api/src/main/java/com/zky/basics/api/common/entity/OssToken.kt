package com.zky.basics.api.common.entity

/**
 * Created by lk
 * Date 2019-11-13
 * Time 10:29
 * Detail:
 */
data class OssToken(
    var StatusCode: String?,
    var AccessKeyId: String?,
    var AccessKeySecret: String?,
    var SecurityToken: String?,
    var Expiration: String?
)