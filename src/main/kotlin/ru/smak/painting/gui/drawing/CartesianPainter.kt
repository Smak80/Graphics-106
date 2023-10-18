package ru.smak.painting.gui.drawing

import ru.smak.drawing.Converter
import ru.smak.drawing.Plane
import java.awt.Color
import java.awt.Graphics
import kotlin.math.roundToInt


class CartesianPainter : Painter {
    var plane: Plane? = null
    override fun paint(g: Graphics) {
        //g.color = Color.BLUE
        //g.fillOval(50, 30, 200, 200)
        //g.color = Color(200, 0, 200, 200)
        //g.fillOval(150, 80, 200, 200)
        paintAxis(g)
        paintTicks(g)
    }
    fun paintAxis(g: Graphics){
        plane?.let { plane ->
            val x0 = Converter.xCrt2Scr(0.0, plane)
            val y0 = Converter.yCrt2Scr(0.0, plane)
            g.drawLine(0, y0, plane.width, y0)
            g.drawLine(x0, 0, x0, plane.height)
        }
    }
    fun paintXLabel(g: Graphics, value: Double){
        plane?.let { plane ->
            val y0 = Converter.yCrt2Scr(0.0, plane)
            val x = Converter.xCrt2Scr(value, plane)
            with(g.fontMetrics.getStringBounds(value.toString(), g)){
                g.drawString(value.toString(), (x - width / 2).toInt(), (y0 + 10 + height).toInt())
            }
        }
    }

    fun paintTicks(g: Graphics){
        plane?.let { plane ->
            val y = Converter.yCrt2Scr(0.0, plane)
            var x = (plane.xMin*10).roundToInt() / 10.0
            val h = 4
            while(x <= plane.xMax) {
                g.color = Color.BLACK
                val xP = Converter.xCrt2Scr(x, plane)
                val r = (x * 10).roundToInt()
                var cH = h
                if(r % 5 == 0) {
                    cH += 2
                    g.color = Color.BLUE
                }
                if(r % 10 == 0) {
                    cH += 3
                    g.color = Color.RED
                    if(r != 0) paintXLabel(g, r / 10.0)
                }
                g.drawLine(xP, y + cH, xP, y - cH)

                x += 0.1
            }
        }
    }
}