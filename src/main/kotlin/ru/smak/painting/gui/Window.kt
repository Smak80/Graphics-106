package ru.smak.painting.gui

import ru.smak.drawing.Plane
import ru.smak.painting.gui.drawing.CartesianPainter
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import javax.swing.GroupLayout
import javax.swing.JFrame
import javax.swing.JPanel

class Window : JFrame(){

    var mainPanel: JPanel

    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(500, 450)
        val cp = CartesianPainter()
        mainPanel = object: JPanel() {
            override fun paint(g: Graphics?) {
                super.paint(g)
                g?.let{ cp.paint(it) }
            }
        }.apply {
            background = Color.WHITE
        }
        mainPanel.addComponentListener(object: ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                cp.plane?.width = mainPanel.width
                cp.plane?.height = mainPanel.height
                mainPanel.repaint()
            }
        })

        layout = GroupLayout(contentPane).apply {
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addComponent(mainPanel)
                    .addGap(8)
            )
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addComponent(mainPanel)
                    .addGap(8)
            )
        }
        pack()
        cp.plane = Plane(-3.0, 6.0, -4.0, 3.0, mainPanel.width, mainPanel.height)
    }
}