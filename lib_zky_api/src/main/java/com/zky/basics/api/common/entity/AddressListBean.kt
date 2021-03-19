package com.zky.basics.api.common.entity

/**
 *create_time : 21-3-19 上午9:52
 *author: lk
 *description： AddressListBean
 */
data class AddressListBean (var point:List<UploadAdressBean>,
                            var line:List<UploadAdressBean>,
                            var plane:List<UploadAdressBean>)