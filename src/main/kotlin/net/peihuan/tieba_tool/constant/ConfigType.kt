package net.peihuan.tieba_tool.constant

import com.baomidou.mybatisplus.annotation.EnumValue

enum class ConfigType(@EnumValue val code: Int, val msg: String) {
    COOKIE(1, "Cookie"),
    CARD(2, "帖子"),
    ;
}