package net.peihuan.tieba_tool

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableAsync
@SpringBootApplication
@MapperScan("net.peihuan.tieba_tool.persistent.mapper")
class TiebaToolApplication

fun main(args: Array<String>) {
	runApplication<TiebaToolApplication>(*args)
}
