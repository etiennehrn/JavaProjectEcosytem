package com.example.map;

import javafx.scene.input.MouseEvent;

public class MouseHandler {
    public static void handleMouseInteraction(MouseEvent event, Player player, MapCreation map, int tileSize) {
        map.getRoot().requestFocus(); // Retour au nœud principal

        double adjustedY = event.getY() - tileSize;

        if (adjustedY >= 0) {
            int col = Math.min(Math.max(player.getX() - player.getVisionRange(), 0), map.getCols() - player.getVisionRange())
                    + (int) (event.getX() / tileSize);
            int row = Math.min(Math.max(player.getY() - player.getVisionRange(), 0), map.getCols() - player.getVisionRange())
                    + (int) (adjustedY / tileSize);

            if (row >= 0 && row < map.getRows() && col >= 0 && col < map.getCols()) {
                map.SetBaseType(row, col);
                map.SetElement(row, col);
                map.Save();
            }
        }
    }
}
