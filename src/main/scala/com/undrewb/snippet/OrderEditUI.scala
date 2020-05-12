package com.undrewb.snippet

import com.undrewb.model.{Order, OrderLine}
import net.liftweb.common.Full
import net.liftweb.http.js.jquery.JqJsCmds.AppendHtml
import net.liftweb.http.{RequestVar, SHtml}
import net.liftweb.util.Helpers._
import net.liftweb.util._

import scala.xml.{NodeSeq, Text}

class OrderEditUI {

  val line_item =
    <div class="order-line">
      <div class="order-line-item"></div>
      <div class="order-line-quantity"></div>
    </div>

  def edit_order(ns: NodeSeq): NodeSeq = {
    OrderViewUI.currentOrderVar match {
      case order => {
        def add_order_line() = {
          val orderLine = OrderLine.create
          orderLine.order_id(order.id.get)
          order.orderLines.append(orderLine)


          AppendHtml("order-lines",
            (".order-line-item *" #> orderLine.item.toForm &
              ".order-line-quantity *" #> orderLine.quantity.toForm
              ).apply(line_item))
        }

        def edit_lines(lines: List[OrderLine]): NodeSeq = {
          lines.flatMap({
            line: OrderLine => {
              (".order-line-item *" #> line.item.toForm &
                ".order-line-quantity *" #> line.quantity.toForm
                ).apply(line_item)
            }
          })
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
          "#order-lines *" #> edit_lines(order.orderLines.toList) &
          ".add-order-line *" #> SHtml.ajaxButton("Add order line", () => add_order_line()) &
          ".order-submit *" #> SHtml.submit("Save Order", () => save_order)
          )
          .apply(ns)
      }
    }
  }

}
