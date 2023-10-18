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
import javax.swing.SpinnerNumberModel
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

    private val mdlXMin: SpinnerNumberModel
    private val mdlXMax: SpinnerNumberModel
    private val mdlYMin: SpinnerNumberModel
    private val mdlYMax: SpinnerNumberModel

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

        lblXMin = JLabel("XMin: ")
        lblXMax = JLabel("XMax: ")
        lblYMin = JLabel("YMin: ")
        lblYMax = JLabel("YMax: ")

        mdlXMin = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        mdlXMax = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        mdlYMin = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        mdlYMax = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)

        mdlXMin.addChangeListener{
            mdlXMax.minimum = mdlXMin.value as Double + 0.1
            cp.plane?.xMin = mdlXMin.value as Double
            mainPanel.repaint()
        }
        mdlXMax.addChangeListener {
            mdlXMin.maximum = mdlXMax.value as Double - 0.1
            cp.plane?.xMax = mdlXMax.value as Double
            mainPanel.repaint()
        }
        mdlYMin.addChangeListener{
            mdlYMax.minimum = mdlYMin.value as Double + 0.1
            cp.plane?.yMin = mdlYMin.value as Double
            mainPanel.repaint()
        }
        mdlYMax.addChangeListener {
            mdlYMin.maximum = mdlYMax.value as Double - 0.1
            cp.plane?.yMax = mdlYMax.value as Double
            mainPanel.repaint()
        }

        spXMin = JSpinner(mdlXMin)
        spXMax = JSpinner(mdlXMax)
        spYMin = JSpinner(mdlYMin)
        spYMax = JSpinner(mdlYMax)

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
        controlPanel.layout = GroupLayout(controlPanel).apply {
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addGroup( createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblXMin, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                        .addComponent(spXMin, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                        .addComponent(lblXMax, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                        .addComponent(spXMax, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                    )
                    .addGap(8)
                    .addGroup( createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblYMin, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                        .addComponent(spYMin, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                        .addComponent(lblYMax, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                        .addComponent(spYMax, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                    )
                    .addGap(8)
            )
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addGroup(createParallelGroup()
                        .addGroup(createSequentialGroup()
                            .addComponent(lblXMin, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                            .addComponent(spXMin, 75, PREFERRED_SIZE, PREFERRED_SIZE)
                            .addGap(8)
                            .addComponent(lblXMax, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                            .addComponent(spXMax, 75, PREFERRED_SIZE, PREFERRED_SIZE)
                        )
                        .addGroup(createSequentialGroup()
                            .addComponent(lblYMin, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                            .addComponent(spYMin, 75, PREFERRED_SIZE, PREFERRED_SIZE)
                            .addGap(8)
                            .addComponent(lblYMax, PREFERRED_SIZE, PREFERRED_SIZE, PREFERRED_SIZE)
                            .addComponent(spYMax, 75, PREFERRED_SIZE, PREFERRED_SIZE)
                        )
                    )
                    .addGap(8, 8, Int.MAX_VALUE)

            )
        }
        pack()
        cp.plane = Plane(mdlXMin.value as Double, mdlXMax.value as Double, mdlYMin.value as Double, mdlYMax.value as Double, mainPanel.width, mainPanel.height)
    }
}