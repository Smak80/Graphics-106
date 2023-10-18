package ru.smak.painting.gui

import ru.smak.drawing.Plane
import ru.smak.painting.gui.drawing.CartesianPainter
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import javax.swing.BorderFactory
import javax.swing.GroupLayout
import javax.swing.GroupLayout.PREFERRED_SIZE
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSpinner
import javax.swing.border.EtchedBorder

class Window : JFrame(){

    private val mainPanel: JPanel
    private val controlPanel: JPanel
    private val lblXMin: JLabel
    private val spXMin: JSpinner
    private val lblXMax: JLabel
    private val spXMax: JSpinner
    private val lblYMin: JLabel
    private val spYMin: JSpinner
    private val lblYMax: JLabel
    private val spYMax: JSpinner


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

        controlPanel = JPanel().apply {
            border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)
        }

        layout = GroupLayout(contentPane).apply {
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addComponent(mainPanel)
                    .addGap(8)
                    .addComponent(controlPanel, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                    .addGap(8)
            )
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addGroup(createParallelGroup()
                        .addComponent(mainPanel)
                        .addComponent(controlPanel)
                    )
                    .addGap(8)
            )
        }
        pack()
        cp.plane = Plane(-3.0, 6.0, -4.0, 3.0, mainPanel.width, mainPanel.height)
    }
}