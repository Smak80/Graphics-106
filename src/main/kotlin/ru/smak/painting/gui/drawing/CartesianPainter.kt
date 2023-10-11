package ru.smak.painting.gui.drawing

import ru.smak.drawing.Converter
import ru.smak.drawing.Plane
import java.awt.Color
import java.awt.Graphics


class CartesianPainter : Painter {
    var plane: Plane? = null
    override fun paint(g: Graphics) {
        //g.color = Color.BLUE
        //g.fillOval(50, 30, 200, 200)
        //g.color = Color(200, 0, 200, 200)
        //g.fillOval(150, 80, 200, 200)
        paintAxis(g)
        paintTicks(g)
        paintLabels(g)
    }
    fun paintAxis(g: Graphics){
        plane?.let { plane ->
            val x0 = Converter.xCrt2Scr(0.0, plane)
            val y0 = Converter.yCrt2Scr(0.0, plane)
            g.drawLine(0, y0, plane.width, y0)
            g.drawLine(x0, 0, x0, plane.height)
        }
    }
    fun paintLabels(g: Graphics){

    }
    fun paintTicks(g: Graphics){

    }
}