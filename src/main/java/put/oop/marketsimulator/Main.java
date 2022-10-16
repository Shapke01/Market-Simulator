/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator;

import put.oop.marketsimulator.Additional.World;
import put.oop.marketsimulator.GUIs.GUI;

/**
 *
 * @author roboc
 */
public class Main {
    public static void main(String[] args) {
        World world=new World();
        new GUI(world);
        world.start();
    }
}
