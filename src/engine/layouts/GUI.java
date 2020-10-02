package engine.layouts;

import engine.component.Camera;
import engine.util.Vec3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JPanel
{
    private JPanel root;
    private JPanel panel;
    private JButton upButton;
    private JButton downButton;
    private JButton leftMoveButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton forwardButton;
    private JButton rightMoveButton;
    private JButton backWardButton;

    public GUI(Camera camera)
    {
        add(root);
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                camera.move(new Vec3(0,-20,0));
                camera.see();
            }
        });
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                camera.move(new Vec3(0,20,0));
                camera.see();
            }
        });

        rightMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                camera.rotate(new Vec3(0.1f,0,0));
                camera.see();
            }
        });
        leftMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                camera.rotate(new Vec3(-0.1f,0,0));
                camera.see();
            }
        });



    }

    private JButton createButton(String text, Color background)
    {
        JButton button = new JButton( text );
        button.setBackground( background );
        return button;
    }
}
