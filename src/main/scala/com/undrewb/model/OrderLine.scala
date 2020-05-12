package com.undrewb.model

import net.liftweb.mapper.{KeyedMapper, KeyedMetaMapper, LongKeyedMapper, LongKeyedMetaMapper, MappedInt, MappedLongForeignKey, MappedLongIndex, MappedString, OneToMany}

object OrderLine extends OrderLine with LongKeyedMetaMapper[OrderLine] {
    override def dbTableName = "order_line_t" // define the DB table name
}

class OrderLine extends LongKeyedMapper[OrderLine] {
    def getSingleton = OrderLine
    def primaryKeyField = id

    object id extends MappedLongIndex(this)
    object item extends MappedString(this, 64)
    object quantity extends MappedInt(((this)))

    object order_id extends MappedLongForeignKey(this, Order)
}