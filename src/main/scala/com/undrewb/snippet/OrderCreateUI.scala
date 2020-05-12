package com.undrewb.snippet

import com.undrewb.model.{Order, OrderLine}
import net.liftweb.http.js.jquery.JqJsCmds.AppendHtml
import net.liftweb.http.{RequestVar, SHtml}
import net.liftweb.util.Helpers._
import net.liftweb.util._

import scala.xml.{NodeSeq, Text}

class OrderCreateUI {

  val line_item =
    <div class="order-line">
      <div class="order-line-item"></div>
      <div class="order-line-quantity"></div>
    </div>

  def create_order(ns: NodeSeq): NodeSeq = {
    val order = Order.create
    val orderLine = OrderLine.create

    orderLine.order_id(order.id.get)
    order.orderLines.append(orderLine)

    def add_order_line() = {
      val orderLine = OrderLine.create
      orderLine.order_id(order)
      order.orderLines.append(orderLine)

      AppendHtml("order-lines",
        (".order-line-item *" #> orderLine.item.toForm &
          ".order-line-quantity *" #> orderLine.quantity.toForm
          ).apply(line_item))
    }

    def save_order() = {
      order.save()
      order.orderLines.foreach {
        line: OrderLine => {
          line.save()
        }
      }
    }

    ((".order-cust-name *") #> order.name.toForm &
      (".order-cust-addr *") #> order.addr.toForm &
      ".order-line-item *" #> orderLine.item.toForm &
      ".order-line-quantity *" #> orderLine.quantity.toForm &
      ".add-order-line *" #> SHtml.ajaxButton("Add order line", () => add_order_line()) &
      ".order-submit *" #> SHtml.submit("Save Order", () => save_order)
      )
      .apply(ns)
  }
}
