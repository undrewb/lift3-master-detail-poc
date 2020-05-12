package com.undrewb.model

import net.liftweb.mapper._

object Order extends Order with LongKeyedMetaMapper[Order] {
    override def dbTableName = "order_t" // define the DB table name
}

class Order extends LongKeyedMapper[Order] with OneToMany[Long, Order] {
    override def getSingleton: KeyedMetaMapper[Long, Order] = Order
    def primaryKeyField = id

    object id extends MappedLongIndex(this)
    object name extends MappedString(this, 64)
    object addr extends MappedString(this, 256)

    object orderLines extends MappedOneToMany(OrderLine, OrderLine.order_id, OrderBy(OrderLine.id, Ascending))
}