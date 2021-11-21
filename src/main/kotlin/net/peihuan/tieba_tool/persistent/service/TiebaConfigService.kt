package net.peihuan.tieba_tool.persistent.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import net.peihuan.tieba_tool.constant.ConfigType
import net.peihuan.tieba_tool.persistent.mapper.TiebaConfigMapper
import net.peihuan.tieba_tool.persistent.po.TiebaConfigPO
import org.springframework.stereotype.Service

@Service
class TiebaConfigService : ServiceImpl<TiebaConfigMapper, TiebaConfigPO>() {

    fun getAllCookies(): List<String> {
        return list(KtQueryWrapper(TiebaConfigPO::class.java).eq(TiebaConfigPO::type, ConfigType.COOKIE.code)).map { it.content }
    }

    fun getAllCards(): List<TiebaConfigPO> {
        return list(KtQueryWrapper(TiebaConfigPO::class.java).eq(TiebaConfigPO::type, ConfigType.CARD.code))
    }


}