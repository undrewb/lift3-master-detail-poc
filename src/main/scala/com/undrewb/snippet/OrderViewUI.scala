package com.undrewb
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import Helpers._
import com.undrewb.model.{Order, OrderLine}
import net.liftweb.common.Full
import net.liftweb.http.js.jquery.JqJsCmds.AppendHtml
import net.liftweb.http.{RequestVar, S, SHtml, SessionVar}

object OrderViewUI extends OrderViewUI

class OrderViewUI {

  object currentOrderVar extends SessionVar[Order](null)

  val line_item =
    <div class="order-line">
      <div class="order-line-item"></div>
      <div class="order-line-quantity"></div>
    </div>

  def view_orders(ns: NodeSeq): NodeSeq = {
    Order.findAll().flatMap {
      order => {
        (
          (".order-cust-name *") #> order.name &
            (".order-cust-addr *") #> order.addr &
            ".order-lines *" #> show_lines(order.orderLines.toList) &
            ".order-edit-link *" #> SHtml.link("/edit_order", () => currentOrderVar(order), Text("Edit Order"))
          ).apply(ns)
      }
    }
  }

  def show_lines(lines: List[OrderLine]): NodeSeq = {
    lines.flatMap({
      line: OrderLine => {
        (".order-line-item *" #> line.item &
          ".order-line-quantity *" #> line.quantity
          ).apply(line_item)
      }
    })
  }


}
