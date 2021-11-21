package net.peihuan.tieba_tool.persistent.po

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import net.peihuan.tieba_tool.constant.ConfigType
import java.util.*

@TableName("tieba_config")
data class TiebaConfigPO(
    @TableId
    var id: Long,
    val content: String,
    @TableField("`desc`")
    val desc: String,
    var type: ConfigType,
    var lastReplyTime: Date?,
    // 回帖间隔秒数
    val frequency: Int,
    @TableLogic
    val deleted: Boolean,
    val createTime: Date,
    var updateTime: Date,
)